import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game
{
	private JFrame frame;
	private JButton buttonGrid[][];
	private Board gameBoard;
	private GameListener listener;

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimension);

		this.listener = new GameListener();
		this.frame = new JFrame("Mine Sweeper!");
		this.buttonGrid = new JButton[dimension][dimension];

		this.buildWindow(dimension);
	}

	private void buildWindow(int dimension)
	{

		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {
                                this.buttonGrid[i][j] = new JButton();
                                this.buttonGrid[i][j].putClientProperty("coordinates", new Integer[]{i,j});
                                this.buttonGrid[i][j].addMouseListener(this.listener);
                                this.frame.add(buttonGrid[i][j]);
                        }

                }
		this.frame.setLayout(new GridLayout(dimension, dimension));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(500,500);
		this.frame.setVisible(true);
		System.out.println(this.gameBoard.toString());
	}

	public void revealTiles(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.drawBoard();
	}
	
	private void drawBoard()
	{
		String board = this.gameBoard.toString();
		
	}

	private class GameListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			JButton button = (JButton) e.getSource();
			Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");

			if (SwingUtilities.isLeftMouseButton(e))
				System.out.println("Left Button pressed at: " + coordinates[0] + ", " + coordinates[1]);
			else if (SwingUtilities.isRightMouseButton(e))
				System.out.println("Right Button pressed at: " + coordinates[0] + ", " + coordinates[1]);
				
			Game.this.revealTiles(coordinates[0], coordinates[1]);
		}
		
		public void mousePressed(MouseEvent e) 
		{}

		public void mouseReleased(MouseEvent e)
		{}

		public void mouseEntered(MouseEvent e)
		{}

      		public void mouseExited(MouseEvent e)
		{}
	}
}
