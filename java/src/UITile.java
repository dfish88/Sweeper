import javax.swing.*; 

public class UITile extends JButton
{
	private int grid_x;
	private int grid_y;

	public UITile(int x, int y)
	{
		super();
		this.grid_x = x;
		this.grid_y = y;
	}

	public int getGridX()
	{
		return this.grid_x;
	}

	public int getGridY()
	{
		return this.grid_y;
	}	
}
