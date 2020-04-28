package Model;

import Presenter.TileRepresentation;

public class Tile
{
	private boolean revealed;	// Is this tile revealed or hidden
	private int adjacentTo;		// How many mines is this tile adjacent to
	private boolean flag;		// Is flag placed on tile	
	private boolean mine;		// Is this tile a mine

	public Tile(int adjacentTo, boolean mine)
	{
		this.adjacentTo = adjacentTo;
		this.revealed = false;
		this.flag = false;
		this.mine = mine;
	}

	/* GETTERS */

	/*
	* Returns the representation of this tile.
	*/
	public TileRepresentation getRep()
	{
		if (this.revealed)
		{
			if (this.mine)
				return TileRepresentation.BOOM;
			else
			{
				switch(this.adjacentTo)
				{
					case 0:
						return TileRepresentation.ZERO;
					case 1:
						return TileRepresentation.ONE;
					case 2:
						return TileRepresentation.TWO;
					case 3:
						return TileRepresentation.THREE;
					case 4:
						return TileRepresentation.FOUR;
					case 5:
						return TileRepresentation.FIVE;
					case 6:
						return TileRepresentation.SIX;
					case 7:
						return TileRepresentation.SEVEN;
					case 8:
						return TileRepresentation.EIGHT;
				}
			}
		}
		else
		{
			if (this.flag)
				return TileRepresentation.FLAG;
			else
				return TileRepresentation.COVERED;
		}
		// Should never get here
		return TileRepresentation.EMPTY;
	}
	
	public int getAdjacent()
	{
		return this.adjacentTo;
	}

	public boolean getRevealed()
	{
		return this.revealed;
	}

	public boolean getFlag()
	{
		return this.flag;
	}

	public boolean getMine()
	{
		return this.mine;
	}

	/* SETTERS */

	/*
	* Only covered tiles can be flags so we
	* set flag to false when tile is revealed
	*/
	public void setRevealed()
	{
		this.revealed = true;
		this.flag = false;
	}

	public void setAdjacent(int adjacentTo)
	{
		this.adjacentTo = adjacentTo;
	}

	public void setMine()
	{
		this.mine = true;
	}

	/*
	* Only covered tiles can be flags so
	* we do nothing if flags are revealed
	*/
	public void setFlag()
	{
		if (this.revealed)
			return;

		this.flag = !(this.flag);
	}
}
