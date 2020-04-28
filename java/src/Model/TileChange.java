package Model;

import Presenter.TileRepresentation;

import java.awt.*;

public class TileChange
{
	private TileRepresentation rep;
	private int x;
	private int y;

	public TileChange(int x, int y, TileRepresentation r)
	{
		this.x = x;
		this.y = y;
		this.setRep(r);
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setRep(TileRepresentation r)
	{
		this.rep = r;
	}

	public TileRepresentation getRep()
	{
		return this.rep;
	}
}
