import java.util.Scanner;

public class test15
{
	public static void main(String[] args)
	{
		Scanner input =new Scanner(System.in);
		char let;
		System.out.print("Enter letter:");
		let = input.next().charAt(0);
		++let;
		System.out.print(let);
	}
}