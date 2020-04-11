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
	* Returns the Icon Rep of this tile.
	*/
	public IconRepresentation getRep()
	{
		if (this.revealed)
		{
			if (this.mine)
				return IconRepresentation.BOOM;
			else
			{
				switch(this.adjacentTo)
				{
					case 0:
						return IconRepresentation.ZERO;
					case 1:
						return IconRepresentation.ONE;
					case 2:
						return IconRepresentation.TWO;
					case 3:
						return IconRepresentation.THREE;
					case 4:
						return IconRepresentation.FOUR;
					case 5:
						return IconRepresentation.FIVE;
					case 6:
						return IconRepresentation.SIX;
					case 7:
						return IconRepresentation.SEVEN;
					case 8:
						return IconRepresentation.EIGHT;
				}
			}
		}
		else
		{
			if (this.flag)
				return IconRepresentation.FLAG;
			else
				return IconRepresentation.COVERED;
		}
		// Should never get here
		return IconRepresentation.EMPTY;
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
