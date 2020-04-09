import java.awt.*;

public class Icon extends Point
{
	private IconRepresentation rep;

	public Icon(int x, int y, IconRepresentation r)
	{
		super(x,y);
		this.setRep(r);
	}

	public void setRep(IconRepresentation r)
	{
		this.rep = r;
	}

	public String getRep()
	{
		return this.rep.getRepresentation();
	}
}
