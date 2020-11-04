package application;

import java.util.Scanner;

import airline.Booking;
import airline.Business;
import airline.Facade;
import exceptions.InvalidId;
import utilities.Constant;
import utilities.DateTime;
import utilities.SeedMenu;
import utilities.SimbaAirwayMenu;

/*
 * Class:			Menu
 * Description:		The class represents a menu that the user will use to
 * 					interact with the application class via a facade. 
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class Menu
{
	private Facade facade = new Facade();
	private Scanner scanner = new Scanner(System.in);

	/**
	 * Add booking
	 */
	public void addBooking()
	{
		String id, rowNumber = "", seatNumber = "";
		String bookingClass, message, flightId;

		do
		{
			System.out.print("---- Enter data for new booking: ----\n");
			System.out.print("Enter id: ");
			id = scanner.nextLine();

			// if blank, cancel the operation
			if (id.length() == 0)
				return;

			if (facade.idExists(id))
			{
				message = "Error: This id already exists in the system.";
				System.out.println(message);
				continue;
			}

			System.out.print("Enter Row Number: ");
			rowNumber = scanner.nextLine();

			if (rowNumber.length() == 0)
				return;

			System.out.print("Enter Seat Number: ");
			seatNumber = scanner.nextLine();

			if (seatNumber.length() == 0)
				return;

			try
			{
				message = facade.validate(id, rowNumber, seatNumber);
			}
			catch (InvalidId e)
			{
				message = e.getMessage();
			}
			if (message.length() > 0)
			{
				System.out.println(message);
			}
		} while (message.length() > 0);

		do
		{
			System.out.print("Economy or Business (E/B): ");
			bookingClass = scanner.nextLine();

			if (bookingClass.length() == 0)
				return;

			bookingClass = bookingClass.toUpperCase();

		} while (!(bookingClass.equals("E") || bookingClass.equals("B")));

		flightId = bookingClass + id;

		if (bookingClass.equals("E"))
			message = facade.addEconomySeat(flightId, rowNumber, seatNumber);
		else
		{

			message = facade.addBusinessSeat(flightId, rowNumber, seatNumber);
		}

		System.out.println(message);
	}

	/**
	 * Book seat
	 */
	public void bookSeat()
	{

		String flightId, seat, firstName, lastName, message = "";

		System.out.print("---- Enter data to book a seat: ----\n");
		System.out.print("Enter flight id: ");
		flightId = scanner.nextLine();

		if (flightId.length() == 0)
			return;

		System.out.print("Enter seat: ");
		seat = scanner.nextLine();

		if (seat.length() == 0)
			return;

		try
		{
			if (!facade.checkIfBookingExists(flightId, seat))
			{
				System.out.println("Error: The booking does not exist.");
				return;
			}
		}
		catch (InvalidId e)
		{
			System.out.println(e.getMessage());
			return;
		}

		if (!facade.checkIfBookingAvailable(flightId))
		{
			System.out.println("Error: The booking is already done.");
			return;
		}

		System.out.print("Enter First Name: ");
		firstName = scanner.nextLine();

		if (firstName.length() == 0)
			return;

		System.out.print("Enter Last Name: ");
		lastName = scanner.nextLine();

		if (lastName.length() == 0)
			return;
		Booking booking = facade.getItemById(flightId, seat);
		if (null != booking)
		{
			if (booking instanceof Business)
			{
				String limosine = "";
				do
				{
					System.out.print("Would you like to book a limosine? (Y/N)");
					limosine = scanner.nextLine();

					if (limosine.length() == 0)
						return;

					limosine = limosine.toUpperCase();

				} while (!(limosine.equals("Y") || limosine.equals("N")));
				boolean isLimosinePickUp = false;
				if ("Y".equalsIgnoreCase(limosine))
				{
					isLimosinePickUp = true;
				}
				((Business) booking).setHasLimosinePickUp(isLimosinePickUp);

				String meal = "";
				do
				{
					System.out.print("Which type of meal you want(Regular/Vegetarian)? (R/V)");
					limosine = scanner.nextLine();

					if (limosine.length() == 0)
						return;

					limosine = limosine.toUpperCase();

				} while (!(limosine.equals("R") || limosine.equals("V")));
				if ("R".equalsIgnoreCase(meal))
				{
					((Business) booking).setMeal(Constant.regularMeal);
				}
				else
				{
					((Business) booking).setMeal(Constant.vegetarianMeal);
				}
			}
			message = facade.book(flightId, seat, firstName, lastName);
			System.out.println(message);
		}
		else
		{
			System.out.println("Error: The booking does not exist.");
		}

	}

	/**
	 * Check in baggage
	 */
	public void checkinBaggage()
	{

		String flightId, seat, lastName, weight, message;

		System.out.print("---- Enter data to checkin baggage: ----\n");
		System.out.print("Enter flight id: ");
		flightId = scanner.nextLine();

		if (flightId.length() == 0)
			return;

		System.out.print("Enter seat: ");
		seat = scanner.nextLine();

		if (seat.length() == 0)
			return;

		System.out.print("Enter last name: ");
		lastName = scanner.nextLine();

		if (lastName.length() == 0)
			return;

		try
		{
			if (!facade.checkIfBookingExists(flightId, seat))
			{
				System.out.println("Error: The booking does not exist.");
				return;
			}
		}
		catch (InvalidId e)
		{
			System.out.println(e.getMessage());
			return;
		}

		System.out.print("Enter weight: ");
		weight = scanner.nextLine();

		if (weight.length() == 0)
			return;

		message = facade.checkBaggage(flightId, seat, lastName, Double.parseDouble(weight));
		System.out.println(message);
	}

	/**
	 * Pickup baggage
	 */
	public void pickUpBaggage()
	{

		String flightId, seat, lastName, message;

		System.out.print("---- Enter data to pickup baggage: ----\n");
		System.out.print("Enter flight id: ");
		flightId = scanner.nextLine();

		if (flightId.length() == 0)
			return;

		System.out.print("Enter seat: ");
		seat = scanner.nextLine();

		if (seat.length() == 0)
			return;

		System.out.print("Enter last name: ");
		lastName = scanner.nextLine();

		if (lastName.length() == 0)
			return;

		try
		{
			if (!facade.checkIfBookingExists(flightId, seat))
			{
				message = String.format("Error: The booking with id: %s - %s, not found", flightId, seat);
				System.out.println(message);
				return;
			}
		}
		catch (InvalidId e)
		{
			System.out.println(e.getMessage());
			return;
		}

		message = facade.collectBaggage(flightId, seat, lastName, new DateTime(1));
		System.out.println(message);
	}

	/**
	 * Display all booking info
	 * 
	 * @param input
	 */
	public void displayBookingInfo(String input)
	{

		String flightId, seat;

		System.out.print("Enter flight id: ");
		flightId = scanner.nextLine();

		if (flightId.length() == 0)
			return;

		System.out.print("Enter seat: ");
		seat = scanner.nextLine();

		if (seat.length() == 0)
			return;

		if (input.equals("DB"))
		{
			System.out.println(facade.displayBooking(flightId, seat));
		}
		else if (input.equals("HB"))
		{
			System.out.println(facade.displayHistoricalBaggage(flightId, seat));
		}
	}

	/**
	 * Show the menu
	 */
	public void showMenu()
	{

		String input = "";

		do
		{
			printSimbaAirwaysMenu();
			input = scanner.nextLine().toUpperCase().trim();
			switch (SimbaAirwayMenu.valueOf(input.trim()))
			{
			case AB:
				addBooking();
				break;
			case BS:
				bookSeat();
				break;
			case CB:
				checkinBaggage();
				break;
			case PB:
				pickUpBaggage();
				break;
			case DB:
			case HB:
				displayBookingInfo(input);
				break;
			case DA:
				System.out.println(facade.displayAllBookings());
				break;
			case SD:
				SeedMenu seedMenu = getSeedMenu();
				facade.seedData(seedMenu);
				break;
			case EX:
				break;
			default:
				System.out.println("Error: Invalid menu option code.");
				break;
			}

		} while (!input.equals("EX"));
		facade.writeData();
		System.out.println("Thank you for using SimbaAirways system.");
	}

	/**
	 * Get the seed menu
	 * 
	 * @return Seed menu
	 */
	private SeedMenu getSeedMenu()
	{
		printSeedMenu();
		String subMenu = "";
		boolean isValid = false;
		do
		{
			subMenu = scanner.nextLine();
			subMenu = subMenu.trim();
			if ("SA".equalsIgnoreCase(subMenu) || "SE".equalsIgnoreCase(subMenu) || "SB".equalsIgnoreCase(subMenu))
			{
				isValid = true;
			}
			else
			{
				System.out.println("Error:Invalid selection");
			}
		} while (!isValid);

		return SeedMenu.valueOf(subMenu.toUpperCase());
	}

	/**
	 * Print the Main menu
	 */
	public void printSimbaAirwaysMenu()
	{
		String result = "*** Simba Airways System Menu ***\n";
		result += "-------------------------------------\n";
		for (SimbaAirwayMenu simbaAirwayMenu : SimbaAirwayMenu.values())
		{
			result += String.format("%-30s %s\n", simbaAirwayMenu, simbaAirwayMenu.getOption());
		}

		result += "-------------------------------------\n";
		result += "Enter selection: ";

		System.out.println(result);
	}

	/**
	 * Print seed Menu
	 * 
	 * @return print seed menu
	 */
	public void printSeedMenu()
	{
		String result = "*** Seed Booking Menu ***\n";
		result += "-------------------------------------\n";
		for (SeedMenu seedMenu : SeedMenu.values())
		{
			result += String.format("%-30s %s\n", seedMenu, seedMenu.getOption());
		}
		result += "-------------------------------------\n";
		result += "Enter selection: ";
		System.out.println(result);
	}

}
