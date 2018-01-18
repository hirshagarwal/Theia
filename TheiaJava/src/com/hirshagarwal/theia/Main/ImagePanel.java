package com.hirshagarwal.theia.Main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
	private Image image;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	public void setImage(Image image){
		this.image = image;
	}

}
