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

public class BoardTest
{
	private Board testBoard;
	private int dimension;

	@After
	public void tearDown()
	{
		this.testBoard = null;
	}

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

	@Test
	public void testBoardRunning()
	{
		this.setUpTwoMines();
		State s = this.testBoard.makeMove(0,2);
		assertEquals(State.RUNNING, s);
	}

	@Test
	public void testBoardWinEightMines()
	{
		this.setUpEightMines();
		State s = this.testBoard.makeMove(1,1);
		assertEquals(State.WON, s);
	}

	@Test
	public void testBoardWinOneMine()
	{
		this.setUpOneMine();
		State s = this.testBoard.makeMove(0,0);
		assertEquals(State.WON, s);
	}

	@Test
	public void testBoardLoss()
	{
		this.setUpOneMine();
		State s = this.testBoard.makeMove(2,2);
		assertEquals(State.LOSS, s);
	}

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


	public void setUpOneMine()
	{
		this.dimension = 3;
		AbstractMineField field = new OneMineField(this.dimension);
		this.testBoard = new Board(this.dimension, field);
	}

	public void setUpTwoMines()
	{
		this.dimension = 3;
		AbstractMineField field = new TwoMineField(this.dimension);
		this.testBoard = new Board(this.dimension, field);
	}

	public void setUpEightMines()
	{
		this.dimension = 3;
		AbstractMineField field = new EightMineField(this.dimension);
		this.testBoard = new Board(this.dimension, field);
	}
}
