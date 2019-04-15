import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game
{
	private JFrame frame;
	private JButton buttonGrid[][];
	private int dimension;
	private Board gameBoard;
	private ButtonListener listener;

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimension);

		listener = new ButtonListener();
		this.frame = new JFrame();
		this.buttonGrid = new JButton[this.dimension][this.dimension];
		this.frame.setLayout(new GridLayout(this.dimension, this.dimension));
		this.frame.setSize(500,500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);

		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {
                                this.buttonGrid[i][j] = new JButton();
                                this.buttonGrid[i][j].putClientProperty("coordinates", new Integer[]{i,j});
                                this.buttonGrid[i][j].addActionListener(listener);
                                this.frame.add(buttonGrid[i][j]);
                        }

                }
	}

	public void revealTiles(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.buttonGrid[x][y].setText(this.gameBoard.toString());
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
