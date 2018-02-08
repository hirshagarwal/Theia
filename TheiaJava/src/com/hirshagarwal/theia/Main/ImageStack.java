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
	
	public void export(ArrayList<Point> crops) {
		// Iterate over all of crops
		for(int i=0; i<crops.size(); i++) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageWriter writer = ImageIO.getImageWritersByFormatName("TIFF").next();
			
			try {
				ImageOutputStream output = ImageIO.createImageOutputStream(new File(Main.getExportDirectory(imageNum).toPath() + "\\" + Main.getExportTitle(imageNum) + (Main.getStartingNumber() + i) + ".tiff"));
				writer.setOutput(output);
				ImageWriteParam params = writer.getDefaultWriteParam();
				writer.prepareWriteSequence(null);
				
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
	
	public void setImageNumber(int num) {
		imageNum = num;
	}
	

}
