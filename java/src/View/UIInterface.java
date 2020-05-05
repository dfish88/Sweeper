/*
*	Copyright (C) 2019-2020  Daniel Fisher
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by *	the Free Software Foundation, either version 3 of the License, or
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

package View;

import Presenter.TileRepresentation;
import Presenter.FaceRepresentation;

/**
* The interface for the View module used by the presenter. This includes
* methods to start, restart, display tiles, display faces, display time,
* and disable.
* 
* @author Daniel Fisher
*/
public interface UIInterface
{
	/**
	* This method starts the game of a given dimension
	*/
	public void startGame(int dimension);

	/**
	* This method restarts the view module.
	*/
	public void restart();

	/**
	* This method displays the image representation of the given
	* tile representation at a given x and y coordinate.
	*
	* @param x	the x coordinate of the tile
	* @param y	the y coordinate of the tile
	* @param rep	the tile representation
	*/
	public void displayTile(int x, int y, TileRepresentation rep);

	/**
	* This method displays the image representation of the given
	* face representation.
	*
	* @param rep	the face representation
	*/
	public void displayFace(FaceRepresentation rep);

	/**
	* This method displays the provided game time. The format expected is
	* "xx : yy" where xx is the minutes elapsed with a leading 0 if necessary
	* and yy is the seconds elapsed in for the current minute with a leading
	* 0 if necessary.
	* 
	* @param time	the time to display.
	*/
	public void displayTime(String time);

	/**
	* This method disables the UI which is used when the game is won/lost
	* to prevent further moves from being made
	*/
	public void disable();
}
