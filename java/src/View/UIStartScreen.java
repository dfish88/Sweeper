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

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.border.*;

/**
* This class provides methods to manage and listen to the buttons on the
* start screen that allow the user to choose the difficulty level of
* the game.
*
* @author Daniel Fisher
*/
public class UIStartScreen extends JPanel
{
	private JButton easy; // easy difficulty
	private JButton hard; // hard difficulty
	private ApplicationInterface app; // reference to presenter

	private final int SCREEN_DIM = 400; // start screen dimension

	/**
	* Constructor that calls methods to build the start screen and
	* saves the provided reference to the presenter module.
	*
	* @param app	the presenter module
	*/
	public UIStartScreen(ApplicationInterface app)
	{
		super();
		this.app = app;
		this.setLayout(new GridLayout(1,2));
		this.setUpStartScreen(new StartScreenListener());
	}

	/**
	* Helper method that builds the start screen by creating the easy
	* and hard button and adding the provided action listener to each.
	*
	* @param listener	the action listener
	*/
	private void setUpStartScreen(ActionListener listener)
	{
		// Start window with 2 options
		this.easy = new JButton();
		this.easy.setText("8x8 (Easy)");
		this.easy.setBackground(Color.LIGHT_GRAY);
		this.easy.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.easy.addActionListener(listener);
		this.hard = new JButton();
		this.hard.setText("16x16 (Hard)");
		this.hard.setBackground(Color.LIGHT_GRAY);
		this.hard.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.hard.addActionListener(listener);
		this.add(this.easy);
		this.add(this.hard);
		this.setPreferredSize(new Dimension(this.SCREEN_DIM, this.SCREEN_DIM));
	}

	/**
	* This method is called by the action listener when a difficulty 
	* has been choosen. The corresponding method in the presenter is
	* called providing the dimension of the game.
	*
	* @param dimension	the dimension of the board
	*/
	private void startGame(int dimension)
	{
		this.app.startGame(dimension);
	}

	/**
	* The action listener that is added to the easy and hard buttons on the
	* start screen. This listener calls the start game method in the UIStartScreen
	* class with the corresponding game dimension (8x8 for easy and 16x16 for hard)
	*
	* @author Daniel Fisher
	*/
	private class StartScreenListener implements ActionListener
	{
		private final int EASY_DIM = 8; // Easy game dimension
		private final int HARD_DIM = 16; // Hard game dimension

		/**
		* Constructor
		*/
		public StartScreenListener()
		{
		}

		/**
		* This method is called when the easy or hard button is clicked. This
		* method calls the start game method in UIStartScreen providing the
		* corresponding game dimension
		*
		* @param e	the action event
		*/
		public void actionPerformed(ActionEvent e)
		{
			JButton btn = (JButton) e.getSource();

			// Call start game from UIStartScreen
			if (btn.equals(UIStartScreen.this.easy))
			{
				UIStartScreen.this.startGame(EASY_DIM);
			}
			else if (btn.equals(UIStartScreen.this.hard))
			{
				UIStartScreen.this.startGame(HARD_DIM);
			}
		}
	}

}
