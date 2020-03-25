import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BoardTest
{
	private Board test;
	private Tile[][] testBoard;	
	private int dimension;

	@Before
	public void setUp()
	{
		// Create a 3x3 board with all 0 tiles (no mines)
		dimension = 3;
		for (i = 0; i < dimension; i++)
		{
			for (j = 0; j < dimension; j++)
			{
				testBoard[i][j] = new Tile(0, false);
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
		testBoard[2][2].setMine();
		testBoard[1][1].setAdjacent(1);
		testBoard[2][1].setAdjacent(1);
		testBoard[1][2].setAdjacent(1);
		test = new Board(dimension, testBoard, (dimension*dimension)-1);
	}

	@After
	public void tearDown()
	{
		testBoard = null;
		test = null;
	}

	@Test
	public void testFlag()
	{
		assertFalse(test.getFlag(0,0));
		test.setFlag(0,0);
		assertTrue(test.getFlag(0,0));
		test.setFlag(0,0);
		assertTrue(test.getFlag(0,0));
	}

	@Test
	public void testWinEmpty()
	{
		assertTrue(test.checkForWin());
	}
	
}
