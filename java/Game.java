import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.Stack;
import java.util.HashMap;
import javax.swing.BorderFactory; 
import javax.swing.border.*;

public class Game
{
	private JFrame frame; // The main JFrame that holds everything
	private JPanel panel; // The panel that holds all the tiles
	private JPanel top; // The panel that has the restart button, time, faces
	private JButton restart;
	private JLabel theTimer;
	private JButton face;
	private JButton hint;
	private JButton eight;
	private JButton sixteen;
	private JButton buttonGrid[][];
	private Board gameBoard;
	private GameListener listener;
	private InitialListener listener2;
	private HashMap<Character, ImageIcon> icons = new HashMap<>();
	private javax.swing.Timer time;
	private int seconds;

	public Game()
	{
		// Start window with 2 options
		this.frame = new JFrame("Mine Sweeper!");
		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(1,2));
		this.listener2 = new InitialListener();
		this.eight = new JButton();
		this.eight.setText("8x8 (Easy)");
		this.eight.setBackground(Color.LIGHT_GRAY);
		this.eight.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.eight.addMouseListener(this.listener2);
		this.sixteen = new JButton();
		this.sixteen.setText("16x16 (Hard)");
		this.sixteen.setBackground(Color.LIGHT_GRAY);
		this.sixteen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.sixteen.addMouseListener(this.listener2);
		this.panel.add(this.eight);
		this.panel.add(this.sixteen);
		this.panel.setPreferredSize(new Dimension(400,400));

		this.loadImages();
		this.top = new JPanel();
		this.top.setLayout(new GridLayout(1,4));
		this.top.setBackground(Color.LIGHT_GRAY);
		this.restart = new JButton("Restart?");
		this.restart.setBackground(Color.LIGHT_GRAY);
		this.restart.setBorder(BorderFactory.createRaisedBevelBorder());
		this.face = new JButton();
		this.face.setIcon(this.icons.get('s'));
		this.face.setBorder(null);
		this.face.setBackground(Color.LIGHT_GRAY);
		this.hint = new JButton("Hint?");
		this.hint.setBackground(Color.LIGHT_GRAY);
		this.hint.setBorder(BorderFactory.createRaisedBevelBorder());


		this.theTimer = new JLabel("0:00");
		this.theTimer.setHorizontalAlignment(JLabel.CENTER);
		this.theTimer.setVerticalAlignment(JLabel.CENTER);
		this.seconds = 0;
		this.time = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Game.this.seconds = Game.this.seconds + 1;
			
				int sec = Game.this.seconds % 60;
				int min = (int)(Game.this.seconds/60); 
				String s = Integer.toString(sec);;

				if (sec < 10)
				{
					s = "0" + sec;
				}
			
				Game.this.theTimer.setText(min + ":" + s);
			}
		});

		this.top.setBorder(BorderFactory.createLoweredBevelBorder());
		this.panel.setBorder(BorderFactory.createLoweredBevelBorder());

		//this.top.setPreferredSize(new Dimension(400,100));
		this.top.add(this.hint, 0);
		this.top.add(this.face,1);
		this.top.add(this.restart,2);
		this.top.add(this.theTimer,3);
		this.frame.add(this.top, BorderLayout.PAGE_START);
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

	public void startGame(int dimension)
	{
		this.gameBoard = new Board(dimension);
	
		this.panel.remove(this.eight);
		this.panel.remove(this.sixteen);
		this.panel.setPreferredSize(null);
	
		this.listener = new GameListener();
		this.hint.addMouseListener(this.listener);
		this.restart.addMouseListener(this.listener);

		this.panel.setLayout(new GridLayout(dimension, dimension));

		this.buttonGrid = new JButton[dimension][dimension];
		this.buildWindow(dimension);
	}

	/*
	* Load all icon images into hash map.  The keys match the characters returned
	* by tiles toChar() method so changing icons can be done in one line.
	*/
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
			icons.put('s', new ImageIcon("../icons/smile.png"));
			icons.put('c', new ImageIcon("../icons/click.png"));
			icons.put('d', new ImageIcon("../icons/dead.png"));
			icons.put('g', new ImageIcon("../icons/glasses.png"));
		}
		catch (Exception e)
		{
		}
	}

	/*
	* Adds and enables button for each tile.  Use a button property to track
	* coordinates instead of doing math.
	*/
	private void addButton(int x, int y, ImageIcon i)
	{

		this.buttonGrid[x][y] = new JButton();
		this.buttonGrid[x][y].putClientProperty("coordinates", new Integer[]{x,y});
		this.buttonGrid[x][y].addMouseListener(this.listener);	
		this.buttonGrid[x][y].setIcon(i);
		this.buttonGrid[x][y].setBorder(null);
		this.panel.add(this.buttonGrid[x][y]);
	}

	/*
	* Addes all the buttons to panels and all the panels to the main frame.
	*/
	private void setUpFrame()
	{
		this.frame.add(this.panel, BorderLayout.CENTER);
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
		this.setUpFrame();
	}

	public void revealTiles(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.drawBoard();
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
			c = changes.pop();
			this.buttonGrid[x][y].setIcon(this.icons.get((char)c));
		}	
	}

	private void placeFlag(int x, int y)
	{
		// Place flag on square if it isn't revealed or is a mine
		if (!(this.gameBoard.getRevealed(x, y)) || this.gameBoard.getMine(x,y))
		{
			this.gameBoard.setFlag(x,y);
			if (this.gameBoard.getFlag(x,y))
				this.buttonGrid[x][y].setIcon(this.icons.get('f'));
			else
				this.buttonGrid[x][y].setIcon(this.icons.get(' '));
		}
	}

	private void gameOver(int x, int y)
	{
		this.gameBoard.revealTile(x,y);
		this.buttonGrid[x][y].setIcon(this.icons.get('b'));
		this.face.setIcon(this.icons.get('d'));
		this.revealMines();
		this.revealFlags();
		this.time.stop();
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

	private void restart()
	{
		this.gameBoard.restart();
		this.drawBoard();
		this.face.setIcon(this.icons.get('s'));
	}

	private void doHint()
	{
		Stack<Integer> stack = this.gameBoard.hint();
		
		while (!(stack.empty()))
		{
			int x = stack.pop();
			int y = stack.pop();
			int c = stack.pop();

			this.buttonGrid[x][y].setIcon(this.icons.get((char)c));
		}
	}

	private class InitialListener implements MouseListener
	{

		public void mouseClicked(MouseEvent e)
		{
		}
		
		public void mousePressed(MouseEvent e) 
		{
		}

		public void mouseReleased(MouseEvent e)
		{
			JButton button = (JButton) e.getSource();

			if (button.equals(Game.this.eight))
			{
				Game.this.startGame(8);
			}
			else if (button.equals(Game.this.sixteen))
			{
				Game.this.startGame(16);
			}
		}

		public void mouseEntered(MouseEvent e)
		{}

      		public void mouseExited(MouseEvent e)
		{}
	}

	private class GameListener implements MouseListener
	{
		private boolean enabled;
		private boolean first;

		public GameListener()
		{
			this.enabled = true;
			this.first = true;
		}

		public void mouseClicked(MouseEvent e)
		{
		}
		
		public void mousePressed(MouseEvent e) 
		{
			// Used to disable buttons when game is over
			if (this.enabled == false)
				return;

			Game.this.face.setIcon(Game.this.icons.get('c'));
		}

		public void mouseReleased(MouseEvent e)
		{
			JButton button = (JButton) e.getSource();

			// Restart button was clicked
			if (button.equals(Game.this.restart))
			{
				this.enabled = true;
				Game.this.restart();
				this.first = true;
				Game.this.seconds = 0;
				Game.this.time.stop();
				Game.this.theTimer.setText("0:00");
				return;
			}

			// Used to disable buttons when game is over
			if (this.enabled == false)
				return;

			Game.this.face.setIcon(Game.this.icons.get('s'));

			if (button.equals(Game.this.hint))
			{
				Game.this.doHint();
				
				if (this.first)
				{
					Game.this.time.start();
					Game.this.theTimer.setText("0:00");
					this.first = false;
				}

				if (Game.this.gameBoard.checkForWin())
				{
					this.enabled = false;
					Game.this.face.setIcon(Game.this.icons.get('g'));
					Game.this.time.stop();
				}

				return;
			}

			Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");

			// Left click reveals tiles, there is a chance a mine has been clicked on.
			if (SwingUtilities.isLeftMouseButton(e))
			{				

				if (this.first)
				{
					Game.this.time.start();
					Game.this.theTimer.setText("0:00");
					this.first = false;
				}

				Game.this.revealTiles(coordinates[0], coordinates[1]);

				// Check if mine was clicked on
				if (Game.this.gameBoard.getMine(coordinates[0], coordinates[1]))
				{
					Game.this.gameOver(coordinates[0], coordinates[1]);
					this.enabled = false;
				}

				// Check if player has won the game
				else if(Game.this.gameBoard.checkForWin())
				{
					this.enabled = false;
					Game.this.face.setIcon(Game.this.icons.get('g'));
					Game.this.time.stop();
				}

			}
			// Right click places flag
			else if (SwingUtilities.isRightMouseButton(e))
			{
				try
				{
					Game.this.placeFlag(coordinates[0], coordinates[1]);
				}
				// Catches exception where players right clicks before making first move
				catch (Exception exc)
				{}
			}
		}

		public void mouseEntered(MouseEvent e)
		{}

      		public void mouseExited(MouseEvent e)
		{}
	}
}
