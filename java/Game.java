import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game
{
	private JFrame frame;
	private JButton buttonGrid[][];
	private Board gameBoard;
	private ButtonListener listener;

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimension);

		this.listener = new ButtonListener();
		this.frame = new JFrame("Mine Sweeper!");
		this.buttonGrid = new JButton[dimension][dimension];

		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {
                                this.buttonGrid[i][j] = new JButton();
                                this.buttonGrid[i][j].putClientProperty("coordinates", new Integer[]{i,j});
                                this.buttonGrid[i][j].addActionListener(this.listener);
                                this.frame.add(buttonGrid[i][j]);
                        }

                }
		this.frame.setLayout(new GridLayout(dimension, dimension));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500,500);
		this.frame.setVisible(true);
	}

	public void revealTiles(int x, int y)
	{
		this.buttonGrid[x][y].setText(this.gameBoard.revealTile(x,y));
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton) e.getSource();
			Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");
			System.out.println("Button pressed at: " + coordinates[0] + ", " + coordinates[1]);
	
			Game.this.revealTiles(coordinates[0], coordinates[1]);
		}
	}
}
