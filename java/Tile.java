public class Tile
{
	// x and y coordinates
	private int x;
	private int y;

	// Count how many mines this tile touches
	private int adjacentTo;

	public Tile(int x, int y, int adjacentTo)
	{
		this.x = x;
		this.y = y;
		this.adjacentTo = adjacentTo;
	}

	public Tile(Tile toCopy)
	{
		this.x = toCopy.getX();
		this.y = toCopy.getY();
		this.adjacentTo = toCopy.getAdjacent();
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setAdjacent(int adjacentTo)
	{
		this.adjacentTo = adjacentTo;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public int getAdjacent()
	{
		return this.adjacentTo;
	}

	public String toString()
	{
		return Integer.toString(this.adjacentTo);
	}
}
