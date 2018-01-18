package com.hirshagarwal.theia.Main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class DisplayTest {
	
	public static void main(String[] args) throws IOException{
		JFrame frame = buildFrame();
		
		final BufferedImage image = ImageIO.read(new File("C:\\Users\\Hirsh Agarwal\\Documents\\Research\\Year 2\\Samples\\Sample 2\\SD055-14 BA41-42-b3 image3 aligned for crops.jpg"));
		
		JPanel pane = new JPanel(){
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}
		};
		frame.add(pane);
		frame.setVisible(true);
	}
	
	private static JFrame buildFrame(){
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);
		return frame;
	}

}
