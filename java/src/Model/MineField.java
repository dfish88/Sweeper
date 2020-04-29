package Model;

import java.util.*;

public class MineField extends AbstractMineField
{
	private final int MINE_RATIO = 5;
	private int dimension;

	public MineField(int x, int y, int dimension)
	{
		int numMines = (int)((dimension*dimension)/this.MINE_RATIO);
		this.dimension = dimension;
		this.buildField(x, y, numMines);
	}

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	private void buildField(int x, int y, int numMines)
	{
		this.initField(this.dimension);
		this.setTilesLeft(0);
		Random rand = new Random();

		// Place mines on the board
		for(int i = 0; i < numMines; i++)
		{
			int randX = rand.nextInt(this.dimension);
			int randY = rand.nextInt(this.dimension);

			// Find random place that isn't where move is made and isn't already a mine
			while ((randX == x && randY == y) || !this.emptyTile(randX, randY))
			{
				randX = rand.nextInt(this.dimension);
				randY = rand.nextInt(this.dimension);
			}
		
			this.setTile(randX, randY, new Tile(0, true));	
		}

		// Determine all non-mine tiles
		int mineCount;
		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {       
				if (this.emptyTile(i, j))
				{
					mineCount = this.countMines(i,j);
					this.setTile(i, j, new Tile(mineCount, false));
					this.incrementTilesLeft();
				}
                        }
                }
	}

	/*
	* Counts the mines adjacent to the tile located at x, y and
	* returns as an integer.
	*/
	private int countMines(int x, int y)
	{
		int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

		// Check all 8 adjacent tiles for mines
		int mineCount = 0;
		for (int i = 0; i < delta.length; i = i + 2)
		{
			int new_x = x + delta[i];
			int new_y = y + delta[i+1];

			// Continue if coordinates are out of bounds
			if (this.outOfBounds(new_x, new_y))
				continue;
		
			// Continue if tile is null
			if (this.emptyTile(new_x, new_y))
				continue;

			if (this.getMine(new_x, new_y))
				mineCount+=1;
		}
		return mineCount;
	}

	private void incrementTilesLeft()
	{
		int tilesLeft = this.getTilesLeft();
		tilesLeft++;
		this.setTilesLeft(tilesLeft);
	}

	private boolean outOfBounds(int x, int y)
	{
		if (x >= this.dimension || y >= this.dimension)
			return true;
		if (x < 0 || y < 0)
			return true;
		return false;
	}
}
