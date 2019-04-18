public class Mine extends Tile
{
	public Mine()
	{
		super(0);
	}

	public String toString()
	{
		if (super.isRevealed())
			return "m";
		else
			return " ";
	}
}

