import java.util.Scanner;

public class array1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] shirts = new char[10];

        // Prompt the user to input the sizes of the t-shirts
        System.out.println("Please enter the sizes of the t-shirts (S, M, L) for 10 customers:");
        for (int i = 0; i < shirts.length; i++) {
            System.out.print("Enter size for customer " + (i + 1) + ": ");
            shirts[i] = scanner.next().charAt(0);
        }

        // Count the quantity of each t-shirt size
        int smallCount = 0, mediumCount = 0, largeCount = 0;
        for (char size : shirts) {
            switch (size) {
                case 'S':
                case 's':
                    smallCount++;
                    break;
                case 'M':
                case 'm':
                    mediumCount++;
                    break;
                case 'L':
                case 'l':
                    largeCount++;
                    break;
                default:
                    System.out.println("Invalid size entered: " + size);
            }
        }

        // Display the quantity of each t-shirt size
        System.out.println("\nQuantity of each t-shirt size:");
        System.out.println("Small (S): " + smallCount);
        System.out.println("Medium (M): " + mediumCount);
        System.out.println("Large (L): " + largeCount);
    }
}
