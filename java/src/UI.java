import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UI implements UIInterface
{
	private UIStartScreen start;
	private UITopPanel top;
	private UIBoard board;
	private JFrame window;
	private Timer timer;
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

		this.timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				UI.this.app.displayTime();
			}
		});
		this.timer.setInitialDelay(0);
	}

	public void startGame(int dimension)
	{
		this.board = new UIBoard(dimension, this.app);
		this.window.remove(this.start);
		this.window.add(this.board, BorderLayout.CENTER);
		this.window.pack();
		this.top.startGame();
		this.timer.start();
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
