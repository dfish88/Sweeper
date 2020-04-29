package Model;

import Presenter.TileRepresentation;

public abstract class AbstractMineField
{
	private Tile[][] field;
	private int tilesLeft;

	public void setFlag(int x, int y)
	{
		this.field[x][y].setFlag();
	}

	public boolean getFlag(int x, int y)
	{
		return this.field[x][y].getFlag();	
	}

	public void setRevealed(int x, int y)
	{
		if (!this.field[x][y].getMine() && !this.field[x][y].getRevealed())
			this.tilesLeft--;

		this.field[x][y].setRevealed();
	}

	public boolean getRevealed(int x, int y)
	{
		return this.field[x][y].getRevealed();
	}

	public boolean getMine(int x, int y)
	{
		return this.field[x][y].getMine();
	}

	public int getAdjacent(int x, int y)
	{
		return this.field[x][y].getAdjacent();
	}

	public TileRepresentation getRep(int x, int y)
	{
		return this.field[x][y].getRep();
	}

	public int getTilesLeft()
	{
		return this.tilesLeft;
	}

	protected void setTilesLeft(int tilesLeft)
	{
		this.tilesLeft = tilesLeft;
	}

	protected void setTile(int x, int y, Tile val)
	{
		this.field[x][y] = new Tile(val);
	}
	
	protected void initField(int dimension)
	{
		this.field = new Tile[dimension][dimension];
	}

	protected boolean emptyTile(int x, int y)
	{
		return this.field[x][y] == null;
	}
}
