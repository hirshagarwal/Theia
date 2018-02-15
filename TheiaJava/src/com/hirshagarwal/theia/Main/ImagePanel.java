package com.hirshagarwal.theia.Main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Class that shows the image panel
 * @author Hirsh R. Agarwal
 *
 */

public class ImagePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6534569916418284351L;
	private Image image;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	public void setImage(Image image){
		this.image = image;
	}

}
