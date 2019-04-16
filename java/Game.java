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
			/*
			one = ImageIO.read(getClass().getResource("../icons/1.png"));
			two = ImageIO.read(getClass().getResource("../icons/2.png"));
			three = ImageIO.read(getClass().getResource("../icons/3.png"));
			four = ImageIO.read(getClass().getResource("../icons/4.png"));
			five = ImageIO.read(getClass().getResource("../icons/5.png"));
			six = ImageIO.read(getClass().getResource("../icons/6.png"));
			seven = ImageIO.read(getClass().getResource("../icons/7.png"));
			eight = ImageIO.read(getClass().getResource("../icons/8.png"));
			flag = ImageIO.read(getClass().getResource("../icons/flag.png"));
			mine = ImageIO.read(getClass().getResource("../icons/mine.png"));
			*/
		}
		catch (Exception e)
		{
			System.out.println(e);
			System.out.println("Couldn't fing images");
		}
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
				
				Image img = this.blank.getImage() ;  
   				Image newimg = img.getScaledInstance( 500/8, 500/8,  java.awt.Image.SCALE_SMOOTH ) ;  
   				this.blank = new ImageIcon( newimg );

				this.buttonGrid[i][j].setIcon(this.blank);
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
				this.buttonGrid[i][j].setText(Character.toString(board[i][j]));
			}
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
