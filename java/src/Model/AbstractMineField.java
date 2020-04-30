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

import Presenter.TileRepresentation;

/**
* Provides methods to manage the minefield (all the tiles) used by
* the board class. By making this class abstract we can create different
* types of minefields which is useful when testing.
*
* @author Daniel Fisher
*/
public abstract class AbstractMineField
{
	private Tile[][] field; // The mine field
	private int tilesLeft; // The number of tile left to be revealed

	/**
	* Flags the tile located at x,y.
	*
	* @param x	the x coordinate of the tile to be flagged
	* @param y	the y coordinate of the tile to be flagged
	*/
	public void setFlag(int x, int y)
	{
		this.field[x][y].setFlag();
	}

	/**
	* Returns true if tile is flagged, false otherwise.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coodrinate of the tile to check
	* @return	true if tile at x,y is flag, false otherwise
	*/
	public boolean getFlag(int x, int y)
	{
		return this.field[x][y].getFlag();	
	}

	/**
	* Reveals the tile located at x,y. If the tile is not a mine and
	* isn't already revealed then we decrement tilesLeft.
	*
	* @param x	the x coordinate of the tile to be revealed
	* @param y	the y coordinate of the tile to be revealed
	*/
	public void setRevealed(int x, int y)
	{
		if (!this.field[x][y].getMine() && !this.field[x][y].getRevealed())
			this.tilesLeft--;

		this.field[x][y].setRevealed();
	}

	/**
	* Returns true if tile at x,y is revealed, false otherwise.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coodrinate of the tile to check
	* @return	true if tile is revealed, false otherwise
	*/
	public boolean getRevealed(int x, int y)
	{
		return this.field[x][y].getRevealed();
	}

	/**
	* Returns true if tile at x,y is a mine, false otherwise.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coodrinate of the tile to check
	* @return	true if tile is a mine, false otherwise
	*/
	public boolean getMine(int x, int y)
	{
		return this.field[x][y].getMine();
	}

	/**
	* Returns the number of mines adjacent to the tile at x,y.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coodrinate of the tile to check
	* @return	the number of adjacent mines to tile at x,y
	*/
	public int getAdjacent(int x, int y)
	{
		return this.field[x][y].getAdjacent();
	}

	/**
	* Returns the representation of the tile at x,y.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coodrinate of the tile to check
	* @return	the representation of the tile at x,y
	*/
	public TileRepresentation getRep(int x, int y)
	{
		return this.field[x][y].getRep();
	}

	/**
	* Returns the number of tiles left to be revealed before game is won.
	*
	* @return	the number of tiles left to be revealed
	*/
	public int getTilesLeft()
	{
		return this.tilesLeft;
	}

	/**
	* Sets the number of tiles left to revealed. This is helpful when
	* building the minefield and is set to protected as it will only be
	* used by classes that extend this class.
	*
	* @param tilesLeft	the number of tiles left to be revealed
	*/
	protected void setTilesLeft(int tilesLeft)
	{
		this.tilesLeft = tilesLeft;
	}

	/**
	* Sets the space at x,y to a copy of the tile provided. This is helpful
	* when building the minefield as is set to protecedd as it will only be
	* used by classes the extend this class.
	*
	* @param x	x coordinate
	* @param y	y coordinate
	* @param val	Tile object to copy and place at x,y
	*/
	protected void setTile(int x, int y, Tile val)
	{
		this.field[x][y] = new Tile(val);
	}

	/**
	* Initilizes the field. This is helpful when building the minefield and 
	* is set to protecedd as it will only be used by classes the extend this class.
	*
	* @param dimension	the dimension of the field
	*/
	protected void initField(int dimension)
	{
		this.field = new Tile[dimension][dimension];
	}

	/**
	* Returns true if the space at x, y is null, false otherwise. This is helpful
	* when building the minefield as is set to protecedd as it will only be
	* used by classes the extend this class.
	*
	* @param x	x coordinate of tile to check
	* @param y	y coordinate of tile to check
	* @return	true if tile is null, false otherwise
	*/
	protected boolean emptyTile(int x, int y)
	{
		return this.field[x][y] == null;
	}
}
