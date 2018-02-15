package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class ThresholdImageStack {
	
	// Private Fields
	private ImageStack rawImage;
	private ImageStack thresholdImageStack;
	private CVHelper cvHelper = new CVHelper();
	
	/**
	 * Constructor to take an input image and threshold and use them to initialize the object
	 * @param inputImage
	 * @param threshold
	 */
	public ThresholdImageStack(ImageStack inputImage, int threshold) {
		rawImage = inputImage;
		for(int i=0; i<rawImage.size(); i++) {
			// Iterate over the input images
			thresholdImageStack.addToStack(thresholdImage(rawImage.getPage(i), threshold));
		}
	}

	/**
	 * Method to take an input image and a threshold value and return the thresholded image. The threshold value is used for each of the RGB channels.
	 * @param inputImage
	 * @param threshold
	 * @return
	 */
	public BufferedImage thresholdImage(BufferedImage image, int threshold) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat newImage = cvHelper.bufferedImageToMat(image);
		Core.inRange(newImage, new Scalar(0, 0, 0), new Scalar(threshold, threshold, threshold), newImage);
		return cvHelper.matToBufferedImage(newImage);
	}

	
	/**
	 * Get the original image
	 * @return
	 */
	public ImageStack getRawImageStack() {
		return rawImage;
	}
	
	/**
	 * Get the thresholded image
	 * @return
	 */
	public ImageStack getImageStack() {
		return thresholdImageStack;
	}
	
}
