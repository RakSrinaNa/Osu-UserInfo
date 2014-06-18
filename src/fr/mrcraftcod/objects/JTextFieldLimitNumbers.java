package fr.mrcraftcod.objects;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Document to limit TextField numbers.
 *
 * @author MrCraftCod
 */
public class JTextFieldLimitNumbers extends PlainDocument
{
	private static final long serialVersionUID = 1L;
	private int limit;

	/**
	 * Constructor.
	 *
	 * @param limit the maximum number of numbers that can be wrote.
	 */
	public JTextFieldLimitNumbers(int limit)
	{
		super();
		this.limit = limit;
	}

	/**
	 * Called when a character is typed.
	 *
	 * @see PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
	{
		if(getLength() + str.length() <= this.limit && isNumber(str))
			try
			{
				Integer.parseInt(str);
				super.insertString(offset, str, attr);
			}
			catch(Exception e)
			{}
	}

	/**
	 * Used to know if the string is a number.
	 *
	 * @param str The string to verify.
	 * @return True if it's a number, false if not.
	 */
	private boolean isNumber(String str)
	{
		boolean verif = true;
		try
		{
			Integer.parseInt(str);
		}
		catch(Exception e)
		{
			verif = false;
		}
		return verif;
	}
}