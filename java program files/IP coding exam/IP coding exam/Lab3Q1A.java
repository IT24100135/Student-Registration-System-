import java.util.Scanner;

public class Lab3Q1A
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
	
		float price;
		System.out.print("Enter the price of 1kg of rice: ");
		price = input.nextFloat();

		int kilogram;
		System.out.print("Enter the number of kilograms you want to buy: ");
		kilogram = input.nextInt();

		float amount;
		amount = price * kilogram;
		System.out.println("Total amount you have to pay: "+ amount);
	}
}