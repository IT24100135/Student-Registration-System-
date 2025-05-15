import java.util.Scanner;

public class  IT24100135Lab7Q3
{
	public static void main(String[] args)
	{
		
		Scanner input = new Scanner(System.in);

		int customer = 1;
		double total_bill_amount;
		double discount = 0;
		
		while( customer <=5 )
		{
			System.out.println("customer "+ customer);
			System.out.print("Enter total bill amount: ");
			total_bill_amount = input.nextDouble();

			char mode_of_payment;
			System.out.print("Enter mode of payment ( C for cash, O for other):");
			mode_of_payment = input.next().toUpperCase().charAt(0);
			
			if(mode_of_payment == 'C')
			{
			
				discount = (5/100.0)*total_bill_amount;
				System.out.println("Discount is: "+ discount);
			}	
					
			else if( mode_of_payment == 'O')
			{
				System.out.println("No dicount applicable");
			}

			else 
			{
			}

			double amount_to_be_paid;
			amount_to_be_paid = total_bill_amount - discount;
			System.out.println("Amount to be paid:" + amount_to_be_paid);
			customer = customer +1;

			System.out.print("\n");
		}
	}
}	
			