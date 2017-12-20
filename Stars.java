import java.util.Arrays;
import java.util.Scanner;

class Stars
{
    public static void main(String[] args)
    {
        System.out.print("Input: How many floors?: ");
        Scanner scanner = new Scanner(System.in);
        
        try
        {
            printStars(scanner.nextInt());
        }
        catch (Exception e)
        {
            System.out.println("Exception thrown ...");
        }
        finally
        {
            scanner.close();
        }
    }
    
    /**
     * @brief Prints stars according to number of floors, n
     * @param n Number of floors
     * @throws Exception
     */
    private static void printStars(final int n) throws Exception
    {
        if (n < 0)
        {
            throw new Exception("Negative value entered.");
        }
        else
        {
            int s = n - 1;
            int a = 1;
            
            for (int i = 0; i < n; i ++)
            {
                char[] spaces = new char[s];
                Arrays.fill(spaces, ' ');
                
                char[] asterisks = new char[a];
                Arrays.fill(asterisks, '*');
                
                System.out.println(new String(spaces) + new String(asterisks));
                
                s = s - 1;
                a = a + 2;
            }
        }
    }
}
