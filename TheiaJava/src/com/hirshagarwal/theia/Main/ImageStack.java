package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageStack {
	
	private ArrayList<BufferedImage> imageStack = new ArrayList<>();
	private int numPages = 0;
	
	
	/***
	 * Add a new bufferedImage to the image stack
	 * @param newPage
	 */
	public void addToStack(BufferedImage newPage) {
		imageStack.add(newPage);
		numPages = imageStack.size();
	}
	

}
