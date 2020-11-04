package airline;

/*
 * Class:			Economy
 * Description:		Implementation of Economy booking class which extends the functionality of Booking class					
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class Economy extends Booking
{
	/**
	 * Boolean value which indicates booked seat is alias seat or not
	 */
	private boolean isAliaSeat = false;

	/**
	 * Constructor calls the super constructor and checks current seat is alias
	 * seat or not
	 * 
	 * @param id
	 *            - Id of the booking
	 * @param rowNumber
	 *            - Row number of seat
	 * @param seatNumber
	 *            - Seat number
	 * @param fee
	 *            - Standard feee
	 */
	public Economy(String id, String rowNumber, String seatNumber, double fee)
	{
		super(id, rowNumber, seatNumber, fee);
		if ("3".endsWith(seatNumber) || "4".endsWith(seatNumber) || "6".endsWith(seatNumber)
				|| "7".endsWith(seatNumber))
		{
			isAliaSeat = true;
			fare += 40;
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAliaSeat()
	{
		return isAliaSeat;
	}

}
