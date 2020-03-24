import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest
{
	@Test
	public void testAdjacent()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		assertEquals(adjacent, test.getAdjacent());
	}

	@Test
	public void testFlag()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		assertFalse(test.getFlag());
		test.setFlag();	
		assertTrue(test.getFlag());
	}

	@Test
	public void testRevealed()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		assertFalse(test.getRevealed());
		test.setRevealed();	
		assertTrue(test.getRevealed());
	}

	@Test
	public void testMine()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		assertFalse(test.getMine());
		test.setMine();	
		assertTrue(test.getMine());
	}


	@Test
	public void testToCharCovered()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		assertEquals(test.toChar(), ' ');
	}

	@Test
	public void testToCharFlag()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		test.setFlag();
		assertEquals(test.toChar(), 'f');
	}

	@Test
	public void testToCharMine()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		test.setMine();
		test.setRevealed();
		assertEquals(test.toChar(), 'b');
	}

	@Test
	public void testToCharAdjacent()
	{
		int adjacent = 2;
		Tile test = new Tile(adjacent, false);
		test.setRevealed();
		assertEquals(test.toChar(), '2');
	}
}
