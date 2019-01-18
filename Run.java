import java.util.Scanner;

public class Run
{
    public static void main( String [] args )
    {
        System.out.println( "Welcome to Sam's Riemann Sums calculator!" );
        Scanner sc = new Scanner( System.in );
        System.out.println( "Please enter your expression: " );
        System.out.print( "f(x) = " );
        String expression = sc.nextLine(); // Takes user input as expression
        
        System.out.println( "Lower bound?" );
        double a = sc.nextInt();
        
        System.out.println( "Upper bound?" );
        double b = sc.nextInt();
        
        System.out.println( "How many subintervals?" );
        double sub = ( b - a ) / sc.nextInt();
        double answer = 0;
        
        // For simplicity's sake, and to reduce the amount of user input needed,
        // only the Left Riemann Sum will be calculated
        for( double i = a; i < b; i += sub )
        {
            String s = Double.toString( i );
            System.out.print( "f(" + i + ") = ");

            // Replace each instance of "x" in expression with numerical value
            Eval e = new Eval( expression.replace("x", Double.toString(i)) );

            // Prints out the value of the function at each subinterval
            System.out.println( e.evaluate() );

            // Adds next slice of sum to "answer"
            answer += e.evaluate() * sub;
        }
        
        // Truncates to thousandths place
        answer = ((int) (answer * 1000)) / 1000.0;
        System.out.print( "The answer is " );
        System.out.println( answer );
    }
}