package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;

public class DisplayImage {
	
	// Hold all of the images
	private BufferedImage rawImage;
	private BufferedImage gridImage;
	private BufferedImage currentImage;
	
	private ArrayList<Point> selectedCrops = new ArrayList<Point>();
	
	public DisplayImage(BufferedImage currentImage){
		this.currentImage = currentImage;
	}
	
	public void selectPoint(int x, int y){
		Point newPoint = new Point(x, y);
		selectedCrops.add(newPoint);
	}
	
	public void removePoint(int x, int y){
		Iterator<Point> cropIterator = selectedCrops.iterator();
		while(cropIterator.hasNext()){
			Point currentPoint = cropIterator.next();
			if(currentPoint.equals(new Point(x, y))){
				cropIterator.remove();
			}
		}
	}

}
