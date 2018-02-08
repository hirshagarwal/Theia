package com.hirshagarwal.theia.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private final int height = 700;
		// Image display size
	private Dimension imagePaneSize = new Dimension(696, 520);
	public static int scalingFactor = 2;
	// Number of pixels in a single crop
	public static final int cropSize = 100;
	
	// Global Fields
	private JFrame frame;
	private JButton cropImageButton;
	private JButton selectCropImagesButton;
	private JTextArea selectedImagePaths;
	private ArrayList<File> imagesToCrop = new ArrayList<>();
	private ArrayList<File> outputDirectories = new ArrayList<>();
	private JTextField startingNumber = new JTextField();
	private JButton resetSelectedOutputButton;
	private JButton autoCropButton;
	private JButton removeCropImageButton;
	private JComboBox<File> selectFileInput;
	private JComboBox<File> selectFileOutput;
	private JLabel selectedImagesLabel;
	private JLabel selectedOutputLabel;
	private JButton findPlaqueButton;
	private JComboBox<String> selectionMode;
	
	private JFileChooser fc = new JFileChooser();
	private JFileChooser directoryChooser = new JFileChooser();
	
	
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
		
		// Set the file choosers
		if (Main.DEBUGGING) {
			fc = new JFileChooser("C:\\Users\\hirsh\\Documents\\Research\\Year 2\\Samples\\Sample 1");
			directoryChooser = new JFileChooser("C:\\Users\\hirsh\\Documents\\Research\\Year 2");
		}

		// Create select image button
		JButton selectImageButton = new JButton("Select Image");
		selectCropImagesButton = new JButton("Add Image to Crop");
		cropImageButton = new JButton("Crop Image");
		selectedImagePaths = new JTextArea();
		resetSelectedOutputButton = new JButton("Reset Images to Crop");
		autoCropButton = new JButton("Auto Crop");
		selectFileInput = new JComboBox();
		selectFileOutput = new JComboBox();
		removeCropImageButton = new JButton("Remove Image to Crop");
		selectedImagesLabel = new JLabel("Selected Images");
		selectedOutputLabel = new JLabel("Selected Output");
		findPlaqueButton = new JButton("Find Plaque");
		selectionMode = new JComboBox();
		
		selectionMode.addItem("Manual");
		selectionMode.addItem("Proximity");

		
		startingNumber.setColumns(4);
		startingNumber.setText("0");

		
		// Layout Constraints
		l.putConstraint(SpringLayout.EAST, imagePane, 5, SpringLayout.EAST, contentPane);
		l.putConstraint(SpringLayout.WEST, selectImageButton, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectImageButton, 5, SpringLayout.NORTH, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectCropImagesButton, 5, SpringLayout.SOUTH, selectImageButton);
		l.putConstraint(SpringLayout.WEST, selectCropImagesButton, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, cropImageButton, 5, SpringLayout.SOUTH, selectFileOutput);
		l.putConstraint(SpringLayout.WEST, cropImageButton, 5, SpringLayout.EAST, startingNumber);
		l.putConstraint(SpringLayout.WEST, removeCropImageButton, 5, SpringLayout.EAST, selectCropImagesButton);
		l.putConstraint(SpringLayout.NORTH, removeCropImageButton, 5, SpringLayout.SOUTH, selectImageButton);
		l.putConstraint(SpringLayout.NORTH, selectedImagesLabel, 5, SpringLayout.SOUTH, selectCropImagesButton);
		l.putConstraint(SpringLayout.WEST, selectedImagesLabel, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.WEST, selectedOutputLabel, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.NORTH, selectedOutputLabel, 5, SpringLayout.SOUTH, selectFileInput);
		l.putConstraint(SpringLayout.NORTH, startingNumber, 5, SpringLayout.SOUTH, selectFileOutput);
		l.putConstraint(SpringLayout.WEST, startingNumber, 5, SpringLayout.WEST, contentPane);
		l.putConstraint(SpringLayout.WEST, resetSelectedOutputButton, 5, SpringLayout.EAST, selectCropImagesButton);
		l.putConstraint(SpringLayout.NORTH, resetSelectedOutputButton, 5, SpringLayout.SOUTH, selectImageButton);
		l.putConstraint(SpringLayout.WEST, autoCropButton, 5, SpringLayout.EAST, selectCropImagesButton);
		l.putConstraint(SpringLayout.NORTH, autoCropButton, 5, SpringLayout.SOUTH, selectImageButton);
		l.putConstraint(SpringLayout.WEST, selectFileInput, 5, SpringLayout.EAST, selectedImagesLabel);
		l.putConstraint(SpringLayout.NORTH, selectFileInput, 5, SpringLayout.SOUTH, selectCropImagesButton);
		l.putConstraint(SpringLayout.NORTH, selectFileOutput, 5, SpringLayout.SOUTH, selectFileInput);
		l.putConstraint(SpringLayout.WEST, selectFileOutput, 5, SpringLayout.EAST, selectedOutputLabel);
		l.putConstraint(SpringLayout.NORTH, selectionMode, 5, SpringLayout.SOUTH, imagePane);
		l.putConstraint(SpringLayout.EAST, selectionMode, 5, SpringLayout.EAST, contentPane);

		
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
		ActionListener changeImageFocus = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeImageSelectFocus(e);
			}
		};
		ActionListener removeCropImage = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeImageToCropAction(e);
			}
		};
		ActionListener changeSelectionMode = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeSelectionModeAction(e);
			}
		};

		selectImageButton.addActionListener(selectImageAction);
		cropImageButton.addActionListener(cropImageAction);
		selectCropImagesButton.addActionListener(selectImagesToCropAction);
		resetSelectedOutputButton.addActionListener(resetSelectedOutput);
		selectFileInput.addActionListener(changeImageFocus);
		removeCropImageButton.addActionListener(removeCropImage);
		selectionMode.addActionListener(changeSelectionMode);
				
		// Add all of the components to the JFrame
		frame.add(selectImageButton);
		frame.add(selectCropImagesButton);
		frame.add(cropImageButton);
		frame.add(removeCropImageButton);
		frame.add(startingNumber);
		frame.add(selectFileInput);
		frame.add(selectFileOutput);
		frame.add(selectedImagesLabel);
		frame.add(selectedOutputLabel);
//		frame.add(findPlaqueButton);
		frame.add(selectionMode);
		
		// Choose which components to display
		selectionMode.setVisible(false);
		findPlaqueButton.setVisible(true);
		selectedImagesLabel.setVisible(false);
		selectedOutputLabel.setVisible(false);
		removeCropImageButton.setVisible(false);
		cropImageButton.setVisible(false);
		selectCropImagesButton.setVisible(false);
		startingNumber.setVisible(false);
		selectFileInput.setVisible(false);
		selectFileOutput.setVisible(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Action performed when a different output image is selected from the dropdown box
	 * @param e
	 */
	private void changeImageSelectFocus(ActionEvent e) {
		if (selectFileOutput.getItemCount() == selectFileInput.getItemCount()) {
			int selectedInput = selectFileInput.getSelectedIndex();
			selectFileOutput.setSelectedIndex(selectedInput);	
		}
	}
	
	/**
	 * Action performed when an image for the display pane is selected
	 * @param e
	 */
	private void selectImageAction(ActionEvent e) {
		// Set the image action
		int returnVal = fc.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			Main.setImageFile(file);
			imagePaneSize.setSize(Main.getCurrentImage().getWidth()/scalingFactor, Main.getCurrentImage().getHeight()/scalingFactor);
			displayImage = new DisplayImage(Main.getCurrentImage());
			drawImage(Main.getCurrentImage());
			selectCropImagesButton.setVisible(true);
			removeCropImageButton.setVisible(true);
			selectFileInput.setVisible(true);
			selectFileOutput.setVisible(true);
			selectedImagesLabel.setVisible(true);
			selectedOutputLabel.setVisible(true);
			selectionMode.setVisible(true);
			frame.repaint();
		}
	}
	
	/**
	 * Remove the currently selected items from the input and output boxes.
	 * @param e
	 */
	private void removeImageToCropAction(ActionEvent e) {
		selectFileInput.removeItemAt(selectFileInput.getSelectedIndex());
		selectFileOutput.removeItemAt(selectFileOutput.getSelectedIndex());
	}
	
	@Deprecated
	private void autoCrop(ActionEvent e) {
		AutomatedCrop autoCrop = new AutomatedCrop();
		autoCrop.cropImage(Main.getCurrentImage());
	}
	
	private void changeSelectionModeAction(ActionEvent e) {
		displayImage.generateCurrentImageProximity();
		redrawImage();
	}
	
	/**
	 * Action performed when an image to crop is added
	 * @param e
	 */
	private void selectImagesToCropAction(ActionEvent e){
		// Get File to Crop
		fc.setDialogTitle("Please Choose File to Crop");
		int returnVal = fc.showOpenDialog(frame);
		File file = new File("");
		File file2 = new File("");
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			
			// Check to make sure that the file is a .tif/.tiff file
			String filePath = fc.getSelectedFile().toString();
			String extension = filePath.substring(filePath.indexOf('.'), filePath.length());
			try {
				if(!(extension.equalsIgnoreCase(".tif") || extension.equalsIgnoreCase(".tiff"))){
					throw new IOException("Selected file not TIFF");
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			
			imagesToCrop.add(file);
			selectFileInput.addItem(file);	
			
		}
		
		// Get Output Location
		directoryChooser.setDialogTitle("Select Output Location");
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		returnVal = directoryChooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			file2 = directoryChooser.getSelectedFile();
//			outputDirectories.add(file2);
			selectFileOutput.addItem(file2);
		}
		
		selectedImagePaths.setText(selectedImagePaths.getText() + "\r\n" + file.toString() + "\r\n -> " + file2.toString());
		
		cropImageButton.setVisible(true);
		startingNumber.setVisible(true);
	}
	
	/**
	 * Action to crop the current images
	 * @param e
	 */
	private void cropImageAction(ActionEvent e) {
		// Add all of the output locations
		outputDirectories.clear();
		imagesToCrop.clear();
		for (int i=0; i<selectFileOutput.getItemCount(); i++) {
			outputDirectories.add(selectFileOutput.getItemAt(i));
			imagesToCrop.add(selectFileInput.getItemAt(i));
		}
		// Set the output directories from the selector box
		Main.setExportDirectories(outputDirectories);
		int startingNumberInteger = Integer.parseInt(startingNumber.getText());
		Main.setStartingNumber(startingNumberInteger);
		
		// Iterate over all the images to crop to get a file name
		for(int i=0; i<imagesToCrop.size(); i++) {
			String s = (String) JOptionPane.showInputDialog(frame, "Enter the desired output filename: ", "Filename", JOptionPane.PLAIN_MESSAGE, null, null, "Crop");
			Main.addExportTitle(s);
			Main.addFileToCrop(imagesToCrop.get(i));
		}
		
		// If in manual mode just export the images
		if(selectionMode.getSelectedIndex() == 0) {
			Main.exportCrops(displayImage.getSelectedCrops());
		
		// If in proximity mode
		} else if (selectionMode.getSelectedIndex() == 1) {

			// Export Near Crops
			Main.exportCrops(displayImage.getNearCrops());
			// Build CSV
			ArrayList<CSV> csv = new ArrayList<>();
			csv.add(new CSV("Name", "Number", "Near to Plaque"));
			// Iterate over each crop
			for(int i=0; i<displayImage.getNearCrops().size(); i++) {
				csv.add(new CSV(Main.getExportTitle(0), i + startingNumberInteger + "", "Yes"));
			}
			
			// Update the starting number so that the far crops continue the numbering
			Main.setStartingNumber(startingNumberInteger + displayImage.getNearCrops().size());

			// Export Far Crops
			System.out.println("Far Crop Count: " + displayImage.getFarCrops().size());
			Main.exportCrops(displayImage.getFarCrops());
			// Iterate over each crop
			for(int i=0; i<displayImage.getFarCrops().size(); i++) {
				csv.add(new CSV(Main.getExportTitle(0), i + Main.getStartingNumber() + "", "No"));
			}
			// Write the CSV File 
			try {
				PrintWriter pw = new PrintWriter(new File(outputDirectories.get(0) + "\\labels.csv"));
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<csv.size(); i++) {
					sb.append(csv.get(i).toString());
				}
				System.out.println(sb.toString());
				pw.write(sb.toString());
				pw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
			// Export the numbered image
			Main.exportBufferedImage(displayImage.generateOutputImage());
			
		}		
		
	}
	
	private void resetAll() {
		// TODO: Create method to reset all of the data
	}
	
	/**
	 * Reset the currently selected output locations
	 * @param e
	 */
	private void resetSelected(ActionEvent e) {
		imagesToCrop.clear();
		outputDirectories.clear();
		Main.setExportDirectories(outputDirectories);
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
				if(selectionMode.getSelectedIndex() == 0) {
					int xLoc = ((int)(e.getX()/(cropSize/scalingFactor)));
					int yLoc = ((int)(e.getY()/(cropSize/scalingFactor)));
					System.out.println("Mouse location: " + xLoc + ", " + yLoc);
					displayImage.addSelectPoint(xLoc, yLoc);
					redrawImage();
				}
				if(selectionMode.getSelectedIndex() == 1) {
					int xLoc = ((int)(e.getX()/(cropSize/scalingFactor)));
					int yLoc = ((int)(e.getY()/(cropSize/scalingFactor)));
					System.out.println("Mouse location: " + xLoc + ", " + yLoc);
					if(e.getButton() == 1) {
						System.out.println("Mouse Button: " + 1);
						displayImage.addNearPoint(xLoc, yLoc);
					} else {
						System.out.println("Mouse Button: Other");
						displayImage.addFarPoint(xLoc, yLoc);
					}
					redrawImage();
				}
				
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
		if(selectionMode.getSelectedIndex() == 1) {
//			BufferedImage currentImage = displayImage.generateCurrentImageProximity();
			BufferedImage currentImage = displayImage.getCurrentImage();
			// Scale down the image for display
			Image tmp = currentImage.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
			imagePane.setImage(tmp);
			imagePane.repaint();
		} else if (selectionMode.getSelectedIndex() == 0) {
			BufferedImage currentImage = displayImage.generateCurrentImage();
			// Scale down the image for display
			Image tmp = currentImage.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
			imagePane.setImage(tmp);
			imagePane.repaint();
		}
		
	}
	
	public String getSelectionMode() {
		return selectionMode.getSelectedItem().toString();
	}
	
}
