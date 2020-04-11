import java.awt.*;

public class Icon
{
	private IconRepresentation rep;
	private int x;
	private int y;

	public Icon(int x, int y, IconRepresentation r)
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

	public void setRep(IconRepresentation r)
	{
		this.rep = r;
	}

	public IconRepresentation getRep()
	{
		return this.rep;
	}
}
