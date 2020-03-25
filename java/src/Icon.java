import java.awt.*;

public class Icon extends Point
{
	private char icon;

	public Icon(int x, int y, char c)
	{
		super(x,y);
		this.setIcon(c);
	}

	public void setIcon(char c)
	{
		this.icon = c;
	}

	public char getIcon()
	{
		return this.icon;
	}
}
