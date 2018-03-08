package com.hirshagarwal.theia.Main;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.WindowConstants;


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
	private JCheckBox guide;
	private JButton resetAllButton;
	private JButton autoThresholdButton;
	
	
	private JFileChooser fc = new JFileChooser();
	private JFileChooser directoryChooser = new JFileChooser();
	
	
	DisplayImage displayImage;
	ImagePanel imagePane = new ImagePanel();
	
	
	/***
	 * Constructor to create the main display.
	 */
	public Display(){
		// Create a blank frame and define the dimensions
		frame = new JFrame("Program");
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
		selectFileInput = new JComboBox<File>();
		selectFileOutput = new JComboBox<File>();
		removeCropImageButton = new JButton("Remove Image to Crop");
		selectedImagesLabel = new JLabel("Selected Images");
		selectedOutputLabel = new JLabel("Selected Output");
		findPlaqueButton = new JButton("Find Plaque");
		selectionMode = new JComboBox<String>();
		guide = new JCheckBox("Guide");
		resetAllButton = new JButton("Reset");
		autoThresholdButton = new JButton("Auto Threshold");
		l.putConstraint(SpringLayout.NORTH, autoThresholdButton, 6, SpringLayout.SOUTH, cropImageButton);
		l.putConstraint(SpringLayout.WEST, autoThresholdButton, 0, SpringLayout.WEST, selectImageButton);
		
		// Create the two possible selection modes, one for manual cropping and one for "proximity" - which allows for near and far crops to be selected
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
		l.putConstraint(SpringLayout.NORTH, guide, 5, SpringLayout.NORTH, contentPane);
		l.putConstraint(SpringLayout.WEST, guide, 5, SpringLayout.EAST, selectImageButton);
		l.putConstraint(SpringLayout.NORTH, resetAllButton, 5, SpringLayout.NORTH, contentPane);
		l.putConstraint(SpringLayout.WEST, resetAllButton, 5, SpringLayout.EAST, guide);
		l.putConstraint(SpringLayout.NORTH, autoCropButton, 10, SpringLayout.SOUTH, startingNumber);
		l.putConstraint(SpringLayout.WEST, autoCropButton, 5, SpringLayout.WEST, contentPane);
		
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
		ActionListener guideEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guideEnableAction(e);
			}
		};
		ActionListener resetButton = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButtonAction(e);
			}
		};
		ActionListener autoThresholdButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				autoThresholdButtonAction(e);
			}
		};
		
		// Bind components to action listeners
		selectImageButton.addActionListener(selectImageAction);
		cropImageButton.addActionListener(cropImageAction);
		selectCropImagesButton.addActionListener(selectImagesToCropAction);
		resetSelectedOutputButton.addActionListener(resetSelectedOutput);
		selectFileInput.addActionListener(changeImageFocus);
		removeCropImageButton.addActionListener(removeCropImage);
		selectionMode.addActionListener(changeSelectionMode);
		guide.addActionListener(guideEnable);		
		resetAllButton.addActionListener(resetButton);
		autoThresholdButton.addActionListener(autoThresholdButtonListener);
		
		// Add all of the components to the JFrame
		frame.getContentPane().add(selectImageButton);
		frame.getContentPane().add(selectCropImagesButton);
		frame.getContentPane().add(cropImageButton);
		frame.getContentPane().add(removeCropImageButton);
		frame.getContentPane().add(startingNumber);
		frame.getContentPane().add(selectFileInput);
		frame.getContentPane().add(selectFileOutput);
		frame.getContentPane().add(selectedImagesLabel);
		frame.getContentPane().add(selectedOutputLabel);
		frame.getContentPane().add(selectionMode);
		frame.getContentPane().add(guide);
		frame.getContentPane().add(resetAllButton);
		frame.getContentPane().add(autoThresholdButton);
		
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
		guide.setVisible(true);
		autoThresholdButton.setVisible(false);
		
		// Finally, setup the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void autoThresholdButtonAction(ActionEvent e) {
		new AutoThresholdDisplay();
	}
	
	/**
	 * Action to trigger the reset all method from the reset button
	 * @param e
	 */
	private void resetButtonAction(ActionEvent e) {
		resetAll();
	}
	
	/**
	 * Action to enable the guide and tell the user that it is enabled
	 * @param e
	 */
	private void guideEnableAction(ActionEvent e) {
		if(guide.isSelected()) {
			// Guide mode is now enabled
			JOptionPane.showMessageDialog(frame, "Guide Mode Enabled - Textual guides will now explain each action that should be taken.\nFirst press the \"Select Image\" button and select a composited JPEG image for reference.");
		} else {
			// Guide mode is disabled - don't display anything
		}
	}
	
	/**
	 * Action performed when a different output image is selected from the dropdown box. It ensures that the selected input and output paths always match.
	 * Although not a critical feature, it's more clear when they are syncronized.
	 * @param e
	 */
	private void changeImageSelectFocus(ActionEvent e) {
		if (selectFileOutput.getItemCount() == selectFileInput.getItemCount()) {
			// Determine the new selected index for the input chooser
			int selectedInput = selectFileInput.getSelectedIndex();
			// Set the output chooser to match
			selectFileOutput.setSelectedIndex(selectedInput);	
		}
	}
	
	/**
	 * Action performed when an image for the display pane is selected.
	 * Sets and resizes the image as well as displays all of the new necessary components.
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
			
			if(guide.isSelected()) {
				// Explain next steps
				JOptionPane.showMessageDialog(frame, "Now that an image has been selected regions of interested can be isolated by clicking on the corresponding boxes overlaid on the image."
						+ "\nPress the \"Add Image to Crop\" button to select a TIFF stack to crop and a desired output location."
						+ "\nThe textbox at the bottom can be used to set the desired starting number for labeling, the default is 0.");
			}
			
			// Turn on all of the relevant components
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
	
	/**
	 * When the selection mode is changed the selected crops will automatically be removed.
	 * @param e
	 */
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
			String extension = filePath.substring(filePath.lastIndexOf('.'), filePath.length());
			System.out.println(extension);
			try {
				if(!(extension.equalsIgnoreCase(".tif") || extension.equalsIgnoreCase(".tiff"))){
					JOptionPane.showMessageDialog(frame, "You must select a .tif or .tiff file");
					throw new IOException("Selected file not TIFF");
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			// Add the image file to the list of files to crop
			imagesToCrop.add(file);
			// Add the image to the drop-down box to select
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
		
		// Add the text from the selected bath to the drop down box
		selectedImagePaths.setText(selectedImagePaths.getText() + "\r\n" + file.toString() + "\r\n -> " + file2.toString());
		
		// Once an output has been selected show the button to crop the image
		cropImageButton.setVisible(true);
		startingNumber.setVisible(true);
		autoThresholdButton.setVisible(true);
	}
	
	/**
	 * Action to take all of the regions of interest from the preview image and export a series of crops from the selected TIFF stacks
	 * This takes advantage of the two possible selection modes, manual and proximity
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
			
			// Generate Excel File
			ArrayList<CSV> csv = new ArrayList<>();
			csv.add(new CSV("Name", "Number", "Near to Plaque"));
			
			// Iterate over each crop
			for(int i=0; i<displayImage.getSelectedCrops().size(); i++) {
				csv.add(new CSV(Main.getExportTitle(0), i + startingNumberInteger + "", "0"));
			}
			
			// Write the CSV File 
			try {
				Path filePath = Paths.get(outputDirectories.get(0).getPath(), "labels.csv");
				PrintWriter pw = new PrintWriter(new File(filePath.toString()));
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
			
			// Export numbered image
			Main.exportBufferedImage(displayImage.generateManualOutputImage());
			
		// If in proximity mode
		} else if (selectionMode.getSelectedIndex() == 1) {

			// Export Near Crops
			Main.exportCrops(displayImage.getNearCrops());
			// Build CSV
			ArrayList<CSV> csv = new ArrayList<>();
			csv.add(new CSV("Name", "Number", "Near to Plaque"));
			// Iterate over each crop
			for(int i=0; i<displayImage.getNearCrops().size(); i++) {
				csv.add(new CSV(Main.getExportTitle(0), i + startingNumberInteger + "", "1"));
			}
			
			// Update the starting number so that the far crops continue the numbering
			Main.setStartingNumber(startingNumberInteger + displayImage.getNearCrops().size());

			// Export Far Crops
			System.out.println("Far Crop Count: " + displayImage.getFarCrops().size());
			Main.exportCrops(displayImage.getFarCrops());
			// Iterate over each crop
			for(int i=0; i<displayImage.getFarCrops().size(); i++) {
				csv.add(new CSV(Main.getExportTitle(0), i + Main.getStartingNumber() + "", "0"));
			}
			// Write the CSV File 
			try {
				Path filePath = Paths.get(outputDirectories.get(0).getPath(), "labels.csv");
				PrintWriter pw = new PrintWriter(new File(filePath.toString()));
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
		
		JOptionPane.showMessageDialog(frame, "Export Complete");
		
	}
	
	/**
	 * Reset all of the data in the program so that a new image can be cropped
	 */
	private void resetAll() {
		// Reset all of the data held in the main class
		imagesToCrop.clear();
		outputDirectories.clear();
		Main.clearData();
		// Calls the method in the Main class which builds a new display
		Main.restartDisplay();
		// Get rid of the current window
		frame.dispose();
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
				// Manual Select
				if(selectionMode.getSelectedIndex() == 0) {
					// Gets the selected crop based on X and Y coordinates
					int xLoc = ((int)(e.getX()/(cropSize/scalingFactor)));
					int yLoc = ((int)(e.getY()/(cropSize/scalingFactor)));
					System.out.println("Mouse location: " + xLoc + ", " + yLoc);
					displayImage.addSelectPoint(xLoc, yLoc);
					redrawImage();
				}
				// Proximity Selection
				if(selectionMode.getSelectedIndex() == 1) {
					// Gets the selected crop based on X and Y coordinates
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
		frame.getContentPane().add(imagePane);
		frame.setVisible(true);
	}
	
	/**
	 * Show the current image from display image. This method works under the assumption that DisplayImage.currentImage is current.
	 */
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
//			BufferedImage currentImage = displayImage.generateCurrentImage();
			BufferedImage currentImage = displayImage.getCurrentImage();
			// Scale down the image for display
			Image tmp = currentImage.getScaledInstance((int)imagePaneSize.getWidth(), (int)imagePaneSize.getHeight(), Image.SCALE_SMOOTH);
			imagePane.setImage(tmp);
			imagePane.repaint();
		}
		
	}
	
	/**
	 * Output the current selection mode (either proximity or manual)
	 * @return
	 */
	public String getSelectionMode() {
		return selectionMode.getSelectedItem().toString();
	}
	
}
