import java.util.*;

public class Board 
{ 
	private Tile[][] theBoard; 
	private int dimension;
 
	public Board(int dimension)
	{
		this.dimension = dimension;
		theBoard = new Tile[this.dimension][this.dimension];		
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
				if (!(this.theBoard[i][j] instanceof Mine))
					this.theBoard[i][j] = new Tile(calculateAdjacent(i,j));
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
		for (int direction = 0; direction < 8; direction++)
		{
			mineCount += checkAdjacent(x, y, direction);
		}

		return mineCount;
	}

	/*
	* Checks for mines adjacent to square (x,y) in direction direction.
	* and returns either 1 or 0.
	*/
	public int checkAdjacent(int x, int y, int direction)
	{
		try
		{
			switch(direction)
			{	
				// North
				case 0:
					if (this.theBoard[x-1][y] instanceof Mine)
						return 1;
					break;
			
				// North East
				case 1:	
					if (this.theBoard[x-1][y+1] instanceof Mine)
						return 1;
					break;
		
				// East
				case 2:
					if (this.theBoard[x][y+1] instanceof Mine)
						return 1;
					break;

				// South East 
				case 3:
					if (this.theBoard[x+1][y+1] instanceof Mine)
						return 1;
					break;
		
				// South
				case 4:
					if (this.theBoard[x+1][y] instanceof Mine)
						return 1;
					break;

				// South West
				case 5:
					if (this.theBoard[x+1][y-1] instanceof Mine)
						return 1;
					break;

				// West
				case 6:
					if (this.theBoard[x][y-1] instanceof Mine)
						return 1;
					break;

				// North West
				case 7:
					if (this.theBoard[x-1][y-1] instanceof Mine)
						return 1;
					break;
				default:
					return 0;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
		
		return 0;
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
			
			this.theBoard[randX][randY] = new Mine();
		}
	}

	public void printBoard()
	{
		for (int i = 0; i < this.dimension; i ++)
		{
			for (int j = 0; j < this.dimension; j++)
			{
				System.out.print("| " + this.theBoard[i][j] + " ");
			}
			System.out.println("|");
		}
	}

	public void revealSquare(int x, int y)
	{
		this.theBoard[x][y].setRevealed(true);
	}
}
