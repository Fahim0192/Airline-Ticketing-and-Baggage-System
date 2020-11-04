package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import airline.Baggage;
import airline.Booking;
import airline.Business;
import airline.Economy;
import exceptions.InvalidDate;
import exceptions.InvalidId;
import utilities.BookingType;
import utilities.Constant;
import utilities.DateTime;
import utilities.SeedMenu;

/*
 * Class:			AirlineSystem
 * Description:		The airline system manager the manages the 
 *              	collection of data. 
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class SimbaAirways
{
	/**
	 * Array of airways booking
	 */
	private Booking[] bookings;
	/**
	 * Number of bookings
	 */
	private int numberOfBooking;

	/**
	 * Constructor used initialize the airways
	 */
	public SimbaAirways()
	{
		bookings = new Booking[100];
		readBookingData();
	}

	/**
	 * Get the booking by Id
	 * 
	 * @param id
	 *            - Booking id
	 * @return Booking details
	 */
	public Booking getBookingById(String id)
	{
		Booking booking = null;
		for (Booking booking1 : bookings)
		{
			if (null != booking1 && booking1.getId().substring(1).equals(id))
			{
				booking = booking1;
				break;
			}

		}
		return booking;
	}

	/**
	 * Add economy booking
	 * 
	 * @param id
	 *            - Id of the booking
	 * @param rowNumber
	 *            - Row number of booking
	 * @param seatNumber
	 *            - Seat number of booking
	 * @return Success message
	 */
	public String addEconomySeat(String id, String rowNumber, String seatNumber)
	{
		return addBooking(id, rowNumber, seatNumber, BookingType.ECONOMY);

	}

	/**
	 * Add business booking
	 * 
	 * @param id
	 *            - Id of the booking
	 * @param rowNumber
	 *            - Row number of booking
	 * @param seatNumber
	 *            - Seat number of booking
	 * @return Success message
	 */

	public String addBusinessSeat(String id, String rowNumber, String seatNumber)
	{
		return addBooking(id, rowNumber, seatNumber, BookingType.BUSINESS);
	}

	/**
	 * Book the seat
	 * 
	 * @param flightId
	 *            - Flight id
	 * @param seatNumber
	 *            - Seat number
	 * @param firstName
	 *            - First name
	 * @param lastName
	 *            - Last name
	 * @return Message of booking
	 */
	public String book(String flightId, String seatNumber, String firstName, String lastName)
	{
		String message;
		Booking booking = getBookingById(flightId);

		if (booking == null)
			return "Error: The booking could not be completed.";

		try
		{
			message = booking.book(firstName, lastName);
			if (message.equals(booking.toString()))
			{
				return booking.getBookingConfirmation();

			}
		}
		catch (InvalidId e)
		{
			message = e.getMessage();
		}

		return message;
	}

	/**
	 * Check in the bags
	 * 
	 * @param flightId
	 *            - Flight id of booking
	 * @param seatNumber
	 *            - Seat number of booking
	 * @param lastName
	 *            - Last name
	 * @param weight
	 *            - Weight of baggage
	 * @return Baggage check-in message
	 */
	public String checkInBaggage(String flightId, String seatNumber, String lastName, double weight)
	{
		String message = "";
		Booking booking = getBookingById(flightId);

		if (booking == null)
			return "Error: The baggage could not be checked in.";

		try
		{
			message = booking.checkInBag(lastName, weight, true);
			if (message.equals(booking.toString()))
				message = String.format("\n\n%s\n", booking.getDetails());
		}
		catch (InvalidId e)
		{
			message = e.getMessage();
		}

		return message;
	}

	/**
	 * Collect the baggage
	 * 
	 * @param flightId
	 *            - Flight id
	 * @param seatNumber
	 *            - Seat number
	 * @param lastName
	 *            - Last name
	 * @param dateOfCollection
	 * @return Checkout message
	 */
	public String checkoutBagage(String flightId, String seatNumber, String lastName, DateTime dateOfCollection)
	{
		String message = "";
		Booking booking = getBookingById(flightId);

		if (booking == null)
			return "Error: The baggage could not be collected.";

		try
		{
			message = booking.collectBags(dateOfCollection);
		}
		catch (InvalidDate e)
		{
			message = e.getMessage();
		}
		if (message.equals(booking.toString()))
			message = String.format("\n\n%s\n", booking.getDetails());

		// clear names to make it available to book
		booking.clearBooking();

		return message;
	}

	/**
	 * Get historical checked out baggage details
	 * 
	 * @param id
	 *            - Id of booking
	 * @return
	 * @throws InvalidId
	 */
	public String displayHistoricalBaggage(String id, String seatId)
	{

		Booking booking = getBookingById(id);
		if (null == booking)
			return "Error: Booking with given id does not exist";

		if (!booking.getSeatNumber().equals(seatId))
		{
			return "Error:Seat number is not matching with booking";
		}
		return booking.getCollectedBagDetails();
	}

	/**
	 * Get all bookings
	 * 
	 * @return Formated string for bookings
	 */
	public String getAllBookings()
	{
		String result = "";

		if (this.numberOfBooking > 0)
		{
			result = "========================================================================\n";
			for (Booking booking : bookings)
			{
				if (null != booking)
				{
					result += booking.getDetails();
					result += "\n========================================================================\n";
				}

			}
		}

		return result;
	}

	/**
	 * Seed add data as per seed menu
	 * 
	 * @param seedMenu
	 *            - Seed Menu
	 */
	public void seedData(SeedMenu seedMenu)
	{
		switch (seedMenu)
		{

		case SA:
			seedAll();
			break;
		case SE:
			seedEconomyBooking();
			break;
		case SB:
			seedBusinessBooking();
			break;

		default:
			break;
		}
	}

	/**
	 * Seed all data
	 */
	private void seedAll()
	{
		seedEconomyBooking();
		seedBusinessBooking();

	}

	/**
	 * Seed booking data
	 */
	private void seedBusinessBooking()
	{
		// 7 hard coded bookings
		addBusinessSeat("BJQ1", "D", "1");
		addBusinessSeat("BJQ2", "F", "2");
		addBusinessSeat("BJQ3", "A", "1");
		addBusinessSeat("BJQ4", "B", "3");
		addBusinessSeat("BJQ5", "G", "4");
		addBusinessSeat("BJQ6", "C", "2");
		addBusinessSeat("BJQ7", "B", "6");
		String message = "";
		for (Booking booking : bookings)
		{
			if (booking instanceof Business)
			{
				switch (booking.getId())
				{
				case "BJQ2":
					((Business) booking).setMeal(Constant.regularMeal);
					try
					{
						message = booking.book("Donald", "Watson");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "BJQ3":
					((Business) booking).setMeal(Constant.vegetarianMeal);
					try
					{
						message = booking.book("Ricky", "Ponting");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Ponting", 2, false);
						booking.checkInBag("Ponting", 3, false);
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "BJQ4":
					((Business) booking).setMeal(Constant.vegetarianMeal);
					try
					{
						message = booking.book("Shen", "Warn");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Warn", 5, false);
						booking.checkInBag("Warn", 6, false);
						booking.checkInBag("Warn", 10, false);
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "BJQ5":
					((Business) booking).setMeal(Constant.vegetarianMeal);
					try
					{
						message = booking.book("Steve", "Waugh");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Waugh", 4, false);
						booking.checkInBag("Waugh", 5, false);
						booking.checkInBag("Waugh", 6, false);
						try
						{
							booking.collectBags(new DateTime(1));
							booking.clearBooking();
						}
						catch (InvalidDate e)
						{
							System.out.println(e.getMessage());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "BJQ6":
					((Business) booking).setHasLimosinePickUp(true);
					((Business) booking).setMeal(Constant.regularMeal);
					try
					{
						message = booking.book("Mark", "Clark");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "BJQ7":
					((Business) booking).setHasLimosinePickUp(false);
					((Business) booking).setMeal(Constant.vegetarianMeal);
					try
					{
						message = booking.book("Nick", "Johanson");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				default:
					break;
				}
			}
		}

	}

	private void seedEconomyBooking()
	{
		// 5 hard coded bookings
		addEconomySeat("EFQ1", "A", "1"); // booking that has not been
											// booked
		addEconomySeat("EFQ2", "A", "2");
		addEconomySeat("EFQ3", "D", "3");
		addEconomySeat("EFQ4", "H", "4");
		addEconomySeat("EFQ5", "A", "5");
		String message = "";
		for (Booking booking : bookings)
		{
			if (booking instanceof Economy)
			{
				switch (booking.getId())
				{
				case "EFQ2":
					// booking with no checked luggage
					try
					{
						message = booking.book("Henry", "Elliot");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "EFQ3":
					// booking with 2 checked luggage
					try
					{
						message = booking.book("John", "Smith");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Smith", 2, false);
						booking.checkInBag("Smith", 3, false);
						break;
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

				case "EFQ4":
					// booking with 3 checked luggage, total weight is more than
					// allowed
					// total weight
					try
					{
						message = bookings[3].book("Robert", "Williams");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Williams", 5, false);
						booking.checkInBag("Williams", 7, false);
						booking.checkInBag("Williams", 10, false);
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;
				case "EFQ5":
					// booking with 3 checked luggage and then collected
					try
					{
						message = bookings[4].book("Liza", "Lopez");
						if (!message.equals(booking.toString()))
						{
							System.out.println(message);
						}
						else
						{
							System.out.println(booking.getBookingConfirmation());
						}
						booking.checkInBag("Lopez", 4, false);
						booking.checkInBag("Lopez", 5, false);
						booking.checkInBag("Lopez", 6, false);
						try
						{
							message = bookings[4].collectBags(new DateTime(1));
							booking.clearBooking();
							System.out.println(message);
						}
						catch (InvalidDate e)
						{
							System.out.println(e.getMessage());
						}
					}
					catch (InvalidId e)
					{
						System.out.println(e.getMessage());
					}

					break;

				default:
					break;
				}
			}
		}

	}

	public boolean checkIfBookingExists(String flightId, String seatNumber) throws InvalidId
	{
		// validate flightId and seatNumber
		String error = isValidId(flightId, seatNumber);

		// if any validation error, return false
		if (error.length() > 0)
			return false;

		Booking booking = getBookingById(flightId);
		if (booking == null)
			return false;

		return (booking.getRowNumner().charAt(0) == seatNumber.charAt(0)
				&& booking.getSeatNumber().charAt(0) == seatNumber.charAt(1));
	}

	public boolean checkIfBookingAvailable(String flightId)
	{
		Booking booking = getBookingById(flightId);
		if (booking == null)
		{
			return false;
		}

		return booking.isAvailable();
	}

	public String isValidId(String id, String seatId) throws InvalidId
	{
		if (id.length() != 3)
			throw new InvalidId("Error: Id must be 3 characters.");

		if (!(seatId.charAt(0) >= 'A' && seatId.charAt(0) <= 'I'))
			throw new InvalidId("Error: Row number must be between A - I");

		int sNo = Integer.parseInt(seatId.substring(1));
		if (sNo < 1 || sNo > 9)
			throw new InvalidId("Error: Seat number must be between 1 - 9");

		return "";
	}

	public String validate(String id, String rowNumber, String seatNumber) throws InvalidId
	{
		if (id.length() != 3)
			throw new InvalidId("Error: Id must be 3 characters.");

		if (!(rowNumber.compareTo("A") >= 0 && rowNumber.compareTo("I") <= 0))
			throw new InvalidId("Error: Row number must be between A - I");

		int sNo = Integer.parseInt(seatNumber);
		if (sNo < 1 || sNo > 9)
		{
			throw new InvalidId("Error: Seat number must be between 1 - 9");
		}
		return "";
	}

	public boolean idExists(String id)
	{
		return getBookingById(id) != null;
	}

	public String getBookingClass(String flightId, String seatNumber)
	{
		Booking booking = getBookingById(flightId);

		return booking.getRowNumner().charAt(0) == seatNumber.charAt(0)
				&& booking.getSeatNumber().charAt(0) == seatNumber.charAt(1) ? String.valueOf(booking.getId().charAt(0))
						: "";

	}

	/**
	 * Write data to file
	 */
	public void writeData()
	{
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter("bookings.txt"));
			for (Booking booking : bookings)
			{
				if (null != booking)
				{
					booking.writeoFileT(bw);
				}
			}
		}
		catch (IOException e)
		{
		}
		finally
		{
			if (null != bw)
			{
				try
				{
					bw.close();
				}
				catch (IOException e)
				{
				}
			}
		}
	}

	/**
	 * Read booking data
	 */
	private void readBookingData()
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("bookings.txt"));
			String line = "";
			while (null != (line = br.readLine()))
			{
				line = line.trim();
				if (!line.isEmpty())
				{
					String[] parts = line.split("#");
					String bookingLine = parts[0];
					String bags = "";
					if (parts.length == 2)
					{
						bags = parts[1];
					}
					String[] bookingStr = bookingLine.split(":");

					if (bookingStr[0].charAt(0) == 'B')
					{
						String id = bookingStr[0].trim();
						String rowNumber = bookingStr[1].trim();
						String seatNumber = bookingStr[2].trim();
						String firstName = bookingStr[3].trim();
						String lastName = bookingStr[4].trim();
						String isLimosinePickUp = bookingStr[5].trim();
						String meal = bookingStr[6].trim();

						Booking booking = new Business(id, rowNumber, seatNumber, 2200);
						if (!"null".equals(firstName) && !"null".equals(lastName))
						{
							try
							{
								booking.book(firstName, lastName);
								((Business) booking)
										.setHasLimosinePickUp("YES".equals(isLimosinePickUp) ? true : false);
								if (!"null".equals(meal))
								{
									((Business) booking).setMeal(meal);
								}
								if (!bags.isEmpty())
								{
									String[] baggages = bags.split("@");
									for (String bagsData : baggages)
									{
										String[] bagData = bagsData.split(":");
										String dateTime = bagData[2];
										String day = dateTime.substring(0, 2);
										String month = dateTime.substring(2, 4);
										String year = dateTime.substring(4, dateTime.length());
										DateTime checkedInDate = new DateTime(Integer.parseInt(month),
												Integer.parseInt(year), Integer.parseInt(day));
										Baggage baggage = new Baggage(bagData[0], bagData[1],
												Double.parseDouble(bagData[2]), checkedInDate);
										booking.checkInBag(baggage);
									}
								}
							}
							catch (InvalidId e)
							{
								System.out.println(e.getMessage());
							}
						}

						else
						{
							((Business) booking).setHasLimosinePickUp("YES".equals(isLimosinePickUp) ? true : false);
							if (!"null".equals(meal))
							{
								((Business) booking).setMeal(meal);
							}
							if (!bags.isEmpty())
							{
								String[] baggages = bags.split("@");
								for (String bagsData : baggages)
								{
									String[] bagData = bagsData.split(":");
									DateTime checkedInDate = getDateTime(bagData[2]);
									Baggage baggage = new Baggage(bagData[0], bagData[1],
											Double.parseDouble(bagData[2]), checkedInDate);
									baggage.setCollectedDate(getDateTime(bagData[3]));
									booking.checkoutBags(baggage);
								}
							}
						}

						bookings[numberOfBooking] = booking;
						numberOfBooking++;
					}
					else if (bookingStr[0].charAt(0) == 'E')
					{
						String id = bookingStr[0].trim();
						String rowNumber = bookingStr[1].trim();
						String seatNumber = bookingStr[2].trim();
						String firstName = bookingStr[3].trim();
						String lastName = bookingStr[4].trim();
						Booking booking = new Economy(id, rowNumber, seatNumber, 1200);
						if (!"null".equals(firstName) && !"null".equals(lastName))
						{
							try
							{
								booking.book(firstName, lastName);
								if (!bags.isEmpty())
								{
									String[] baggages = bags.split("@");
									for (String bagsData : baggages)
									{
										String[] bagData = bagsData.split(":");
										String dateTime = bagData[2];
										String day = dateTime.substring(0, 2);
										String month = dateTime.substring(2, 4);
										String year = dateTime.substring(4, dateTime.length());
										DateTime checkedInDate = new DateTime(Integer.parseInt(month),
												Integer.parseInt(year), Integer.parseInt(day));
										Baggage baggage = new Baggage(bagData[0], bagData[1],
												Double.parseDouble(bagData[2]), checkedInDate);
										booking.checkInBag(baggage);
									}
								}
							}
							catch (InvalidId e)
							{
								System.out.println(e.getMessage());
							}
						}
						else
						{
							if (!bags.isEmpty())
							{
								String[] baggages = bags.split("@");
								for (String bagsData : baggages)
								{
									String[] bagData = bagsData.split(":");
									DateTime checkedInDate = getDateTime(bagData[3]);
									Baggage baggage = new Baggage(bagData[0], bagData[1],
											Double.parseDouble(bagData[2]), checkedInDate);
									baggage.setCollectedDate(getDateTime(bagData[3]));
									booking.checkoutBags(baggage);
								}
							}
						}
						bookings[numberOfBooking] = booking;
						numberOfBooking++;

					}

				}
			}
		}
		catch (

		IOException e)
		{
		}
		finally
		{
			if (null != br)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
				}
			}
		}

	}

	/**
	 * Get booking
	 * 
	 * @param dateTime
	 * @return
	 */
	private DateTime getDateTime(String dateTime)
	{

		String day = dateTime.substring(0, 2);
		String month = dateTime.substring(2, 4);
		String year = dateTime.substring(4, dateTime.length());
		DateTime checkedInDate = new DateTime(Integer.parseInt(month), Integer.parseInt(year), Integer.parseInt(day));
		return checkedInDate;
	}

	/**
	 * Add booking of given type
	 * 
	 * @param id
	 *            - Booking id
	 * @param rowNumber
	 *            - Booking row number
	 * @param seatNumber
	 *            - Booking seat number
	 * @param bookingType
	 *            - Booking type
	 * @return Booking message
	 */
	private String addBooking(String id, String rowNumber, String seatNumber, BookingType bookingType)
	{
		String message = "";
		switch (bookingType)
		{
		case BUSINESS:
		{
			Booking booking = new Business(id, rowNumber, seatNumber, 2200);
			bookings[numberOfBooking] = booking;
			numberOfBooking++;
			message = "New Business booking added successfully for booking id: " + id;
		}
			break;
		case ECONOMY:
		{
			Booking booking = new Economy(id, rowNumber, seatNumber, 1200);
			bookings[numberOfBooking] = booking;
			numberOfBooking++;
			message = "New Economy booking added successfully for booking id: " + id;
		}
			break;
		default:

		}
		return message;

	}

	/**
	 * Get booking by id and seat number
	 * 
	 * @param id
	 *            - Booking id
	 * @param seatId
	 *            - booking seat number
	 * @return Formated string
	 * @throws InvalidId
	 */
	public String displayBooking(String id, String seatId)
	{

		Booking booking = getBookingById(id);
		if (null == booking)
			return "Error: Booking with given id does not exist";

		if (!booking.getSeatNumber().equals(seatId))
		{
			return "Error:Seat number is not matching with booking";
		}
		return booking.getDetails();
	}

}
