public class Game
{
	private Board gameBoard;
	private Timer gameTimer;
	private int dimension;
	private boolean firstMove;
	private State state;

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimenstion);
		this.dimension = dimension;
		this.gameTimer = new Timer();
		this.player = new Player();
		this.firstMove = false;
		this.state = State.RUNNING;
	}

	public void makeMove(int x, int y)
	{
		if (this.firstMove)
		{
			this.gameBoard.buildBoard(x, y);
			this.firstMove = false;
		}

		this.gameBoard.makeMove(x, y);
	}

	/*
	* Picks a random non-mine, non-revealed tile
	* and makes a move.
	*/
	public void hint()
	{
		if (this.state != State.RUNNING)
			return;

		int randX = rand.nextInt(this.dimension);
		int randY = rand.nextInt(this.dimension);

		while (!this.firstMove && (this.gameBoard.getRevealed(randX, randY) || this.gameBoard.getMine(randX, randY)))
		{
			randX = rand.nextInt(this.dimension);
			randY = rand.nextInt(this.dimension);
		}

		this.gameBoard.makeMove(randX, randY);
	}

	public void placeFlag()
	{}

	public void restart()
	{}
}
