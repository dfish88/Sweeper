import javax.swing.*;
import java.util.*;
import java.awt.*;  

public class Game
{
	public static void main(String[] args)
	{
		int dimension = 16;
		
		ButtonListener listener = new ButtonListener();
		JFrame frame = new JFrame();
		JButton buttonGrid[][] = new JButton[dimension][dimension];		

		for (int i = 0; i < dimension; i++)
		{	
			for (int j = 0; j < dimension; j++)
			{
				buttonGrid[i][j] = new JButton();
				buttonGrid[i][j].putClientProperty("coordinates", new Integer[]{i,j});
				buttonGrid[i][j].addActionListener(listener);
				frame.add(buttonGrid[i][j]);
			}
		}

		frame.setLayout(new GridLayout(dimension,dimension));
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create board
		Board gameBoard = new Board(dimension);

		// Game loop
		while (true)
		{
		}
	}
}
