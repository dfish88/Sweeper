import javax.swing.*;
import javax.swing.border.*;
import java.awt.*; 
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory; 
import javax.swing.border.*;

public class UI
{
	private JFrame frame; // The main JFrame that holds everything
	private JPanel panel; // The panel that holds all the tiles
	private JPanel top; // The panel that has the restart button, time, faces
	private JButton face; // Face icon
	private JButton restart;
	private JButton hint;
	private JButton eight;
	private JButton sixteen;
	private JButton buttonGrid[][]; // Holds all the buttons for each tile
	private Board gameBoard;
	private GameListener listener;
	private HashMap<Character, ImageIcon> icons = new HashMap<>(); // Hashmap for button icons
	private javax.swing.Timer time; // Game timer
	private JLabel theTimer; // Game timer label
	private int seconds; // Seconds elapsed since game started

	public Game()
	{
		this.setUpStartScreen();
		this.loadImages();
		this.setUpTimer();	
		this.setUpTopPanel();
		this.setUpFrame();
	}


	/*
	* Builds start screen where player chooses board size.
	*/
	private void setUpStartScreen()
	{
		// Start window with 2 options
		this.frame = new JFrame("Mine Sweeper!");
		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(1,2));
		this.listener = new GameListener();
		this.eight = new JButton();
		this.eight.setText("8x8 (Easy)");
		this.eight.setBackground(Color.LIGHT_GRAY);
		this.eight.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.eight.addMouseListener(this.listener);
		this.sixteen = new JButton();
		this.sixteen.setText("16x16 (Hard)");
		this.sixteen.setBackground(Color.LIGHT_GRAY);
		this.sixteen.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		this.sixteen.addMouseListener(this.listener);
		this.panel.add(this.eight);
		this.panel.add(this.sixteen);
		this.panel.setPreferredSize(new Dimension(400,400));
	}

	/*
	* Load all icon images into hash map.  The keys match the characters returned
	* by tiles toChar() method so changing icons can be done in one line.
	*/
	private void loadImages()
	{
		try
		{
			icons.put(' ', new ImageIcon("../../img/blank.png"));
			icons.put('0', new ImageIcon("../../img/0.png"));
			icons.put('1', new ImageIcon("../../img/1.png"));
			icons.put('2', new ImageIcon("../../img/2.png"));
			icons.put('3', new ImageIcon("../../img/3.png"));
			icons.put('4', new ImageIcon("../../img/4.png"));
			icons.put('5', new ImageIcon("../../img/5.png"));
			icons.put('6', new ImageIcon("../../img/6.png"));
			icons.put('7', new ImageIcon("../../img/7.png"));
			icons.put('8', new ImageIcon("../../img/8.png"));
			icons.put('m', new ImageIcon("../../img/mine.png"));
			icons.put('f', new ImageIcon("../../img/flag.png"));
			icons.put('b', new ImageIcon("../../img/boom.png"));
			icons.put('w', new ImageIcon("../../img/wrong.png"));
			icons.put('s', new ImageIcon("../../img/smile.png"));
			icons.put('c', new ImageIcon("../../img/click.png"));
			icons.put('d', new ImageIcon("../../img/dead.png"));
			icons.put('g', new ImageIcon("../../img/glasses.png"));
		}
		catch (Exception e)
		{
		}
	}

	/*
	* Creates timer for game.
	*/
	private void setUpTimer()
	{
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

	}

	/*
	* Puts buttons on top panel
	*/
	private void setUpTopPanel()
	{
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
		this.top.setBorder(BorderFactory.createLoweredBevelBorder());
		this.panel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.top.add(this.hint, 0);
		this.top.add(this.face,1);
		this.top.add(this.restart,2);
		this.top.add(this.theTimer,3);
	}

	/*
	* Adds panels to frame, sets close actions, draws frame.
	*/
	private void setUpFrame()
	{
		this.frame.add(this.top, BorderLayout.PAGE_START);
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.pack();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setVisible(true);
	}

	/*
	* Displays start screen and enables listeners.
	*/
	private void startScreen()
	{
		this.frame.remove(this.panel);
		this.panel = new JPanel();
		this.panel.setLayout(new GridLayout(1,2));
		this.panel.add(this.eight);
		this.panel.add(this.sixteen);
		this.panel.setPreferredSize(new Dimension(400,400));
		this.frame.add(this.panel, BorderLayout.CENTER);
		this.frame.pack();
	}

	/*
	* Starts the game with a board size of dimension.
	*/
	private void startGame(int dimension)
	{
		this.gameBoard = new Board(dimension);
	
		this.panel.remove(this.eight);
		this.panel.remove(this.sixteen);
		this.panel.setPreferredSize(null);
	
		// Only add mouse listener once
		if (this.hint.getMouseListeners().length == 1)
			this.hint.addMouseListener(this.listener);
	
		if (this.restart.getMouseListeners().length == 1)
			this.restart.addMouseListener(this.listener);

		this.panel.setLayout(new GridLayout(dimension, dimension));

		this.buttonGrid = new JButton[dimension][dimension];
		this.buildWindow(dimension);
		this.frame.pack();
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
	* Adds a button to the frame for each tile.
	*/
	private void buildWindow(int dimension)
	{
		for (int i = 0; i < dimension; i++)
                {
                        for (int j = 0; j < dimension; j++)
                        {
				this.addButton(i, j, this.icons.get(' '));
                        }
                }
	}
	
	/*
	* Draws the board
	*/
	private void drawBoard(int x, int y)
	{
		// Display changes
		ArrayList<Icon> changes = this.gameBoard.getChanges();
		while(!(changes.isEmpty()))
		{
			Icon i = changes.remove(0);
			this.buttonGrid[(int)i.getX()][(int)i.getY()].setIcon(Game.this.icons.get(i.getChar()));
		}

		// Reveal mines and check flags on loss
		if (this.gameBoard.getState() == Board.State.LOSS)
		{
			ArrayList<Icon> flags = this.gameBoard.getFlags();
			ArrayList<Icon> mines = this.gameBoard.getMines();

			while (!(flags.isEmpty()))
			{
				Icon i = flags.remove(0);
				this.buttonGrid[(int)i.getX()][(int)i.getY()].setIcon(Game.this.icons.get(i.getChar()));
			}

			while (!(mines.isEmpty()))
			{
				Icon i = mines.remove(0);
				this.buttonGrid[(int)i.getX()][(int)i.getY()].setIcon(Game.this.icons.get(i.getChar()));
			}

			// Put boom on mine that was clicked
			this.buttonGrid[x][y].setIcon(Game.this.icons.get('b'));
		}
	}


	/*
	* Mouse listener registerd on all game buttons.
	*/
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

			JButton button = (JButton) e.getSource();
			
			// Only change face once games starts and only on tiles
			if (button.equals(Game.this.eight) || button.equals(Game.this.sixteen) || button.equals((Game.this.hint)))
				return;
			Game.this.face.setIcon(Game.this.icons.get('c'));
		}

		public void mouseReleased(MouseEvent e)
		{
			JButton button = (JButton) e.getSource();

			// Used for initial screen
			if (button.equals(Game.this.eight))
			{
				Game.this.startGame(8);
				return;
			}
			if (button.equals(Game.this.sixteen))
			{
				Game.this.startGame(16);
				return;
			}
	

			// Restart button was clicked
			if (button.equals(Game.this.restart))
			{
				this.restartClicked();
				return;
			}

			// Used to disable buttons when game is over
			if (this.enabled == false)
				return;


			// Hint button clicked
			if (button.equals(Game.this.hint))
			{
				this.hintClicked();
				return;
			}

			Game.this.face.setIcon(Game.this.icons.get('s'));
			Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");

			// Left click reveals tiles, there is a chance a mine has been clicked on.
			if (SwingUtilities.isLeftMouseButton(e))
			{				
				this.leftClick(coordinates[0], coordinates[1]);
			}
			// Right click places flag
			else if (SwingUtilities.isRightMouseButton(e))
			{
				try
				{
					this.placeFlag(coordinates[0], coordinates[1]);
				}
				// Catches exception where players right clicks before making first move
				catch (Exception exc)
				{}
			}
		}

		private void hintClicked()
		{
			if (this.first)
			{
				Game.this.time.start();
				Game.this.theTimer.setText("0:00");
				this.first = false;
			}

			Game.this.gameBoard.hint();
			Game.this.drawBoard(0,0);
			Board.State state = Game.this.gameBoard.getState();

			if (state == Board.State.WON)
			{
				this.enabled = false;
				Game.this.face.setIcon(Game.this.icons.get('g'));
				Game.this.time.stop();
			}
		}

		private void restartClicked()
		{
			this.enabled = true;
			Game.this.startScreen();
			Game.this.face.setIcon(Game.this.icons.get('s'));
			this.first = true;
			Game.this.seconds = 0;
			Game.this.time.stop();
			Game.this.theTimer.setText("0:00");
		}

		private void leftClick(int x, int y)
		{
			if (this.first)
			{
				Game.this.time.start();
				Game.this.theTimer.setText("0:00");
				this.first = false;
			}

			Game.this.gameBoard.makeMove(x,y);
			Board.State state = Game.this.gameBoard.getState();
			Game.this.drawBoard(x, y);
	
			if (state == Board.State.WON)
			{
				this.enabled = false;
				Game.this.face.setIcon(Game.this.icons.get('g'));
				Game.this.time.stop();
			}
			else if (state == Board.State.LOSS)
			{
				this.enabled = false;
				Game.this.face.setIcon(Game.this.icons.get('d'));
				Game.this.time.stop();
			}

		}

		/*
		* Changes icon on tile x y to a flag or to a blank square if already flag.
		*/
		private void placeFlag(int x, int y)
		{
			// Place flag on square if it isn't revealed or is a mine
			if (!(Game.this.gameBoard.getRevealed(x, y)))
			{
				Game.this.gameBoard.setFlag(x,y);
				if (Game.this.gameBoard.getFlag(x,y))
					Game.this.buttonGrid[x][y].setIcon(Game.this.icons.get('f'));
				else
					Game.this.buttonGrid[x][y].setIcon(Game.this.icons.get(' '));
			}
		}

		public void mouseEntered(MouseEvent e)
		{}

      		public void mouseExited(MouseEvent e)
		{}
	}
}
