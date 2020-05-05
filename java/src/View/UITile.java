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

package View;

import javax.swing.*; 

/**
* This class provides methods to manage a UI board tile such as
* getting the x and y coordinates of the tile on the board.
*
* @author Daniel Fisher
*/
public class UITile extends JButton
{
	private int grid_x;
	private int grid_y;

	/** 
	* Constructor that sets the grid position to the given
	* x and y coordinates
	*
	* @param x	the x coordinate
	* @param y	the y coordinate
	*/
	public UITile(int x, int y)
	{
		super();
		this.grid_x = x;
		this.grid_y = y;
	}

	/**
	* This method returns the x coordinate of this tile's position
	* on the board
	*
	* @return	the x coordinate
	*/
	public int getGridX()
	{
		return this.grid_x;
	}

	/**
	* This method returns the y coordinate of this tile's position
	* on the board
	*
	* @return	the y coordinate
	*/
	public int getGridY()
	{
		return this.grid_y;
	}	
}
