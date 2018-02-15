package com.hirshagarwal.theia.Main;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.swing.SpringLayout;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class AutoThresholdDisplay {
	
	JFrame frame;
	private JTextField textField;
	private JButton btnThresholdImages;
	private JFileChooser directoryChooser = new JFileChooser();
	private File cropDirectory;
	private ArrayList<ImageStack> images = new ArrayList<>();

	/**
	 * Main display for the auto thresholding tool
	 * This tool allows a directory of TIFF files to be selected and automatically thresholded
	 */
	public AutoThresholdDisplay() {
		frame = new JFrame("Auto Threshold");
		frame.setSize(500, 400);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JButton btnSelectFolder = new JButton("Select Folder");
		springLayout.putConstraint(SpringLayout.NORTH, btnSelectFolder, 9, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnSelectFolder, -121, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnSelectFolder);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnSelectFolder);
		springLayout.putConstraint(SpringLayout.NORTH, textField, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnThresholdImages = new JButton("Threshold Images");
		springLayout.putConstraint(SpringLayout.NORTH, btnThresholdImages, 16, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, btnThresholdImages, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnThresholdImages);
		
		ActionListener selectFolderButtonAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderButtonAction(e);
			}
		};
		
		btnSelectFolder.addActionListener(selectFolderButtonAction);
		
		// Finally, setup the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Select the directory containing all of the crops
	 * @param e
	 */
	private void selectFolderButtonAction(ActionEvent e) {
		// Allow the user to select a directory containing their tiff files
		int returnVal = 0;
		directoryChooser.setDialogTitle("Select Crop Folder");
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		returnVal = directoryChooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			cropDirectory = directoryChooser.getSelectedFile();
		}
		textField.setText(cropDirectory.toString());
		
		// Get all of the files from the selected folder
		ArrayList<Path> filePaths = new ArrayList<>();
		try (Stream<Path> paths = Files.walk(Paths.get(cropDirectory.toURI()))) {
		    paths
		        .filter(Files::isRegularFile)
		        .forEach(item->filePaths.add(item));
		} catch(IOException error) {
			error.printStackTrace();
		}
		System.out.println("First File Path: " + filePaths.get(0));
		
		// Iterate over files and take out any that are not .tiff
		for(int i=0; i<filePaths.size(); i++) {
			String path = filePaths.get(i).toString();
			String extension = path.substring(path.indexOf('.'), path.length());
			if(!(extension.equals(".tif") || extension.equals(".tiff"))) {
				filePaths.remove(i);
			}
		}
		
		// Read all of the images
		readImages(filePaths);
	}
	
	private void readImages(ArrayList<Path> filePaths) {
		for(int i=0; i<filePaths.size(); i++) {
			ImageStack currentImage = new ImageStack();
			currentImage.readFromFile(filePaths.get(i).toFile());
			images.add(currentImage);
		}
	}
	
}
