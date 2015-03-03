# Introduction #

This document introduces the format used for the Nortnacs input forms. A form is broken down into 4 main components. First, there are four calibration points located in the 4 corners of the input image. Next, there are row and column markers which indicate where a potential answer might be. At the intersection of each combination of rows and columns, there is a place for the end user to bubble in. Finally, there are the filled in bubbles themselves which is the data that finally gets represented after processing.


# Calibration Points #

The calibration points in some ways are the most detailed and difficult aspect of the Form Processing algorithm. Once these are found everything else is relative to their location in an expected location, so relatively easy to process, but the calibration points can be anywhere and with several potential data variants, so it is the most tricky.