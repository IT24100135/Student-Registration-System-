import java.util.Scanner;

public class Lab3Q3
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a five digit number: ");
		int number = input.nextInt();

		System.out.print("\n");
		int amount;
		amount = number/10000;
		System.out.print(amount+" ");
		number = number%10000;

		amount = number/1000;
		System.out.print(amount+" ");
		number  = number%1000;

		amount = number/100;
		System.out.print(amount+" ");
		number = number%100;

		amount = number/10;
		System.out.print(amount+" ");
		number = number%10;

		System.out.print(number);
	}
}