public class Application implements ApplicationInterface
{
	private UIInterface ui;
	private Game game;

	public Application(UIInterface ui)
	{
		this.ui = ui;
	}

	public void startGame(int dimension)
	{
		// Create Game object
		this.ui.startGame(dimension);
	}

	public void hintClicked()
	{}

	public void restartClicked()
	{}

	public void tileClicked(int x, int y)
	{}
		
}
