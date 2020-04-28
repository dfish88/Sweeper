package View;

import Presenter.ApplicationInterface;
import Presenter.FaceRepresentation;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class UITopPanel extends JPanel
{
	private JButton face; // Face icon
	private JButton restart;
	private JButton hint;
	private JLabel timer;
	private ApplicationInterface app;

	public UITopPanel(ApplicationInterface a)
	{
		super();
		this.app = a;
		this.setLayout(new GridLayout(1,4));
		this.setBackground(Color.LIGHT_GRAY);
		this.setUpTopPanel();
	}

	public void shutOff()
	{
		this.removeAllActionListeners(this.hint);
	}

	public void restart()
	{
		// Remove action listeners from restart and hint buttons
		this.removeAllActionListeners(this.hint);
		this.removeAllActionListeners(this.restart);

		// Set Timer back to 0
		this.timer.setText("00 : 00");

		this.displayFace(FaceRepresentation.SMILE);
	}

	public void displayFace(FaceRepresentation rep)
	{
		this.face.setIcon(ImageUtilities.getFaceImage(rep));
	}

	public void displayTime(String time)
	{
		this.timer.setText(time);
	}

	public void startGame()
	{
		ActionListener listener = new TopPanelListener();
		this.restart.addActionListener(listener);
		this.hint.addActionListener(listener);
	}

	public void setApp(ApplicationInterface a)
	{
		this.app = a;
	}

	private void removeAllActionListeners(JButton btn)
	{
		for (ActionListener al : btn.getActionListeners())
		{
			btn.removeActionListener(al);
		}
	}

	private void hintClicked()
	{
		this.app.hintClicked();
	}

	private void restartClicked()
	{
		this.app.restartClicked();
	}

	/*
	* Puts buttons on top panel
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

	/*
	* Mouse listener registerd on all game buttons.
	*/
	private class TopPanelListener implements ActionListener
	{
		public TopPanelListener()
		{
		}

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
