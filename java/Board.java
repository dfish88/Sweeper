public class Board
{
	private Tile[][] theBoard;
	private int dimension;

	public Board(int dimension)
	{
		this.dimension = dimension;
		for (int x = 0; x < dimension; x++)
		{
			for(int y = 0; y < dimension; y++)
			{
				this.theBoard[x][y] = new Tile(x, y);
			}
		}
	}

	public void addTile(Tile newTile, int x, int y)
	{
		// Need copy of new tile
		this.theBoard[x][y] = newTile; 
	}
}
