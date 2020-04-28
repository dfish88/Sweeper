package Model;

import java.util.*;

public class Game
{
	private Board gameBoard;
	private GameTimer gameTimer;
	private int dimension;
	private boolean firstMove;
	private State state;

	public Game(int dimension)
	{
		this.firstMove = true;
		this.dimension = dimension;
		this.state = State.RUNNING;
		this.gameTimer = new GameTimer();
	}

	public State makeMove(int x, int y)
	{
		if (this.firstMove)
		{
			this.firstMove = false;
			AbstractMineField field = new MineField(x, y, this.dimension);
			this.gameBoard = new Board(this.dimension, field);
			this.gameTimer.start();
		}

		this.state = this.gameBoard.makeMove(x, y);
		if (this.state != State.RUNNING)
			this.gameTimer.stop();

		return this.state;
	}

	/*
	* Picks a random non-mine, non-revealed tile
	* and makes a move.
	*/
	public State hint()
	{
		if (this.state != State.RUNNING)
			return this.state;

		Random rand = new Random();

		int randX = rand.nextInt(this.dimension);
		int randY = rand.nextInt(this.dimension);

		while (!this.firstMove && (this.gameBoard.getRevealed(randX, randY) || this.gameBoard.getMine(randX, randY)))
		{
			randX = rand.nextInt(this.dimension);
			randY = rand.nextInt(this.dimension);
		}

		return this.makeMove(randX, randY);
	}

	public void placeFlag(int x, int y)
	{
		this.gameBoard.setFlag(x, y);
	}

	public ArrayList<TileChange> getChanges()
	{
		return this.gameBoard.getChanges();
	}

	public String getGameTime()
	{
		return this.gameTimer.getTime();
	}
}
