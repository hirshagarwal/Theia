package com.hirshagarwal.theia.test;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.hirshagarwal.theia.Main.DisplayImage;

public class DisplayImageTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCreation(){
		BufferedImage sample = null;
		try{
			sample = ImageIO.read(new File("../Res/sample2.jpg"));
		} catch (IOException e){
			e.printStackTrace();
		}

		DisplayImage di = new DisplayImage(sample);
	}

}
