import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Game
{
	private JFrame frame;
	private JPanel panel;
	private JPanel top;
	private JButton buttonGrid[][];
	private Board gameBoard;
	private GameListener listener;
	private HashMap<Character, ImageIcon> icons = new HashMap<>();

	public Game(int dimension)
	{
		this.gameBoard = new Board(dimension);
		this.loadImages();

		this.listener = new GameListener();
		this.frame = new JFrame("Mine Sweeper!");
		this.panel = new JPanel();
		this.top = new JPanel();
		this.buttonGrid = new JButton[dimension][dimension];

		this.buildWindow(dimension);
	}

	private void loadImages()
	{
		try
		{
			icons.put(' ', new ImageIcon("../icons/blank.png"));
			icons.put('0', new ImageIcon("../icons/0.png"));
			icons.put('1', new ImageIcon("../icons/1.png"));
			icons.put('2', new ImageIcon("../icons/2.png"));
			icons.put('3', new ImageIcon("../icons/3.png"));
			icons.put('4', new ImageIcon("../icons/4.png"));
			icons.put('5', new ImageIcon("../icons/5.png"));
			icons.put('6', new ImageIcon("../icons/6.png"));
			icons.put('7', new ImageIcon("../icons/7.png"));
			icons.put('8', new ImageIcon("../icons/8.png"));
			icons.put('m', new ImageIcon("../icons/mine.png"));
			icons.put('f', new ImageIcon("../icons/flag.png"));
			icons.put('b', new ImageIcon("../icons/boom.png"));
			icons.put('w', new ImageIcon("../icons/wrong.png"));
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
		this.panel.add(this.buttonGrid[x][y]);
	}

	private void setUpPanel(int dimension)
	{
		this.panel.setLayout(new GridLayout(dimension, dimension));
		this.top.setBackground(Color.LIGHT_GRAY);
		this.top.setPreferredSize(new Dimension(4*50,50));
		this.frame.add(this.top, BorderLayout.PAGE_START);
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.pack();
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

	private void buildWindow(int dimension)
	{
		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {
				this.addButton(i, j, this.icons.get(' '));
                        }
                }
		this.setUpPanel(dimension);
	}

	public void revealTiles(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.drawBoard();
		
		if(this.gameBoard.checkForWin())
			this.frame.setEnabled(false);
	}
	
	private void drawBoard()
	{
		Stack<Integer> changes = this.gameBoard.getChanges();
		
		int x;
		int y;
		int c;

		while(!(changes.empty()))
		{
			x = changes.pop();
			y = changes.pop();
			c = Character.forDigit(changes.pop(), 10);
			this.buttonGrid[x][y].setIcon(this.icons.get((char)c));
		}	
	}

	private void changeIcon(int x, int y, char tile)
	{
	}

	private void placeFlag(int x, int y)
	{
		// Place flag on square if it isn't revealed or is a mine
		if (!(this.gameBoard.revealed(x, y)) || this.gameBoard.mine(x,y))
		{
			this.buttonGrid[x][y].setIcon(this.icons.get('f'));
			this.gameBoard.setFlag(x,y);
		}
	}

	private void gameOver(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.buttonGrid[x][y].setIcon(this.icons.get('b'));
		this.frame.setEnabled(false);
		this.revealMines();
		this.revealFlags();
	}

	private void revealMines()
	{
		Stack<Integer> mines = this.gameBoard.getMines();
		int x;
		int y;

		while(!(mines.isEmpty()))
		{
			x = mines.pop();
			y = mines.pop();
		
			this.buttonGrid[x][y].setIcon(this.icons.get('m'));
		}
	}

	private void revealFlags()
	{
		Stack<Integer> wrongs = this.gameBoard.getFlags();
		int x;
		int y;
	
		while(!(wrongs.isEmpty()))
		{
			x = wrongs.pop();
			y = wrongs.pop();
			
			this.buttonGrid[x][y].setIcon(this.icons.get('w'));
		}
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
				else
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
