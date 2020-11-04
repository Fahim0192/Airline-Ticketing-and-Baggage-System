package airline;
/*
* Class:			Business
* Description:		Implementation of Business booking class which extends the functionality of Booking class
* Author:			[Fahim Tahmeed] - [s3680881]
*/

import java.io.BufferedWriter;
import java.io.IOException;

public class Business extends Booking
{
	private boolean hasLimosinePickUp;
	private String meal;

	/**
	 * Constructor used to call super constructor and also set limosine pick up
	 * is there or not
	 */
	/**
	 * Constructor used to initialize the fields
	 * 
	 * @param id
	 *            - Booking id
	 * @param rowNumber
	 *            - Row number
	 * @param seatNumber
	 *            - Seat number
	 * @param fee
	 *            - Standard booking fee
	 */
	public Business(String id, String rowNumber, String seatNumber, double fee)
	{
		super(id, rowNumber, seatNumber, fee);
	}

	@Override
	/**
	 * Get the booking details
	 */
	public String getDetails()
	{

		String idStr = this.id;
		/**
		 * Change the ID if there is
		 */
		if (hasLimosinePickUp)
		{
			idStr = String.valueOf(id.charAt(0)) + "_" + id.substring(1, id.length());

		}

		String result = getBasicDetails(idStr);
		if (null != meal)
		{
			result += String.format("\n%-15s %s", "Meal:", meal.toString());
		}
		result += String.format("\n%-15s %s", "Limosine:", (this.hasLimosinePickUp ? "YES" : "NO"));

		int checkedInBags = getCheckedInBagCount();
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
	 * Get the limosine pick up is there or not
	 * 
	 * @return True/False
	 */
	public boolean isLimosinePickUp()
	{
		return hasLimosinePickUp;
	}

	/**
	 * Set the limosine pick up is there or not
	 * 
	 * @return Set booking has limosine pickup or not
	 */
	public void setHasLimosinePickUp(boolean hasLimosinePickUp)
	{
		this.hasLimosinePickUp = hasLimosinePickUp;
		if (hasLimosinePickUp)
		{
			this.fare += 200;
		}
	}

	/**
	 * Get the meal selected at the time of booking
	 * 
	 * @return Selected meal
	 */
	public String getMeal()
	{
		return meal;
	}

	/**
	 * Set the meal selected at the time of booking
	 * 
	 * @param meal
	 *            - Selected meal
	 */
	public void setMeal(String meal)
	{
		this.meal = meal;
	}

	public String toString()
	{

		String result = this.id + ":" + this.rowNo + ":" + this.seatNo + ":" + fare + ":" + this.firstName + ":"
				+ this.lastName + ":" + (isExitRow() ? "ER" : "NO") + ":" + (this.isLimosinePickUp() ? "YES" : "NO")
				+ (null != meal ? ":" + meal.toString() : "");
		for (Baggage baggage : checkedInBaggages)
		{
			if (null != baggage)
			{
				result += ":" + baggage.toString();
			}

		}

		return result;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see airline.Booking#getBookingConfirmation()
	 */
	public String getBookingConfirmation()
	{
		String idStr = getId();
		if (hasLimosinePickUp)
		{
			idStr = String.valueOf(idStr.charAt(0)) + "_" + idStr.substring(1, idStr.length());
		}

		return String.format("The flight: %s - %s has been successfully booked.", idStr, seatNo);

	}

	@Override
	/*
	 * 
	 * @see airline.Booking#writeoFileT(java.io.BufferedWriter)
	 */
	public void writeoFileT(BufferedWriter bw) throws IOException
	{
		String result = this.id + ":" + this.rowNo + ":" + this.seatNo + ":" + this.firstName + ":" + this.lastName
				+ ":" + (this.isLimosinePickUp() ? "YES" : "NO") + ":" + (null != meal ? meal.toString() : null);
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

}
