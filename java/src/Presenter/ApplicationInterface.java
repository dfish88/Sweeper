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

package Presenter;

/**
* The interface for the Application which is the Presenter. These methods are used by
* the view module to update the presenter about which buttons have been pressed.
*
* @author Daniel Fisher
*/
public interface ApplicationInterface
{
	/**
	* Called when the game is started and a dimension is specified.
	*
	* @param dimension	the dimension of the board to be created
	*/
	public void startGame(int dimension);

	/**
	* Called when the hint button is clicked.
	*/
	public void hintClicked();

	/**
	* Called when the restart button is clicked.
	*/
	public void restartClicked();

	/**
	* Called when a tile is clicked (move is made) at a specified
	* x and y coordinate.
	*
	* @param x	the x coordinate of the tile clicked
	* @param y	the y coordinate of the tile clicked
	*/
	public void tileClicked(int x, int y);

	/**
	* Called when a tile is right clicked on (flag placed) at a
	* specified x and y coordinate.
	*
	* @param x	the x coordinate of the tile clicked
	* @param y	the y coordinate of the tile clicked
	*/
	public void placeFlag(int x, int y);

	/**
	* Called when the mouse has been pressed on a tile but not released.
	* This allows the face to be animated during game play.
	*/
	public void mousePressed();

	/**
	* Called when the mouse has been released on a tile.
	* This allows the face to be animated during game play.
	*/
	public void mouseReleased();
}
