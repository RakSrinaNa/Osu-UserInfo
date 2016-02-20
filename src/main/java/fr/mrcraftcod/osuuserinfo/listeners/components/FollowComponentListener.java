package mrcraftcod.osuuserinfo.listeners.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class FollowComponentListener implements ComponentListener
{
	private final Component moving;
	private final int padx;
	private final int pady;

	public FollowComponentListener(Component moving, int padx, int pady)
	{
		super();
		this.moving = moving;
		this.padx = padx;
		this.pady = pady;
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		updatePos(e);
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		updatePos(e);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
	}

	private void updatePos(ComponentEvent e)
	{
		if(e.getSource() instanceof JFrame)
		{
			JFrame source = (JFrame) e.getSource();
			Point p = source.getLocation();
			p.setLocation(p.getX() + source.getSize().getWidth() / 2 - this.padx / 2, p.getY() + source.getSize().getHeight() / 2 - this.pady / 2);
			this.moving.setLocation(p);
		}
	}
}
