/*
*	Copyright (C) 2019-2020  Daniel Fisher
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package Presenter;

import View.UIInterface;
import Model.Game;
import Model.TileChange;
import Model.State;

import java.util.ArrayList;
import java.util.Timer; 
import java.util.TimerTask;

/**
* This class implements the ApplicationInterface and plays the role of the Presenter.
*
* @author Daniel Fisher
*/
public class Application implements ApplicationInterface
{
	private UIInterface ui; 	// The view interface used to update the UI
	private Game game; 		// The model object used to update the model
	private Timer timer; 		// Timer object that polls the game timer

	/**
	* Constructor that takes a UI that implements the UIInterface that will
	* be used to update the View module
	*/
	public Application(UIInterface ui)
	{
		this.ui = ui;
	}

	/**
	* This method is called when the game is started and a dimension is
	* provided. This method starts the game, the ui, and the timer.
	*
	* @param dimension	the dimension of the game board
	*/
	public void startGame(int dimension)
	{
		this.game = new Game(dimension);
		this.ui.startGame(dimension);
		this.startTimer();
	}

	/**
	* This method is called when the hint button is clicked. This calls
	* the hint method provided by the Model and then calls the process
	* results method providing the game state returned by the Model.
	*/
	public void hintClicked()
	{
		State state = this.game.hint();
		this.processResults(state);
	}

	/**
	* This method is called when the restart button is clicked. This calls
	* the restart method in the View module, discards the current game and 
	* timer.
	*/
	public void restartClicked()
	{
		this.ui.restart();

		this.game = null;
		this.timer.cancel();
		this.timer = null;
	}

	/**
	* This method is called when a game tile is clicked and the coordinates of
	* the tile are provided. This calls the make move method provided by the
	* Model and calls the process results method providing the game state
	* returned by the Model.
	*
	* @param x	the x coordinate of the tile clicked
	* @param y	the y coordinate of the tile clicked
	*/
	public void tileClicked(int x, int y)
	{
		State state = this.game.makeMove(x,y);
		this.processResults(state);
	}

	/**
	* This method is called when a tile is right clicked (flag placed) and the
	* coordinates of the tile are provided. This calls the place flag method
	* provided by the model and then calls the display results method.
	*
	* @param x	the x coordinate of the tile clicked
	* @param y	the y coordinate of the tile clicked
	*/
	public void placeFlag(int x, int y)
	{
		this.game.placeFlag(x,y);
		this.displayResults();
	}

	/**
	* Called when the mouse has been pressed on a tile but not released. This
	* tells the View module to display the surprised face.
	*/
	public void mousePressed()
	{
		this.ui.displayFace(FaceRepresentation.SURPRISED);
	}

	/**
	* Called when the mouse has been released on a tile. This tells the View
	* module to display the smile face
	*/
	public void mouseReleased()
	{
		this.ui.displayFace(FaceRepresentation.SMILE);
	}

	/**
	* Helper method that takes a game state and calls the display results and
	* display face methods and disables the ui if the game has ended (won or lost)
	*
	* @param state		the game state
	*/
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

	/**
	* Helper method that displays all current changes accumlated by the Model.
	*/
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

	/**
	* Helper method that tells the View to display a face based on the game state
	* provided.
	*
	* @param state		the game state
	*/
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

	/**
	* Helper method that starts the timer which will probe the game timer
	* and update the View module.
	*/
	private void startTimer()
	{
		this.timer = new Timer();
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				String gameTime = Application.this.game.getGameTime();
				Application.this.ui.displayTime(gameTime);
			}
		};
		this.timer.scheduleAtFixedRate(task, 0, 1000);
	}
}
