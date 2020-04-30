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

/*
* This class holds infromation about each Tile on the minesweeper board such as
* the number of mines adjacent to this tile, if the tile is a mine, if the tile
* has been flagged, and if the tile has been revealed.
*
* @author Daniel Fisher
*/
public class Tile
{
	private boolean revealed;	// Is this tile revealed or hidden
	private int adjacentTo;		// How many mines is this tile adjacent to
	private boolean flag;		// Is a flag placed on this tile
	private boolean mine;		// Is this tile a mine

	/**
	* Copy constructor that creates a copy of the provided tile.
	* 
	* @param copy	the tile to be copied
	*/
	public Tile(Tile copy)
	{
		this.revealed = copy.getRevealed();
		this.adjacentTo = copy.getAdjacent();
		this.flag = copy.getFlag();
		this.mine = copy.getMine();	
	}

	/**
	* Constructor that creates a tile with the provided adjacent
	* and mine parameters. Revealed and flag are not included
	* because these are changed later during game play.
	* 
	* @param adjacentTo	the number of mines this tile is adjacent
	*			to
	* @param mine		true if tile is a mine, false otherwise
	*/
	public Tile(int adjacentTo, boolean mine)
	{
		this.adjacentTo = adjacentTo;
		this.revealed = false;
		this.flag = false;
		this.mine = mine;
	}

	/* GETTERS */

	/**
	* Returns the representation of the tile which is based on
	* all the field (revealed, adjacentTo, flag, mine).
	*
	* @return	the representation of this tile, see the
	*		enum TileRepresentation for possible values.
	*/
	public TileRepresentation getRep()
	{
		if (this.revealed)
		{
			if (this.mine)
				return TileRepresentation.BOOM;
			else
			{
				switch(this.adjacentTo)
				{
					case 0:
						return TileRepresentation.ZERO;
					case 1:
						return TileRepresentation.ONE;
					case 2:
						return TileRepresentation.TWO;
					case 3:
						return TileRepresentation.THREE;
					case 4:
						return TileRepresentation.FOUR;
					case 5:
						return TileRepresentation.FIVE;
					case 6:
						return TileRepresentation.SIX;
					case 7:
						return TileRepresentation.SEVEN;
					case 8:
						return TileRepresentation.EIGHT;
				}
			}
		}
		else
		{
			if (this.flag)
				return TileRepresentation.FLAG;
			else
				return TileRepresentation.COVERED;
		}
		// Should never get here
		return TileRepresentation.EMPTY;
	}
	
	/**
	* Returns the number of mines this tile is adjacent to.
	*
	* @return	the number of adjacent mines (0-8).
	*/
	public int getAdjacent()
	{
		return this.adjacentTo;
	}

	/**
	* Returns true if this tile if revealed, false otherwise.
	*
	* @return	true if revealed, false otherwise.
	*/
	public boolean getRevealed()
	{
		return this.revealed;
	}

	/**
	* Returns true if this tile is flagged, false otherwise.
	*
	* @return	true if flagged, false otherwise
	*/
	public boolean getFlag()
	{
		return this.flag;
	}

	/**
	* Returns true if tile is a mine, false otherwise.
	*
	* @return	true if mine, false otherwise.
	*/
	public boolean getMine()
	{
		return this.mine;
	}

	/* SETTERS */

	/**
	* Reveals the current tile. This method also sets flag to false
	* because revealed tiles can't be flagged.
	*/
	public void setRevealed()
	{
		this.revealed = true;
		this.flag = false;
	}

	/**
	* Sets the number of mines this tile is adjacent to. This method
	* assumes the input is valid (0-8).
	*
	* @param adjacentTo	the number of adjacent mines (0-8).
	*/
	public void setAdjacent(int adjacentTo)
	{
		this.adjacentTo = adjacentTo;
	}

	/**
	* Sets this tile as a mine.
	*/
	public void setMine()
	{
		this.mine = true;
	}

	/**
	* Sets this tile as a flag or unflaggs this tile if it is already
	* flagged. If this tile is revealed then we do not flag this tile.
	*/
	public void setFlag()
	{
		if (this.revealed)
			return;

		this.flag = !(this.flag);
	}
}
