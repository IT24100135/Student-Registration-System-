import java.util.Scanner;
	
public class IT24100135Q1B
{
	public static void main(String[] args)
	{
		Scanner input= new Scanner(System.in);
		char position[][] = new char[3][3];
	
		for(int i =0; i <position.length; i++)
		{
			for(int j =0; j <position[i].length; j++)
			{
				System.out.print("Enter color for Position["+ i +","+j+"]  :" );
				position[i][j] = input.next().charAt(0);

				while(position[i][j] != 'R' && position[i][j] != 'G' && position[i][j] != 'B')
				{
					System.out.println(" Invalid color type,Please Enter a one color (R, G, or B)");
					System.out.print("Enter color for Position["+ i +","+j+"]  :");
					position[i][j] = input.next().charAt(0);
				}	
			}	
			System.out.println("\n");
		}
		
		System.out.println("LED Panel Visual Dispaly:");
		for(int i = 0; i<position.length; i++) 
		{
			for(int j = 0; j< position[i].length; j++)
			{
				System.out.print(position[i][j]+" ");	
			}
			System.out.println();
		}
		
		System.out.println("\n");
	
		System.out.print("Select a color(R, G, or B)to display all locations:");
		char color = input.next().charAt(0);


		System.out.print("Location of All Red LED Bulbs are :");
				
		for(int i = 0; i <position.length; i++)
		{
			for(int j = 0; j< position[i].length; j++)
			{
				if(color == position[i][j])
				{
					int row = i;
					int coloum = j;	
					System.out.print("[" + row + "," + coloum + "]");
				}
			}
		}
	
	}
}