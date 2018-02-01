package com.hirshagarwal.theia.Main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

public class AutomatedCrop {
	
	private ArrayList<Point> cropsFound = new ArrayList<>();
	
	public ArrayList<Point> cropImage(BufferedImage inputImage){
		byte[] pixels = ((DataBufferByte) inputImage.getRaster().getDataBuffer()).getData();
		final int width = inputImage.getWidth();
	    final int height = inputImage.getHeight();
	    final boolean hasAlphaChannel = inputImage.getAlphaRaster() != null;

	    int[][] result = new int[height][width];
	    
		return cropsFound;
	}

}
