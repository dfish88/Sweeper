import Model.AbstractMineField;
import Model.Tile;

public class OneMineField extends AbstractMineField
{
	public OneMineField(int dimension)
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
		 * |0|0|0|
		 * |0|1|1|
		 * |0|1|m|
		 */
		Tile mine = new Tile(0, true);
		this.setTile(2, 2, mine);

		Tile adj = new Tile(1, false);
		this.setTile(1, 1, adj);
		this.setTile(2, 1, adj);
		this.setTile(1, 2, adj);

		this.setTilesLeft(8);
	}
}
