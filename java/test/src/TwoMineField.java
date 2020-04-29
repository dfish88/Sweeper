import Model.AbstractMineField;
import Model.Tile;

public class TwoMineField extends AbstractMineField
{
	public TwoMineField(int dimension)
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
		 * |m|1|0|
		 * |1|2|1|
		 * |0|1|m|
		 */
		Tile mine = new Tile(0, true);
		this.setTile(2, 2, mine);
		this.setTile(0, 0, mine);

		Tile adj_1 = new Tile(1, false);
		Tile adj_2 = new Tile(2, false);
		this.setTile(1, 1, adj_2);
		this.setTile(2, 1, adj_1);
		this.setTile(1, 2, adj_1);
		this.setTile(1, 0, adj_1);
		this.setTile(0, 1, adj_1);

		this.setTilesLeft(7);
	}
}
