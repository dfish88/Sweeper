import javax.swing.*;
import java.util.*;

public class Game
{
	public static void main(String[] args)
	{
		// Create board
		int dimension = 8;
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
