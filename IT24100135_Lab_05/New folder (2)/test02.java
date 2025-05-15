import java.util.Scanner;

public class test02
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int  number[] = new int[5];
		
		for(int i = 0; i <number.length ; i++)
		{
			System.out.print("Enter Number "+ (i+1) + " :");
			number[i] = input.nextInt();
		}
		
		System.out.println("Index\tValue");
		
		for(int i = 0; i <number.length; i++)
		{
			System.out.println((i+1)+"\t"+number[i]);
		}
	}
}