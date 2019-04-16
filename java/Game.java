import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;

public class Game
{
	private JFrame frame;
	private JButton buttonGrid[][];
	private Board gameBoard;
	private GameListener listener;

	private ImageIcon blank;
	private ImageIcon zero;
	private ImageIcon one;
	private ImageIcon two;
	private ImageIcon three;
	private ImageIcon four;
	private ImageIcon five;
	private ImageIcon six;
	private ImageIcon seven;
	private ImageIcon eight;
	private ImageIcon flag;
	private ImageIcon mine;

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimension);
		this.loadImages();

		this.listener = new GameListener();
		this.frame = new JFrame("Mine Sweeper!");
		this.buttonGrid = new JButton[dimension][dimension];

		this.buildWindow(dimension);
	}

	private void loadImages()
	{
		try
		{
			blank = new ImageIcon("../icons/blank.png");
			zero = new ImageIcon("../icons/0.png");
			one = new ImageIcon("../icons/1.png");
			two = new ImageIcon("../icons/2.png");
			three = new ImageIcon("../icons/3.png");
			four = new ImageIcon("../icons/4.png");
			five = new ImageIcon("../icons/5.png");
			six = new ImageIcon("../icons/6.png");
			seven = new ImageIcon("../icons/7.png");
			eight = new ImageIcon("../icons/8.png");
			mine = new ImageIcon("../icons/mine.png");
			flag = new ImageIcon("../icons/flag.png");
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Couldn't fing images");
		}
	}

	private void addButton(int x, int y, ImageIcon i)
	{

		this.buttonGrid[x][y] = new JButton();
		this.buttonGrid[x][y].putClientProperty("coordinates", new Integer[]{x,y});
		this.buttonGrid[x][y].addMouseListener(this.listener);	

		JLabel l = new JLabel(i);
		this.buttonGrid[x][y].setLayout(new BorderLayout());
		this.buttonGrid[x][y].add(l);

		this.frame.add(this.buttonGrid[x][y], BorderLayout.CENTER);
	}

	private void buildWindow(int dimension)
	{
		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {
				this.addButton(i, j, this.blank);
                        }
                }
		this.frame.setLayout(new GridLayout(dimension, dimension));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(400,400);
		this.frame.setVisible(true);
		System.out.println(this.gameBoard.toString());
	}

	public void revealTiles(int x, int y)
	{
		// Clicked on mine!
		if (this.gameBoard.mine(x,y))
		{
			System.out.println("GAME OVER!!!");
			System.exit(0);
		}

		this.gameBoard.revealTile(x,y);
		this.drawBoard();
	}
	
	private void drawBoard()
	{
		char[][] board = gameBoard.boardToArray();
		
		for (int i = 0; i < gameBoard.getDimension(); i++)
		{
			for (int j = 0; j < gameBoard.getDimension(); j++)
			{
				this.changeIcon(i,j, board[i][j]);
			}
		}
	}

	private void changeIcon(int x, int y, char tile)
	{
		//this.buttonGrid[x][y].setVisible(false);
		this.frame.remove(this.buttonGrid[x][y]);

		switch(tile)
		{
			case 0:
				this.addButton(x, y, this.zero);
				break;
			case 1:
				this.addButton(x, y, this.one);
				break;
			case 2:
				this.addButton(x, y, this.two);
				break;
			case 3:
				this.addButton(x, y, this.three);
				break;
			case 4:
				this.addButton(x, y, this.four);
				break;
			case 5:
				this.addButton(x, y, this.five);
				break;
			case 6:
				this.addButton(x, y, this.one);
				break;
			case 7:
				this.addButton(x, y, this.one);
				break;
			case 8:
				this.addButton(x, y, this.one);
				break;
		}
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
