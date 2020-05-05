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

package View;

import Presenter.ApplicationInterface;
import Presenter.Application;
import Presenter.TileRepresentation;
import Presenter.FaceRepresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* This class provides methods to manage and update the View module which is
* used by the presenter module. This includes displaying the game time, the
* results of a move made (changing tiles), and displaying face images.
*
* @author Daniel Fisher
*/
public class UI implements UIInterface
{
	private UIStartScreen start; // The start screen
	private UITopPanel top; // The top panel
	private UIBoard board; // The game board
	private JFrame window; // The main window that holds everyting
	private ApplicationInterface app; // Reference to the presenter

	/**
	* The constructor that starts the entire application by creating
	* a presenter and initializing the start screen and main window
	*/
	public UI()
	{
		this.app = new Application(this);

		this.window = new JFrame("Mine Sweeper!");
		this.top = new UITopPanel(this.app);
		this.start = new UIStartScreen(this.app);

		this.window.add(this.top, BorderLayout.PAGE_START);
		this.window.add(this.start, BorderLayout.CENTER);
		this.window.pack();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setResizable(false); 
		this.window.setVisible(true);
	}

	/**
	* This method disables the UI when the game is won/lost to prevent 
	* further moves from being made. This method disables the top panel
	* and game board
	*/
	public void disable()
	{
		this.top.shutOff();
		this.board.shutOff();
	}

	/**
	* This method is called when the game is started by choosing a difficulty.
	* This method starts the game by creating a UIBoard object and removes
	* the start screen buttons from the window
	*
	* @param dimension	the game dimension
	*/
	public void startGame(int dimension)
	{
		this.board = new UIBoard(dimension, this.app);
		this.window.remove(this.start);
		this.window.add(this.board, BorderLayout.CENTER);
		this.window.pack();
		this.top.startGame();
	}

	/**
	* This method is called when the restart button is clicked on the top panel.
	* This method removes the game board from the window, re-adds the start screen,
	* resets the top panel including the timer and face icon
	*/
	public void restart()
	{
		this.window.remove(this.board);
		this.window.add(this.start, BorderLayout.CENTER);
		this.top.restart();
		this.start.revalidate();
		this.start.repaint();
		this.window.pack();
	}

	/**
	* This method displays the image representation of the provided tile representation
	* at the provided x and y coordinates.
	*
	* @param x	the x coordinate of the tile
	* @param y	the y coordinate of the tile
	* @param rep	the tile representation
	*/
	public void displayTile(int x, int y, TileRepresentation rep)
	{
		this.board.updateTileIcon(x, y, rep);		
	}

	/**
	* This method displays the image representation of the provided face represetation
	*
	* @param rep	the face representation
	*/
	public void displayFace(FaceRepresentation rep)
	{
		this.top.displayFace(rep);
	}

	/**
	* This method displays the provided game time. The format expected is
	* "xx : yy" where xx is the minutes elapsed with a leading 0 if necessary
	* and yy is the seconds elapsed in for the current minute with a leading
	* 0 if necessary.
	* 
	* @param time	the time to display.
	*/
	public void displayTime(String time)
	{
		this.top.displayTime(time);
	}
}
