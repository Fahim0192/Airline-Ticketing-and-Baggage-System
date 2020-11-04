package airline;

import exceptions.InvalidDate;
import utilities.DateTime;

/*
 * Class:			Baggage
 * Description:		The class represents a piece of luggage 
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class Baggage
{
	/**
	 * Id of the baggage
	 */
	private String id;
	/**
	 * Weight of the baggage
	 */
	private double weight;
	/**
	 * Baggage check in date
	 */
	private DateTime checkInDate;
	/**
	 * Baggage collect in date
	 */
	private DateTime collectedDate;

	/**
	 * Constructor used to initialize the the class attributes
	 * 
	 * @param id
	 *            - Baggage id
	 * @param passengerId
	 *            - Passenger Id
	 * @param weight
	 *            - Weight of the baggage
	 * @param checkedInDate
	 *            - Baggage check in date
	 */
	public Baggage(String id, String passengerId, double weight, DateTime checkedInDate)
	{
		/**
		 * Generate the id of baggage by concatenating id,passenger id and
		 * check-in date formatted in eight digit
		 */
		this.id = id + "_" + passengerId + "_" + checkedInDate.getEightDigitDate();
		this.weight = weight;
		this.checkInDate = checkedInDate;
		this.collectedDate = null;
	}

	/**
	 * Collect the baggage on given date
	 * 
	 * @param collectionDate
	 *            - Baggage collection date
	 * @return Boolean value which indicates baggage is collect
	 * @throws InvalidDate
	 *             - Exception is thrown if date is invalid or baggage is
	 *             already collected
	 */
	public boolean collect(DateTime collectionDate) throws InvalidDate
	{

		if (this.collectedDate != null)
			throw new InvalidDate("Error: The luggage is already collected.");

		if (DateTime.diffDays(collectionDate, checkInDate) < 0)
			throw new InvalidDate("Error: The luggage can not be collected before checked in date.");

		this.collectedDate = collectionDate;
		return true;
	}

	/**
	 * Get the details of baggage
	 * 
	 * @return Details of baggage
	 */
	public String getDetails()
	{
		String result = String.format("%-15s %s\n%-15s %dkg\n%-15s %s", "Baggage Id:", this.id, "Weight:",
				Math.round(this.weight), "Checked in Date:", this.checkInDate.getFormattedDate());

		// Append collection date if baggage is collected
		if (this.collectedDate != null)
		{
			result += String.format("\n%-15s %s", "Collected Date:", this.collectedDate.getFormattedDate());
		}

		return result;
	}

	@Override
	/*
	 * String representation of Baggage class.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		// String representation of baggage separated by colon
		return this.id + ":" + Math.round(this.weight) + ":" + this.checkInDate.getEightDigitDate() + ":"
				+ (this.collectedDate != null ? this.collectedDate.getEightDigitDate() : "NO");
	}

	/**
	 * Get the baggage collection date
	 * 
	 * @return Collection date of baggage
	 */
	public DateTime getCollectedDate()
	{
		return collectedDate;
	}

	/**
	 * Set the baggage collection date
	 * 
	 * @param collectedDate
	 *            - Baggage collection date
	 */
	public void setCollectedDate(DateTime collectedDate)
	{
		this.collectedDate = collectedDate;
	}

	/**
	 * Get the baggage id
	 * 
	 * @return Id of the baggage
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Get the baggage weight
	 * 
	 * @return Weight of the baggage
	 */
	public double getWeight()
	{
		return weight;
	}

	/**
	 * Get the check-in date
	 * 
	 * @return the checkInDate
	 */
	public DateTime getCheckInDate()
	{
		return checkInDate;
	}

}
