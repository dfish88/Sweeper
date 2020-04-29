import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import Model.MineField;
import Model.AbstractMineField;
import Presenter.TileRepresentation;

public class MineFieldTest
{
	private AbstractMineField test;
	private int dimension;

	@After
	public void tearDown()
	{
		this.test = null;
	}

	@Test
	public void testValidFlag()
	{
		this.setUpOneMine();
		assertFalse(this.test.getFlag(0,0));

		this.test.setFlag(0,0);
		assertTrue(this.test.getFlag(0,0));
	}

	@Test
	public void testInvalidFlag()
	{
		this.setUpOneMine();
		this.test.setRevealed(1,1);
		
		assertFalse(this.test.getFlag(1,1));
		this.test.setFlag(1,1);
		assertFalse(this.test.getFlag(1,1));
	}

	@Test
	public void testRevealed()
	{
		this.setUpOneMine();
		int tilesLeft = this.test.getTilesLeft();
	
		assertFalse(this.test.getRevealed(0,0));
		assertFalse(this.test.getRevealed(0,1));
		assertFalse(this.test.getRevealed(0,2));
		assertFalse(this.test.getRevealed(1,0));
		assertFalse(this.test.getRevealed(1,1));
		assertFalse(this.test.getRevealed(1,2));
		assertFalse(this.test.getRevealed(2,0));
		assertFalse(this.test.getRevealed(2,1));
		assertFalse(this.test.getRevealed(2,2));

		this.test.setRevealed(0,0);
		assertEquals(tilesLeft-1, this.test.getTilesLeft());

		assertTrue(this.test.getRevealed(0,0));
		assertFalse(this.test.getRevealed(0,1));
		assertFalse(this.test.getRevealed(0,2));
		assertFalse(this.test.getRevealed(1,0));
		assertFalse(this.test.getRevealed(1,1));
		assertFalse(this.test.getRevealed(1,2));
		assertFalse(this.test.getRevealed(2,0));
		assertFalse(this.test.getRevealed(2,1));
		assertFalse(this.test.getRevealed(2,2));
	}

	@Test
	public void testMine()
	{
		this.setUpOneMine();

		assertFalse(this.test.getMine(0,0));
		assertFalse(this.test.getMine(0,1));
		assertFalse(this.test.getMine(0,2));
		assertFalse(this.test.getMine(1,0));
		assertFalse(this.test.getMine(1,1));
		assertFalse(this.test.getMine(1,2));
		assertFalse(this.test.getMine(2,0));
		assertFalse(this.test.getMine(2,1));
		assertTrue(this.test.getMine(2,2));
	}

	@Test
	public void testAdjacent()
	{
		this.setUpOneMine();

		assertEquals(0, this.test.getAdjacent(0,0));
		assertEquals(0, this.test.getAdjacent(0,1));
		assertEquals(0, this.test.getAdjacent(0,2));
		assertEquals(0, this.test.getAdjacent(1,0));
		assertEquals(1, this.test.getAdjacent(1,1));
		assertEquals(1, this.test.getAdjacent(1,2));
		assertEquals(0, this.test.getAdjacent(2,0));
		assertEquals(1, this.test.getAdjacent(2,1));
		assertEquals(0, this.test.getAdjacent(2,2));
	}

	@Test
	public void testRepCovered()
	{
		this.setUpOneMine();
		assertEquals(TileRepresentation.COVERED, this.test.getRep(1,1));
	}

	@Test
	public void testRepUncovered()
	{
		this.setUpOneMine();
		this.test.setRevealed(1,1);
		assertEquals(TileRepresentation.ONE, this.test.getRep(1,1));
	}

	@Test
	public void testRepFlag()
	{
		this.setUpOneMine();
		this.test.setFlag(1,1);
		assertEquals(TileRepresentation.FLAG, this.test.getRep(1,1));
	}

	@Test
	public void testRepBoom()
	{
		this.setUpOneMine();
		this.test.setRevealed(2,2);
		assertEquals(TileRepresentation.BOOM, this.test.getRep(2,2));
	}

	@Test
	public void testNumMinesPlaced()
	{
		this.setUpDefault();

		int expectedMines = 5;
		int expectedNonMines = (this.dimension * this.dimension) - expectedMines;

		// MineField uses (dim*dim)/5 to determine number of mines
		assertEquals(expectedNonMines, this.test.getTilesLeft());
	}

	@Test
	public void testReveal()
	{
		this.setUpDefault();

		// Reveal should only change tiles left when non-revealed
		// tiles are revealed
		this.test.setRevealed(0,0);
		int expectedTilesLeft = this.test.getTilesLeft();

		this.test.setRevealed(0,0);
		assertEquals(expectedTilesLeft, this.test.getTilesLeft());
	}

	public void setUpDefault()
	{
		this.dimension = 5;
		this.test = new MineField(0,0, this.dimension);
	}

	public void setUpOneMine()
	{
		this.dimension = 3;
		this.test = new OneMineField(this.dimension);
	}
}	
