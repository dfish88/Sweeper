/*
*	Copyright (C) 2019-2020  Daniel Fisher
*
*	This program is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*
*	This program is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*
*	You should have received a copy of the GNU General Public License
*	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package View;

import Presenter.TileRepresentation;
import Presenter.FaceRepresentation;

import java.util.EnumMap;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
* Helper class that loads images from file used by the UI to display tiles and faces.
*
* @author Daniel Fisher
*/
public final class ImageUtilities
{
	// Hash table that maps tile reps to image icons
	private static final EnumMap<TileRepresentation, ImageIcon> tileImgs = new EnumMap<>(TileRepresentation.class);

	// Hash table that maps face reps to image icons
	private static final EnumMap<FaceRepresentation, ImageIcon> faceImgs = new EnumMap<>(FaceRepresentation.class);

	/**
	* Private constructor beacuse this helper class only has static methods and 
	* you shouldn't want to create an instance of this class.
	*/
	private ImageUtilities()
	{ 
	}

	/**
	* This methods returns the image icon for the corresponding tile representation.
	* This method loads and stores the images on a by need basis.
	*
	* @param rep	the tile representation
	*/
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

	/**
	* This methods returns the image icon for the corresponding face representation.
	* This method loads and stores the images on a by need basis.
	*
	* @param rep	the face representation
	*/
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

	/**
	* Helper method that loads and stores an image for the corresponding tile representation.
	* This method is called when a tile representation is not currently in the hash table.
	* This allows use to load and store images on a by need basis.
	*
	* @param rep	the tile representation
	*/
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

	/**
	* Helper method that loads and stores an image for the corresponding face representation.
	* This method is called when a face representation is not currently in the hash table.
	* This allows use to load and store images on a by need basis.
	*
	* @param rep	the face representation
	*/
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
