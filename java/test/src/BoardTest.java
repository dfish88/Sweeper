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
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

import Model.Tile;
import Model.Board;
import Model.State;
import Model.TileChange;
import Model.AbstractMineField;
import Presenter.TileRepresentation;

/**
* This class tests the methods provided by the Board class that involve
* manipulating tiles on the board through making moves and flagging tiles
*
* @author Daniel Fisher
*/
public class BoardTest
{
	private Board testBoard; // The test board
	private int dimension; // The board dimensions

	/**
	* Tears down the test by setting board to null
	*/
	@After
	public void tearDown()
	{
		this.testBoard = null;
	}

	/**
	* Tests flagging a non-mine tile then losing the game and checking if the
	* board marks the flag as incorrect
	*/
	@Test
	public void testBoardFlagIncorrect()
	{
		this.setUpOneMine();
		this.testBoard.setFlag(0,0);

		ArrayList<TileChange> changes = this.testBoard.getChanges();
		int expected_changes = 1;
		assertEquals(expected_changes, changes.size());


		this.testBoard.makeMove(2,2);
		changes = this.testBoard.getChanges();
		expected_changes = 2;
		assertEquals(expected_changes, changes.size());

		while (!changes.isEmpty())
		{
			TileChange change = changes.remove(0);
			int x = change.getX();
			int y = change.getY();
			TileRepresentation r = change.getRep();
			assertTrue((x == 0 && y == 0 && r == TileRepresentation.FLAG_WRONG) || (x == 2 && y == 2 && r == TileRepresentation.BOOM));
		}
	}

	/**
	* Tests flagging a mine tile then winning the game and checking if the
	* board marks the flag as correct
	*/
	@Test
	public void testBoardFlagCorrect()
	{
		this.setUpTwoMines();
		this.testBoard.setFlag(2,2);

		ArrayList<TileChange> changes = this.testBoard.getChanges();
		int expected_changes = 1;
		assertEquals(expected_changes, changes.size());

		this.testBoard.makeMove(0,0);

		changes = this.testBoard.getChanges();
		expected_changes = 1;
		assertEquals(expected_changes, changes.size());

		TileChange change = changes.get(0);
		assertEquals(change.getX(), 0);
		assertEquals(change.getY(), 0);
		assertEquals(TileRepresentation.BOOM, change.getRep());
		
	}

	/**
	* Tests flagging a covered tile and checks if flag field was set (valid case)
	*/
	@Test
	public void testBoardValidFlag()
	{
		this.setUpOneMine();
		this.testBoard.setFlag(0,0);

		ArrayList<TileChange> changes = this.testBoard.getChanges();
		TileChange change = changes.remove(0);
		
		assertTrue(changes.isEmpty());

		int expected_x = 0;
		int expected_y = 0;
		TileRepresentation expected_rep = TileRepresentation.FLAG;

		assertEquals(expected_x, change.getX());
		assertEquals(expected_y, change.getY());
		assertEquals(expected_rep, change.getRep());

	}

	/**
	* Tests flagging a revealed tile and checks if flag field was not set (invalid case)
	*/
	@Test
	public void testBoardInvalidFlag()
	{
		this.setUpOneMine();
		this.testBoard.makeMove(1,1);

		ArrayList<TileChange> changes = this.testBoard.getChanges();
		int expected_changes = 1;
		assertEquals(expected_changes, changes.size());	

		this.testBoard.setFlag(1,1);
		changes = this.testBoard.getChanges();
		assertTrue(changes.isEmpty());
	}

	/**
	* Tests revealing a single tile where no adjacent tiles will be revealed
	*/
	@Test
	public void testBoardRevealOneTile()
	{
		this.setUpOneMine();
		assertFalse(this.testBoard.getRevealed(1,1));
		this.testBoard.makeMove(1,1);

		ArrayList<TileChange> changes = this.testBoard.getChanges();
		TileChange change = changes.remove(0);

		assertEquals(1, (int)change.getX());
		assertEquals(1, (int)change.getY());
		assertEquals(TileRepresentation.ONE, change.getRep());
		assertTrue(changes.isEmpty());
	}

	/**
	* Tests if the correct state is returned after making a move when the game
	* is still running
	*/
	@Test
	public void testBoardRunning()
	{
		this.setUpTwoMines();
		State s = this.testBoard.makeMove(0,2);
		assertEquals(State.RUNNING, s);
	}

	/**
	* Tests if the correct state is returned after making a move when the game
	* has been won with eight mines
	*/
	@Test
	public void testBoardWinEightMines()
	{
		this.setUpEightMines();
		State s = this.testBoard.makeMove(1,1);
		assertEquals(State.WON, s);
	}

	/**
	* Tests if the correct state is returned after making a move when the game
	* has been won with one mine
	*/
	@Test
	public void testBoardWinOneMine()
	{
		this.setUpOneMine();
		State s = this.testBoard.makeMove(0,0);
		assertEquals(State.WON, s);
	}

	/**
	* Tests if the correct state is returned after making a move when the game
	* has been lost with one mine
	*/
	@Test
	public void testBoardLoss()
	{
		this.setUpOneMine();
		State s = this.testBoard.makeMove(2,2);
		assertEquals(State.LOST, s);
	}

	/**
	* Tests revealing a 0 tile and checking if adjacent non-mine tiles where also
	* revealed 
	*/
	@Test
	public void testRevealTwoMines()
	{
		this.setUpTwoMines();
		this.testBoard.makeMove(0,2);

		assertTrue(this.testBoard.getRevealed(0,2));
		assertTrue(this.testBoard.getRevealed(0,1));
		assertTrue(this.testBoard.getRevealed(1,1));
		assertTrue(this.testBoard.getRevealed(1,2));
		assertFalse(this.testBoard.getRevealed(1,0));
		assertFalse(this.testBoard.getRevealed(2,0));
		assertFalse(this.testBoard.getRevealed(2,1));
	}

	/**
	* Creates a one mine field and injects into the board
	*/
	public void setUpOneMine()
	{
		this.dimension = 3;
		AbstractMineField field = new OneMineField();
		this.testBoard = new Board(this.dimension, field);
	}

	/**
	* Creates a two mine field and injects into the board
	*/
	public void setUpTwoMines()
	{
		this.dimension = 3;
		AbstractMineField field = new TwoMineField();
		this.testBoard = new Board(this.dimension, field);
	}

	/**
	* Creates an eight mine field and injects into the board
	*/
	public void setUpEightMines()
	{
		this.dimension = 3;
		AbstractMineField field = new EightMineField();
		this.testBoard = new Board(this.dimension, field);
	}
}
