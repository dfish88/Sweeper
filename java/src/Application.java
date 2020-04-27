import java.util.ArrayList;
import java.util.Timer; 
import java.util.TimerTask;

public class Application implements ApplicationInterface
{
	private UIInterface ui;
	private Game game;
	private Timer timer;
	private boolean enabled;

	public Application(UIInterface ui)
	{
		this.ui = ui;
		this.timer = new Timer();
		this.enabled = true;
	}

	public void startGame(int dimension)
	{
		this.game = new Game(dimension);
		this.ui.startGame(dimension);
		this.startTimer();
	}

	public void hintClicked()
	{
		if (!this.enabled)
			return;
		
		State state = this.game.hint();
		this.displayResults(state);
		this.checkForEndConditions(state);
	}

	public void restartClicked()
	{
		this.ui.restart();
		this.restart();
	}

	public void tileClicked(int x, int y)
	{
		if (!this.enabled)
			return;

		State state = this.game.makeMove(x,y);
		this.displayResults(state);
		this.checkForEndConditions(state);
	}

	public void placeFlag(int x, int y)
	{
		if (!this.enabled)
			return;

		this.game.placeFlag(x,y);
		this.displayChanges();
	}

	public void mousePressed()
	{
		if (!this.enabled)
			return;

		this.ui.displayFace(FaceRepresentation.SURPRISED);
	}

	public void mouseReleased()
	{
		if (!this.enabled)
			return;

		this.ui.displayFace(FaceRepresentation.SMILE);
	}

	private void restart()
	{
		this.game = null;
		this.timer.cancel();
		this.enabled = true;
	}

	private void displayResults(State state)
	{
		this.displayChanges();
		this.displayFace(state);

	}

	private void checkForEndConditions(State state)
	{
		if (state == State.WON || state == State.LOSS)
		{
			this.disable();
		}
	}

	private void disable()
	{
		this.enabled = false;
		this.timer.cancel();
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
