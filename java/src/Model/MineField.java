/*
*	Copyright (C) 2019-2020  Daniel Fisher
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package Model;

import java.util.*;

/**
* Extends the AbstractMineField class and adds a buildField method
* that randomly adds x number of mines where x is based on a mine 
* ratio defined in this class.
*
* @author Daniel Fisher
*/
public class MineField extends AbstractMineField
{
	private final int MINE_RATIO = 5; // Determines number of mines on board
	private int dimension; // Dimensions of mine field

	/**
	* Constructor that takes the x and y coordinates of the first move made
	* and the dimensions of the mine field. This method will call the method
	* to build the mine field which requires the x and y coordinates so we
	* don't place a mine on the tile that was clicked first.
	*
	* @param x		the x coordinate of the first move
	* @param y 		the y coordinate of the first move
	* @param dimension	the dimension of the mine field
	*/
	public MineField(int x, int y, int dimension)
	{
		int numMines = (int)((dimension*dimension)/this.MINE_RATIO);
		this.dimension = dimension;
		this.buildField(x, y, numMines);
	}

	/**
	* Builds the minefield of size dimension randomly placing the number
	* of mines provided and ensuring that a mine isn't placed on the
	* coordinates provided.
	*
	* @param x		the x coordinate of the first move
	* @param y 		the y coordinate of the first move
	* @param numMines	the number of mines to randomly place
	*/
	private void buildField(int x, int y, int numMines)
	{
		this.initField(this.dimension);
		Random rand = new Random();
		int num_tiles = 0;

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
					num_tiles++;
				}
                        }
                }
		this.setTilesLeft(num_tiles);
	}

	/**
	* Returns the number of mines adjacent to the tile at x,y.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coordinate of the tile to check
	* @return	the number of mines adjacent to tile x,y
	*/
	private int countMines(int x, int y)
	{
		/* Each pair of offsets represents a direction from a
		   starting x and y. The first pair is -1,-1 which is
		   the x and y delta respecitvely. When added to a given
		   x and y coordinate, -1, -1 would give you the tile
		   north west of the starting tile.
		*/
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

	/**
	* Returns true if the provided x and y coordinate are out of
	* bounds (off the minefield).
	*
	* @param x	the x coordinate to check
	* @param y	the y coordinate to check
	* @return	true if out of bounds, false otherwise
	*/
	private boolean outOfBounds(int x, int y)
	{
		if (x >= this.dimension || y >= this.dimension)
			return true;
		if (x < 0 || y < 0)
			return true;
		return false;
	}
}
