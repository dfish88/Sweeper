import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.border.*;

public class UIStartScreen extends JPanel
{
	private JButton easy;
	private JButton hard; 

	private final int EASY_DIM = 8;
	private final int HARD_DIM = 16;
	private final int SCREEN_DIM = 400;

	public UIStartScreen()
	{
		super();
		this.setLayout(new GridLayout(1,2));
		this.setUpStartScreen(new StartScreenListener());
	}

	/*
	* Builds start screen where player chooses board size.
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

	/*
	* Mouse listener registerd on all game buttons.
	*/
	private class StartScreenListener implements ActionListener
	{
		public StartScreenListener()
		{
		}

		public void actionPerformed(ActionEvent e)
		{
			JButton btn = (JButton) e.getSource();

			// Call start game from UI class
			if (btn.equals(UIStartScreen.this.easy))
			{
				//startGame(EASY_DIM);
			}
			else if (btn.equals(UIStartScreen.this.hard))
			{
				//startGame(HARD_DIM);
			}
		}
	}
}
