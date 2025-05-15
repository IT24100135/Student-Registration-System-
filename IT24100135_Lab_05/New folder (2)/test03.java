import java.util.Scanner;

public class test03
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		
		float total = 0;
		float incame[] = new float[7];
		for(int row = 0; row <incame.length; row++)
		{
			System.out.print("Enter the daily incame :");
			incame[row] = input.nextFloat();
			
			total += incame[row];
		}
		
		System.out.println("\n");
		System.out.print("Total incame is :" + total);

	}
}