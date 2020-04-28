package View;

import Presenter.TileRepresentation;
import Presenter.FaceRepresentation;

import java.util.EnumMap;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class ImageUtilities
{
	private static final EnumMap<TileRepresentation, ImageIcon> tileImgs = new EnumMap<>(TileRepresentation.class);
	private static final EnumMap<FaceRepresentation, ImageIcon> faceImgs = new EnumMap<>(FaceRepresentation.class);

	private ImageUtilities()
	{ 
	}

	public static ImageIcon getTileImage(TileRepresentation rep)
	{
		ImageIcon ret = tileImgs.get(rep);
		if (ret == null)
		{
			loadTileImage(rep);
		}
		
		ret = tileImgs.get(rep);
		return ret;
	}

	public static ImageIcon getFaceImage(FaceRepresentation rep)
	{
		ImageIcon ret = faceImgs.get(rep);
		if (ret == null)
		{
			loadFaceImage(rep);
		}
		
		ret = faceImgs.get(rep);
		return ret;
	}

	private static void loadTileImage(TileRepresentation rep)
	{

		switch(rep)
		{
			case ZERO:
				tileImgs.put(TileRepresentation.ZERO, new ImageIcon("../../img/0.png"));
				break;

			case ONE:
				tileImgs.put(TileRepresentation.ONE, new ImageIcon("../../img/1.png"));
				break;

			case TWO:
				tileImgs.put(TileRepresentation.TWO, new ImageIcon("../../img/2.png"));
				break;

			case THREE:
				tileImgs.put(TileRepresentation.THREE, new ImageIcon("../../img/3.png"));
				break;

			case FOUR:
				tileImgs.put(TileRepresentation.FOUR, new ImageIcon("../../img/4.png"));
				break;

			case FIVE:
				tileImgs.put(TileRepresentation.FIVE, new ImageIcon("../../img/5.png"));
				break;

			case SIX:
				tileImgs.put(TileRepresentation.SIX, new ImageIcon("../../img/6.png"));
				break;

			case SEVEN:
				tileImgs.put(TileRepresentation.SEVEN, new ImageIcon("../../img/7.png"));
				break;

			case EIGHT:
				tileImgs.put(TileRepresentation.EIGHT, new ImageIcon("../../img/8.png"));
				break;

			case EMPTY:
				tileImgs.put(TileRepresentation.EMPTY, new ImageIcon("../../img/empty.png"));
				break;

			case COVERED:
				tileImgs.put(TileRepresentation.COVERED, new ImageIcon("../../img/covered.png"));
				break;

			case MINE:
				tileImgs.put(TileRepresentation.MINE, new ImageIcon("../../img/mine.png"));
				break;

			case FLAG:
				tileImgs.put(TileRepresentation.FLAG, new ImageIcon("../../img/flag.png"));
				break;

			case BOOM:
				tileImgs.put(TileRepresentation.BOOM, new ImageIcon("../../img/boom.png"));
				break;

			case FLAG_WRONG:
				tileImgs.put(TileRepresentation.FLAG_WRONG, new ImageIcon("../../img/wrong.png"));
				break;
		}
	}

	private static void loadFaceImage(FaceRepresentation rep)
	{
		switch (rep)
		{
			case SMILE:
				faceImgs.put(FaceRepresentation.SMILE, new ImageIcon("../../img/smile.png"));
				break;

			case SURPRISED:
				faceImgs.put(FaceRepresentation.SURPRISED, new ImageIcon("../../img/click.png"));
				break;
				
			case GLASSES:
				faceImgs.put(FaceRepresentation.GLASSES, new ImageIcon("../../img/glasses.png"));
				break;
				
			case DEAD:
				faceImgs.put(FaceRepresentation.DEAD, new ImageIcon("../../img/dead.png"));
				break;
		}	
	}
}
