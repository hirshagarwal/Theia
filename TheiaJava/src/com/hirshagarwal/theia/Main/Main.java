package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.plugins.tiff.*;
import javax.imageio.stream.ImageInputStream;


public class Main {
	
	private static Display mainDisplay;
	private static BufferedImage imageFile;
	private static ArrayList<ImageStack> stacksToCrop = new ArrayList<>();
	
	// Start the interface
	public static void main(String[] args){
		mainDisplay = new Display();
	}
	
	
	/***
	 * Takes a .tif file path and adds the image to a list of buffered images to crop.
	 * @param file
	 */
	public static void addFileToCrop(File file) {
		
		// Add a single file to the list of images to crop
		try {
			// Build the TIFF image reader
			Iterator<ImageReader> tiffReaders = ImageIO.getImageReadersByFormatName("TIFF");
			ImageInputStream imageStream = ImageIO.createImageInputStream(file);
			
			// Ensure TIFF reader is available
			if(!tiffReaders.hasNext()) {
				throw new UnsupportedOperationException("No TIFF  Reader Available");
			}
			
			ImageReader tiffReader = tiffReaders.next();
			tiffReader.setInput(imageStream);
			int pages = tiffReader.getNumImages(true);
			
			ImageStack currentStack = new ImageStack();
			
			// Read the stack of images
			for(int i=0; i<pages; i++) {
				BufferedImage newPage = tiffReader.read(i);
				currentStack.addToStack(newPage);
			}
			
			stacksToCrop.add(currentStack);
			System.out.println("File Added to List. " + stacksToCrop.size() + " stacks to crop.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void setImageFile(File file){
		// Convert the file to an image
		try{
			// Store the image
			imageFile = ImageIO.read(file);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static BufferedImage getCurrentImage(){
		return imageFile;
	}
	

}
