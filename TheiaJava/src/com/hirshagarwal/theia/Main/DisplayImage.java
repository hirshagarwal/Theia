package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class DisplayImage {
	
	// Hold all of the images
	private BufferedImage rawImage;
	private BufferedImage gridImage;
	private BufferedImage currentImage;
	
	// Fields
	private ArrayList<Point> selectedCrops = new ArrayList<Point>();
	private int gridSize = 10;
	
	public DisplayImage(BufferedImage currentImage){
		this.currentImage = currentImage;
	}
	
	public void addSelectPoint(int x, int y){
		Point newPoint = new Point(x, y);
		selectedCrops.add(newPoint);
	}
	
	public void removeSelectPoint(int x, int y){
		Iterator<Point> cropIterator = selectedCrops.iterator();
		while(cropIterator.hasNext()){
			Point currentPoint = cropIterator.next();
			if(currentPoint.equals(new Point(x, y))){
				cropIterator.remove();
			}
		}
	}
	
	public ArrayList<Point> getSelectPoints(){
		return selectedCrops;
	}
	
	public BufferedImage generateGridImage(){
		BufferedImage gridImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = gridImage.createGraphics();
		g2d.drawImage(rawImage, 0, 0, null);
		g2d.setBackground(Color.WHITE);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		
		// Draw white grid lines
		for(int i=0; i<gridSize+1; i++){
			g2d.drawLine((rawImage.getWidth()+2)/gridSize*i, 0, (rawImage.getWidth()+2)/gridSize*i, rawImage.getHeight()-1);
			g2d.drawLine(0, (rawImage.getHeight()+2)/gridSize*i, rawImage.getWidth()-1, (rawImage.getHeight()+2)/gridSize*i);
		}
		return gridImage;
	}
	
	public void setRawImage(BufferedImage image){
		rawImage = image;
	}
	
	public void setGridSize(int gridSize){
		this.gridSize = gridSize;
	}

}
