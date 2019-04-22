public class Tile
{
	private boolean revealed;
	private int adjacentTo;
	private boolean flag;
	private boolean mine;

	public Tile(int adjacentTo, boolean mine)
	{
		this.adjacentTo = adjacentTo;
		this.revealed = false;
		this.flag = false;
		this.mine = mine;
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

	public void setMine()
	{
		this.mine = true;
	}

	public boolean isMine()
	{
		return this.mine;
	}

	public char toChar()
	{
		if (this.revealed)
		{
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
}
