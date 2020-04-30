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
import java.awt.*;

/**
* This class helps provide the Presenter module with changes
* made in the Model module. Each object has an x and y coordinate
* and a representation(see the TileRepresentation enum) 
* which together represent a change to the tile located at the x and y coordinates.
*
* @author Daniel Fisher
*/
public class TileChange
{
	private TileRepresentation rep; // The representation to change to
	private int x; // The x coordinate of the tile to be changed
	private int y; // The y coordiante of the tile to be changed

	/**
	* Constructor that sets each field to the provided value.
	*
	* @param x 	the x coordinate of the tile to change
	* @param y 	the y coordinate of the tile to change
	* @param r	the tile repersentaion of the tile to change
	*/
	public TileChange(int x, int y, TileRepresentation r)
	{
		this.x = x;
		this.y = y;
		this.setRep(r);
	}

	/**
	* Returns the x coordinate.
	* 
	* @return	the x coordinate of the tile to be changed
	*/
	public int getX()
	{
		return this.x;
	}

	/**
	* Returns the y coordinate.
	* 
	* @return	the y coordinate of the tile to be changed
	*/
	public int getY()
	{
		return this.y;
	}

	/**
	* Sets the tile representation.
	*
	* @param r	the tile representation
	*/
	public void setRep(TileRepresentation r)
	{
		this.rep = r;
	}

	/**
	* Returns the tile representation.
	*
	* @return the tile representation.
	*/
	public TileRepresentation getRep()
	{
		return this.rep;
	}
}
