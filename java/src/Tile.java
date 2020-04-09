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
	* Character representation of a tile, used to load icons in the Game class
	* ' ' if not revealed
	* 'f' if not revealed and flag placed
	* # of mines adjacent to if revealed
	*/
	public char toChar()
	{
		if (this.revealed)
		{
			if (this.mine)
				return 'b';
			else
				return Character.forDigit(this.adjacentTo, 10);
		}
		else
		{
			if (this.flag)
				return 'f';
			else
				return ' ';
		}
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
