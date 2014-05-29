package fr.mrcraftcod.objects;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimitNumbers extends PlainDocument // TODO Javadoc
{
	private static final long serialVersionUID = 1L;
	private int limit;

	public JTextFieldLimitNumbers(int limit)
	{
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
	{
		if((getLength() + str.length()) <= limit && isNumber(str))
		{
			try
			{
				Integer.parseInt(str);
				super.insertString(offset, str, attr);
			}
			catch(Exception e)
			{}
		}
	}

	private boolean isNumber(String str)
	{
		boolean verif = true;
		for(char c : str.toCharArray())
			verif &= (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9');
		return verif;
	}
}