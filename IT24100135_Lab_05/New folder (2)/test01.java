import java.util.Scanner;

public class test01
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int number[] = new int[5];

		for(int i = 0; i < number.length; i++)
		{
			number[i] = 0;
		}
		
		System.out.println("Index\tValue");
		for(int i = 0; i < number.length; i++)
		{
			System.out.println(i+"\t"+ number[i]);
		}
	}
} 
		
		