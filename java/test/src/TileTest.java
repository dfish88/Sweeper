import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import Model.Tile;
import Presenter.TileRepresentation;

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
	public void testGetRepCovered()
	{
		assertEquals(TileRepresentation.COVERED, test.getRep());
	}

	@Test
	public void testGetRepFlag()
	{
		test.setFlag();
		assertEquals(TileRepresentation.FLAG, test.getRep());
	}

	@Test
	public void testGetRepMine()
	{
		test.setMine();
		test.setRevealed();
		assertEquals(TileRepresentation.BOOM, test.getRep());
	}

	@Test
	public void testGetRepAdjacent()
	{
		test.setRevealed();
		assertEquals(TileRepresentation.TWO, test.getRep());
	}
}
