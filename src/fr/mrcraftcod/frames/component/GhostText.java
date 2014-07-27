package fr.mrcraftcod.frames.component;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Come from Internet.
 */
public class GhostText implements FocusListener, DocumentListener, PropertyChangeListener
{
	private final JTextField textfield;
	private boolean isEmpty;
	private Color ghostColor;
	private Color foregroundColor;
	private final String ghostText;

	public GhostText(final JTextField textfield, String ghostText)
	{
		super();
		this.textfield = textfield;
		this.ghostText = ghostText;
		this.ghostColor = Color.LIGHT_GRAY;
		textfield.addFocusListener(this);
		registerListeners();
		updateState();
		if(!this.textfield.hasFocus())
			focusLost(null);
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		updateState();
	}

	public void delete()
	{
		unregisterListeners();
		this.textfield.removeFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e)
	{
		if(this.isEmpty)
		{
			unregisterListeners();
			try
			{
				this.textfield.setText("");
				this.textfield.setForeground(this.foregroundColor);
			}
			finally
			{
				registerListeners();
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e)
	{
		if(this.isEmpty)
		{
			unregisterListeners();
			try
			{
				this.textfield.setText(this.ghostText);
				this.textfield.setForeground(this.ghostColor);
			}
			finally
			{
				registerListeners();
			}
		}
	}

	public Color getGhostColor()
	{
		return this.ghostColor;
	}

	@Override
	public void insertUpdate(DocumentEvent e)
	{
		updateState();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		updateState();
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		updateState();
	}

	public void setGhostColor(Color ghostColor)
	{
		this.ghostColor = ghostColor;
	}

	private void registerListeners()
	{
		this.textfield.getDocument().addDocumentListener(this);
		this.textfield.addPropertyChangeListener("foreground", this);
	}

	private void unregisterListeners()
	{
		this.textfield.getDocument().removeDocumentListener(this);
		this.textfield.removePropertyChangeListener("foreground", this);
	}

	private void updateState()
	{
		this.isEmpty = this.textfield.getText().length() == 0;
		this.foregroundColor = this.textfield.getForeground();
	}
}