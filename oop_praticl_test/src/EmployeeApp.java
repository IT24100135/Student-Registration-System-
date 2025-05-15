import java.sql.SQLOutput;
import java.util.Scanner;
class DEPARTMENT{
    private String departmentName;


    public DEPARTMENT(String departmentName){
        this.departmentName = departmentName;
    }

    public void DISPLAYDEPARTMENTDETAILS(){
        System.out.println("Department Name :" + departmentName );
    }
}

class EMPLOYEE{
    private String employeeId;
    private String employeeName;
    private double Salary;
    private DEPARTMENT department;

    public void setEmployeeId(String employeeId){
        this.employeeId=employeeId;
    }

    public void setEmployeeName(String employeeName){
        this.employeeName = employeeName;
    }

    public void setSalary(double Salary){
       if(Salary > 0){
        this.Salary = Salary;
       }
       else
       {
           System.out.println("Salary is invalid");
       }
    }

    public String getEmployeeId(){
        return employeeId;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public double getSalary(){
        return Salary;
    }

    public boolean ISELIGIBLEFORBOUNS(){
        if(Salary >= 100000){
            return true;
        }
        else
        {
            return false;
        }
    }

    public void DISPLAYEMPLOYEEDETAILS()
    {
        System.out.println("Employee ID :" + employeeId);
        System.out.println("Employee Name :"+ employeeName);
        System.out.println("Salary :"+ Salary);
        department.DISPLAYDEPARTMENTDETAILS();

    }

    public class EmployeeApp {
        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            DEPARTMENT D01 = new DEPARTMENT();
            EMPLOYEE E01 = new EMPLOYEE();
        }
    }

