import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton) e.getSource();
		Integer[] coordinates = (Integer []) button.getClientProperty("coordinates");
		System.out.println("Button pressed at: " + coordinates[0] + ", " + coordinates[1]);
	}
}
