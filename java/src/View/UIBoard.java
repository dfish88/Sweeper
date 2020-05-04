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
import Presenter.TileRepresentation;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

/**
* This class provides methods to manage the tiles of the UI board such as
* initializing the board and changing icons of tiles. 
*
* @author Daniel Fisher
*/
public class UIBoard extends JPanel
{
	private UITile board[][]; // Holds all the buttons for each tile
	private int dimension;
	private ApplicationInterface app; // The presenter module

	/**
	* The constructor that builds a board based on the given dimension
	* and stores a reference to the presenter module
	*
	* @param dimension	the dimension of the board
	* @param app		the presenter module
	*/
	public UIBoard(int dimension, ApplicationInterface app)
	{
		super();
		this.app = app
		this.dimension = dimension;
		this.setLayout(new GridLayout(dimension, dimension));
		this.buildBoard(dimension, new GameListener());
	}

	/**
	* This method updates the image icon at a given coordinates based on the
	* given tile representation.
	*
	* @param x	the x coordinate of the tile
	* @param y	the y coordinate of the tile
	* @param rep	the tile representation
	*/
	public void updateTileIcon(int x, int y, TileRepresentation rep)
	{
		this.board[x][y].setIcon(ImageUtilities.getTileImage(rep));
	}

	/**
	* This method disables each tile rendering future clicks on tiles useless. 
	* This method is called when the game is won/lost to prevent further moves
	* from being made
	*/
	public void shutOff()
	{
		for(int i = 0; i < this.dimension; i++)
		{
			for(int j = 0; j < this.dimension; j++)
			{
				this.removeAllMouseListeners(this.board[i][j]);
			}
		}
	}

	/**
	* Helper method that loops through all button listerns on a given JButton
	* and removes them. This method is called on each tile of the board by
	* the shutOff method.
	*
	* @param btn	the JButton
	*/
	private void removeAllMouseListeners(JButton btn)
	{
		for (MouseListener ml : btn.getMouseListeners())
		{
			btn.removeMouseListener(ml);
		}
	}

	/**
	* This method is called by the mouse listener when a button has been
	* left clicked. This calls the corresponding method in the presenter
	* module.
	*
	* @param x	the x coordinate of the button clicked
	* @param y	the y coordinate of the button clicked
	*/
	private void leftClick(int x, int y)
	{
		this.app.tileClicked(x,y);
	}

	/**
	* This method is called by the mouse listener when a button has been
	* right clicked. This calls the correseponding method in the presenter
	* module.
	*
	* @param x	the x coordinate of the button clicked
	* @param y	the y coordinate of the button clicked
	*/
	private void rightClick(int x, int y)
	{
		this.app.placeFlag(x,y);
	}

	/**
	* This method is called by the mouse listener when a mouse button
	* has been pressed but not released. This method calls the the
	* corresponding method in the presenter module. This allows the
	* face to be animated when buttons are clicked.
	*/
	private void mousePressed()
	{
		this.app.mousePressed();
	}

	/**
	* This method is called by the mouse listener when a mouse button
	* has been released. This method calls the the
	* corresponding method in the presenter module. This allows the
	* face to be animated when buttons are clicked.
	*/
	private void mouseReleased()
	{
		this.app.mouseReleased();
	}

	/**
	* Helper method that builds the board using the dimension provided and
	* adds the given mouse listener to each board tile.
	*
	* @param dimension	the dimension of the board
	* @param listern	the mouse listener
	*/
	private void buildBoard(int dimension, MouseListener listener)
	{
		this.board = new UITile[dimension][dimension];
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				this.board[i][j] = new UITile(i,j);
				this.board[i][j].addMouseListener(listener);	
				this.board[i][j].setIcon(ImageUtilities.getTileImage(TileRepresentation.COVERED));
				this.board[i][j].setBorder(null);
				this.add(this.board[i][j]);
			}
		}
	}

	
	/**
	* Game listener class the is add to each tile on the game board. This listener
	* allows the user to make moves (left click), place flags (right click), and
	* animates the face on clicks.
	* 
	* @author Daniel Fisher
	*/
	private class GameListener implements MouseListener
	{
		/**
		* Constructor
		*/
		public GameListener()
		{
		}

		/**
		* Called when a button has been pressed on but not released.
		* This method calls the correseponding method in the UI board
		* and allows the face the be animated on clicks.
		*
		* @param e	the mouse event
		*/
		public void mousePressed(MouseEvent e) 
		{
			// Change face to surprised
			if (SwingUtilities.isLeftMouseButton(e))
			{
				UIBoard.this.mousePressed();
			}
		}

		/**
		* Called when a button has been released on. This method calls the corresponding
		* mouse released method as well as the right or left click method in the
		* UI board class.
		*
		* @param e	mouse event
		*/
		public void mouseReleased(MouseEvent e)
		{
			UITile tile = (UITile) e.getSource();

			int x = tile.getGridX();
			int y = tile.getGridY();

			// Left click reveals tiles, there is a chance a mine has been clicked on.
			if (SwingUtilities.isLeftMouseButton(e))
			{				
				UIBoard.this.mouseReleased();
				UIBoard.this.leftClick(x, y);
			}
			// Right click places flag
			else if (SwingUtilities.isRightMouseButton(e))
			{
				try
				{
					UIBoard.this.rightClick(x, y);
				}
				// Catches exception where players right clicks before making first move
				catch (Exception exc)
				{}
			}
		}

		/**
		* Not used but required by the interface.
		*
		* @param e	the mouse event
		*/
		public void mouseClicked(MouseEvent e)
		{}
		
		/**
		* Not used but required by the interface.
		*
		* @param e	the mouse event
		*/
		public void mouseEntered(MouseEvent e)
		{}

		/**
		* Not used but required by the interface.
		*
		* @param e	the mouse event
		*/
      		public void mouseExited(MouseEvent e)
		{}
	}
}
