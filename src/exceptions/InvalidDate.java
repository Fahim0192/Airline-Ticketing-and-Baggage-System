package exceptions;

/*
 * Class:			InvalidDate
 * Description:		Exception thrown when date is invalid
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class InvalidDate extends Exception
{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -3313485282286870323L;

	/**
	 * Default constructor
	 */
	public InvalidDate()
	{
		super();
	}

	/**
	 * Constructor used to set error message and cause of exception
	 * 
	 * @param message
	 *            - Exception message
	 * @param cause
	 *            - Cause of exception
	 */
	public InvalidDate(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor used to set error message
	 * 
	 * @param message
	 *            - Exception message
	 */
	public InvalidDate(String message)
	{
		super(message);
	}

	/**
	 * Cause of exception
	 * 
	 * @param cause
	 *            - Cause of exception
	 */
	public InvalidDate(Throwable cause)
	{
		super(cause);
	}

}
