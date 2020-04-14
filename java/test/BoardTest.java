import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

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
	public void testBoardValidFlag()
	{
		this.setUpOneMine();
		assertFalse(this.testBoard.getFlag(0,0));
		this.testBoard.setFlag(0,0);
		assertTrue(this.testBoard.getFlag(0,0));
		this.testBoard.setFlag(0,0);
		assertFalse(this.testBoard.getFlag(0,0));
	}

	@Test
	public void testBoardInvalidFlag()
	{
		this.setUpOneMine();
		this.testBoard.makeMove(1,1);
		assertFalse(this.testBoard.getFlag(1,1));
		this.testBoard.setFlag(1,1);
		assertFalse(this.testBoard.getFlag(1,1));
	}

	@Test
	public void testBoardRevealOneTile()
	{
		this.setUpOneMine();
		assertFalse(this.testBoard.getRevealed(1,1));
		this.testBoard.makeMove(1,1);

		ArrayList<Icon> changes = this.testBoard.getChanges();
		Icon change = changes.remove(0);

		assertEquals(1, (int)change.getX());
		assertEquals(1, (int)change.getY());
		assertEquals(IconRepresentation.ONE, change.getRep());
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

	@Test
	public void testBoardFlagIncorrect()
	{
		this.setUpOneMine();
		this.testBoard.setFlag(0,0);
		this.testBoard.makeMove(2,2);

		ArrayList<Icon> changes = this.testBoard.getChanges();
		assertEquals(2, changes.size());

		while (!changes.isEmpty())
		{
			Icon change = changes.remove(0);
			int x = change.getX();
			int y = change.getY();
			IconRepresentation r = change.getRep();
			assertTrue((x == 0 && y == 0 && r == IconRepresentation.FLAG_WRONG) || (x == 2 && y == 2 && r == IconRepresentation.BOOM));
		}
	}

	@Test
	public void testBoardFlagCorrect()
	{
		this.setUpTwoMines();
		this.testBoard.setFlag(2,2);
		this.testBoard.makeMove(0,0);

		ArrayList<Icon> changes = this.testBoard.getChanges();
		assertEquals(1, changes.size());

		Icon change = changes.get(0);
		assertEquals(change.getX(), 0);
		assertEquals(change.getY(), 0);
		assertEquals(IconRepresentation.BOOM, change.getRep());
		
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

	public class OneMineField extends AbstractMineField
	{
		public OneMineField(int dimension)
		{
			this.tilesLeft = 8;
			this.buildField(dimension);
		}

		private void buildField(int dimension)
		{
			this.field = new Tile[dimension][dimension];
			for (int i = 0; i < dimension; i++)
			{
				for (int j = 0; j < dimension; j++)
				{       
					this.field[i][j] = new Tile(0, false);
				}
			}

			/*
			 * |0|0|0|
			 * |0|1|1|
			 * |0|1|m|
			 */
			this.field[2][2].setMine();
			this.field[1][1].setAdjacent(1);
			this.field[2][1].setAdjacent(1);
			this.field[1][2].setAdjacent(1);
		}
	}

	public class TwoMineField extends AbstractMineField
	{
		public TwoMineField(int dimension)
		{
			this.tilesLeft = 7;
			this.buildField(dimension);
		}

		private void buildField(int dimension)
		{
			this.field = new Tile[dimension][dimension];
			for (int i = 0; i < dimension; i++)
			{
				for (int j = 0; j < dimension; j++)
				{       
					this.field[i][j] = new Tile(0, false);
				}
			}

			/*
			 * |m|1|0|
			 * |1|2|1|
			 * |0|1|m|
			 */
			this.field[2][2].setMine();
			this.field[0][0].setMine();
			this.field[1][1].setAdjacent(2);
			this.field[2][1].setAdjacent(1);
			this.field[1][2].setAdjacent(1);
			this.field[1][0].setAdjacent(1);
			this.field[0][1].setAdjacent(1);
		}
	}

	public class EightMineField extends AbstractMineField
	{
		public EightMineField(int dimension)
		{
			this.tilesLeft = 1;
			this.buildField(dimension);
		}

		private void buildField(int dimension)
		{
			this.field = new Tile[dimension][dimension];
			for (int i = 0; i < dimension; i++)
			{
				for (int j = 0; j < dimension; j++)
				{       
					this.field[i][j] = new Tile(0, false);
				}
			}

			/*
			 * |m|m|m|
			 * |m|8|m|
			 * |m|m|m|
			 */
			this.field[0][0].setMine();
			this.field[0][1].setMine();
			this.field[0][2].setMine();
			this.field[1][0].setMine();
			this.field[1][2].setMine();
			this.field[2][0].setMine();
			this.field[2][1].setMine();
			this.field[2][2].setMine();
			this.field[1][1].setAdjacent(8);
		}
	}
}
