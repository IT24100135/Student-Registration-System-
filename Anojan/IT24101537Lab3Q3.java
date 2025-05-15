import java.util.Scanner;

public class IT24101537Lab3Q3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int A[] = new int[5];
        int m = 1;


        System.out.println("Enter 5 Numbers :");
        for (int x = 0; x < A.length; x++) {
            A[x] = input.nextInt();
        }
        System.out.print("Initial list : ");
        for (int x = 0; x < A.length; x++) {
            System.out.print(A[x] + " ");
        }

        for (int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i = i - 1;

            }
            A[i + 1] = key;
            System.out.println("\nStep" + m);
            for (int x = 0; x < A.length; x++) {
                System.out.print(A[x] + " ");

            }
            m++;
        }
        System.out.println("\nsorted list : ");
        for (int x = 0; x < A.length; x++) {
            System.out.print(A[x] + " ");
        }
    }
}