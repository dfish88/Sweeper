import java.util.*;
import java.awt.*;
import java.io.*;

public class Board 
{ 
	private Tile[][] theBoard; 	// Stores all the tiles 
	private int dimension;		
	private Stack<Integer> changes;	// Stores all changes made
	private boolean firstMove;	// If this is first move
	private int tilesLeft;

	// Used to calculate coordinates of all 8 adjacent tiles
	private int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

	public Board(int dimension)
	{
		this.dimension = dimension;
		theBoard = new Tile[this.dimension][this.dimension];		
		this.changes = new Stack<>();
		this.firstMove = true;
		this.tilesLeft = 0;
	}

	/*
	* Called when first move (hint or click) is made.
	* We don't build board until first move to ensure that
	* first move isn't a mine.
	*/
	public void first(int x, int y)
	{
		this.buildBoard(x,y);
	}

	/*
	* Builds the board
	*/
	public void buildBoard(int x, int y)
	{
		addMines(x,y);
		placeAdjacent();
	}

	/*
	* Executes a hint.  Hint reveals a tile adjacent to
	* an already adjacent tile. We start by looking for
	* non zero adjacent tiles, if non found we then look
	* for adjacent zeros.
	*/
	public Stack<Integer> hint()
	{
		if (this.firstMove)
		{
			Random rand = new Random();

			int randX = rand.nextInt(dimension);
			int randY = rand.nextInt(dimension);

			this.revealTile(randX, randY);
			this.tilesLeft--;
			
			Stack<Integer> s = new Stack<>();
			s.addAll(this.changes);
			this.changes.clear();
			return s;
		}

		Stack<Integer> stack = new Stack<>();

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
	}

	/*
	* Restarts game when restart button is pressed by rebuilding board.
	*/
	public void restart()
	{
		if (this.firstMove)
			return;

		this.changes.clear();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (this.theBoard[x][y].getRevealed() || this.theBoard[x][y].getFlag() || this.theBoard[x][y].getMine())
				{
					this.changes.push((int)' ');
					this.changes.push(y);
					this.changes.push(x);
				}
			}
		}

		theBoard = new Tile[this.dimension][this.dimension];
		this.firstMove = true;
		this.tilesLeft = 0;
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public void revealTile(int x, int y)
	{
		// Build board if first move (hint or click)
		if (this.firstMove)
		{
			this.first(x,y);
			this.firstMove = false;
		}

		if (this.theBoard[x][y].getRevealed())
			return;

		if (this.theBoard[x][y].getMine())
			this.changes.push((int)'b');
		else
			this.changes.push(this.theBoard[x][y].getAdjacent() + '0');
			
		this.changes.push(y);
		this.changes.push(x);

		// Reveal adjacent 0s if 0 is clicked on
		if (this.theBoard[x][y].getAdjacent() == 0 && !(this.theBoard[x][y].getMine())) 
		{
			this.revealAdjacentTiles(x,y);
		}
		// Reveals tile if non 0 clicked on
		else
		{
			this.tilesLeft--;
			this.theBoard[x][y].setRevealed();
		}
			
			
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

	/* PRIVATE HELPERS */

	/*
	* Randomly places at most dimension mines on the board.
	* If there is overlap we simply skip that mine which is
	* how we can get less than dimension mines.
	*/
	private void addMines(int x, int y)
	{
		Random rand = new Random();

		// Loop dimension times to place mines
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
	}

	/*
	* Places the non mine tiles on the board
	*/
	private void placeAdjacent()
	{
		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {       
				if (this.theBoard[i][j] == null)
				{
					this.theBoard[i][j] = new Tile(calculateAdjacent(i,j), false);
					this.tilesLeft++;
				}
                        }
                }
	}

	/*
	* Checks for mines adjacent to the tile at (x,y)
	*/
	private int calculateAdjacent(int x, int y)
	{
		int mineCount = 0;

		// Check all 8 adjacent tiles for mines
		for (int i = 0; i < this.delta.length; i = i + 2)
		{
			mineCount += checkForMine(x + this.delta[i], y + this.delta[i+1]);
		}
		return mineCount;
	}

	/*
	* Returns 1 if tile at x y is 0, 0 otherwise.
	*/
	private int checkForZero(int x, int y)
	{
		try
		{
			if (this.theBoard[x][y].getAdjacent() == 0 && this.theBoard[x][y].getRevealed() == false)
				return 1;
			else
				return 0;
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
	* Returns 1 if tile at x y is a mine, 0 otherwise.
	*/
	private int checkForMine(int x, int y)
	{
		try
		{
			if (this.theBoard[x][y].getMine())
				return 1;
			else
				return 0;
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
	* Check if tile at x y is not a mine and not revealed, then check
	* if it is adjacent to at least one non mine, revelaed tile.
	*/
	private boolean hintTile(int x, int y, boolean zeros)
	{
		if (this.theBoard[x][y].getMine() || this.theBoard[x][y].getRevealed())
			return false;
		else if(zeros == false && this.theBoard[x][y].getAdjacent() == 0)
			return false;
		else
		{
			if (this.checkAdjacentHint(x,y))
				return true;
			else
				return false;
		}
	}

	/*
	* Check all adjacent tiles to x y for revealed non zero tiles.
	*/
	private boolean checkAdjacentHint(int x, int y)
	{	
		for (int i = 0; i < this.delta.length; i = i + 2)
		{	
			if (this.checkAdjacentTileHint(x + this.delta[i], y + this.delta[i+1]))
				return true;
		}

		if (this.surrounded(x,y))
			return true;

		return false;
	}

	/*
	* Return true if tile x y is a revealed tile.
	*/
	private boolean checkAdjacentTileHint(int x, int y)
	{
		try
		{
			if (this.theBoard[x][y].getRevealed())
			{
				return true;
			}
			else
				return false;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	/*
	* Returns true if tile is completely surrounded by mine.
	* This is used by hint because hint reveals tiles adjacent to
	* already revealed tiles but tiles surrounded by mines would
	* never be revealed.
	*/
	private boolean surrounded(int x, int y)
	{
		if (this.theBoard[x][y].getAdjacent() < 3)
			return false;
	
		// Check all adjacent directions, return true if surrounded by mines
		boolean sur = true;
		for (int i = 0; i < this.delta.length; i = i + 2)
		{	
			if (!(this.checkAdjacentTileMine(x + this.delta[i], y + this.delta[i+1])))
				return false;
		}
		return sur;
	}

	/*
	* Returns true if x y is mine.
	*/
	private boolean checkAdjacentTileMine(int x, int y)
	{
		try
		{
			if (this.theBoard[x][y].getMine())
			{
				return true;
			}
			else
				return false;
		}
		catch (Exception e)
		{
			return true;
		}
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
			if (!(this.theBoard[currentX][currentY].getRevealed()))
			{
				this.theBoard[currentX][currentY].setRevealed();
				this.tilesLeft--;
			

				this.changes.push(this.theBoard[currentX][currentY].getAdjacent() + '0');
				this.changes.push(currentY);
				this.changes.push(currentX);
			}

			// Add adjacent 0 tiles to stack
			this.findAdjacentZero(currentX, currentY, adjacent);
		}
	}

	/*
	* Pushes adjacent 0 tiles on to stack so we can check tiles adjacent to those tiles too.
	* When we get to non zero, non mine, adjacent tiles we reveal them and don't push them on stack.
	*/
	private void findAdjacentZero(int x, int y, Stack<Integer> s)
	{
		// Check all 8 adjacent tiles for mines
		for (int i = 0; i < this.delta.length; i = i + 2)
		{
			if (this.checkForZero(x + this.delta[i], y + this.delta[i+1]) == 1)
			{
				s.push(y + this.delta[i+1]);
				s.push(x + this.delta[i]);
			}
			// The non 0 edges of the 0 area are covered in this case
			else
			{
				this.revealEdges(x + this.delta[i], y + this.delta[i+1]);
			}
		}
	}

	private void revealEdges(int x, int y)
	{
		// Try catch incase coordinates are off board
		try
		{
			if(!(this.theBoard[x][y].getRevealed()))
			{
				this.theBoard[x][y].setRevealed();
				this.changes.push(this.theBoard[x][y].getAdjacent() + '0');
				this.changes.push(y);
				this.changes.push(x);
				this.tilesLeft--;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
		}
	}

	/* GETTERS */

	/*
	* Returns a stack of x,y coordinates of flags on board.
	*/
	public Stack<Integer> getFlags()
	{
		Stack<Integer> stack = new Stack<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getRevealed()) && !(this.theBoard[x][y].getMine()) && this.theBoard[x][y].getFlag())
				{
					stack.push(y);
					stack.push(x);
				}	
				
			}
		}
		return stack;
	}

	/*
	* Returns a stack of x,y coordinates of mined on board.
	*/
	public Stack<Integer> getMines()
	{
		Stack<Integer> stack = new Stack<>();
		for (int x = 0; x < this.dimension; x++)
		{
			for (int y = 0; y < this.dimension; y++)
			{
				if (!(this.theBoard[x][y].getRevealed()) && this.theBoard[x][y].getMine() && !(this.theBoard[x][y].getFlag()))
				{
					stack.push(y);
					stack.push(x);
				}	
			}
		}
		return stack;
	}
	
	/*
	* Returns changes made to board.
	*/
	public Stack<Integer> getChanges()
	{
		Stack<Integer> ret = new Stack<>();
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

	/* SETTERS */

	public void setFlag(int x, int y)
	{
		this.theBoard[x][y].setFlag();
	}

} 
