package fr.mrcraftcod.listeners.components;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;

public class FollowComponentListener implements ComponentListener
{
	private Component moving;
	private int padx;
	private int pady;

	public FollowComponentListener(Component moving)
	{
		this(moving, 0, 0);
	}

	public FollowComponentListener(Component moving, int padx, int pady)
	{
		super();
		this.moving = moving;
		this.padx = padx;
		this.pady = pady;
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		updatePos(e);
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		updatePos(e);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{}

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
