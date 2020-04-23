import javax.swing.*; 

public class UITile extends JButton
{
	private int x;
	private int y;

	public UITile(String text, int x, int y)
	{
		super(text);
		this.x = x;
		this.y = y;
	}

	public UITile(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	public int getGridX()
	{
		return this.x;
	}

	public int getGridY()
	{
		return this.y;
	}	
}
