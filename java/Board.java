import java.util.*;

public class Board 
{ 
	private Tile[][] theBoard; 
	private int dimension;
 
	public Board(int dimension)
	{
		this.dimension = dimension;
		
		buildBoard();
	}

	/*
	* Builds the board
	*/
	public void buildBoard()
	{
		initializeBoard();
		addMines();
		placeAdjacent();
	}

	/*
	* Places the non mine tiles on the board
	*/
	public void placeAdjacent()
	{
		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {       
				this.theBoard[i][j] = new Tile(i, j, calculateAdjacent(i,j));
                        }
                }

	}

	/*
	* Checks for mines adjacent to the tile at (x,y)
	*/
	public int calculateAdjacent(int x, int y)
	{
		int mineCount = 0;

		// Check all 8 adjacent tiles for mines
		for (int direction = 0; i < 8; direction++)
		{
			mineCount += checkAdjacent(x, y, direction);
		}

		return mineCount;
	}

	public int checkAdjacent(int x, int y, int direction)
	{
		int mineCount = 0;

		switch(direction)
		{
			// North
			case 0:
				if (this.theBoard[x][y+1] instanceof Mine)
					mineCount++;
				break;
		
			// North East
			case 1:	
		}
	}

	/*
	* Initialize each tile to null	
	*/
	public void initializeBoard()
	{
		for (int i = 0; i < this.dimension; i++)
		{
			for (int j = 0; j < this.dimension; j++)
			{
				this.theBoard[i][j] = null;
			}
		}
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
		for(int i = 0; i < this.dimension; i++)
		{
			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);
			
			this.theBoard[randX][randY] = new Mine(randX, randY);
		}
	}
}
