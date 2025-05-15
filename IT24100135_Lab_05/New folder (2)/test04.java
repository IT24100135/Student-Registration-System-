import java.util.Scanner;

public class test04
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		char grade[] = new char[5];
		for(int i = 0; i < grade.length; i++)
		{
			System.out.print("Enter the grade of student "+(i+1)+": ");
			grade[i] = input.next().charAt(0);	
		}
		
		System.out.println("\n");
		
		for(int i = 0; i < grade.length; i++)
		{
			System.out.println("Grade of Student No. "+(i+1)+" is "+grade[i]);
		}
		
		 
	}
}