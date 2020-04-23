import java.util.*;

public class Application implements ApplicationInterface
{
	private UIInterface ui;
	private Game game;

	public Application(UIInterface ui)
	{
		this.ui = ui;
	}

	public void startGame(int dimension)
	{
		// Create Game object
		this.game = new Game(dimension);
		this.ui.startGame(dimension);
	}

	public void hintClicked()
	{}

	public void restartClicked()
	{}

	public void tileClicked(int x, int y)
	{
		State state = this.game.makeMove(x,y);
		this.displayChanges();
	}

	public void placeFlag(int x, int y)
	{
		this.game.placeFlag(x,y);
		this.displayChanges();
	}

	private void displayChanges()
	{
		ArrayList<TileChange> changes = this.game.getChanges();
		while (!changes.isEmpty())
		{
			TileChange change = changes.remove(0);
			this.ui.displayTile(change.getX(), change.getY(), change.getRep());
		}
	}
}
