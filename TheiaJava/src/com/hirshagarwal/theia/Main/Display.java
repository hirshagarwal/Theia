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
		// Create image to draw grid lines on
		BufferedImage gridImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = gridImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.setBackground(Color.WHITE);
		BasicStroke bs = new BasicStroke(2);
		g2d.setStroke(bs);
		
		// Draw white grid lines
		for(int i=0; i<gridSize+1; i++){
			g2d.drawLine((image.getWidth()+2)/gridSize*i, 0, (image.getWidth()+2)/gridSize*i, image.getHeight()-1);
			g2d.drawLine(0, (image.getHeight()+2)/gridSize*i, image.getWidth()-1, (image.getHeight()+2)/gridSize*i);
		}
		
		// Resize Image
		Image tmp = gridImage.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
		JPanel imagePane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(tmp, 0, 0, null);
			}
		};
		
		// Set the pane size in the layout
		imagePane.setPreferredSize(imagePaneSize);
		
		// Set image pane on-click actions
		imagePane.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				System.out.println("Mouse location: " + e.getX() + ", " + e.getY());
				
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
	
}
