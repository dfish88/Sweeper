import java.awt.*;

public class Icon extends Point
{
	private char icon;

	public Icon(int x, int y, char c)
	{
		super(x,y);
		this.setChar(c);
	}

	public void setChar(char c)
	{
		this.icon = c;
	}

	public char getChar()
	{
		return this.icon;
	}
}
