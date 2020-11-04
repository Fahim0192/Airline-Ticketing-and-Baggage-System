package airline;

import java.io.BufferedWriter;
import java.io.IOException;

import exceptions.InvalidDate;
import exceptions.InvalidId;
import utilities.Constant;
import utilities.DateTime;

/*
 * Class:			Booking
 * Description:		The class represents a seat on a flight that can be booked 
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class Booking
{
	/**
	 * Id which is combination of flightId+booking class
	 */
	protected String id;
	/**
	 * Baggage identifier
	 */
	protected String buggateId;
	/**
	 * Row number of booking. Must be in between A and I
	 */
	protected String rowNo;
	/**
	 * Seat number must be in between 1 and 9
	 */
	protected String seatNo;

	/**
	 * Standard fair
	 */
	protected double fare;
	/**
	 * First Name of the passenger
	 */
	protected String firstName;
	/**
	 * Last Name of the passenger
	 */
	protected String lastName;
	/**
	 * Array of checked in bags
	 */
	protected Baggage checkedInBaggages[];
	/**
	 * Array of collected in bags
	 */
	protected Baggage collectedBaggages[];

	/**
	 * Parameterized constructor used to initialize class fields
	 * 
	 * @param id
	 *            - Booking id
	 * @param rowNumber
	 *            - Booking row number
	 * @param seatNumber
	 *            - Booking seat number
	 * @param fee
	 *            - Booking fee
	 */
	public Booking(String id, String rowNumber, String seatNumber, double fee)
	{

		this.id = id;
		this.rowNo = rowNumber;
		this.seatNo = seatNumber;
		this.fare = fee;
		// Maximum three bags can check in
		this.checkedInBaggages = new Baggage[Constant.MAX_BAG_CHECKIN];
		// Maximum 10 historical can be stored
		this.collectedBaggages = new Baggage[Constant.MAX_HISTORICAL_DATA];
	}

	/**
	 * Get the booking id
	 * 
	 * @return Booking id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Get the row number
	 * 
	 * @return Row number
	 */
	public String getRowNumner()
	{
		return rowNo;
	}

	/**
	 * Get the seat number
	 * 
	 * @return Seat number
	 */
	public String getSeatNumber()
	{
		return seatNo;
	}

	/**
	 * Book the seat for given first name and last name.If booking is not
	 * available then throw error message else return string representation of
	 * booking.
	 * 
	 * @param firstName
	 *            - First name
	 * @param lastName
	 *            - Last name
	 * @return String representation of booking
	 */
	public String book(String firstName, String lastName) throws InvalidId
	{
		if (isAvailable())
		{
			this.firstName = firstName;
			this.lastName = lastName;
			return this.toString();
		}

		throw new InvalidId("Error: The booking is already done by another user.");

	}

	/**
	 * Check in the bag of given for given user.If number of bags to check in
	 * reached to maximum limit then throw exception
	 * 
	 * @param lastName
	 *            - Last name
	 * @param weight
	 *            - Weight of bag
	 * @param validationRequired
	 *            - Validation required or not
	 * @return String representation of booking
	 * @throws InvalidId
	 */
	public String checkInBag(String lastName, double weight, boolean validationRequired) throws InvalidId
	{
		int checkedInBagCount = getCheckedInBagCount();

		if (checkedInBagCount == Constant.MAX_BAG_CHECKIN)
			throw new InvalidId("Error: Maximum limit to check-in the bags is three.");

		double totalWeight = 0;
		for (int i = 0; i < checkedInBagCount; i++)
		{
			totalWeight += this.checkedInBaggages[i].getWeight();
		}

		if (totalWeight + weight > 20)
			throw new InvalidId("Error: Maximum weight of bag to be checked in is 20kg.");

		if (isAvailable())
			throw new InvalidId("Error: Booking is yet not done.");

		if (!this.lastName.equals(lastName))
			throw new InvalidId("Error: Last name is not matching to the with the booked person last name.");

		this.checkedInBaggages[checkedInBagCount] = new Baggage(this.id, lastName, weight, new DateTime());

		return this.toString();

	}

	/**
	 * Get the count of checked in bags
	 * 
	 * @return Count of checked in bag
	 */
	protected int getCheckedInBagCount()
	{
		int count = 0;
		for (Baggage baggage : checkedInBaggages)
		{
			if (null != baggage)
				count++;

		}
		return count;
	}

	/**
	 * Get the count of collected bags
	 * 
	 * @return Count of collected bags
	 */
	protected int getCollectedBagCount()
	{
		int count = 0;
		for (Baggage baggage : collectedBaggages)
		{
			if (null != baggage)
				count++;

		}
		return count;
	}

	/**
	 * Collect the checked in bags.Booking is not yet done then throw error. If
	 * count of bags checked in on given date are 0 then throw error message.
	 * message else collect all checked bags.
	 * 
	 * @param dateCollected
	 *            - Date on which bags are collected
	 * @return
	 * @throws InvalidDate
	 */
	public String collectBags(DateTime dateCollected) throws InvalidDate
	{
		// Booking is available that means booking is not yet done so cannot
		// collect bags
		if (isAvailable())
			throw new InvalidDate("Error: No booking is done yet.");

		int checkedInBags = getCheckedInBagCount();
		if (checkedInBags == 0)
			throw new InvalidDate("Error: No of the bag is checked in.");

		for (int i = 0; i < checkedInBags; i++)
		{
			Baggage bag = this.checkedInBaggages[i];
			boolean isColleced = bag.collect(dateCollected);
			if (isColleced)
			{
				removeHistoricalData();
				checkedInBags = getCheckedInBagCount();
				// Add new bag in array
				this.collectedBaggages[checkedInBags] = bag;
			}

		}
		// Remove all bags from checked in bags
		this.checkedInBaggages = new Baggage[Constant.MAX_BAG_CHECKIN];
		return this.toString();
	}

	/**
	 * Remove historical data and rearrange the array elements.
	 */
	private void removeHistoricalData()
	{
		int checkedInBags = getCheckedInBagCount();
		/*
		 * If array of collected bags reached to maximum historical data storage
		 * limit then remove older data and rearrange the data
		 */
		if (checkedInBags == Constant.MAX_HISTORICAL_DATA)
		{
			for (int j = 1; j < checkedInBags; j++)
			{
				this.collectedBaggages[j - 1] = this.collectedBaggages[j];
			}
		}
	}

	/**
	 * Get the details of booking
	 * 
	 * @return Booking details
	 */
	public String getDetails()
	{

		String result = getBasicDetails(id);
		boolean isExitRow = isExitRow();
		result += String.format("\n%-15s %s", "Exit Row:", (isExitRow ? "YES" : "NO"));
		int checkedInBags = getCollectedBagCount();
		int collectedBags = getCollectedBagCount();
		if (checkedInBags > 0)
		{
			result = getCheckedInBagDetails(result, checkedInBags);
		}
		else
		{
			if (collectedBags > 0)
				result += "\n" + getCollectedBagDetails(collectedBags);
		}

		return result;
	}

	/**
	 * Get the details of checked in bag
	 * 
	 * @param result
	 *            - Result String
	 * @param checkedInBags
	 *            - Number of checked in bags
	 * @return String concatenated with checked in bags
	 */
	public String getCheckedInBagDetails(String result, int checkedInBags)
	{
		result += String.format("\n%15s %s\n", "", "--------------------------------------------");
		for (Baggage bag : checkedInBaggages)
		{
			if (null != bag)
			{
				result += String.format("%15s %-15s %s\n%15s %-15s %s kg\n%15s %-15s %s", "", "Baggage ID:",
						bag.getId(), "", "Weight:", Constant.df.format(bag.getWeight()), "", "Check in Date:",
						bag.getCheckInDate().getFormattedDate());

				result += String.format("\n%15s %s\n", "", "--------------------------------------------");
			}
		}

		return result;
	}

	/**
	 * Check current booked row is exit row or not.
	 * 
	 * @return True/False
	 */
	public boolean isExitRow()
	{
		return seatNo.equals("3") || seatNo.equals("4") || seatNo.equals("6") || seatNo.equals("7") ? true : false;
	}

	/**
	 * Get details of collected books
	 * 
	 * @param totalCollectedBags
	 *            - Total number of collected books
	 * @return Collected bags details
	 */
	public String getCollectedBagDetails(int totalCollectedBags)
	{
		String result = "";

		if (totalCollectedBags > 0)
		{
			result = "--------------------------------------------\n";
			for (Baggage baggage : collectedBaggages)
			{
				if (null != baggage)
				{
					result += baggage.getDetails();
					result += "\n--------------------------------------------\n";
				}
			}
		}
		else
		{
			result = "No bags collected for this booking yet.";
		}

		return result;
	}

	/*
	 * String representation of string
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String result = this.id + ":" + this.rowNo + ":" + this.seatNo + ":" + fare + ":" + this.firstName + ":"
				+ this.lastName + ":" + (isExitRow() ? "ER" : "NO");

		for (int i = 0; i < getCheckedInBagCount(); i++)
		{
			result += ":" + this.checkedInBaggages[i].toString();
		}

		return result;
	}

	/**
	 * Return true if booking is available to book
	 * 
	 * @return True/False
	 */
	public boolean isAvailable()
	{
		return this.firstName != null && this.lastName != null ? false : true;
	}

	/**
	 * Clear booking
	 */
	public void clearBooking()
	{
		firstName = null;
		lastName = null;
	}

	/**
	 * Get booking confirmation message
	 * 
	 * @return Booking confirmation message
	 */
	public String getBookingConfirmation()
	{
		return String.format("The flight: %s - %s booking is confirmed.", id, seatNo);
	}

	/**
	 * Write booking data to file
	 * 
	 * @param bw
	 *            - BufferedWriter
	 * @throws IOException
	 */
	public void writeoFileT(BufferedWriter bw) throws IOException
	{
		String result = this.id + ":" + this.rowNo + ":" + this.seatNo + ":" + this.firstName + ":" + this.lastName;

		result += "#";
		int checkedInBags = getCheckedInBagCount();
		for (int i = 0; i < checkedInBags; i++)
		{
			result += this.checkedInBaggages[i].toString();
			if (i < checkedInBags - 1)
			{
				result += "@";
			}
		}
		int collectedBags = getCheckedInBagCount();
		for (int i = 0; i < collectedBags; i++)
		{
			result += this.collectedBaggages[i].toString();
			if (i < collectedBags - 1)
			{
				result += "@";
			}
		}
		result += "\n";
		bw.write(result);
	}

	/**
	 * Check in the bag
	 * 
	 * @param baggage
	 *            - Check in the given bag
	 */
	public void checkInBag(Baggage baggage)
	{
		if (null != baggage)
			this.checkedInBaggages[getCheckedInBagCount()] = baggage;
	}

	/**
	 * Collect the bag
	 * 
	 * @param baggage
	 *            - Collect given bag
	 */
	public void checkoutBags(Baggage baggage)
	{
		if (null != baggage)
			this.collectedBaggages[getCollectedBagCount()] = baggage;
	}

	/**
	 * Get the basic details of booking
	 * 
	 * @param id
	 *            - Id of the booking
	 * @return Formated basic details of booking
	 */
	public String getBasicDetails(String id)
	{
		String result = String.format("%-15s %s\n%-15s %s\n%-15s %s\n%-15s $%s", "ID:", id, "Row Number:", this.rowNo,
				"Seat Number:", this.seatNo, "Standard Fare:", Constant.df.format(this.fare));

		if (this.firstName != null && this.lastName != null)
		{
			result += String.format("\n%-15s %s\n%-15s %s", "First Name:", this.firstName, "Last Name:", this.lastName);
		}
		return result;
	}

	/**
	 * Get collected bags
	 * 
	 * @return Collected bags details
	 */
	public String getCollectedBagDetails()
	{
		int collectedBags = getCollectedBagCount();
		return getCollectedBagDetails(collectedBags);
	}
}
