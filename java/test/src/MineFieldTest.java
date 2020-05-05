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

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import Model.MineField;
import Model.AbstractMineField;
import Presenter.TileRepresentation;

/**
* This class test the MineField class in the Model module
*
* @author Daniel Fisher
*/
public class MineFieldTest
{
	private AbstractMineField test; // The minefield to test
	private int dimension; // The dimensions of the mine field

	/**
	* Tears down the test by setting the minefield to null
	*/
	@After
	public void tearDown()
	{
		this.test = null;
	}

	/**
	* Tests flagging a tile when it is covered (valid case)
	*/
	@Test
	public void testValidFlag()
	{
		this.setUpOneMine();
		assertFalse(this.test.getFlag(0,0));

		this.test.setFlag(0,0);
		assertTrue(this.test.getFlag(0,0));
	}

	/**
	* Tests flagging a tile when it is revealed (invalid case)
	*/
	@Test
	public void testInvalidFlag()
	{
		this.setUpOneMine();
		this.test.setRevealed(1,1);
		
		assertFalse(this.test.getFlag(1,1));
		this.test.setFlag(1,1);
		assertFalse(this.test.getFlag(1,1));
	}

	/**
	* Tests if mine field is constructed properly (all tiles covered initially)
	* and if revealing a single tile only reveals that tile
	*/
	@Test
	public void testRevealed()
	{
		this.setUpOneMine();
		int tilesLeft = this.test.getTilesLeft();
	
		assertFalse(this.test.getRevealed(0,0));
		assertFalse(this.test.getRevealed(0,1));
		assertFalse(this.test.getRevealed(0,2));
		assertFalse(this.test.getRevealed(1,0));
		assertFalse(this.test.getRevealed(1,1));
		assertFalse(this.test.getRevealed(1,2));
		assertFalse(this.test.getRevealed(2,0));
		assertFalse(this.test.getRevealed(2,1));
		assertFalse(this.test.getRevealed(2,2));

		this.test.setRevealed(0,0);
		assertEquals(tilesLeft-1, this.test.getTilesLeft());

		assertTrue(this.test.getRevealed(0,0));
		assertFalse(this.test.getRevealed(0,1));
		assertFalse(this.test.getRevealed(0,2));
		assertFalse(this.test.getRevealed(1,0));
		assertFalse(this.test.getRevealed(1,1));
		assertFalse(this.test.getRevealed(1,2));
		assertFalse(this.test.getRevealed(2,0));
		assertFalse(this.test.getRevealed(2,1));
		assertFalse(this.test.getRevealed(2,2));
	}

	/**
	* Tests if field is constructed properly (only one mine)
	*/
	@Test
	public void testMine()
	{
		this.setUpOneMine();

		assertFalse(this.test.getMine(0,0));
		assertFalse(this.test.getMine(0,1));
		assertFalse(this.test.getMine(0,2));
		assertFalse(this.test.getMine(1,0));
		assertFalse(this.test.getMine(1,1));
		assertFalse(this.test.getMine(1,2));
		assertFalse(this.test.getMine(2,0));
		assertFalse(this.test.getMine(2,1));
		assertTrue(this.test.getMine(2,2));
	}

	/**
	* Tests if field was created correctly (adjacent field set correctly)
	*/
	@Test
	public void testAdjacent()
	{
		this.setUpOneMine();

		assertEquals(0, this.test.getAdjacent(0,0));
		assertEquals(0, this.test.getAdjacent(0,1));
		assertEquals(0, this.test.getAdjacent(0,2));
		assertEquals(0, this.test.getAdjacent(1,0));
		assertEquals(1, this.test.getAdjacent(1,1));
		assertEquals(1, this.test.getAdjacent(1,2));
		assertEquals(0, this.test.getAdjacent(2,0));
		assertEquals(1, this.test.getAdjacent(2,1));
		assertEquals(0, this.test.getAdjacent(2,2));
	}

	/**
	* Test if the getRep method produces the correct results when tile is covered
	*/
	@Test
	public void testRepCovered()
	{
		this.setUpOneMine();
		assertEquals(TileRepresentation.COVERED, this.test.getRep(1,1));
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* is revealed and is not a mine
	*/
	@Test
	public void testRepUncovered()
	{
		this.setUpOneMine();
		this.test.setRevealed(1,1);
		assertEquals(TileRepresentation.ONE, this.test.getRep(1,1));
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* has been flagged
	*/
	@Test
	public void testRepFlag()
	{
		this.setUpOneMine();
		this.test.setFlag(1,1);
		assertEquals(TileRepresentation.FLAG, this.test.getRep(1,1));
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* is a mine that has been revealed (clicked on)
	*/
	@Test
	public void testRepBoom()
	{
		this.setUpOneMine();
		this.test.setRevealed(2,2);
		assertEquals(TileRepresentation.BOOM, this.test.getRep(2,2));
	}

	/**
	* Test if the minefield class places the correct number of random mines
	*/
	@Test
	public void testNumMinesPlaced()
	{
		this.setUpDefault();

		int expectedMines = 5;
		int expectedNonMines = (this.dimension * this.dimension) - expectedMines;

		// MineField uses (dim*dim)/5 to determine number of mines
		assertEquals(expectedNonMines, this.test.getTilesLeft());
	}

	/**
	* Test if the tiles left field is only changed when uncovered tiles are revealed
	*/
	@Test
	public void testReveal()
	{
		this.setUpDefault();

		// Reveal should only change tiles left when non-revealed
		// tiles are revealed
		this.test.setRevealed(0,0);
		int expectedTilesLeft = this.test.getTilesLeft();

		this.test.setRevealed(0,0);
		assertEquals(expectedTilesLeft, this.test.getTilesLeft());
	}

	/**
	* Sets up a 5x5 minefield using the random mine placement in
	* the minefield class
	*/
	public void setUpDefault()
	{
		this.dimension = 5;
		this.test = new MineField(0,0, this.dimension);
	}

	/**
	* Sets up a 3x3 minefield with one mine (see OneMineField class)
	*/
	public void setUpOneMine()
	{
		this.dimension = 3;
		this.test = new OneMineField();
	}
}	
