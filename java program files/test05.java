import java.util.Scanner;

public class test05
{
	public static void main(String[] args)
	{
		int n;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the last number :");	
		n = input.nextInt();

		int number = 1;
		System.out.print(number);

		while ( number <= n-1 )
		{
			number = number + 1;
			System.out.print( " + " + number);
		}	
}
}