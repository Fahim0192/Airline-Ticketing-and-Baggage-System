package utilities;

/*
 * Class:			SeedMenu
 * Description:		Seed data menu enum
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public enum SeedMenu
{
	SA("Seed All bookings"), SE("Seed only Economy Bookings"), SB("Seed only Business Bookings");
	private SeedMenu(String option)
	{
		this.option = option;
	}

	/**
	 * Option for menu
	 */
	private String option;

	/**
	 * Get the menu option
	 * 
	 * @return Menu option
	 */
	public String getOption()
	{
		return option;
	}

}
