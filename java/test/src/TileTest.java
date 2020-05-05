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

import Model.Tile;
import Presenter.TileRepresentation;

/**
* This class tests the Tile class in the Model module.
*
* @author Daniel Fisher
*/
public class TileTest
{
	private int adjacent; // # adjacent mines
	private Tile test; // the tile to test

	/**
	* Sets the test up by creating a new Tile object with 2 adjacent mines
	* that is not a mine
	*/
	@Before
	public void setUp()
	{
		adjacent = 2;
		test = new Tile(adjacent, false);
	}

	/**
	* Tears down the test by setting the test Tile to null
	*/
	@After
	public void tearDown()
	{
		test = null;
	}

	/**
	* Tests the copy constructor of the Tile class by creating a tile, copying
	* it, changing the fields of the original and seeing if the copy tile fields
	* have remained unchanged.
	*/
	@Test
	public void testCopy()
	{
		Tile copy = new Tile(this.test);

		assertEquals(this.test.getAdjacent(), copy.getAdjacent());
		assertEquals(this.test.getRevealed(), copy.getRevealed());
		assertEquals(this.test.getMine(), copy.getMine());
		assertEquals(this.test.getFlag(), copy.getFlag());

		this.test.setAdjacent(0);
		this.test.setMine();
		this.test.setFlag();

		assertEquals(this.adjacent, copy.getAdjacent());
		assertFalse(copy.getMine());
		assertFalse(copy.getFlag());

		this.test.setRevealed();

		assertFalse(copy.getRevealed());
	}

	/**
	* Test if the adjacent field was set when creating the test tile
	*/
	@Test
	public void testAdjacent()
	{
		assertEquals(adjacent, test.getAdjacent());
	}

	/**
	* Test to see if the flag field can be set when the tile is not
	* revealed (valid case)
	*/
	@Test
	public void testFlagValid()
	{
		assertFalse(test.getFlag());
		test.setFlag();	
		assertTrue(test.getFlag());
	}

	/**
	* Test to see if the flag field is turned off when tile is revealed and
	* if it can't be set when the tile is revealed (invalid case)
	*/
	@Test
	public void testFlagInvalid()
	{
		test.setFlag();	
		assertTrue(test.getFlag());
		test.setRevealed();
		assertFalse(test.getFlag());
		test.setFlag();
		assertFalse(test.getFlag());
	}

	/**
	* Test if revealing a tile works
	*/
	@Test
	public void testRevealed()
	{
		assertFalse(test.getRevealed());
		test.setRevealed();	
		assertTrue(test.getRevealed());
	}

	/**
	* Test if setting and getting the mine field works
	*/
	@Test
	public void testMine()
	{
		assertFalse(test.getMine());
		test.setMine();	
		assertTrue(test.getMine());
	}

	/**
	* Test if the getRep method produces the correct results when tile is covered
	*/
	@Test
	public void testGetRepCovered()
	{
		assertEquals(TileRepresentation.COVERED, test.getRep());
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* has been flagged
	*/
	@Test
	public void testGetRepFlag()
	{
		test.setFlag();
		assertEquals(TileRepresentation.FLAG, test.getRep());
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* is a mine that has been revealed (clicked on)
	*/
	@Test
	public void testGetRepMine()
	{
		test.setMine();
		test.setRevealed();
		assertEquals(TileRepresentation.BOOM, test.getRep());
	}

	/**
	* Test if the getRep method produces the correct results when the tile
	* is revealed and is not a mine
	*/
	@Test
	public void testGetRepAdjacent()
	{
		test.setRevealed();
		assertEquals(TileRepresentation.TWO, test.getRep());
	}
}
