import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class MineFieldTest
{
	private MineField test;
	private int dimension = 5;
	private int expectedMines = 5;
	private int expectedNonMines = (this.dimension * this.dimension) - this.expectedMines;

	@Before
	public void setUp()
	{
		this.test = new MineField(0,0, this.dimension);
	}

	@After
	public void tearDown()
	{
		this.test = null;
	}

	@Test
	public void testNumMinesPlaced()
	{
		// MineField uses (dim*dim)/5 to determine number of mines
		assertEquals(this.expectedNonMines, this.test.getTilesLeft());
	}

	@Test
	public void testReveal()
	{
		// Reveal should only change tiles left when non-revealed
		// tiles are revealed
		this.test.setRevealed(0,0);
		int expectedTilesLeft = this.test.getTilesLeft();

		this.test.setRevealed(0,0);
		assertEquals(expectedTilesLeft, this.test.getTilesLeft());
	}
}	
