import java.util.*;
import java.awt.*;

public class Board 
{ 
	private Tile[][] theBoard; 
	private int dimension;
	private Stack<Integer> changes;

	// Used to calculate coordinates of all 8 adjacent tiles of
	// a particular tile
	private int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

	public Board(int dimension)
	{
		this.dimension = dimension;
		theBoard = new Tile[this.dimension][this.dimension];		
		buildBoard();
		this.changes = new Stack<>();
	}

	public void restart()
	{
		this.changes.clear();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (this.theBoard[x][y].isRevealed() || this.theBoard[x][y].getFlag())
				{
					this.changes.push((int)' ');
					this.changes.push(y);
					this.changes.push(x);
				}
			}
		}

		theBoard = new Tile[this.dimension][this.dimension];		
		buildBoard();
	}

	public void setFlag(int x, int y)
	{
		this.theBoard[x][y].setFlag();
	}

	public boolean getFlag(int x, int y)
	{
		return this.theBoard[x][y].getFlag();
	}

	public int getDimension()
	{
		return this.dimension;
	}	

	public boolean mine(int x, int y)
	{
		return this.theBoard[x][y].isMine();
	}

	public boolean revealed(int x, int y)
	{
		return this.theBoard[x][y].isRevealed();
	}

	/*
	* Builds the board
	*/
	public void buildBoard()
	{
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
				if (this.theBoard[i][j] == null)
					this.theBoard[i][j] = new Tile(calculateAdjacent(i,j), false);
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
		for (int i = 0; i < this.delta.length; i = i + 2)
		{
			mineCount += checkTile(x + this.delta[i], y + this.delta[i+1], true);
		}
		return mineCount;
	}

	public int checkTile(int x, int y, boolean mine)
	{
		try
		{
			if (mine)
			{
				if (this.theBoard[x][y].isMine())
					return 1;
				else
					return 0;
			}
			else
			{
				if (this.theBoard[x][y].getAdjacent() == 0 && this.theBoard[x][y].isRevealed() == false)
					return 1;
				else
					return 0;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return 0;
		}
		catch(NullPointerException e)
		{
			return 0;
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
		int limit = (int)((this.dimension * this.dimension) / 6);
		for(int i = 0; i < limit; i++)
		{
			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);
			
			this.theBoard[randX][randY] = new Tile(0, true);
		}
	}

	/*
	* Returns a string representation of the board.
	* Use a string for each row and new lines to go to next row.
	*/
	public String toString()
	{
		return "";
	}

	public String toStringReveal()
	{
		return "";
	}

	public Stack<Integer> getChanges()
	{
		Stack<Integer> ret = new Stack<>();
		ret.addAll(this.changes); 
		this.changes.clear();
		return ret;
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public void revealTile(int x, int y)
	{
		this.theBoard[x][y].reveal();
		this.changes.push(this.theBoard[x][y].getAdjacent() + '0');
		this.changes.push(y);
		this.changes.push(x);
		if (this.theBoard[x][y].getAdjacent() == 0 && !(this.theBoard[x][y].isMine())) 
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

		int currentX;
		int currentY;

		// Loop until stack is empty
		while (!adjacent.empty())
		{
			currentX = adjacent.pop();
			currentY = adjacent.pop();
			// Reveal top tile on stack
			this.theBoard[currentX][currentY].reveal();

			this.changes.push(this.theBoard[currentX][currentY].getAdjacent() + '0');
			this.changes.push(currentY);
			this.changes.push(currentX);

			// Add adjacent 0 tiles to stack
			this.findAdjacentZero(currentX, currentY, adjacent);
		}
	}

	private void findAdjacentZero(int x, int y, Stack<Integer> s)
	{
		// Check all 8 adjacent tiles for mines
		for (int i = 0; i < this.delta.length; i = i + 2)
		{
			if (this.checkTile(x + this.delta[i], y + this.delta[i+1], false) == 1)
			{
				s.push(y + this.delta[i+1]);
				s.push(x + this.delta[i]);
			}
			// The non 0 edges of the 0 area are convered in this case
			else
			{
				try
				{
					this.theBoard[x + this.delta[i]][y + this.delta[i+1]].reveal();
					this.changes.push(this.theBoard[x + this.delta[i]][y + this.delta[i+1]].getAdjacent() + '0');
					this.changes.push(y + this.delta[i+1]);
					this.changes.push(x + this.delta[i]);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					continue;
				}
			}
		}
	}

	public Stack<Integer> getMines()
	{
		Stack<Integer> stack = new Stack<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].isRevealed()) && this.theBoard[x][y].isMine() && !(this.theBoard[x][y].getFlag()))
				{
					stack.push(y);
					stack.push(x);
				}	
			}
		}
		return stack;
	}
	
	public Stack<Integer> getFlags()
	{
		Stack<Integer> stack = new Stack<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].isRevealed()) && !(this.theBoard[x][y].isMine()) && this.theBoard[x][y].getFlag())
				{
					stack.push(y);
					stack.push(x);
				}	
				
			}
		}
		return stack;
	}

	public boolean checkForWin()
	{
		boolean win = true;

		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if(!(this.theBoard[x][y].isRevealed()) && !(this.theBoard[x][y].isMine()))
				{
					win = false;
					break;
				}
			}
		}
		return win;
	}
} 
