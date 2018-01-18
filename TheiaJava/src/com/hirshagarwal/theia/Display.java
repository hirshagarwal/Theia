package com.hirshagarwal.theia;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Display {
	
	private JFrame frame;
	private final int width = 700;
	private final int height = 700;
	private final JFileChooser fc = new JFileChooser();
	
	// Create the main display
	public Display(){
		frame = new JFrame();
		frame.setSize(width, height);
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		// Create the display elements
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
		frame.setVisible(true);
	}
	
	public void drawImage(BufferedImage image){
		// TODO: Make images scale correctly
		int imageLabelWidth = 1392/2;
		int imageLabelHeight = 1040/2;
		Image scaledImage = image.getScaledInstance(imageLabelWidth, imageLabelHeight, Image.SCALE_SMOOTH);
		JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
		imageLabel.setBounds(0, 50, imageLabelWidth, imageLabelHeight);
		imageLabel.setVisible(true);
		frame.add(imageLabel);
		frame.repaint();
	}

}
