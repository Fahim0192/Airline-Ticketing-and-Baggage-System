package airline;

import application.AirlineSystem;
import application.SimbaAirways;
import exceptions.InvalidId;
import utilities.DateTime;
import utilities.SeedMenu;

/*
 * Class:			Facade
 * Description:		The class is used to separate the menu from the actual system. 
 * Author:			[Fahim Tahmeed] - [s3680881]
 * NOTE: 			You will need to modify the method bodies in this class
 * 					to forward calls to the application class.
 * 					You will need to make sure the return statements are also
 * 					updated to return the correct data back to the menu.
 */
public class Facade implements AirlineSystem
{

	SimbaAirways system = new SimbaAirways();

	@Override
	public String addEconomySeat(String id, String rowNumber, String seatNumber)
	{
		return system.addEconomySeat(id, rowNumber, seatNumber);
	}

	@Override
	public String addBusinessSeat(String id, String rowNumber, String seatNumber)
	{
		return system.addBusinessSeat(id, rowNumber, seatNumber);
	}

	@Override
	public String book(String flightId, String seatNumber, String firstName, String lastName)
	{
		return system.book(flightId, seatNumber, firstName, lastName);
	}

	@Override
	public String checkBaggage(String flightId, String seatNumber, String lastName, double weight)
	{
		return system.checkInBaggage(flightId, seatNumber, lastName, weight);
	}

	@Override
	public String collectBaggage(String flightId, String seatNumber, String lastName, DateTime dateOfCollection)
	{
		return system.checkoutBagage(flightId, seatNumber, lastName, dateOfCollection);
	}

	public Booking getItemById(String flightId, String seatNumber)
	{
		Booking booking = system.getBookingById(flightId);
		if (null != booking)
		{
			if (booking.getRowNumner().charAt(0) == seatNumber.charAt(0)
					&& booking.getSeatNumber().charAt(0) == seatNumber.charAt(1))
			{
				return booking;
			}
		}
		return null;
	}

	@Override
	public String bookEconomy(String id, String firstName, String lastName, int age, boolean exitRow)
	{
		return "";
	}

	@Override
	public String displayAllBookings()
	{
		return system.getAllBookings();
	}

	@Override
	public String bookBusiness(String id, String firstName, String lastName, int age, String platforms)
	{
		return "";
	}

	@Override
	public void seedData(SeedMenu seedMenu)
	{
		system.seedData(seedMenu);
	}

	@Override
	public void writeData()
	{
		system.writeData();
	}

	@Override
	public boolean checkIfBookingExists(String flightId, String seatNumber) throws InvalidId
	{
		return system.checkIfBookingExists(flightId, seatNumber);
	}

	public boolean checkIfBookingAvailable(String flightId)
	{
		return system.checkIfBookingAvailable(flightId);
	}

	@Override
	public String displayBooking(String id, String seatId)
	{
		return system.displayBooking(id, seatId);
	}

	@Override
	public String displayHistoricalBaggage(String id, String seatId)
	{
		return system.displayHistoricalBaggage(id, seatId);
	}

	@Override
	public String isValidId(String id, String seatId) throws InvalidId
	{
		return system.isValidId(id, seatId);
	}

	public String validate(String id, String rowNumber, String seatNumber) throws InvalidId
	{
		return system.validate(id, rowNumber, seatNumber);
	}

	public boolean idExists(String id)
	{
		return system.idExists(id);
	}

}
