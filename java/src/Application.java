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
		this.displayFace(state);
	}

	public void placeFlag(int x, int y)
	{
		this.game.placeFlag(x,y);
		this.displayChanges();
	}

	public void mousePressed()
	{
		this.ui.displayFace(FaceRepresentation.SURPRISED);
	}

	public void mouseReleased()
	{
		this.ui.displayFace(FaceRepresentation.SMILE);
	}

	private void displayFace(State state)
	{
		switch (state)
		{
			case RUNNING:
				this.ui.displayFace(FaceRepresentation.SMILE);
				break;

			case WON:
				this.ui.displayFace(FaceRepresentation.GLASSES);
				break;

			case LOSS:
				this.ui.displayFace(FaceRepresentation.DEAD);
				break;
		}
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
