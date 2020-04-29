import Model.AbstractMineField;
import Model.Tile;

public class EightMineField extends AbstractMineField
{
	public EightMineField(int dimension)
	{
		this.build(dimension);
	}

	private void build(int dimension)
	{
		this.initField(dimension);
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{       
				this.setTile(i, j, new Tile(0, false));
			}
		}

		/*
		 * |m|m|m|
		 * |m|8|m|
		 * |m|m|m|
		 */
		Tile mine = new Tile(0, true);
		this.setTile(0, 0, mine);
		this.setTile(0, 1, mine);
		this.setTile(0, 2, mine);
		this.setTile(1, 0, mine);
		this.setTile(1, 2, mine);
		this.setTile(2, 0, mine);
		this.setTile(2, 1, mine);
		this.setTile(2, 2, mine);

		Tile adj = new Tile(8, false);
		this.setTile(1, 1, adj);

		this.setTilesLeft(1);
	}
}
