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
import java.util.*;
import java.awt.*;
import java.io.*;

/**
* This class represents the game board which contains a minefield, has a certain 
* dimension and maintains a list of changes made to the board.
*
* @author Daniel Fisher
*/
public class Board 
{ 
	private AbstractMineField field; 	// Stores all the tiles
	private int dimension;		
	private ArrayList<TileChange> changes;	// Stores all changes made

	/**
	* Constructor that takes the dimension of the board and
	* a mine field. Injecting the mine field allows you to build
	* a board with a specific (non-random) mine field which is
	* helpful for testing.
	*
	* @param dimension	the dimension of the board
	* @param field		the mine field
	*/
	public Board(int dimension, AbstractMineField field)
	{
		this.dimension = dimension;
		this.field = field;
		this.changes = new ArrayList<>();
	}

	/**
	* Returns a list of changes that have been accumulated since the
	* last call to this method. This method returns a copy of the list
	* of changes and clears the list.
	*
	* @return the list of changes
	*/
	public ArrayList<TileChange> getChanges()
	{
		ArrayList<TileChange> ret = new ArrayList<>();
		ret.addAll(this.changes);
		this.changes.clear();
		return ret;
	}

	/**
	* Returns true if a tile is revealed, false otherwise.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coordinate of the tile to check
	* @return	true if revealed, false otherwise
	*/
	public boolean getRevealed(int x, int y)
	{
		return this.field.getRevealed(x, y);
	}

	/**
	* Returns true if a tile is a mines, false otherwise.
	*
	* @param x	the x coordinate of the tile to check
	* @param y	the y coordinate of the tile to check
	* @return	true if a mine, false otherwise
	*/
	public boolean getMine(int x, int y)
	{
		return this.field.getMine(x, y);
	}

	/**
	* Flags the tile at x, y.
	*
	* @param x	the x coordinate of the tile to flag
	* @param y	the y coordinate of the tile to flag
	*/
	public void setFlag(int x, int y)
	{
		this.field.setFlag(x, y);

		// Add to changes if flag was placed on covered tile
		// If flag is placed on covered tile then there is no change
		if (!this.field.getRevealed(x, y))
		{
			this.changes.add(new TileChange(x, y, this.field.getRep(x,y)));
		}
	}

	/**
	* Method called when a tile is clicked at the coordinates x,y. This method
	* reveals that tile and all adjacent tiles recursively for 0 tiles. The game
	* state is returned after the move has been made. getChanges will generally be
	* called after this method.
	*
	* @param x	the x coordinate of the tile clicked on
	* @param y	the y coordinate of the tile clicked on
	* @return	the state of the game after the move
	*/
	public State makeMove(int x, int y)
	{
		State ret = State.RUNNING;
		if (this.field.getRevealed(x,y))
			return ret;

		// Reveal current tile
		this.field.setRevealed(x,y);
		this.changes.add(new TileChange(x, y, this.field.getRep(x,y)));

		if (this.field.getMine(x,y))
		{
			this.revealMines();
			this.checkFlags();
			ret = State.LOST;
		}

		// Create list of all adjacent tiles if tile clicked on is 0
		ArrayList<Point> adjacent = new ArrayList<>();
		if (!this.field.getMine(x,y) && this.field.getAdjacent(x,y) == 0)
			adjacent.add(new Point(x,y));

		// Find all adjacent 0 tiles if tile is 0 and reveal them
		int currentX;
		int currentY;
		while (!adjacent.isEmpty())
		{
			Point current = adjacent.remove(0);
			currentX = (int)current.getX();
			currentY = (int)current.getY();

			// Reveal tile
			if (!(this.field.getRevealed(currentX, currentY)))
			{
				this.field.setRevealed(currentX, currentY);
				this.changes.add(new TileChange(currentX, currentY, this.field.getRep(currentX, currentY)));
			}

			// Add adjacent 0 tiles to list
			this.findAdjacentZero(currentX, currentY, adjacent);
		}

		if (this.field.getTilesLeft() <= 0)
		{
			ret = State.WON;
			this.checkFlags();
		}

		return ret;
	}

	/* PRIVATE HELPERS */

	/**
	* This method finds the adjacent 0 tiles to tile at the coordinate x,y.
	* Adds adjacent 0 to the adjacent list if they haven't been revealed yet.
	*
	* @param x	the x coordinate of the tile to check around
	* @param y	the y coordinate of the tile to check around
	* @param adj	the list of adjacent tiles
	*/
	private void findAdjacentZero(int x, int y, ArrayList<Point> adj)
	{
		/* Each pair of offsets represents a direction from a
		   starting x and y. The first pair is -1,-1 which is
		   the x and y delta respecitvely. When added to a given
		   x and y coordinate, -1, -1 would give you the tile
		   north west of the starting tile.
		*/
		int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

		// Check all 8 adjacent tiles 0 tiles
		for (int i = 0; i < delta.length; i = i + 2)
		{

			int currentX = x + delta[i];
			int currentY = y + delta[i+1];

			try
			{
				// add 0 tiles to the list
				if (this.field.getAdjacent(currentX, currentY) == 0 && !this.field.getRevealed(currentX, currentY))
					adj.add(new Point(currentX, currentY));

				// Reveal non-zero tiles
				else
				{
					if(!(this.field.getRevealed(currentX, currentY)))
					{
						this.field.setRevealed(currentX, currentY);
						this.changes.add(new TileChange(currentX, currentY, this.field.getRep(currentX, currentY)));
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
		}
	}


	/**
	* Checks the currently placed flags on the board to determine if they
	* are correct (on a mine) or not. This will be used to reveal which 
	* flags are correct at the end of the game so we only add flags that 
	* are incorrect to changes and leave correct ones as they are.
	*/
	private void checkFlags()
	{
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.field.getMine(x, y)) && this.field.getFlag(x, y))
				{
					changes.add(new TileChange(x, y, TileRepresentation.FLAG_WRONG));
				}	
				
			}
		}
	}

	/**
	* Reveals the mines on the board at the end of the game when the player
	* has lost (clicked on a mine). A mine is revealed when it wasn't clicked
	* on and when it hasn't been flagged. Mines that meet those criteria are
	* added to the changed.
	*/
	private void revealMines()
	{
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.field.getRevealed(x, y)) && this.field.getMine(x, y) && !(this.field.getFlag(x, y)))
				{
					changes.add(new TileChange(x, y, TileRepresentation.MINE));
				}	
			}
		}
	}
} 
