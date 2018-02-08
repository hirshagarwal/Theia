package com.hirshagarwal.theia.Main;

import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageStack {
	
	private ArrayList<BufferedImage> imageStack = new ArrayList<>();
	private int numPages = 0;
	private int imageNum = 0;
	
	
	/***
	 * Add a new bufferedImage to the image stack
	 * @param newPage
	 */
	public void addToStack(BufferedImage newPage) {
		imageStack.add(newPage);
		numPages = imageStack.size();
	}
	
	/**
	 * Take an Array List of points and export each crop from the current image stack.
	 * @param crops
	 */
	public void export(ArrayList<Point> crops) {
		// Iterate over all of crops
		for(int i=0; i<crops.size(); i++) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageWriter writer = ImageIO.getImageWritersByFormatName("TIFF").next();
			
			try {
				// Set the output parameters and file writer
				ImageOutputStream output = ImageIO.createImageOutputStream(new File(Main.getExportDirectory(imageNum).toPath() + "\\" + Main.getExportTitle(imageNum) + (Main.getStartingNumber() + i) + ".tiff"));
				writer.setOutput(output);
				ImageWriteParam params = writer.getDefaultWriteParam();
				writer.prepareWriteSequence(null);
				
				// Iterate over each page in the stack and cut out a crop
				for(int j=0; j<numPages; j++) {
					Point currentCrop = crops.get(i);
					BufferedImage currentPage = imageStack.get(j);
					int cropSize = Main.getCropSize();
					BufferedImage pageCrop = currentPage.getSubimage((int)currentCrop.getX()*cropSize, (int)currentCrop.getY()*cropSize, cropSize, cropSize);
					writer.writeToSequence(new IIOImage(pageCrop, null, null), params);
				}
				
				writer.endWriteSequence();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	/**
	 * Set the base image number 
	 * @param num
	 */
	public void setImageNumber(int num) {
		imageNum = num;
	}
	

}
