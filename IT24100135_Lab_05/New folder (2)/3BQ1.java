import java.util.Scanner;

public class 3BQ1
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		char ID = input.next().UpperCase().charAt(0);

		int participants ;
		float amount;
		while( ID != X )
		{
			
			if( ID == A )
			{
				System.out.print("Number of participants :");
				participants = input.nextInt();
				amount = participants*350;
				System.out.print("Vacation Total Package cost :" + amount);
				
			}
			else if( ID == B)
			{
				System.out.print("Number of participants :");
				participants = input.nextInt();
				if( paricipants >= 10 && participants < 0)
				{
					System.out.print("Invalid participants count.");
					countinue;
				}
				else if( participants <= 6 )
				{
					amount = participants*420;
				}
				else
				{
					amount = participants*420 + (participants - 6)*490;	
				}
				System.out.print("Vacation Total Package cost :" + amount);
			}
			else if( ID == C )
			{
				System.out.print("Number of participants :");
				participants = input.nextInt();
				amount_01 = participants*530;
					
				System.out.print("IF participants request for natural pool access( 1 mean Yes 0 mean No) :");
				int boolean = input.next.Int();
				if(boolean == 1)
				{
					amount_02 = 6000;
				}
				System.out.print("IF participants request for camping facility( 1 mean Yes 0 mean No) :");
				int boolean = input.nextInt();
				if(boolean == 1)
				{
					amount_03 = 16000;
				}
			}
			else if( ID == D )
			{
				participants = input.nextInt();
				amount = participants*670;
				System.out.print("Number of participants requested for photograph :");
				participants = input.nextInt();
				amount = participants*1200;
				float totalcharge = (12/100.0)*amount;
				amount = amount + amount;

			}
		
			}	
					
			} 