import java.util.Scanner;

public class Tute_6_Q_1-A
{
	public static void main(String[] args)
	{
		int subject_01,subject_02,subject_03,subject_04;
		Scanner input = new Scanner(System.in);

		System.out.print("Enter marks: ");
			
		subject_01 = input.nextInt();

		subject_02 = input.nextInt();
	
		subject_03 = input.nextInt();

		subject_04 = input.nextInt();

		double average = (subject_01 + subject_02 + subject_03 + subject_04)/4;

		System.out.print("Grade is : ");

		if(average > 100 || averge <0 )
		{
			System.out.print("Error");
		else if(average >=75)
		{
			System.out.print("Distinction");
		}
		else if(average >= 50)
		{
			System.out.print("credit");
		}
		else (average >= 49)
		{
			System.out.print("Fail");
		}
	}
}