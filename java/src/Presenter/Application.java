package Presenter;

import View.UIInterface;
import Model.Game;
import Model.TileChange;
import Model.State;

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
		State state = this.game.hint();
		this.processResults(state);
	}

	public void restartClicked()
	{
		this.ui.restart();

		this.game = null;
		this.enabled = true;
		this.timer.cancel();
		this.timer = null;
	}

	public void tileClicked(int x, int y)
	{
		State state = this.game.makeMove(x,y);
		this.processResults(state);
	}

	public void placeFlag(int x, int y)
	{
		this.game.placeFlag(x,y);
		this.displayResults();
	}

	public void mousePressed()
	{
		this.ui.displayFace(FaceRepresentation.SURPRISED);
	}

	public void mouseReleased()
	{
		this.ui.displayFace(FaceRepresentation.SMILE);
	}

	private void processResults(State state)
	{
		this.displayResults();
		this.displayFace(state);

		if (state == State.WON || state == State.LOST)
		{
			this.ui.disable();
			this.timer.cancel();
		}
	}

	private void displayResults()
	{
		ArrayList<TileChange> changes = this.game.getChanges();

		while (!changes.isEmpty())
		{
			TileChange change = changes.remove(0);

			int x = change.getX();
			int y = change.getY();
			TileRepresentation rep = change.getRep();		

			this.ui.displayTile(x, y, rep);
		}
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

			case LOST:
				this.ui.displayFace(FaceRepresentation.DEAD);
				break;
		}
	}

	private void startTimer()
	{
		this.timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				Application.this.ui.displayTime(Application.this.game.getGameTime());
			}
		};
		this.timer.scheduleAtFixedRate(task, 0, 1000);
	}
}
