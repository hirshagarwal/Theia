package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;

public class ThresholdImages {
	
	private BufferedImage rawImage;
	private BufferedImage thresholdImage;
	
	
	/**
	 * Constructor to take an input image and threshold and use them to initialize the object
	 * @param inputImage
	 * @param threshold
	 */
	public ThresholdImages(BufferedImage inputImage, int threshold) {
		rawImage = inputImage;
		
	}

	/**
	 * Method to take an input image and a threshold value and return the thresholded image
	 * @param inputImage
	 * @param threshold
	 * @return
	 */
	public BufferedImage thresholdImage(int threshold) {
		//TODO: Fill out method
		return null;
	}

	
	/**
	 * Get the original image
	 * @return
	 */
	public BufferedImage getRawImage() {
		return rawImage;
	}
	
	/**
	 * Get the thresholded image
	 * @return
	 */
	public BufferedImage getImage() {
		return thresholdImage;
	}
	
}
