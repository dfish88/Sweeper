public interface UIInterface
{
	public void startGame(int dimension);
	public void restart();
	public void displayTile(int x, int y, TileRepresentation rep);
	public void displayFace(FaceRepresentation rep);
	public void displayTime(String time);
	public void disable();
}
