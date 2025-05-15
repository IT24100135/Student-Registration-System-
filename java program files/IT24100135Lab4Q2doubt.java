import java.util.Scanner;
public class  IT24100135Lab4Q2doubt{
public static void main(String[] args){

int Exam, lab_submission;
double Examp, lab_submissionp, Final_Exam_Mark; 

Scanner input = new Scanner(System.in);

System.out.print("Please enter exam marks (out of 100) :");
Exam = input.nextInt();

if( Exam > 100 || Exam < 0 )
{
	System.out.print("Invalid input for exam marks. Terminating program."); 
	
}

else{
System.out.print("Please enter lab submission marks (out of 100) :");
lab_submission = input.nextInt();

if( lab_submission > 100 || lab_submission < 0 )
{	System.out.print("Invalid input for lab submission marks. Terminating program."); 
	return;
}

System.out.print("Please enter the percentage given for the exam :");
Examp = input.nextDouble();

System.out.print("Please enter the percentage given for the lab submission :");
lab_submissionp = input.nextDouble();

if( Examp + lab_submissionp >100 || Examp + lab_submissionp <0 )
{
	System.out.print("The percentages must add up to 100. Terminating program."); 
	return;
}

Final_Exam_Mark = Exam*(Examp/100) + lab_submission*(lab_submissionp/100);

System.out.print("\n");

System.out.print("Final Exam Mark is :"+ Final_Exam_Mark); 
}
}
}