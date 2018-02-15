package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class CVHelper {
	

	/**
	 * Take a buffered image and convert it into an OpenCV Mat
	 * @param image
	 * @return Mat (OpenCV)
	 */
	public Mat bufferedImageToMat(BufferedImage image) {
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		  byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		  mat.put(0, 0, data);
		  return mat;
	}
	
	/**
	 * Take an input image in an OpenCV Mat format and convert it into a BufferedImage
	 * @param inputImage
	 * @return BufferedImage
	 */
	public BufferedImage matToBufferedImage(Mat inputImage) {
		MatOfByte mob = new MatOfByte();
	    Imgcodecs.imencode(".jpg", inputImage, mob);
	    try {
	    	return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return new BufferedImage(0, 0, 0);
	    }
	}

}
