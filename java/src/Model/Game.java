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
* Class that represents the minesweeper game which contains a game board, 
* a timer, the dimensions of the board, the state of the game, and tracks
* if this is the first move or not.
*
* @author Daniel Fisher
*/
public class Game
{
	private Board gameBoard; 
	private GameTimer gameTimer;
	private int dimension;
	private boolean firstMove;
	private State state;

	/**
	* Constructor of the game that takes the dimension of the board.
	* This initilizes the timer, the state, the first move, and sets
	* the dimension.
	*/
	public Game(int dimension)
	{
		this.firstMove = true;
		this.dimension = dimension;
		this.state = State.RUNNING;
		this.gameTimer = new GameTimer();
	}

	/**
	* This method is called when a tile is clicked at the coordinates x, y.
	* If this is the first move then the mine field and game board are built
	* and the game timer is started. Returns the game state after this move
	* and stops the timer if the state is anything but RUNNING.
	*
	* @param x	the x coordinate of the tile clicked on
	* @param y	the y coordinate of the tile clicked on
	* @return	the game state after move made
	*/
	public State makeMove(int x, int y)
	{
		if (this.firstMove)
		{
			this.firstMove = false;
			AbstractMineField field = new MineField(x, y, this.dimension);
			this.gameBoard = new Board(this.dimension, field);
			this.gameTimer.start();
		}

		this.state = this.gameBoard.makeMove(x, y);
		if (this.state != State.RUNNING)
			this.gameTimer.stop();

		return this.state;
	}

	/**
	* This method is called when the player clicks the hint button. This method
	* picks a random tile that hasn't been revealed and isn't a mine and
	* makes and called the maveMove method. The game state is returned after
	* the move is made.
	*
	* @return	the game state after the move
	*/
	public State hint()
	{
		if (this.state != State.RUNNING)
			return this.state;

		Random rand = new Random();

		int randX = rand.nextInt(this.dimension);
		int randY = rand.nextInt(this.dimension);

		while (!this.firstMove && (this.gameBoard.getRevealed(randX, randY) || this.gameBoard.getMine(randX, randY)))
		{
			randX = rand.nextInt(this.dimension);
			randY = rand.nextInt(this.dimension);
		}

		return this.makeMove(randX, randY);
	}

	/**
	* Flags the tile at x,y.
	*
	* @param x	the x coordinate of the tile to flag
	* @param y	the y coordinate of the tile to flag
	*/
	public void placeFlag(int x, int y)
	{
		this.gameBoard.setFlag(x, y);
	}

	/**
	* Returns the changes made to the game board since the last call to this method.
	* This method is called after makeMove, hint, and placeFlag to get which tiles
	* have been changed.
	*
	* @return	the list of changes made to the game board since last call
	*/
	public ArrayList<TileChange> getChanges()
	{
		return this.gameBoard.getChanges();
	}

	/**
	* Returns the current time as a string in the format "xx : yy" where
	* xx is the mintues elapsed in two decimal places and yy is the seconds
	* elapsed for the current minute in two decimal places.
	*
	* @return	the string representation of the current time.
	*/	
	public String getGameTime()
	{
		return this.gameTimer.getTime();
	}
}
