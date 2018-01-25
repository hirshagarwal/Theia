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
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
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
	private final int width = 1500;
	private final int height = 900;
		// Image display size
	private final Dimension imagePaneSize = new Dimension(696, 520);
		// Number of grid lines (gridSize x gridSize)
	private final int gridSize = 10;
	
	// Global Fields
	private JFrame frame;
	private JButton cropImageButton;
	private JButton selectCropImagesButton;
	private JTextArea selectedImagePaths;
	private ArrayList<File> imagesToCrop = new ArrayList<>();
	private ArrayList<File> outputDirectories = new ArrayList<>();
	private JTextField startingNumber = new JTextField();
	private JButton resetSelectedOutputButton;
	
	// TODO: Remove default file path
//	private final JFileChooser fc = new JFileChooser();
	private final JFileChooser fc = new JFileChooser("C:\\Users\\hirsh\\Documents\\Research\\Year 2\\Samples\\Sample 1");
	private final JFileChooser directoryChooser = new JFileChooser("C:\\Users\\hirsh\\Documents\\Research\\Year 2");
	
	
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
//		contentPane.setLayout(new FlowLayout());
		SpringLayout l = new SpringLayout();
		contentPane.setLayout(l);

		// Create select image button
		JButton selectImageButton = new JButton("Select Image");
		selectCropImagesButton = new JButton("Add Images to Crop");
		cropImageButton = new JButton("Crop Image");
		selectedImagePaths = new JTextArea();
		resetSelectedOutputButton = new JButton("Reset Images to Crop");
		
		selectedImagePaths.setColumns(20);
		startingNumber.setColumns(4);
		startingNumber.setText("0");
		selectImageButton.setBounds(0, 0, 120, 40);
		cropImageButton.setBounds(0, 300, 120, 40);
		selectCropImagesButton.setBounds(0, 150, 120, 40);
		
		// Layout Constraints
		l.putConstraint(SpringLayout.EAST, imagePane, 5, SpringLayout.EAST, contentPane);
		l.putConstraint(SpringLayout.WEST, selectImageButton, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectImageButton, 5, SpringLayout.NORTH, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectCropImagesButton, 5, SpringLayout.SOUTH, selectImageButton);
		l.putConstraint(SpringLayout.WEST, selectCropImagesButton, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, cropImageButton, 5, SpringLayout.SOUTH, selectCropImagesButton);
		l.putConstraint(SpringLayout.WEST, cropImageButton, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectedImagePaths, 15, SpringLayout.SOUTH, cropImageButton);
		l.putConstraint(SpringLayout.WEST, selectedImagePaths, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, startingNumber, 5, SpringLayout.SOUTH, selectedImagePaths);
		l.putConstraint(SpringLayout.WEST, startingNumber, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.WEST, resetSelectedOutputButton, 5, SpringLayout.EAST, startingNumber);
		l.putConstraint(SpringLayout.NORTH, resetSelectedOutputButton, 5, SpringLayout.SOUTH, startingNumber);

		
		// Create the button action listeners  
		ActionListener selectImageAction = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				selectImageAction(e);
			}
		};
		ActionListener cropImageAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cropImageAction(e);
			}
		};
		ActionListener selectImagesToCropAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImagesToCropAction(e);
			}
		};
		ActionListener resetSelectedOutput = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetSelected(e);
			}
		};

		selectImageButton.addActionListener(selectImageAction);
		cropImageButton.addActionListener(cropImageAction);
		selectCropImagesButton.addActionListener(selectImagesToCropAction);
		resetSelectedOutputButton.addActionListener(resetSelectedOutput);
		
		frame.add(selectImageButton);
		frame.add(selectCropImagesButton);
		frame.add(cropImageButton);
		frame.add(selectedImagePaths);
		frame.add(startingNumber);
		frame.add(resetSelectedOutputButton);
		cropImageButton.setVisible(false);
		selectCropImagesButton.setVisible(false);
		selectedImagePaths.setVisible(false);
		selectedImagePaths.setEditable(false);
		resetSelectedOutputButton.setVisible(false);
		startingNumber.setVisible(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void selectImageAction(ActionEvent e) {
		// Set the image action
		int returnVal = fc.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			Main.setImageFile(file);
			displayImage = null;
			drawImage(Main.getCurrentImage());
			selectCropImagesButton.setVisible(true);
		}
	}
	
	private void selectImagesToCropAction(ActionEvent e) {
		// Get File to Crop
		fc.setDialogTitle("Please Choose File to Crop");
		int returnVal = fc.showOpenDialog(frame);
		File file = new File("");
		File file2 = new File("");
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			// Iterate through all of the selected files and add to list
			imagesToCrop.add(file);
		}
		
		// Get Output Location
		directoryChooser.setDialogTitle("Select Output Location");
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		returnVal = directoryChooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file2 = directoryChooser.getSelectedFile();
			outputDirectories.add(file2);
		}
		
		selectedImagePaths.setText(selectedImagePaths.getText() + "\r\n" + file.toString() + "\r\n -> " + file2.toString());
		
		selectedImagePaths.setVisible(true);
		cropImageButton.setVisible(true);
		resetSelectedOutputButton.setVisible(true);
		startingNumber.setVisible(true);
	}
	
	private void cropImageAction(ActionEvent e) {
		Main.setExportDirectories(outputDirectories);
		Main.setStartingNumber(Integer.parseInt(startingNumber.getText()));
		for(int i=0; i<imagesToCrop.size(); i++) {
			String s = (String) JOptionPane.showInputDialog(frame, "Enter the desired output filename: ", "Filename", JOptionPane.PLAIN_MESSAGE, null, null, "Crop");
			Main.addExportTitle(s);
			Main.addFileToCrop(imagesToCrop.get(i));
		}
		Main.exportCrops(displayImage.getSelectedCrops());
	}
	
	private void resetSelected(ActionEvent e) {
		imagesToCrop.clear();
		outputDirectories.clear();
		selectedImagePaths.setText("");
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
