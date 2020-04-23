public interface ApplicationInterface
{
	public void startGame(int dimension);
	public void hintClicked();
	public void restartClicked();
	public void tileClicked(int x, int y);
	public void placeFlag(int x, int y);
}
