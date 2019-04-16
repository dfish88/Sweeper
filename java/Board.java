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
                for (int dx = -1; dx < 2; dx++)
                {
                        for (int dy = -1; dy < 2; dy++)
                        {
                                if (x+dx != 0 && y+dy != 0)
                                        mineCount += checkTile(x+dx, y+dy, true);
                        }
                }
		return mineCount;
	}

	public int checkTile(int x, int y, boolean mine)
	{
		try
		{
			if (mine)
			{
				if (this.theBoard[x][y] instanceof Mine)
					return 1;
				return 0;
			}
			else
			{
				if (this.theBoard[x][y].getAdjacent() == 0 && this.theBoard[x][y].isRevealed() == false)
					return 1;
				return 0;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return 0;
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
			
			this.theBoard[randX][randY] = new Mine();
		}
	}

	/*
	* Returns a string representation of the board.
	* Use a string for each row and new lines to go to next row.
	*/
	public String toString()
	{
		String rep = "";
		for (int i = 0; i < this.dimension; i ++)
		{
			for (int j = 0; j < this.dimension; j++)
			{
				rep = rep + this.theBoard[i][j].toString();
			}
				rep = rep + "\n";
		}
		return rep;
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public void revealTile(int x, int y)
	{
		this.theBoard[x][y].reveal();;
		this.revealAdjacentTiles(x,y);
	}

	/*
	* Reveals 0 tiles adjacent to tile at x,y.
	*/
	private void revealAdjacentTiles(int x, int y)
	{
		// Use a stack to store adjacent values, starting with origin tile
		Stack<Integer> adjacent = new Stack<>();
		adjacent.push(y);
		adjacent.push(x);

		// Loop until stack is empty
		while (!adjacent.empty())
		{
			// Reveal top tile on stack
			this.theBoard[adjacent.pop()][adjacent.pop()].reveal();

			// Add adjacent 0 tiles to stack
			this.findAdjacentZero(x, y, adjacent);
		}
	}

	private void findAdjacentZero(int x, int y, Stack s)
	{
		// Check all 8 adjacent tiles for mines
                for (int dx = -1; dx < 2; dx++)
                {
			for (int dy = -1; dy < 2; dy++)
			{
				if (x+dx != 0 && y+dy != 0)
				{
					if (this.checkTile(x+dx, y+dy, false) == 1)
					{
						s.push(y+dy);
						s.push(x+dx);
					}
				}
			}
                }
	
	}
} 
