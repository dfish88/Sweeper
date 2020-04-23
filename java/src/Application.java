import java.util.ArrayList;
import java.util.Timer; 
import java.util.TimerTask;

public class Application implements ApplicationInterface
{
	private UIInterface ui;
	private Game game;
	private Timer timer;

	public Application(UIInterface ui)
	{
		this.ui = ui;
		this.timer = new Timer();
	}

	public void startGame(int dimension)
	{
		this.game = new Game(dimension);
		this.ui.startGame(dimension);
		this.startTimer();
	}

	public void hintClicked()
	{
		State state = this.game.hint();
		this.displayChanges();
		this.displayFace(state);
	}

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

	private void updateTime()
	{
		this.ui.displayTime(this.game.getGameTime());
	}

	private void startTimer()
	{
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				Application.this.updateTime();
			}
		};
		this.timer.scheduleAtFixedRate(task, 0, 1000);
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
