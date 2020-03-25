import java.util.*;
import java.awt.*;
import java.io.*;

public class Board 
{ 
	private Tile[][] theBoard; 	// Stores all the tiles 
	private int dimension;		
	private ArrayList<Icon> changes;	// Stores all changes made
	private boolean firstMove;	// If this is first move
	private int tilesLeft;

	// Used to calculate coordinates of all 8 adjacent tiles
	private int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

	public Board(int dimension)
	{
		this.dimension = dimension;
		theBoard = new Tile[this.dimension][this.dimension];		
		this.changes = new ArrayList<>();
		this.firstMove = true;
		this.tilesLeft = 0;
	}

	/*
	* Create a board that has already been populated and mines
	* wont be placed randomly. Used for testing.
	*/
	public Board(int dimension, Tile[][] board, int tilesLeft)
	{
		this.dimension = dimension;
		this.theBoard = board;
		this.changes = new ArrayList<>();
		this.firstMove = false;
		this.tilesLeft = tilesLeft;
	}

	/*
	* Executes a hint.  Hint reveals a tile adjacent to
	* an already adjacent tile. We start by looking for
	* non zero adjacent tiles, if non found we then look
	* for adjacent zeros.
	*/
	public void hint()
	{
		/*
		if (this.firstMove)
		{
			Random rand = new Random();

			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);

			this.revealTile(randX, randY);
			this.tilesLeft--;
			
			ArrayList<Icon> s = new ArrayList<>();
			s.addAll(this.changes);
			this.changes.clear();
			return s;
		}

		ArrayList<Icon> stack = new ArrayList<>();

		// Only checks for adjacent non zero tiles.
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				// We want to find a non mine, non revealed tile that is adjacent
				// to at least one other non mine, revealed tile
				if (this.hintTile(x,y, false))
				{
					stack.push(this.theBoard[x][y].getAdjacent() + '0');
					stack.push(y);
					stack.push(x);
					this.theBoard[x][y].setRevealed();
					this.tilesLeft--;
					return stack;
				}
			}
		}

		if (!(stack.empty()))
			return stack;

		// Second pass can reveal 0 tiles too.
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				// We want to find a non mine, non revealed tile that is adjacent
				// to at least one other non mine, revealed tile
				if (this.hintTile(x,y, true))
				{
					stack.push(this.theBoard[x][y].getAdjacent() + '0');
					stack.push(y);
					stack.push(x);
					this.revealTile(x,y);
					stack.addAll(this.changes);
					this.changes.clear();
					return stack;
				}
			}
		}
		return stack;
		*/
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public void makeMove(int x, int y)
	{
		// Build board if first move (hint or click)
		if (this.firstMove)
		{
			this.buildBoard(x,y);
			this.firstMove = false;
		}

		if (this.theBoard[x][y].getRevealed())
			return;

		// Reveal current tile
		this.theBoard[x][y].setRevealed();
		this.tilesLeft--;
		this.changes.add(new Icon(x, y, this.theBoard[x][y].toChar()));

		// Create list of all adjacent tiles if tile clicked on is 0
		ArrayList<Point> adjacent = new ArrayList<>();
		if (!this.theBoard[x][y].getMine() && this.theBoard[x][y].getAdjacent() == 0)
			adjacent.add(new Point(x,y));

		// Find all adjacent 0 tiles if tile is 0 and reveal them
		int currentX;
		int currentY;
		while (!adjacent.isEmpty())
		{
			Point current = adjacent.remove(0);
			currentX = (int)current.getX();
			currentY = (int)current.getY();

			// Reveal tile
			if (!(this.theBoard[currentX][currentY].getRevealed()))
			{
				this.theBoard[currentX][currentY].setRevealed();
				this.tilesLeft--;
				this.changes.add(new Icon(currentX, currentY, this.theBoard[currentX][currentY].toChar()));
			}

			// Add adjacent 0 tiles to list
			this.findAdjacentZero(currentX, currentY, adjacent);
		}
	}



	/* PRIVATE HELPERS */

	/*
	* Pushes adjacent 0 tiles on to stack so we can check tiles adjacent to those tiles too.
	* When we get to non zero, non mine, adjacent tiles we reveal them and don't push them on stack.
	*/
	private void findAdjacentZero(int x, int y, ArrayList<Point> a)
	{
		// Check all 8 adjacent tiles 0 tiles
		for (int i = 0; i < this.delta.length; i = i + 2)
		{

			int currentX = x + this.delta[i];
			int currentY = y + this.delta[i+1];

			try
			{
				// add 0 tiles to the list
				if (this.theBoard[currentX][currentY].getAdjacent() == 0 && !this.theBoard[currentX][currentY].getRevealed())
					a.add(new Point(currentX, currentY));

				// Reveal non-zero tiles
				else
				{
					if(!(this.theBoard[currentX][currentY].getRevealed()))
					{
						this.theBoard[currentX][currentY].setRevealed();
						this.changes.add(new Icon(currentX, currentY, this.theBoard[currentX][currentY].toChar()));
						this.tilesLeft--;
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
			/*
			catch(NullPointerException e)
			{
				return 0;
			}
			*/
		}
	}

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	private void buildBoard(int x, int y)
	{
		Random rand = new Random();

		// Place mines on the board
		int limit = (int)((this.dimension * this.dimension) / 6);
		for(int i = 0; i < limit; i++)
		{
			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);
	
			if(randX == x && randY == y)
				continue;
			
			if (this.theBoard[randX][randY] == null)
			{
				this.theBoard[randX][randY] = new Tile(0, true);
			}
		}

		// Determine all non-mine tiles
		int mineCount;
		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {       
				if (this.theBoard[i][j] == null)
				{
					mineCount = this.countMines(i,j);
					this.theBoard[i][j] = new Tile(mineCount, false);
					this.tilesLeft++;
				}
                        }
                }
	}

	/*
	* Counts the mines adjacent to the tile located at x, y and
	* returns as an integer.
	*/
	private int countMines(int x, int y)
	{
		// Check all 8 adjacent tiles for mines
		int mineCount = 0;
		for (int i = 0; i < this.delta.length; i = i + 2)
		{
			try
			{
				if (this.theBoard[x + this.delta[i]][y + this.delta[i+1]].getMine())
					mineCount+=1;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
			catch(NullPointerException e)
			{
				continue;
			}
		}
		return mineCount;
	}

	/* GETTERS */

	/*
	* Returns a list of flags on the board. This will be used to reveal which
	* flags are correct at the end of the game so we only return flags that
	* incorrect.
	*/
	public ArrayList<Icon> getFlags()
	{
		ArrayList<Icon> flags = new ArrayList<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getRevealed()) && !(this.theBoard[x][y].getMine()) && this.theBoard[x][y].getFlag())
				{
					flags.add(new Icon(x, y, 'f'));
				}	
				
			}
		}
		return flags;
	}

	/*
	* Returns a list of mines on the board. This will be used to reveal mines at
	* end of the game so we only need to return the mines that haven't been clicked on
	* and haven't been flagged.
	*/
	public ArrayList<Icon> getMines()
	{
		ArrayList<Icon> mines = new ArrayList<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getRevealed()) && this.theBoard[x][y].getMine() && !(this.theBoard[x][y].getFlag()))
				{
					mines.add(new Icon(x, y, 'm'));
				}	
			}
		}
		return mines;
	}
	
	/*
	* Returns changes made to board.
	*/
	public ArrayList<Icon> getChanges()
	{
		ArrayList<Icon> ret = new ArrayList<>();
		ret.addAll(this.changes);
		this.changes.clear();
		return ret;
	}

	/*
	* Does this tile have a flag?
	*/
	public boolean getFlag(int x, int y)
	{
		return this.theBoard[x][y].getFlag();
	}

	public int getDimension()
	{
		return this.dimension;
	}	

	public boolean getMine(int x, int y)
	{
		return this.theBoard[x][y].getMine();
	}

	public boolean getRevealed(int x, int y)
	{
		return this.theBoard[x][y].getRevealed();
	}

	/*
	* Checks if all non mine tiles have been revealed.
	*/
	public boolean checkForWin()
	{
		if (this.tilesLeft <=0)
			return true;
		else
			return false;
	}

	/* SETTERS */

	public void setFlag(int x, int y)
	{
		this.theBoard[x][y].setFlag();
	}

} 
