package com.hirshagarwal.theia.Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	private static Display mainDisplay;
	private static BufferedImage imageFile;
	
	// Start the interface
	public static void main(String[] args){
		mainDisplay = new Display();
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
