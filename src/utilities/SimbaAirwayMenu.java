package utilities;

/*
 * Class:			SimbaAirwayMenu
 * Description:		Simba Airways menu enum
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public enum SimbaAirwayMenu
{
	AB("Add Booking"), BS("Book Seat"), CB("Checkin Baggage"), PB("Pick up Baggage"), DB("Display Booking Details"), DA(
			"Display ALL Bookings"), HB("Display Historical Baggage"), SD("Seed Data"), EX("Exit Program");
	private SimbaAirwayMenu(String option)
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
