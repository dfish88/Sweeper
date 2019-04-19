public class Tile
{
	enum tileType
	{
		ADJACENT,
		MINE
	}

	// has the tile been clicked on?
	private boolean revealed;

	// Count how many mines this tile touches
	private int adjacentTo;

	private boolean flag;
	private tileType type;

	public Tile(int adjacentTo, tileType t)
	{
		this.adjacentTo = adjacentTo;
		this.revealed = false;
		this.flag = false;
		this.type = t;
	}

	public Tile(Tile toCopy)
	{
		this.adjacentTo = toCopy.getAdjacent();
	}

	public void setAdjacent(int adjacentTo)
	{
		this.adjacentTo = adjacentTo;
	}

	public void reveal()
	{
		this.revealed = true;
	}

	public int getAdjacent()
	{
		return this.adjacentTo;
	}

	public boolean isRevealed()
	{
		return this.revealed;
	}

	public void setFlag()
	{
		this.flag = true;
	}

	public boolean getFlag()
	{
		return this.flag;
	}

	public tileType getType()
	{
		return this.type;
	}

	public void setType(tileType t)
	{
		this.type = t;
	}

	public String toString()
	{
		if (this.revealed)
		{
			if (this.type == tileType.ADJACENT)
				return Integer.toString(this.adjacentTo);
			else
				return "m";
		}
		else
			return " ";
	}
}
