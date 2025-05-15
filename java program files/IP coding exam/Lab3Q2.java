import java.util.Scanner;

public class Lab3Q2
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter the rupee amount: ");
		int rupee;
		rupee = input.nextInt();
		
		int amount;
		amount = rupee/5000 ;
		System.out.println("5000 Notes - "+amount);
		rupee = rupee%5000;

		amount = rupee/1000 ;
		System.out.println("1000 Notes - "+amount);
		rupee = rupee%1000;

		amount  = rupee/500 ;
		System.out.println("500  Notes - "+ amount);
		rupee= rupee%500;

		amount = rupee/200 ;
		System.out.println("200 Notes - "+ amount);
		rupee = rupee%200;

		amount = rupee/100 ;
		System.out.println("100 Notes - "+ amount);
		rupee = rupee%100;

		amount = rupee/50 ;
		System.out.println("50 Notes - "+ amount);
		rupee = rupee;

		amount = rupee/20 ;
		System.out.println("20 Notes - "+ amount);
		rupee = rupee%20;

		amount = rupee/10 ;
		System.out.println("10 Coins - "+ amount);
		rupee = rupee%10;

		amount = rupee/5 ;
		System.out.println("05 Coins - "+ amount);
		rupee = rupee%5;
		
		amount = rupee/2 ;
		System.out.println("02 Coins - "+ amount);
		rupee = rupee%2;
		
		amount = rupee/1 ;
		System.out.println("01 Coins - "+ amount);
	}
}







