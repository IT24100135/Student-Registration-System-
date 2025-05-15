public class test08
{
	public static double calculateArea(double length, double width)
	{
		return length*width;
	}
	
	public static void main(String[] args)
	{
		//rectangle 1
		double area_1 = calculateArea(5.0,3.0);
		System.out.println("Area of the rectangle_1 : "+ area_1);
	}
}