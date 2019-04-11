import java.util.Random;

public class Board
{
	private Tile[][] theBoard;
	private int dimension;

	public Board(int dimension)
	{
		this.dimension = dimension;
		
		// Must initialize board
	}

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	public void addMines()
	{
		Random rand = new Random();

		// Loop dimension times to place mines
		for(int i = 0; i < dimension; i++)
		{
			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);
			
			this.theBoard[randX][randY] = new Mine(randX, randY);
		}
	}
}
