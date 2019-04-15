import javax.swing.*;
import java.util.*;
import java.awt.*;  

public class Game
{
	public static void main(String[] args)
	{
		int dimension = 8;
	
		JFrame frame = new JFrame();
		JButton buttonGrid[][] = new JButton[dimension][dimension];		

		for (int i = 0; i < dimension; i++)
		{	
			for (int j = 0; j < dimension; j++)
			{
				buttonGrid[i][j] = new JButton();
				frame.add(buttonGrid[i][j]);
			}
		}

		frame.setLayout(new GridLayout(dimension,dimension));
		frame.setSize(300,300);
		frame.setVisible(true);

		// Create board
		Board gameBoard = new Board(dimension);

		int x;
		int y;
		Scanner sc = new Scanner(System.in);

		System.out.println(gameBoard);		

		// Game loop
		while (true)
		{
			// Get input
			System.out.print("Enter x: ");
			x = sc.nextInt();
			System.out.print("Enter y: ");
			y = sc.nextInt();		

			// "Click" on square
			gameBoard.revealSquare(x,y);

			// Display board
			System.out.println(gameBoard);
		}
	}
}
