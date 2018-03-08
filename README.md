# Theia - [![Build Status](https://travis-ci.com/hirshagarwal/Theia.svg?token=mSzPbV9PoZUsz4A2j2RR&branch=master)](https://travis-ci.com/hirshagarwal/Theia)

Automated Image Processing

## General Information
This program runs on the latest version of Java (JRE 9). Without Java 9 the program will run, but will fail to execute critical functions.

## Exporting Regions of Interest
When the program starts only a single button is available. This allows for a flattened JPG image to be loaded, which is displayed for reference when selecting image regions. After an image is selected several other functions become available.

Underneath the image a combo box allows for the selection between two different modes. The default mode is manual, in which one type of region can be selected and exported. There is also an option for "proximity mode" (explained later).

On the left of the window there should be two buttons, "Add images to crop" and "Remove images to crop". These buttons allow the user to choose TIFF stacks from which regions will be exported. When the "Add images to crop" button is pressed the user will be presented with two file selectors. The first file selector is used to choose an input TIFF stack. The second file selector is used to select and output destination. These selections are displayed in the boxes below.

Underneath these selection boxes there is the "crop" button and a textbox. The textbox is used to determine the image starting number. When pressed the crop button will export regions from the selected TIFF stacks to the output destination folder. The user will be asked to provide a name for each input TIFF, these are used to label the output files.

## Proximity Cropping
The "proximity" mode allows for the selection of regions near and far from a plaque. In this mode the left mouse button will select, in blue, a region to be labelled as close to a plaque. The right mouse button will place a green selection box on regions farther away from plaques. Once regions have been selected they can be exported normally and are distinguished with a column in the corresponding excel file.

## Output Files
After regions are selected and the program is told to make crops several files will be generated. Firstly, in each corresponding output folder, the TIFF stacks will be cropped, and the resulting regions will be present as smaller TIFF stacks. In the first output folder selected there will also be a JPG image showing the region number to their location in the original image. Finally, there will be an Excel (.csv) file which contains information about which crops correspond to which number and (if applicable) which ones are near to plaques.

## Known Issues
In the event of a problem restarting the program is usually the best solution.
### Program doesn't seem to save any files
	The most likely situation is that an outdated version of Java is being used. Updating to Java 9 should resolve the issue.
