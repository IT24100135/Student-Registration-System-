import java.util.Scanner;

public class Sales {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int salesArray[][] = new int[3][4];


        for (int i = 0; i <salesArray.length ; i++) {
	int sum = 0;
	System.out.println("Product "+(i + 1));
            for (int j = 0; j < salesArray[i].length; j++) {
                System.out.print("Enter Number of Sales from Salesperson "+(j + 1)+" :");
                salesArray[i][j] = scanner.nextInt();
		sum = sum + salesArray[i][j]
;            }
		System.out.print("Total Number of Sales for product "+ ( i + 1 ) +" :"+ sum);
		System.out.println("\n");
        }
	}
}
