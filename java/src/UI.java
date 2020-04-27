import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI implements UIInterface
{
	private UIStartScreen start;
	private UITopPanel top;
	private UIBoard board;
	private JFrame window;
	private ApplicationInterface app;

	public static void main(String[] args)
	{
		UI ui = new UI();	
	}

	public UI()
	{
		this.app = new Application(this);

		this.window = new JFrame("Mine Sweeper!");
		this.top = new UITopPanel(this.app);
		this.start = new UIStartScreen(this.app);

		this.window.add(this.top, BorderLayout.PAGE_START);
		this.window.add(this.start, BorderLayout.CENTER);
		this.window.pack();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setResizable(false); 
		this.window.setVisible(true);
	}

	public void disable()
	{
		this.top.shutOff();
		this.board.shutOff();
	}

	public void startGame(int dimension)
	{
		this.board = new UIBoard(dimension, this.app);
		this.window.remove(this.start);
		this.window.add(this.board, BorderLayout.CENTER);
		this.window.pack();
		this.top.startGame();
	}
	
	public void restart()
	{
		this.window.remove(this.board);
		this.window.add(this.start, BorderLayout.CENTER);
		this.top.restart();
		this.start.revalidate();
		this.start.repaint();
		this.window.pack();
	}

	public void displayTile(int x, int y, TileRepresentation rep)
	{
		this.board.updateTileIcon(x, y, rep);		
	}

	public void displayFace(FaceRepresentation rep)
	{
		this.top.displayFace(rep);
	}

	public void displayTime(String time)
	{
		this.top.displayTime(time);
	}
}
