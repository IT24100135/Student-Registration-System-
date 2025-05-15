import java.util.Scanner;

public class array {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int myArray[] = new int[5];

	
	for(int i = 0 ; i < myArray.length; i++)
	{
		System.out.print("Enter the number: ");
		myArray[i] = input.nextInt();
	}
	System.out.println("\n");
	for(int i = myArray.length - 1 ; i >= 0 ; i-- )
	{
		System.out.println("Number "+ (i+1) + ":" + myArray[i] );
	}	    

}
}
