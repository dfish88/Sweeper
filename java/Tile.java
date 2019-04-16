public class Tile
{
	// has the tile been clicked on?
	private boolean revealed;

	// Count how many mines this tile touches
	private int adjacentTo;

	public Tile(int adjacentTo)
	{
		this.adjacentTo = adjacentTo;
		this.revealed = false;
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

	public String toString()
	{
		if (this.revealed)
			return Integer.toString(this.adjacentTo);
		else
			return " ";
	}

	public boolean isRevealed()
	{
		return this.revealed;
	}
}
