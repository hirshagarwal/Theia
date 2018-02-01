package com.hirshagarwal.theia.Main;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FindPlaque {
	
	public static void findPlaque(File imageFile) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Mat image = Imgcodecs.imread(imageFile.toString());
		Mat newImage = new Mat();
		Core.inRange(image, new Scalar(15, 15, 15), new Scalar(255, 255, 255), newImage);
		
		// Morphological Operations
		 Mat erode = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10,10));
	     Mat dilate = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(10,10));
	     
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.dilate(newImage, newImage, dilate);
	     Imgproc.erode(newImage, newImage, erode);

	     // Find contours
	     List<MatOfPoint> contours = new ArrayList<>();
	     Imgproc.findContours(newImage, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
	     // Determine longest contour
	     int plaqueContouridx = 0;
	     double maxContourSize = 0;
	     for(int i=0; i<contours.size(); i++) {
	    	 double contourArea = Imgproc.contourArea(contours.get(i));
	    	 if(contourArea > maxContourSize) {
	    		 System.out.println(i + ": Contour Area: " + contourArea);
	    		 plaqueContouridx = i;
	    		 maxContourSize = contourArea;
	    	 }
	     }
	     
	     Imgproc.drawContours(image, contours, plaqueContouridx, new Scalar(255,255,0));
	     
	     
	     showMat(image);
	     showMat(newImage);
	}

	
	/***
	 * Convert a Buffered Image (Java) to a matrix for OpenCV
	 * @param bi
	 * @return
	 */
	public static Mat bufferedImageToMat(BufferedImage bi) {
		  Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
		  byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
		  mat.put(0, 0, data);
		  return mat;
	}
	
	/***
	 * Convert a matrix (OpenCV) to a Buffered Image for Java
	 * @param image
	 * @return
	 */
	public static BufferedImage matToBufferedImage(Mat image){
		MatOfByte mob = new MatOfByte();
	    Imgcodecs.imencode(".jpg", image, mob);
	    try {
	    	return ImageIO.read(new ByteArrayInputStream(mob.toArray()));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return new BufferedImage(0, 0, 0);
	    }
	}
	
	/***
	 * Generic method to quickly display buffered image
	 * @param im
	 */
	public static void showBufferedImage(BufferedImage im) {
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new JLabel(new ImageIcon(im.getScaledInstance(600, 500, 0))));
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/***
	 * Pipelined operation for displaying an OpenCV matrix
	 * @param image
	 */
	public static void showMat(Mat image) {
		showBufferedImage(matToBufferedImage(image));
	}
}
