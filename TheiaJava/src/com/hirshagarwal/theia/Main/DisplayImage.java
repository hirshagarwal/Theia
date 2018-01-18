package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/***
 * Class to generate images to be displayed
 * @author Hirsh Agarwal
 *
 */
public class DisplayImage {
	
	// Hold all of the images
	private BufferedImage rawImage;
	private BufferedImage gridImage;
	private BufferedImage currentImage;
	
	// Fields
	private ArrayList<Point> selectedCrops = new ArrayList<Point>();
	private int gridSize = 15;
	
	
	/***
	 * Constructor
	 * Should be given the raw image and a buffered image
	 * @param currentImage
	 */
	public DisplayImage(BufferedImage rawImage){
		this.rawImage = rawImage;
	}
	
	/***
	 * Adds a pair of points to the list of selected points
	 * Input is simply an x,y pair specifying the box number starting at 0
	 * The method checks to make sure that the current point hasn't already been added, if it has it is removed.
	 * @param x
	 * @param y
	 */
	public void addSelectPoint(int x, int y){
		Point newPoint = new Point(x, y);
		// Check if box is already selected
		if(selectedCrops.contains(newPoint)){
			selectedCrops.remove(newPoint);
		} else {
			selectedCrops.add(newPoint);			
		}
	}
	
	/***
	 * Removes a pair of points from the list of selected points
	 * Input is x,y pair specifying box number starting at 0
	 * @param x
	 * @param y
	 */
	public void removeSelectPoint(int x, int y){
		Iterator<Point> cropIterator = selectedCrops.iterator();
		while(cropIterator.hasNext()){
			Point currentPoint = cropIterator.next();
			if(currentPoint.equals(new Point(x, y))){
				cropIterator.remove();
			}
		}
	}
	
	/***
	 * Returns the arraylist of currently selected points
	 * @return
	 */
	public ArrayList<Point> getSelectPoints(){
		return selectedCrops;
	}
	
	/***
	 * Generates an image with a grid on top
	 * @return
	 */
	public BufferedImage generateGridImage(){
		// Make new blank BufferedImage
		gridImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// Create a Graphics2D object for the new BufferedImage
		Graphics2D g2d = gridImage.createGraphics();
		// Draw the original image on top
		g2d.drawImage(rawImage, 0, 0, null);
		g2d.setBackground(Color.WHITE);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		
		// Draw white grid lines
		for(int i=0; i<gridSize+1; i++){
			// TODO: Make the grid size more variable
			g2d.drawLine(100*i, 0, 100*i, rawImage.getHeight()-1);
			g2d.drawLine(0, 100*i, rawImage.getWidth()-1, 100*i);
		}
		return gridImage;
	}
	
	/***
	 * Puts selection boxes on top of the image with an overlaid grid
	 * @return
	 */
	public BufferedImage generateCurrentImage(){
		// Make new blank BufferedImage
		currentImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// Create a Graphics2D object for the new BufferedImage
		Graphics2D g2d = currentImage.createGraphics();
		// Use the grid image as the base image
		g2d.drawImage(gridImage, 0, 0, null);
		// Set the overlay color and opacity (all values are 0-255) (R, G, B, A)
		Color overlayColor = new Color(0, 255, 0, 100);
		g2d.setColor(overlayColor);
		Iterator<Point> selectedCropsIterator = selectedCrops.iterator();
		while(selectedCropsIterator.hasNext()){
			Point currentCropPoint = selectedCropsIterator.next();
			g2d.fillRect((int)currentCropPoint.getX()*100, (int)currentCropPoint.getY()*100, 100, 100);
		}
		return currentImage;
	}
	
	/***
	 * Lets the raw image file be reset
	 * @param image
	 */
	public void setRawImage(BufferedImage image){
		rawImage = image;
	}
	
	/***
	 * Specify the grid size
	 * @param gridSize
	 */
	public void setGridSize(int gridSize){
		this.gridSize = gridSize;
	}

}
