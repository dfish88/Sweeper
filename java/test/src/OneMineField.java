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

import Model.AbstractMineField;
import Model.Tile;

/**
* This class extends the abstract minefield and creates a minefield
* that is 3x3 and has one mine. This is used for testing purposes
*
* @author Daniel Fisher
*/
public class OneMineField extends AbstractMineField
{
	/**
	* Constructor that calls the method to build the mine field
	*/
	public OneMineField()
	{
		this.build();
	}

	/**
	* Helper method that builds the mine field
	*/
	private void build()
	{
		int dimension = 3;
		this.initField(dimension);
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{       
				this.setTile(i, j, new Tile(0, false));
			}
		}

		/*
		 * |0|0|0|
		 * |0|1|1|
		 * |0|1|m|
		 */
		Tile mine = new Tile(0, true);
		this.setTile(2, 2, mine);

		Tile adj = new Tile(1, false);
		this.setTile(1, 1, adj);
		this.setTile(2, 1, adj);
		this.setTile(1, 2, adj);

		this.setTilesLeft(8);
	}
}
