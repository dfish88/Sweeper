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
import Presenter.FaceRepresentation;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

/**
* This class provides methods to manage the top panel of the UI screen. This includes
* displaying the time, listening to the hint and restart buttons, and displaying
* the game face.
*
* @author Daniel Fisher
*/
public class UITopPanel extends JPanel
{
	private JButton face; // Face icon
	private JButton restart; // restart button
	private JButton hint; // hint button
	private JLabel timer; // game timer
	private ApplicationInterface app; // reference to the presenter 

	/**
	* Constructor that initializes the top panel and saves a reference to
	* the provided presenter interface
	*
	* @param app	the presenter interface
	*/
	public UITopPanel(ApplicationInterface app)
	{
		super();
		this.app = app;
		this.setLayout(new GridLayout(1,4));
		this.setBackground(Color.LIGHT_GRAY);
		this.setUpTopPanel();
	}

	/**
	* This method is called when the game is won/lost to disable the 
	* hint and restart buttons
	*/
	public void shutOff()
	{
		this.removeAllActionListeners(this.hint);
	}

	/**
	* This method is called to restart the top panel. This method resets the timer,
	* sets the face back to a smile and removes current mouse listeners from the
	* hint and restart buttons.
	*/
	public void restart()
	{
		// Remove action listeners from restart and hint buttons
		this.removeAllActionListeners(this.hint);
		this.removeAllActionListeners(this.restart);

		// Set Timer back to 0
		this.timer.setText("00 : 00");

		this.displayFace(FaceRepresentation.SMILE);
	}

	/**
	* This method displays the corresponding face image for the provided
	* face representation
	*
	* @param rep	the face representation
	*/
	public void displayFace(FaceRepresentation rep)
	{
		this.face.setIcon(ImageUtilities.getFaceImage(rep));
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
		this.timer.setText(time);
	}

	/**
	* This method is called when the game is started and adds listeners to
	* the hint and restart buttons
	*/
	public void startGame()
	{
		ActionListener listener = new TopPanelListener();
		this.restart.addActionListener(listener);
		this.hint.addActionListener(listener);
	}

	/**
	* Helper method that removes all action listeners from the provided 
	* JButton. This is used to disable the hint and restart buttons when
	* the game is won/lost
	*
	* @param btn	the JButton
	*/
	private void removeAllActionListeners(JButton btn)
	{
		for (ActionListener al : btn.getActionListeners())
		{
			btn.removeActionListener(al);
		}
	}

	/**
	* This method is called by the action listener when the hint button is
	* clicked. This method calls the corresponding method provided by the
	* presenter module.
	*/
	private void hintClicked()
	{
		this.app.hintClicked();
	}

	/**
	* This method is called by the action listener when the restart button is
	* clicked. This method calls the corresponding method provided by the
	* presenter module.
	*/
	private void restartClicked()
	{
		this.app.restartClicked();
	}

	/**
	* Helper method that initializes the top panel by creating the buttons, faces,
	* timer label, and borders.
	*/
	private void setUpTopPanel()
	{
		this.restart = new JButton("Restart?");
		this.restart.setBackground(Color.LIGHT_GRAY);
		this.restart.setBorder(BorderFactory.createRaisedBevelBorder());
		this.face = new JButton();
		this.face.setIcon(ImageUtilities.getFaceImage(FaceRepresentation.SMILE));
		this.face.setBorder(null);
		this.face.setBackground(Color.LIGHT_GRAY);
		this.hint = new JButton("Hint?");
		this.hint.setBackground(Color.LIGHT_GRAY);
		this.hint.setBorder(BorderFactory.createRaisedBevelBorder());
		this.timer = new JLabel("00 : 00");
		this.timer.setHorizontalAlignment(JLabel.CENTER);
		this.timer.setVerticalAlignment(JLabel.CENTER);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.hint, 0);
		this.add(this.face,1);
		this.add(this.restart,2);
		this.add(this.timer,3);
	}

	/**
	* Action listern that is added to the hint and restart buttons on the top panel.
	*
	* @author Daniel Fisher
	*/
	private class TopPanelListener implements ActionListener
	{
		/**
		* Constructor
		*/
		public TopPanelListener()
		{
		}

		/**
		* This method is called when a button is clicked on and the
		* action event is provided. This method determines if the
		* restart or hint button was clicked and calls the corresponding
		* method in the UITopPanel class.
		*
		* @param e	the action event
		*/
		public void actionPerformed(ActionEvent e)
		{
			JButton btn = (JButton) e.getSource();

			if (btn.equals(UITopPanel.this.hint))
			{
				UITopPanel.this.hintClicked();
			}
			else if (btn.equals(UITopPanel.this.restart))
			{
				UITopPanel.this.restartClicked();
			}
		}
	}
}
