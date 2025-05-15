public class ans01
{
	public static int multiply(int a, int b )
	{
		return a*b;
	}
	
	public static int add(int a, int b)
	{
		return a+b;
	}
	
	public static int square(int a)
	{
		return a*a;
	}
	
	public static void main(String[] args)
	{
		System.out.print("ans 01: ");
		int ans_01 = square(add(multiply(4,3),multiply(2,5)));
		System.out.print(ans_01);
	}
}