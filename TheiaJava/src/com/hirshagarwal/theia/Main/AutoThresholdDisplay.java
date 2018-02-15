package com.hirshagarwal.theia.Main;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;

public class AutoThresholdDisplay {
	
	JFrame frame;
	private JTextField textField;
	private JButton btnThresholdImages;
	private JFileChooser directoryChooser = new JFileChooser();
	private File cropDirectory;
	
	public AutoThresholdDisplay() {
		frame = new JFrame();
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JButton btnSelectFolder = new JButton("Select Folder");
		springLayout.putConstraint(SpringLayout.NORTH, btnSelectFolder, 9, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(btnSelectFolder);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, btnSelectFolder, 28, SpringLayout.EAST, textField);
		springLayout.putConstraint(SpringLayout.EAST, textField, -354, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnThresholdImages = new JButton("Threshold Images");
		springLayout.putConstraint(SpringLayout.NORTH, btnThresholdImages, 16, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, btnThresholdImages, 0, SpringLayout.WEST, textField);
		frame.getContentPane().add(btnThresholdImages);
		
		ActionListener selectFolderButtonAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectFolderButtonAction(e);
			}
		};
		
		btnSelectFolder.addActionListener(selectFolderButtonAction);
		
	}
	
	/**
	 * Select the directory containing all of the crops
	 * @param e
	 */
	private void selectFolderButtonAction(ActionEvent e) {
		int returnVal = 0;
		directoryChooser.setDialogTitle("Select Crop Folder");
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		returnVal = directoryChooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			cropDirectory = directoryChooser.getSelectedFile();
		}
	}
	
}
