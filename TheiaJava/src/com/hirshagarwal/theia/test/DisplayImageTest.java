package com.hirshagarwal.theia.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.hirshagarwal.theia.Main.DisplayImage;

public class DisplayImageTest {
	
	@Test
	public void testSelectPointAdd(){
		DisplayImage di = new DisplayImage(null);
		di.addSelectPoint(1, 1);
		assertEquals(di.getSelectPoints().size(), 1);
		di.addSelectPoint(2, 2);
		assertEquals(di.getSelectPoints().size(), 2);
	}
	
	@Test
	public void testSelectPointRemove(){
		DisplayImage di = new DisplayImage(null);
		di.addSelectPoint(1, 1);
		di.addSelectPoint(2, 2);
		assertEquals(di.getSelectPoints().size(), 2);
		di.removeSelectPoint(2, 2);
		assertEquals(di.getSelectPoints().size(), 1);
	}
	
	@Test
	public void failingTest() {
		assertTrue(false);
	}

}
