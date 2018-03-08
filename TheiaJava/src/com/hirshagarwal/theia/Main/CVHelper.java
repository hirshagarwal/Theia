package com.hirshagarwal.theia.Main;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
		//TODO: Fix this line - can't cast to DataBufferByte
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
	
	/**
	 * Use a JFrame to display a buffered image
	 * @param imageToShow
	 */
	public void showBufferedImage(BufferedImage imageToShow) {
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new JLabel(new ImageIcon(imageToShow.getScaledInstance(700, 500, 0))));
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/***
	 * Pipelined operation for displaying an OpenCV matrix
	 * @param image
	 */
	public void showMat(Mat image) {
		showBufferedImage(matToBufferedImage(image));
	}

}
