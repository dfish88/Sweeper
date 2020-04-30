package View;

import Presenter.ApplicationInterface;
import Presenter.TileRepresentation;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class UIBoard extends JPanel
{
	private UITile board[][]; // Holds all the buttons for each tile
	private int dimension;
	private ApplicationInterface app;

	public UIBoard(int dimension, ApplicationInterface a)
	{
		super();
		this.app = a;
		this.dimension = dimension;
		this.setLayout(new GridLayout(dimension, dimension));
		this.buildBoard(dimension, new GameListener());
	}

	public void updateTileIcon(int x, int y, TileRepresentation rep)
	{
		this.board[x][y].setIcon(ImageUtilities.getTileImage(rep));
	}

	public void shutOff()
	{
		for(int i = 0; i < this.dimension; i++)
		{
			for(int j = 0; j < this.dimension; j++)
			{
				this.removeAllMouseListeners(this.board[i][j]);
			}
		}
	}

	private void removeAllMouseListeners(JButton btn)
	{
		for (MouseListener ml : btn.getMouseListeners())
		{
			btn.removeMouseListener(ml);
		}
	}

	private void leftClick(int x, int y)
	{
		this.app.tileClicked(x,y);
	}

	private void rightClick(int x, int y)
	{
		this.app.placeFlag(x,y);
	}

	private void mousePressed()
	{
		this.app.mousePressed();
	}

	private void mouseReleased()
	{
		this.app.mouseReleased();
	}

	private void buildBoard(int dimension, MouseListener listener)
	{
		this.board = new UITile[dimension][dimension];
		for(int i = 0; i < dimension; i++)
		{
			for(int j = 0; j < dimension; j++)
			{
				this.board[i][j] = new UITile(i,j);
				this.board[i][j].addMouseListener(listener);	
				this.board[i][j].setIcon(ImageUtilities.getTileImage(TileRepresentation.COVERED));
				this.board[i][j].setBorder(null);
				this.add(this.board[i][j]);
			}
		}
	}

	
	/*
	* Mouse listener registerd on all game buttons.
	*/
	private class GameListener implements MouseListener
	{
		public GameListener()
		{
		}

		public void mouseClicked(MouseEvent e)
		{
		}
		
		public void mousePressed(MouseEvent e) 
		{
			// Change face to surprised
			if (SwingUtilities.isLeftMouseButton(e))
			{
				UIBoard.this.mousePressed();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			UITile tile = (UITile) e.getSource();

			int x = tile.getGridX();
			int y = tile.getGridY();

			// Left click reveals tiles, there is a chance a mine has been clicked on.
			if (SwingUtilities.isLeftMouseButton(e))
			{				
				UIBoard.this.mouseReleased();
				UIBoard.this.leftClick(x, y);
			}
			// Right click places flag
			else if (SwingUtilities.isRightMouseButton(e))
			{
				try
				{
					UIBoard.this.rightClick(x, y);
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