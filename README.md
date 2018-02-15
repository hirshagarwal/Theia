# Theia - [![Build Status](https://travis-ci.com/hirshagarwal/Theia.svg?token=mSzPbV9PoZUsz4A2j2RR&branch=master)](https://travis-ci.com/hirshagarwal/Theia)

Automated Image Processing

## General Information
This program runs on the latest version of Java (JRE 9). It may execute on earlier versions of the Java Runtime Environment, however they are not necessarily fully supported.

## Exporting Regions of Interest
When the program starts only a single button is available. This allows for a flattened JPG image to be loaded, which is displayed for reference when selecting image regions. After an image is selected several other functions become available.

Underneath the image a combo box allows for the selection between two different modes. The default mode is manual, in which one type of region can be selected and exported. There is also an option for "proximity mode" (explain later).

On the left of the window there should be two buttons, "Add images to crop" and "Remove images to crop". These buttons allow the user to choose TIFF stacks from which regions will be exported. When the "Add images to crop" button is pressed the user will be presented with two file selectors. The first file selector is used to choose an input TIFF stack. The second file selector is used to select and output destination. These selections are displayed in the boxes below.