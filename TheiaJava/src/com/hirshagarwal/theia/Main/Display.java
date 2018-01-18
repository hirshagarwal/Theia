package com.hirshagarwal.theia.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;


/***
 * Class to render the GUI for the entire software.
 * @author Hirsh Agarwal
 *
 */
public class Display {
	
	// Parameters
		// Window width and height
	private final int width = 900;
	private final int height = 700;
		// Image display size
	private final Dimension imagePaneSize = new Dimension(696, 520);
		// Number of grid lines (gridSize x gridSize)
	private final int gridSize = 10;

	
	// Global Fields
	private JFrame frame;
	private final JFileChooser fc = new JFileChooser();
	DisplayImage displayImage;
	ImagePanel imagePane = new ImagePanel();
	
	/***
	 * Constructor to create the main display.
	 */
	public Display(){
		// Create a blank frame and define the dimensions
		frame = new JFrame();
		frame.setSize(width, height);
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());

		// Create select image button
		JButton selectImageButton = new JButton("Select Image");
		selectImageButton.setBounds(0, 0, 120, 40);
		ActionListener selectImageAction = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// Set the image action
				int returnVal = fc.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					Main.setImageFile(file);
					drawImage(Main.getCurrentImage());
				}
			}
		};
		selectImageButton.addActionListener(selectImageAction);
		
		frame.add(selectImageButton);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	
	/***
	 * Draws an image with a grid on top so that the user can select crops from it.
	 * @param image
	 */
	public void drawImage(BufferedImage image){	
		displayImage = new DisplayImage(image);
		BufferedImage imageTest = displayImage.generateGridImage();
		
		// Resize Image
		Image tmp = imageTest.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
//		Image tmp = imageTest;
		imagePane.setImage(tmp);
		imagePane.repaint();
		
		// Set the pane size in the layout
		imagePane.setPreferredSize(imagePaneSize);
		
		// Set image pane on-click actions
		imagePane.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				// TODO: Make the scaling value (5) dynamic based on how much the image is being scaled
				int xLoc = ((int)(e.getX()*2)/100);
				int yLoc = ((int)(e.getY()*2)/100);
				System.out.println("Mouse location: " + xLoc + ", " + yLoc);
				displayImage.addSelectPoint(xLoc, yLoc);
				redrawImage();
			}
			
			public void mouseEntered(MouseEvent e){

			}
			
			public void mouseExited(MouseEvent e){

			}
			
			public void mouseReleased(MouseEvent e){
				
			}
			
			public void mousePressed(MouseEvent e){
				
			}
			
		});
		
		// Add the image pane to the frame
		frame.add(imagePane);
		frame.setVisible(true);
	}
	
	public void redrawImage(){
		// Generate a new image based off selected points
		BufferedImage currentImage = displayImage.generateCurrentImage();
		// Scale down the image for display
		Image tmp = currentImage.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
		imagePane.setImage(tmp);
		imagePane.repaint();
	}
	
}
