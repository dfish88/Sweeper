package Model;

import java.util.*;

public class MineField extends AbstractMineField
{
	protected final int MINE_RATIO = 5;

	public MineField(int x, int y, int dimension)
	{
		this.buildField(x, y, dimension, (int)((dimension*dimension)/this.MINE_RATIO));
	}

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	private void buildField(int x, int y, int dimension, int mineLimit)
	{
		this.field = new Tile[dimension][dimension];
		this.tilesLeft = 0;
		Random rand = new Random();

		// Place mines on the board
		for(int i = 0; i < mineLimit; i++)
		{
			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);

			// Find random place that isn't where move is made and isn't already a mine
			while ((randX == x && randY == y) || (this.field[randX][randY] != null))
			{
				randX = rand.nextInt(dimension);
				randY = rand.nextInt(dimension);
			}
			
			this.field[randX][randY] = new Tile(0, true);
		}

		// Determine all non-mine tiles
		int mineCount;
		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {       
				if (this.field[i][j] == null)
				{
					mineCount = this.countMines(i,j);
					this.field[i][j] = new Tile(mineCount, false);
					this.tilesLeft++;
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
			try
			{
				if (this.field[x + delta[i]][y + delta[i+1]].getMine())
					mineCount+=1;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
			catch(NullPointerException e)
			{
				continue;
			}
		}
		return mineCount;
	}
}
