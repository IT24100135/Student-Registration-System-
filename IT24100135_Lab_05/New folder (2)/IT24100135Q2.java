import java.util.Scanner;

public class IT24100135Q2
{
	public static double calDiscount(int time, double totalAmount)
	{
		double discount = 0;
		if(time >= 19 && time < 22 )
		{
			if(totalAmount >= 5000 )
			{
				discount = (10/100.0)*totalAmount;  
			}
			else if(totalAmount >= 2500  )
			{
				discount = (7/100.0)*totalAmount;
			}
			else
			{
				discount = 0;
			}
		}
		else if(time >= 22 && time <= 23)
		{
			if(totalAmount >= 5000 )
			{
				discount = (12/100.0)*totalAmount;  
			}
			else if(totalAmount >= 2500  )
			{
				discount = (9/100.0)*totalAmount;
			}
			else
			{
				discount = 0;
			}
		}
		return discount;
		
	}

	public static void displayGift(double finalTotal)
	{
		String gift;
		if(finalTotal >= 7000)
		{
			gift = "Packet of Milk";
		}
		else if(finalTotal >= 5000)
		{
			gift = "Pack of 6 Eggs";
		}
		else if(finalTotal >= 3000)
		{
			gift = "1Kg of Sugar";
		}
		else
		{
			gift = "No Gift";
		}
		
		System.out.print("The gift for the customer is : "+ gift);
	}
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.print("Please Enter the time :");
		int time = input.nextInt();
		if(time > 23 || time <19)
		{
			System.out.print("Error, Invalid time.");
			return;
		}
		
		System.out.print("Please Enter the total Amount :");
		double totalAmount = input.nextDouble();
	
		double discount = calDiscount(time, totalAmount);	
		double finalTotal = totalAmount - discount;
		System.out.println("The final total amount : " + finalTotal);
		
		displayGift(finalTotal);
		
	}
}