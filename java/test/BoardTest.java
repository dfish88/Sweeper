import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

public class BoardTest
{
	private Board test;
	private Tile[][] testBoard;	
	private int dimension;

	@Before
	public void setUp()
	{
		// Create a 3x3 board with all 0 tiles (no mines)
		this.dimension = 3;
		this.testBoard = new Tile[this.dimension][this.dimension];
		for (int i = 0; i < this.dimension; i++)
		{
			for (int j = 0; j < this.dimension; j++)
			{
				this.testBoard[i][j] = new Tile(0, false);
			}
		}
	}

	public void setUpOneMine()
	{
		/*
		 * |0|0|0|
		 * |0|1|1|
		 * |0|1|m|
		 */
		this.testBoard[2][2].setMine();
		this.testBoard[1][1].setAdjacent(1);
		this.testBoard[2][1].setAdjacent(1);
		this.testBoard[1][2].setAdjacent(1);
		this.test = new Board(this.dimension, this.testBoard, (this.dimension*this.dimension)-1);
	}

	public void setUpTwoMines()
	{
		/*
		 * |m|1|0|
		 * |1|2|1|
		 * |0|1|m|
		 */
		this.testBoard[2][2].setMine();
		this.testBoard[0][0].setMine();
		this.testBoard[1][1].setAdjacent(2);
		this.testBoard[2][1].setAdjacent(1);
		this.testBoard[1][2].setAdjacent(1);
		this.testBoard[1][0].setAdjacent(1);
		this.testBoard[0][1].setAdjacent(1);
		this.test = new Board(this.dimension, this.testBoard, (this.dimension*this.dimension)-2);
	}

	public void setUpEightMines()
	{
		/*
		 * |m|m|m|
		 * |m|8|m|
		 * |m|m|m|
		 */
		this.testBoard[0][0].setMine();
		this.testBoard[0][1].setMine();
		this.testBoard[0][2].setMine();
		this.testBoard[1][0].setMine();
		this.testBoard[1][2].setMine();
		this.testBoard[2][0].setMine();
		this.testBoard[2][1].setMine();
		this.testBoard[2][2].setMine();
		this.testBoard[1][1].setAdjacent(8);
		this.test = new Board(this.dimension, this.testBoard, 1);
	}

	@After
	public void tearDown()
	{
		this.testBoard = null;
		this.test = null;
	}

	@Test
	public void testFlag()
	{
		this.setUpOneMine();
		assertFalse(this.test.getFlag(0,0));
		test.setFlag(0,0);
		assertTrue(this.test.getFlag(0,0));
		test.setFlag(0,0);
		assertFalse(this.test.getFlag(0,0));
	}

	@Test
	public void testMine()
	{
		this.setUpOneMine();
		assertFalse(this.test.getMine(0,0));
		assertTrue(this.test.getMine(2,2));
	}

	@Test
	public void testRevealOneTile()
	{
		this.setUpOneMine();
		assertFalse(this.test.getRevealed(1,1));
		this.test.makeMove(1,1);

		ArrayList<Icon> changes = this.test.getChanges();
		Icon change = changes.remove(0);

		assertEquals((int)change.getX(), 1);
		assertEquals((int)change.getY(), 1);
		assertEquals(change.getChar(), '1');
		assertTrue(changes.isEmpty());
	}

	@Test
	public void testWinEightMines()
	{
		this.setUpEightMines();
		assertEquals(this.test.getState(), Board.State.RUNNING);
		this.test.makeMove(1,1);
		assertEquals(this.test.getState(), Board.State.WON);
	}

	@Test
	public void testWinOneMine()
	{
		this.setUpOneMine();
		assertEquals(this.test.getState(), Board.State.RUNNING);
		this.test.makeMove(0,0);
		assertEquals(this.test.getState(), Board.State.WON);
	}

	@Test
	public void testLoss()
	{
		this.setUpOneMine();
		assertEquals(this.test.getState(), Board.State.RUNNING);
		this.test.makeMove(2,2);
		assertEquals(this.test.getState(), Board.State.LOSS);
	}

	@Test
	public void testMoveTwoMines()
	{
		this.setUpTwoMines();
		this.test.makeMove(0,2);

		assertTrue(this.test.getRevealed(0,2));
		assertTrue(this.test.getRevealed(0,1));
		assertTrue(this.test.getRevealed(1,1));
		assertTrue(this.test.getRevealed(1,2));
		assertFalse(this.test.getRevealed(1,0));
		assertFalse(this.test.getRevealed(2,0));
		assertFalse(this.test.getRevealed(2,1));
	}

	@Test
	public void testGetFlagsIncorrect()
	{
		this.setUpOneMine();
		this.test.setFlag(0,0);
		this.test.setFlag(1,1);

		ArrayList<Icon> flags = this.test.getFlags();
		
		assertEquals(flags.size(), 2);

		int x;
		int y;
		while (!flags.isEmpty())
		{
			Icon flag = flags.remove(0);
		
			x = (int)flag.getX();
			y = (int)flag.getY();
	
			assertTrue((x == 1 && y == 1) || (x == 0 && y == 0));
			assertEquals(flag.getChar(), 'w');
		}
	}

	@Test
	public void testGetFlagCorrect()
	{
		this.setUpOneMine();
		this.test.setFlag(2,2);

		ArrayList<Icon> flags = this.test.getFlags();
		assertTrue(flags.isEmpty());
	}

	@Test
	public void testGetMinesNoFlags()
	{
		this.setUpOneMine();

		ArrayList<Icon> mines = this.test.getMines();
		assertEquals(mines.size(), 1);

		Icon mine = mines.get(0);

		assertEquals((int)mine.getX(), 2);
		assertEquals((int)mine.getY(), 2);
	}

	@Test
	public void testGetMinesFlags()
	{
		this.setUpTwoMines();
		this.test.setFlag(0,0);

		ArrayList<Icon> mines = this.test.getMines();
		assertEquals(mines.size(), 1);

		Icon mine = mines.get(0);

		assertEquals((int)mine.getX(), 2);
		assertEquals((int)mine.getY(), 2);
	}
}
