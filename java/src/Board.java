import java.util.*;
import java.awt.*;
import java.io.*;

public class Board 
{ 
	private AbstractMineField field; 	// Stores all the tiles
	private int dimension;		
	private ArrayList<Icon> changes;	// Stores all changes made

	/*
	* Creates a Board object with a MineField being injected.
	*/
	public Board(int dimension, AbstractMineField field)
	{
		this.dimension = dimension;
		this.field = field;
		this.changes = new ArrayList<>();
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
		return this.field.getFlag(x, y);
	}

	public boolean getMine(int x, int y)
	{
		return this.field.getMine(x, y);
	}

	public boolean getRevealed(int x, int y)
	{
		return this.field.getRevealed(x, y);
	}

	/* SETTERS */

	public void setFlag(int x, int y)
	{
		this.field.setFlag(x, y);
	}

	/*
	* Reveals a square, used when square is clicked on.  If the tile
	* is adjacent to 0 mines then it also reveals all adjacent 0 tiles.
	*/
	public State makeMove(int x, int y)
	{
		State ret = State.RUNNING;
		if (this.field.getRevealed(x,y))
			return ret;

		// Reveal current tile
		this.field.setRevealed(x,y);
		this.changes.add(new Icon(x, y, this.field.getRep(x,y)));

		if (this.field.getMine(x,y))
		{
			this.revealMines();
			this.checkFlags();
			ret = State.LOSS;
		}

		// Create list of all adjacent tiles if tile clicked on is 0
		ArrayList<Point> adjacent = new ArrayList<>();
		if (!this.field.getMine(x,y) && this.field.getAdjacent(x,y) == 0)
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
			if (!(this.field.getRevealed(currentX, currentY)))
			{
				this.field.setRevealed(currentX, currentY);
				this.changes.add(new Icon(currentX, currentY, this.field.getRep(currentX, currentY)));
			}

			// Add adjacent 0 tiles to list
			this.findAdjacentZero(currentX, currentY, adjacent);
		}

		if (this.field.getTilesLeft() <= 0)
		{
			ret = State.WON;
			this.checkFlags();
		}

		return ret;
	}

	/* PRIVATE HELPERS */

	/*
	* Pushes adjacent 0 tiles on to stack so we can check tiles adjacent to those tiles too.
	* When we get to non zero, non mine, adjacent tiles we reveal them and don't push them on stack.
	*/
	private void findAdjacentZero(int x, int y, ArrayList<Point> a)
	{
		// Used to calculate coordinates of all 8 adjacent tiles
		int[] delta = {-1,-1,-1,0,-1,1,0,-1,0,1,1,-1,1,0,1,1};

		// Check all 8 adjacent tiles 0 tiles
		for (int i = 0; i < delta.length; i = i + 2)
		{

			int currentX = x + delta[i];
			int currentY = y + delta[i+1];

			try
			{
				// add 0 tiles to the list
				if (this.field.getAdjacent(currentX, currentY) == 0 && !this.field.getRevealed(currentX, currentY))
					a.add(new Point(currentX, currentY));

				// Reveal non-zero tiles
				else
				{
					if(!(this.field.getRevealed(currentX, currentY)))
					{
						this.field.setRevealed(currentX, currentY);
						this.changes.add(new Icon(currentX, currentY, this.field.getRep(currentX, currentY)));
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				continue;
			}
		}
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
				if (!(this.field.getMine(x, y)) && this.field.getFlag(x, y))
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
				if (!(this.field.getRevealed(x, y)) && this.field.getMine(x, y) && !(this.field.getFlag(x, y)))
				{
					changes.add(new Icon(x, y, IconRepresentation.MINE));
				}	
			}
		}
	}
} 
