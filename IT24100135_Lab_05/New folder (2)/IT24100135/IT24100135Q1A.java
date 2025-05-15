import java.util.Scanner;

public class IT24100135Q1A
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		String Array1[] = new String[5];
		int Array2[] = new int[5];
		char Array3[] = new char[5];
		float Array4[] = new float[5];

			for(int j = 0; j < 5; j++ )
			{
				System.out.print("Enter Customer "+ (j+1) +" Name : ");
				Array1[j] = input.next();
				
				System.out.print("Enter Account Number : ");
				Array2[j] = input.nextInt();
				
				System.out.print("Enter Transaction Type : ");
				Array3[j] = input.next().charAt(0);
	
				System.out.print("Enter Amount : ");
				Array4[j] = input.nextFloat();

				System.out.print("\n");
			}

		System.out.print("\n");

		int count1 = 0;
		int count2 = 0;
		float deposit = 0;
		float withdrawal = 0;
		float maxdeposit = 0;
		float maxwithdrawal = 0;
		String maxdepositcustomer = "";
		String maxwithdrawalcustomer = "";
		for(int i = 0; i < Array3.length; i++)
		{
			if(Array3[i] == 'D' || Array3[i] == 'd' )
			{
				count1++;
				deposit = deposit + Array4[i];
				if(maxdeposit < Array4[i])
				{
					maxdeposit = Array4[i];
					maxdepositcustomer = Array1[i];
					
				}
			} 
			else 
			{
				count2++;
				withdrawal = withdrawal + Array4[i];
				if(maxwithdrawal  < Array4[i])
				{
					maxwithdrawal = Array4[i];
					maxwithdrawalcustomer = Array1[i];
				}
			}
		}
			
			System.out.println("Total Deposit Amount is : "+ deposit);
			System.out.println("Total Withdrawal Amount is :"+ withdrawal);
			System.out.println("Count of All Deposit Transactions is :"+ count1);
			System.out.println("Count of All Withdrawal Transactions is :"+ count2);
			System.out.println("Maximum Deposit Amount Customer Name is :"+ maxdepositcustomer );
			System.out.println("Maximum Withdrawal Amount Customer Name is :"+ maxwithdrawalcustomer);


	}
} 