package exceptions;
/*
* Class:			InvalidId
* Description:		Exception is thrown if id is invalid
* Author:			[Fahim Tahmeed] - [s3680881]
*/

public class InvalidId extends Exception
{
	/**
	 * Serial Version UID
	 */

	private static final long serialVersionUID = -911109547352722880L;

	/**
	 * Default constructor
	 */
	public InvalidId()
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
	public InvalidId(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructor used to set error message
	 * 
	 * @param message
	 *            - Exception message
	 */
	public InvalidId(String message)
	{
		super(message);
	}

	/**
	 * Cause of exception
	 * 
	 * @param cause
	 *            - Cause of exception
	 */
	public InvalidId(Throwable cause)
	{
		super(cause);
	}

}
