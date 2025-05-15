import java.util.Scanner;

public class Vehicle_Parking_Management_System
{
	public static void add( int no)
	{
		int num[] = new int[10];
		for(int i = 0; i <= num.length; i++)
		{
			System.out.print("Enter vehicle plate number: ");
			num[i] = input.nextint();
			System.out.print("Enter hours parked: ");
			hours[i] = input.nextInt();
			System.out.print("Vehicle added successfully.");
		}
	}
	public static void main(String[] args)
	{
			System.out.println("1. Add vehicle");
			System.out.println("2. Remove vehicle");
			System.out.println("3. Search vehicle");
			System.out.println("4. Display Summary");
			System.out.println("5. Exit");

			Scanner input = new Scanner(System.in);
			System.in.print("Enter your choice: ");
			int no = input.nextInt() ;


			if( no = 1 )
			{
				new = add(no);
			}
			else
			{
				System.out.print("Exit");
			}
 
	}
}
			









	