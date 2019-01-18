import java.util.Stack;

public class Eval
{
    private char[] tokens;
    private Stack<Double> values;
    private Stack<Character> ops;
    private Stack<String> fun;
    

    public Eval(String s)
    {
        tokens = s.toCharArray();

        // Stack for numbers
        values = new Stack<Double>();

        // Stack for operators
        ops = new Stack<Character>();

        //Stack for functions
        functions = new Stack<String>();
    }
    

    public double evaluate()
    {

        for (int i = 0; i < tokens.length; i++)
        {
            // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9' )
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && ( ( tokens[i] >= '0' && 
                	tokens[i] <= '9' ) || tokens[i] == '.' ) )
                { 
                       sbuf.append(tokens[i]);
                       i++;
                }
                values.push(Double.parseDouble(sbuf.toString()));
            }
            // Current token is e or pi, push it to stack for numbers
            else if ( tokens[i] == 'e' || tokens[i] == 'p' )
            {
                if( tokens[i] == 'e' )
                {
                    values.push( Math.E );
                    i++;
                }
                else if( tokens[i] == 'p' )
                {
                    values.push( Math.PI );
                    i += 2;
                }
            }


            // Current token is an opening parenthesis, push it to 'ops'
            else if (tokens[i] == '(')
            {
                ops.push(tokens[i]);
            }

            // Closing parenthesis encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                {
                	values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                // Remove opening parenthesis
                ops.pop();
                
                // Evaluates function of the value inside parentheses
                if(!function.empty())
                {
                	values.push(applyFun(function.pop(), values.pop()));
                }
            }

            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^' )
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                {
                	values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
            // Current token is a function, push it to 'function'
			else 
			{    
				String functionName = "";

				for( int j = i; j < i + 7; j++)
				{
					if(tokens[j] == '(' )
					{
						i = j - 1;
						break;
					}
					functionName += tokens[j];

				}
				function.push( functionName );
			}       
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        // Top of 'values' contains result, return it
        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '^') && (op2 == '*' || op2 == '/'))
            return false;
        if ((op1 == '^') && (op2 == '+' || op2 == '-'))
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    // Method to apply an operator 'op' on 'a' 
    // and 'b'. Return the result.
    public double applyOp(char op, double b, double a)
    {
        switch (op)
        {
        	case '^':
            	return Math.pow( a, b );
	        case '+':
    	        return a + b;
        	case '-':
            	return a - b;
	        case '*':
    	        return a * b;
        	case '/':
            	if (b == 0)
                	throw new
         	    	UnsupportedOperationException("Cannot divide by zero");
            return a / b;
        }
        return 0;
    }

    // Applies function to value, returns result.
    public double applyFun(String fun, double a)
    {
    	switch (fun)
    	{
    		case "abs":
    			return Math.abs(a);
            case "sqrt":
                return Math.sqrt(a);
    		case "cbrt":
    			return Math.cbrt(a);
    		case "ln":
    			return Math.log(a);
    		case "log":
    			return Math.log10(a);
    		case "sin":
    			return Math.sin(a);
            case "cos":
                return Math.cos(a);
    		case "tan":
    			return Math.tan(a);
    		case "csc":
    			return 1/Math.sin(a);
    		case "sec":
    			return 1/Math.cos(a);
    		case "cot":
    			return 1/Math.tan(a);
            case "arcsin":
                return Math.asin(a);
            case "arccos":
                return Math.acos(a);
            case "arctan":
                return Math.atan(a);
    	}
    	System.out.println( fun + "is not a function" );
    	return 0;
    }

}