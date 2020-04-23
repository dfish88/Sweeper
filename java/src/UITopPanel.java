import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class UITopPanel extends JPanel
{
	private JButton face; // Face icon
	private JButton restart;
	private JButton hint;
	private JLabel theTimer;
	private ApplicationInterface app;

	public UITopPanel(ApplicationInterface a)
	{
		super();
		this.app = a;
		this.setLayout(new GridLayout(1,4));
		this.setBackground(Color.LIGHT_GRAY);
		this.setUpTopPanel();
	}

	public void displayFace(FaceRepresentation rep)
	{
		this.face.setIcon(ImageUtilities.getFaceImage(rep));
	}

	public void displayTime(String time)
	{
		this.theTimer.setText(time);
	}

	public void startGame()
	{
		ActionListener listener = new TopPanelListener();
		this.restart.addActionListener(listener);
		this.hint.addActionListener(listener);
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
		this.theTimer = new JLabel("0:00");
		this.theTimer.setHorizontalAlignment(JLabel.CENTER);
		this.theTimer.setVerticalAlignment(JLabel.CENTER);
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(this.hint, 0);
		this.add(this.face,1);
		this.add(this.restart,2);
		this.add(this.theTimer,3);
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
