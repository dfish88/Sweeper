import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class TileTest
{

	private int adjacent;
	private Tile test;

	@Before
	public void setUp()
	{
		adjacent = 2;
		test = new Tile(adjacent, false);
	}

	@After
	public void tearDown()
	{
		test = null;
	}

	@Test
	public void testAdjacent()
	{
		assertEquals(adjacent, test.getAdjacent());
	}

	@Test
	public void testFlagValid()
	{
		assertFalse(test.getFlag());
		test.setFlag();	
		assertTrue(test.getFlag());
	}

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

	@Test
	public void testRevealed()
	{
		assertFalse(test.getRevealed());
		test.setRevealed();	
		assertTrue(test.getRevealed());
	}

	@Test
	public void testMine()
	{
		assertFalse(test.getMine());
		test.setMine();	
		assertTrue(test.getMine());
	}


	@Test
	public void testToCharCovered()
	{
		assertEquals(test.toChar(), ' ');
	}

	@Test
	public void testToCharFlag()
	{
		test.setFlag();
		assertEquals(test.toChar(), 'f');
	}

	@Test
	public void testToCharMine()
	{
		test.setMine();
		test.setRevealed();
		assertEquals(test.toChar(), 'b');
	}

	@Test
	public void testToCharAdjacent()
	{
		test.setRevealed();
		assertEquals(test.toChar(), '2');
	}
}
