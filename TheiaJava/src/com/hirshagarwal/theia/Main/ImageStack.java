package com.hirshagarwal.theia.Main;

import java.awt.Point;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class ImageStack {
	
	private ArrayList<BufferedImage> imageStack = new ArrayList<>();
	private int numPages = 0;
	private int imageNum = 0;
	
	
	/**
	 * Read a tiff stack from a file into an ImageStack object
	 * @param f
	 */
	public void readFromFile(File f) {
		// Add a single file to the list of images to crop
			try {
				// Build the TIFF image reader
				Iterator<ImageReader> tiffReaders = ImageIO.getImageReadersByFormatName("TIFF");
				ImageInputStream imageStream = ImageIO.createImageInputStream(f);
				
				// Ensure TIFF reader is available
				if(!tiffReaders.hasNext()) {
					throw new UnsupportedOperationException("No TIFF  Reader Available");
				}
				
				ImageReader tiffReader = tiffReaders.next();
				tiffReader.setInput(imageStream);
				int pages = tiffReader.getNumImages(true);
								
				// Read the stack of images
				for(int i=0; i<pages; i++) {
					BufferedImage newPage = tiffReader.read(i);
					addToStack(newPage);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/***
	 * Add a new bufferedImage to the image stack
	 * @param newPage
	 */
	public void addToStack(BufferedImage newPage) {
		imageStack.add(newPage);
		numPages = imageStack.size();
	}
	
	/**
	 * Return a single page from the stack
	 * @param i
	 * @return BufferedImage
	 */
	public BufferedImage getPage(int i) {
		return imageStack.get(i);
	}
	
	/**
	 * Take an Array List of points and export each crop from the current image stack.
	 * @param crops
	 */
	public void export(ArrayList<Point> crops) {
		// Iterate over all of crops
		for(int i=0; i<crops.size(); i++) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName("TIFF").next();
			
			try {
				// Set the output parameters and file writer
				Path filePath = Paths.get(Main.getExportDirectory(imageNum).getPath(), Main.getExportTitle(imageNum) + (Main.getStartingNumber() + i) + ".tiff");
				ImageOutputStream output = ImageIO.createImageOutputStream(new File(filePath.toString()));
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
	
	/**
	 * Return the size of the current image stack
	 */
	public int size() {
		return imageStack.size();
	}
	

}
