import java.util.Scanner;

public class  IT24100135Lab5Q1
{
	public static void main(String[] args)
	{
		int number01 , number02 , number03 ;
		int smallest , largest;
	
		Scanner input = new Scanner(System.in);
	
		System.out.print("Enter number01 :");
		number01 = input.nextInt();
		
		System.out.print("Enter number02 :");
		number02 = input.nextInt();

		System.out.print("Enter number03 :");
		number03 = input.nextInt();

		smallest = number01;
		largest = number01;
		
		if(number02 < smallest)
		{
			smallest = number02;
		}
		else if (number02 > largest)
		{
			largest = number02;
		}

		if(number03 < smallest)
		{
			smallest = number03;
		}
		else if(number03 > largest)
		{
			largest = number03;
		}

		System.out.print("User entered number are : " +number01+" "+number02+" "+number03 );			

		System.out.print("The Smallest number is :"+ smallest);
		System.out.print("The Largest number is :"+ largest);
}
}