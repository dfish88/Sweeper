import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonListener implements ActionListener
{
	private JFrame frame;
	private JButton buttonGrid[][];
	private int dimension;
	private Board gameBoard;

	public ButtonListener()
	{
		this.gameBoard = new Board(8);
		this.dimension = 8;
		this.frame = new JFrame();
		this.buttonGrid = new JButton[this.dimension][this.dimension];
		this.frame.setLayout(new GridLayout(this.dimension, this.dimension));
		this.frame.setSize(500,500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (int i = 0; i < this.dimension; i++)
                {
                        for (int j = 0; j < this.dimension; j++)
                        {
                                this.buttonGrid[i][j] = new JButton();
                                this.buttonGrid[i][j].putClientProperty("coordinates", new Integer[]{i,j});
                                this.buttonGrid[i][j].addActionListener(this);
                                this.frame.add(buttonGrid[i][j]);
                        }
                }
		this.frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");
		System.out.println("Button pressed at: " + coordinates[0] + ", " + coordinates[1]);
		this.buttonGrid[coordinates[0]][coordinates[1]].setText(this.gameBoard.revealSquare(coordinates[0], coordinates[1]));
	}

	public static void main(String[] args)
	{
		ButtonListener game = new ButtonListener();
	}	
}
