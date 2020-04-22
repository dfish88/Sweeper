import javax.swing.*;
import java.awt.*;

public class UI
{
	private UIStartScreen start;
	private UITopPanel top;
	//private UIBoard board;
	private JFrame screen;

	public static void main(String[] args)
	{
		UI ui = new UI();
	}

	public UI()
	{
		this.screen = new JFrame("Mine Sweeper!");
		this.top = new UITopPanel();
		this.start = new UIStartScreen();

		this.screen.add(this.top, BorderLayout.PAGE_START);
		this.screen.add(this.start, BorderLayout.CENTER);
		this.screen.pack();
		this.screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.screen.setResizable(false);
		this.screen.setVisible(true);
	}

	public void startGame(int dimension)
	{
		/*
		this.board = new UIBoard(dimension);
		this.screen.remove(start);
		this.screen.add(this.board, BorderLayout.CENTER);
		this.screen.pack();
		*/
	}

	public void displayTile(int x, int y, ImageIcon img)
	{}

	public void displayFace(ImageIcon img)
	{}

	public void displayTime(String time)
	{}
}
