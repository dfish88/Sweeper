import java.util.*;
import java.awt.*;
import java.io.*;

public class Board 
{ 
	private Tile[][] theBoard; 	// Stores all the tiles 
	private int dimension;		
	private ArrayList<Icon> changes;	// Stores all changes made
	private int tilesLeft;
	private Random rand;

	// Used to calculate coordinates of all 8 adjacent tiles
	private int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

	public Board(int dimension)
	{
		this.rand = new Random();
		this.dimension = dimension;
		theBoard = new Tile[this.dimension][this.dimension];		
		this.changes = new ArrayList<>();
		this.tilesLeft = 0;
	}

	/*
	* Create a board that has already been populated and mines
	* wont be placed randomly. Used for testing.
	*/
	public Board(int dimension, Tile[][] board, int tilesLeft)
	{
		this.rand = new Random();
		this.dimension = dimension;
		this.theBoard = board;
		this.changes = new ArrayList<>();
		this.tilesLeft = tilesLeft;
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public State makeMove(int x, int y)
	{
		State ret = State.RUNNING;
		if (this.theBoard[x][y].getRevealed())
			return ret;

		// Reveal current tile
		this.theBoard[x][y].setRevealed();
		this.tilesLeft--;
		this.changes.add(new Icon(x, y, this.theBoard[x][y].getRep()));

		if (this.theBoard[x][y].getMine())
		{
			this.revealMines();
			this.checkFlags();
			ret = State.LOSS;
		}

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
				this.changes.add(new Icon(currentX, currentY, this.theBoard[currentX][currentY].getRep()));
			}

			// Add adjacent 0 tiles to list
			this.findAdjacentZero(currentX, currentY, adjacent);
		}

		if (this.tilesLeft <= 0)
		{
			ret = State.WON;
			this.checkFlags();
		}

		return ret;
	}

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	public void buildBoard(int x, int y)
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
						this.changes.add(new Icon(currentX, currentY, this.theBoard[currentX][currentY].getRep()));
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

	/*
	* Returns a list of flags on the board. This will be used to reveal which
	* flags are correct at the end of the game so we only return flags that are
	* incorrect and leave correct ones as they are.
	*/
	private void checkFlags()
	{
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getMine()) && this.theBoard[x][y].getFlag())
				{
					changes.add(new Icon(x, y, IconRepresentation.FLAG_WRONG));
				}	
				
			}
		}
	}

	/*
	* Returns a list of mines on the board. This will be used to reveal mines at
	* end of the game so we only need to return the mines that haven't been clicked on
	* and haven't been flagged.
	*/
	private void revealMines()
	{
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getRevealed()) && this.theBoard[x][y].getMine() && !(this.theBoard[x][y].getFlag()))
				{
					changes.add(new Icon(x, y, IconRepresentation.MINE));
				}	
			}
		}
	}
	
	/* GETTERS */

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

	public boolean getMine(int x, int y)
	{
		return this.theBoard[x][y].getMine();
	}

	public boolean getRevealed(int x, int y)
	{
		return this.theBoard[x][y].getRevealed();
	}

	/* SETTERS */

	public void setFlag(int x, int y)
	{
		this.theBoard[x][y].setFlag();
	}

} 
