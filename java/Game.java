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
	private ImageIcon boom;
	private ImageIcon wrong;

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
			boom = new ImageIcon("../icons/boom.png");
			wrong = new ImageIcon("../icons/wrong.png");
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
		this.buttonGrid[x][y].setIcon(i);
		this.buttonGrid[x][y].setBorder(null);
		this.frame.add(this.buttonGrid[x][y]);
	}

	private void setUpFrame(int dimension)
	{
		this.frame.setLayout(new GridLayout(dimension, dimension));
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);
		System.out.println(this.gameBoard.toStringReveal());
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
		this.setUpFrame(dimension);
	}

	public void revealTiles(int x, int y)
	{
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
		switch(tile)
		{
			case '0':
				this.buttonGrid[x][y].setIcon(this.zero);
				break;
			case '1':
				this.buttonGrid[x][y].setIcon(this.one);
				break;
			case '2':
				this.buttonGrid[x][y].setIcon(this.two);
				break;
			case '3':
				this.buttonGrid[x][y].setIcon(this.three);
				break;
			case '4':
				this.addButton(x, y, this.four);
				break;
			case '5':
				this.addButton(x, y, this.five);
				break;
			case '6':
				this.addButton(x, y, this.one);
				break;
			case '7':
				this.addButton(x, y, this.one);
				break;
			case '8':
				this.addButton(x, y, this.one);
				break;
			default:
				break;
		}
	}

	private void placeFlag(int x, int y)
	{
		// Place flag on square if it isn't revealed or is a mine
		if (!(this.gameBoard.revealed(x, y)) || this.gameBoard.mine(x,y))
		{
			this.buttonGrid[x][y].setIcon(this.flag);
		}
	}

	private void gameOver(int x, int y)
	{
		this.buttonGrid[x][y].setIcon(this.boom);
	}

	private class GameListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e)
		{
			JButton button = (JButton) e.getSource();
			Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");

			if (SwingUtilities.isLeftMouseButton(e))
			{
				// Check if mine was clicked on
				if (Game.this.gameBoard.mine(coordinates[0], coordinates[1]))
					Game.this.gameOver(coordinates[0], coordinates[1]);

				// Reveal square clicked on
				Game.this.revealTiles(coordinates[0], coordinates[1]);
			}
			else if (SwingUtilities.isRightMouseButton(e))
			{
				Game.this.placeFlag(coordinates[0], coordinates[1]);
			}
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
