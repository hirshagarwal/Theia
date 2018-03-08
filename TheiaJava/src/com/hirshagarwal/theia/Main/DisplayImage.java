package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	private ArrayList<Point> selectedNear = new ArrayList<Point>();
	private ArrayList<Point> selectedFar = new ArrayList<Point>();
	
	// Number of boxes to be drawn on the image. This only affects the number drawn, not the size.
	private int gridSize = 14;
	
	// Amount to offset text from corner
	private int textOffsetX = 35;
	private int textOffsetY = 60;
	private int textSize = 32;
	
	
	/***
	 * Constructor
	 * Should be given the raw image and a buffered image
	 * @param currentImage
	 */
	public DisplayImage(BufferedImage rawImage){
		this.rawImage = rawImage;
	}
	
	
	/**
	 * Add a point near to a plaque
	 * Only applicable in proximity mode
	 * @param x
	 * @param y
	 */
	public void addNearPoint(int x, int y) {
		Point newPoint = new Point(x, y);
		// Check if box is already selected
		if(selectedNear.contains(newPoint)){
			// If the point is already selected it needs to be removed
			selectedNear.remove(newPoint);
			// Generate a new image to remove points
			generateCurrentImageProximity();
		} else {
			// Add a new point to the list and display the updated image
			selectedNear.add(newPoint);
			// Since there's only one square added just draw the new square on the existing image
			redrawNewPoint(true, newPoint);
		}
	}
	
	/**
	 * Add a point that's far away from a plaque
	 * Only applicable in proximity mode
	 * @param x
	 * @param y
	 */
	public void addFarPoint(int x, int y) {
		Point newPoint = new Point(x, y);
		// Check if box is already selected
		if(selectedFar.contains(newPoint)){
			// Point has already been selected, so remove it
			selectedFar.remove(newPoint);
			// Generate a new image to remove points
			generateCurrentImageProximity();
		} else {
			selectedFar.add(newPoint);
			// Add the new point to the exiting image (rather than redrawing the whole thing)
			redrawNewPoint(false, newPoint);
		}
		
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
			// TODO: Add a method to redraw the image when a selection point is removed
		} else {
			selectedCrops.add(newPoint);	
			redrawManualPoint(newPoint);
		}
	}
	
	/***
	 * Removes a pair of points from the list of selected points
	 * Input is x,y pair specifying box number starting at 0
	 * @param x
	 * @param y
	 */
	public void removeSelectPoint(int x, int y){
		// Build an iterator for all of the selected crop points
		Iterator<Point> cropIterator = selectedCrops.iterator();
		// Iterate over all of the crop points
		while(cropIterator.hasNext()){
			Point currentPoint = cropIterator.next();
			if(currentPoint.equals(new Point(x, y))){
				cropIterator.remove();
			}
		}
	}
	
	/***
	 * Sets all of the selected points at once
	 * @param selectedPoints
	 */
	public void setSelectedPoints(ArrayList<Point> selectedPoints) {
		selectedCrops.clear();
		selectedCrops = selectedPoints;
	}
	
	/***
	 * Returns the arraylist of currently selected points
	 * @return
	 */
	public ArrayList<Point> getSelectPoints(){
		return selectedCrops;
	}
	
	/**
	 * Adds a crop to the existing image panel. This is much faster than redrawing the whole image every time.
	 * @param near
	 * @param newPoint
	 */
	private void redrawNewPoint(boolean near, Point newPoint) {
		// Set the color depending on if the point is near or far
		Color overlayColor;
		if(near) {
			overlayColor = new Color(0, 50, 255, 100);
		} else {
			overlayColor = new Color(50, 255, 0, 100);
		}
		
		// Build a graphics 2D object
		Graphics2D g2d = currentImage.createGraphics();
		g2d.setColor(Color.WHITE);
		
		// Draw the new shaded region on top of the existing image
		int cropSize = Display.cropSize;
		g2d.setColor(overlayColor);
		g2d.fillRect((int) newPoint.getX()*cropSize, (int) newPoint.getY()*cropSize, cropSize, cropSize);
	}
	
	/**
	 * Method to draw an extra point when in manual mode. Much faster than rendering the entire image.
	 * @param newPoint
	 */
	private void redrawManualPoint(Point newPoint) {
		// Set the overlay color
		Color overlayColor = new Color(50, 255, 0, 100);
		// Build a graphics 2D object
		Graphics2D g2d = currentImage.createGraphics();
		
		// Draw the new shaded image
		int cropSize = Display.cropSize;
		g2d.setColor(overlayColor);
		g2d.fillRect((int) newPoint.getX()*cropSize, (int) newPoint.getY()*cropSize, cropSize, cropSize);
		
	}
	
	/**
	 * Calculate the grid number based off the coordinates
	 * @param newPoint
	 * @return
	 */
	public int calculateGridNumber(Point newPoint) {
		int x = (int) newPoint.getX();
		int y = (int) newPoint.getY();
		int finalPoint = gridSize*y + x;
		return finalPoint;
	}
	
	/**
	 * Generate an output image with numbers on each selection region for a manual crop image (not proximity)
	 * This is only used to export the image for reference at the end (after crops have been exported)
	 * @return
	 */
	public BufferedImage generateManualOutputImage() {
		BufferedImage outputImage = new BufferedImage(gridImage.getWidth(), gridImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		// Create and setup graphics object
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(currentImage, 0, 0, null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, textSize));
		
		// Draw the numbers for the near points
		for (int i=0; i<selectedCrops.size(); i++) {
			g2d.drawString("" + (Main.getStartingNumber() + i), (int)selectedCrops.get(i).getX()*100+textOffsetX, (int)selectedCrops.get(i).getY()*100+textOffsetY);
		}
		
		return outputImage;	
	}
	
	
	/**
	 * Generate an output image with numbers on each selection region for a proximity crop image (with both regions near and far from plaques)
	 * @return
	 */
	public BufferedImage generateOutputImage() {
		BufferedImage outputImage = new BufferedImage(gridImage.getWidth(), gridImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		
		// Create and setup graphics object
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(currentImage, 0, 0, null);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("TimesRoman", Font.BOLD, textSize));
		
		// Draw the numbers for the near points
		for (int i=0; i<selectedNear.size(); i++) {
			g2d.drawString("" + (Main.getStartingNumber()-selectedNear.size() + i), (int)selectedNear.get(i).getX()*100+textOffsetX, (int)selectedNear.get(i).getY()*100+textOffsetY);
		}
		// Draw the numbers for the far points
		for (int i=0; i<selectedFar.size(); i++) {
			g2d.drawString("" + (Main.getStartingNumber() + i), (int)selectedFar.get(i).getX()*100+textOffsetX, (int)selectedFar.get(i).getY()*100+textOffsetY);
		}
		
		return outputImage;
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
		int cropSize = Display.cropSize;
		// Draw white grid lines
		for(int i=0; i<gridSize+1; i++){
			g2d.drawLine(cropSize*i, 0, cropSize*i, rawImage.getHeight()-1);
			g2d.drawLine(0, cropSize*i, rawImage.getWidth()-1, cropSize*i);
		}
		
		// Copy the grid image to currentImage
		WritableRaster w = gridImage.copyData(null);
		currentImage = new BufferedImage(gridImage.getColorModel(), w, gridImage.isAlphaPremultiplied(), null);
		return gridImage;
	}
	
	/***
	 * Draws selection boxes from currently selected regions on top of the image with an overlaid grid
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
		int cropSize = Display.cropSize;
		while(selectedCropsIterator.hasNext()){
			Point currentCropPoint = selectedCropsIterator.next();
			g2d.fillRect((int)currentCropPoint.getX()*cropSize, (int)currentCropPoint.getY()*cropSize, cropSize, cropSize);
		}
		return currentImage;
	}
	
	/***
	 * Generate an image with different colored selection boxes for when the program is running in proximity mode
	 * @return
	 */
	public BufferedImage generateCurrentImageProximity() {
		// Make new blank bufferedImage
		currentImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		// Create Graphics2D Object for new bufferedImage
		Graphics2D g2d = currentImage.createGraphics();
		// Use grid image as base
		g2d.drawImage(gridImage, 0, 0, null);
		// Set the current color
		Color overlayNear = new Color(0, 50, 255, 100);
		g2d.setColor(overlayNear);
		
		// Draw the near overlays
		Iterator<Point>selectedCropsIterator = selectedNear.iterator();
		while(selectedCropsIterator.hasNext()) {
			Point currentCropPoint = selectedCropsIterator.next();
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("TimesRoman", Font.BOLD, textSize));
//			g2d.drawString("" + calculateGridNumber(currentCropPoint), (int)currentCropPoint.getX()*100+textOffsetX, (int)currentCropPoint.getY()*100+textOffsetY);
			g2d.setColor(overlayNear);
			int cropSize = Display.cropSize;
			g2d.fillRect((int)currentCropPoint.getX()*cropSize, (int)currentCropPoint.getY()*cropSize, cropSize, cropSize);
		}
		
		// Draw the far overlays 
		Color overlayFar = new Color(50, 255, 0, 100);
		g2d.setColor(overlayFar);
		selectedCropsIterator = selectedFar.iterator();
		while(selectedCropsIterator.hasNext()) {
			Point currentCropPoint = selectedCropsIterator.next();
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("TimesRoman", Font.BOLD, textSize));
			g2d.setColor(overlayFar);
			int cropSize = Display.cropSize;
			g2d.fillRect((int)currentCropPoint.getX()*cropSize, (int)currentCropPoint.getY()*cropSize, cropSize, cropSize);
		}
		
		return currentImage;
	}
	
	/***
	 * Allows the raw image file be reset
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
	
	public ArrayList<Point> getSelectedCrops(){
		return selectedCrops;
	}
	
	public ArrayList<Point> getNearCrops(){
		return selectedNear;
	}
	
	public ArrayList<Point> getFarCrops(){
		return selectedFar;
	}
	
	public BufferedImage getCurrentImage() {
		return currentImage;
	}
	
	public int getGridSize() {
		return gridSize;
	}

}
