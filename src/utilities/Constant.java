package utilities;

import java.text.DecimalFormat;

public class Constant
{
	public static int MAX_BAG_CHECKIN = 3;
	public static int MAX_HISTORICAL_DATA = 10;
	public static int MAX_WEIGHT = 20;
	public static DecimalFormat df = new DecimalFormat("#.##");
	{
		df.setMinimumFractionDigits(2);
	}
	public static final int MAX_OPTIONS_COUNT = 9;
	public static final String regularMeal = "Beef Burgandy,Cheese Platter,Glass of Wine";
	public static final String vegetarianMeal = "Potato and Eggplant Curry,Chocolate Mouse,Fresh Juice";
}
