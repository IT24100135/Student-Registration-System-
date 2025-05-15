public class test09
{
	public static void displaysquare(int side)
	{
		for(int i = 0; i< side; i++)
		{
			for(int j = 0; j < side; j++)
			{
				System.out.print("*");
			}
			System.out.print("\n");
		}
	}
	public static void main(String[] args)
	{
		displaysquare(4);
	}
}