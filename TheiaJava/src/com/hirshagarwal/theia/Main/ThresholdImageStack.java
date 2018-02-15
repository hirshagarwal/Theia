package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class ThresholdImages {
	
	private BufferedImage rawImage;
	private BufferedImage thresholdImage;
	private CVHelper cvHelper = new CVHelper();
	
	/**
	 * Constructor to take an input image and threshold and use them to initialize the object
	 * @param inputImage
	 * @param threshold
	 */
	public ThresholdImages(BufferedImage inputImage, int threshold) {
		rawImage = inputImage;
		thresholdImage = cvHelper.matToBufferedImage(thresholdImage(threshold));
	}

	/**
	 * Method to take an input image and a threshold value and return the thresholded image. The threshold value is used for each of the RGB channels.
	 * @param inputImage
	 * @param threshold
	 * @return
	 */
	public Mat thresholdImage(int threshold) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat newImage = cvHelper.bufferedImageToMat(rawImage);
		Core.inRange(newImage, new Scalar(0, 0, 0), new Scalar(threshold, threshold, threshold), newImage);
		return newImage;
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
