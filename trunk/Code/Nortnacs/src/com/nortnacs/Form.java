/*
 * @author Anthony Macias, Scott Pierro , Henry Crossley, Angela Dominguez, Jose Romero
 *
 * Nortnacs package project declaration
 * Import of Java API's awt.Point, awt.image.BufferedImage, io.File, io.IOException, util.Arraylist, imageio.ImageIO
 */
package com.nortnacs;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 * Form class declaration - variables of the Form class are required to locate and store the data points
 * Point subclass declaration. JAI operator
 * Point variable declarations calibrationTL, calibrationTR, calibrationBL, calibrationBR. For row and column calibration.
 * Array declarations for Rows and Columns. Integer type declarations to keep count of rows and columns
 * BufferImage type declared for test image
 */

public final class Form {

    private PointDescriptor[][] points;
    private Point calibrationTL;
    private Point calibrationTR;
    private Point calibrationBL;
    private Point calibrationBR;
    private int avgCalibrationPointSize;
    private int columnCount;
    private int rowCount;
    private ArrayList<Integer> rows = new ArrayList<Integer>();
    private ArrayList<Integer> columns = new ArrayList<Integer>();
    private BufferedImage inputImage;


/**
 * Constructor for Form class File type.
 * Image file to be processed is now of File type and set as variable "f".
 * @param string
 *
 */
     Form(String imgPath) throws IOException {
        inputImage = ImageIO.read(new File(imgPath));
    }
/**
 * Constructor for Form class BufferedImage type.
 * @param BufferedImage
 *
 */
    Form(BufferedImage img) throws IOException {
        inputImage = img;
    }

    /**
     * Function call to "process" passes "sensitivity" which is the amount of whitespace allowed past calibration points
     * failure to call "process" returns exception.
     * @throws IOException
     */
    public void process() throws IOException {
        process(5);
    }

    /**
     * Begin function "process". Passing sensitivity.
     * "process" will call "findCalibrationPoints" which will locate the four points in the four corners of testing form
     * "process" will call "findRows" which will use the calibration points to obtain the rows
     * "process" will call "findColumns" which will use the calibration points to obtain the columns
     * "points" array is a PointDescriptor array type of size rowCount and columnCount.
     * finally "process" will increment through the points and find filled areas with function "processXY".
     * failure to obtain calibration points, rows, or colummns returns exception
     * @param sensitivity
     * @throws IOException
     */
    public void process(int sensitivity) throws IOException {
        findCalibrationPoints();

        findRows();

        findColumns();

        //TODO: If Rows and/or columns are empty, attempt to rotate image and try again

        if(rows.isEmpty() && columns.isEmpty()) {
            throw new IOException("Could not find any rows or columns");
        } else if(rows.isEmpty()) {
            throw new IOException("Could not find any rows");
        } else if(columns.isEmpty()) {
            throw new IOException("Could not find any columns");
        }

        //System.out.println(rows.size() + ", " + columns.size());
        points = new PointDescriptor[rowCount][columnCount];

        for(int rowNum = 0; rowNum < rowCount; rowNum++) {
            for(int colNum = 0; colNum < columnCount; colNum++) {
                points[rowNum][colNum] = processXY(rowNum, colNum);
            }
        }
    }

    /**
     * Boolean function isMarked overloaded with integer parameters row and column which returns row and column of mark if true
     * and with point parameter p which returns x and y of point if true.
     * @param row
     * @param col
     * @return
     */
    public boolean isMarked(int row, int col) {
        return points[row][col].isBubbled;
    }

    /**
     * 
     * @param p
     * @return
     */
    public boolean isMarked(Point p) {
        return isMarked(p.x, p.y);
    }

    /**
     * Member function getPointDescriptor obtains row and columns of points.
     * @param row
     * @param col
     * @return
     */
    public PointDescriptor getPointDescriptor(int row, int col) {
        return points[row][col];
    }

    /**
     * Member function getRows returns rowCount.
     * @return
     */
    public int getRows() {
        return rowCount;
    }

    /**
     * member function getColumns returns columnCount.
     * @return
     */
    public int getColumns() {
        return columnCount;
    }
    /**
     * The following function "findCalibrationPoints()" will locate the four uniformally square points at which the rows and columns will be aligned with.
     * These left hand points will be found first then the right hand points.
     * @throws java.io.IOException
     */
    private void findCalibrationPoints() throws IOException {
        Point[] tmpPoints = new Point[4];
        int pointCount = 0;

        int IMG_WIDTH = inputImage.getWidth();  //obtains the value that will be used for max image width
        int IMG_HEIGHT = inputImage.getHeight(); //obtains the value that will be used for max image height

        ArrayList<Rectangle> foundRects = new ArrayList<Rectangle>();

        for(int y = 0; y < IMG_HEIGHT; y++) {
            for(int x = 0; x < IMG_WIDTH; x++) {
                /**
                 * test for isBlack. use member function getRGB to obtain color value at x and y
                 */

                //TODO: skip x value to edge of found rect to save extra pixel checking
                if(isBlack(inputImage.getRGB(x, y)) && pointNotFound(x, y, foundRects)) {
                    Rectangle ret;
                    ret = findFromStart(x, y);
                    if(ret != null) {
                        foundRects.add(ret);
                    }
                }
            }
        }

        for(int i=0; i<foundRects.size(); i++) {
            Rectangle r = foundRects.get(i);
            if(Math.abs(r.height - r.width) < (r.height/10)) {
                System.out.println(r.x + ", " + r.y + ", " + r.height);
                int mpW = r.x + r.width / 2;
                int mpH = r.y + r.height / 2;
                tmpPoints[pointCount++] = new Point(mpW, mpH);
                avgCalibrationPointSize = avgCalibrationPointSize + r.width;
            } else {
                //System.out.println(r.height + "   " + r.x + ", " + r.y);
            }
        }

        if(pointCount != 4) {
            throw new IOException("Could not find all 4 calibration points.");
        }

        avgCalibrationPointSize = avgCalibrationPointSize / 4;
        /**
         * Call function assignCalibrationPoints to assign the calibration points by passing the temporary points tmpPoints variable
         */
        assignCalibrationPoints(tmpPoints);
    }

    private boolean pointNotFound(int x, int y, ArrayList<Rectangle> foundRects) {
        for(int i=0; i<foundRects.size(); i++) {
            Rectangle r = foundRects.get(i);
            if(x >= r.x && x <= (r.x+r.width) && y >= r.y && y <= (r.y + r.height)) {
                return false;
            }
        }
        return true;
    }

    private Rectangle findFromStart(int x, int y) {

        int IMG_WIDTH = inputImage.getWidth();  //obtains the value that will be used for max image width
        int IMG_HEIGHT = inputImage.getHeight(); //obtains the value that will be used for max image height

        int MaxWhitePixels = 10; //maximum amount of white pixels before row or column search is decremented


        int colStart = x;
        int lineStart = y;
        int lineCount = 0;
        int avgWidth = 0;
        int runningWidth = 0;

        try {
            /**
             * Outer while loop increments through rows to find a black value, stops if bottom of page reached or end of current block.
             * Call to isBlacXBuffer. Store offset amount of pixels to next black of the next line in linespacing variable, if line spacing is negative 1 end of block reached,
             * this will be used later to determine skew of image.
             * Inner while loop increments through columns to find the amount of columns that correspond to width of calibration block.
             * linespacing here will be used to offset for skew.
             *
             * TODO: check up to MaxWhitePixels to the left and to the right of the
             * next line down, not just straight down
             */

            int lineSpacing = 0;
            /**
             * Loop while our current line position is less than image height minus the max allowable white pixels,
             * and set linespacing as equal to isBlackXBuffer as long as it is not equal to -1 meaning the end of the calibration point
             */
            while(y < IMG_HEIGHT - MaxWhitePixels && ((lineSpacing = isBlackXBuffer(x, y, MaxWhitePixels, 1)) != -1)) {
                y++;

                /**
                 * Loop while our current column position is less than image width minus the max allowable white pixels,
                 * and set linespacing as equal to isBlackXBuffer as long as it is not equal to -1 meaning the end of the calibration point
                 */
                while(x < IMG_WIDTH - MaxWhitePixels && isBlackXBuffer(x + lineSpacing, y, MaxWhitePixels, 1) != -1) {
                    x++;
                }

                /**
                 * runningWidth will be width variable for average width calculation
                 * x will be reset to column where calibration point found so as not to start from edge of page
                 */
                runningWidth = runningWidth + (x - colStart);
                lineCount++;
                x = colStart;
            }

            int height = y - lineStart;
            avgWidth = runningWidth / lineCount;
            /**
             * As long as we have an approximate square set the temporary points for this calibration point and save the
             * average size of this calibration point. Store this point in the tmpPoints array as the next temporary calibration point.
             * Reset avgWidth.
             */
            if(height > 30) {
                return new Rectangle(colStart, lineStart, avgWidth, height);
            } else {
                return null;
            }
        } catch(Exception e) {
            //System.out.println("Left: " + x + " " + y);
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Function isBlack will test pixel value against an acceptable range of values.
     * isBlack will return true if pixel value is within the range of black values.
     */ 
    private boolean isBlack(int RGB) {
        int red, green, blue;
        blue = (( RGB ) & 0xFF);
        green = (( RGB >> 8 ) & 0xFF);
        red = (( RGB >> 16 ) & 0xFF);

        int maxDifference = (int)(255*0.10); //Within 10% of color spectrum
        int maxBlackColor = 0x40;

        return (Math.abs(red - blue) < maxDifference && Math.abs(red - green) < maxDifference && red < maxBlackColor);
    }

    /**
     * Function isBlackXBuffer will adjust x to obtain the correct starting point to obtain the RGB value for this x,y coordinate.
     * isBlackXBuffer will then return true if that pixel is black, this will cease if we obtain MaxWhitePixels
     * @param x
     * @param y
     * @param MaxWhitePixels
     * @param direction
     * @return
     */
    private int isBlackXBuffer(int x, int y, int MaxWhitePixels, int direction) {
        boolean isBlack = false;
        int whitePixels = 0;

        do {
            isBlack = isBlack(inputImage.getRGB(x + whitePixels * direction, y));
        } while(!isBlack && whitePixels++ < MaxWhitePixels);

        if(isBlack) {
            return whitePixels;
        } else {
            return -1;
        }
    }

    /**
     * Function isBlackYBuffer not called. will isBlackYBuffer be used later?
     * @param x
     * @param y
     * @param MaxWhitePixels
     * @return
     */
    private boolean isBlackYBuffer(int x, int y, int MaxWhitePixels) {
        return isBlack(inputImage.getRGB(x, y));
    }

    /**
     * Function isGlack will test pixel value against an acceptable range of values.
     * isGlack will return true if pixel value is within the range of acceptable red, green, and blue values.
     * @param RGB
     * @return
     */
    private boolean isGrey(int RGB) {
        int red, green, blue;
        blue = (( RGB ) & 0xFF);
        green = (( RGB >> 8 ) & 0xFF);
        red = (( RGB >> 16 ) & 0xFF);

        int maxDifference = (int)(255*0.10); //Within 10% of color spectrum
        int maxGreyColor = 0x95;
        
        return (Math.abs(red - blue) < maxDifference && Math.abs(red - green) < maxDifference && red < maxGreyColor);
    }

    /**
     * Function assignCalibrationPoints will determine which corner of the the page each calibration point is located and reassign the tmpPoints
     * variable to more specifically the objects calibrationTL, calibrationTR, calibrationBL, and calibrationBR
     * 
     */
    private void assignCalibrationPoints(Point[] foundPoints) throws IOException {
        CalibrationPointInfo[] pts = {new CalibrationPointInfo(foundPoints[0]), new CalibrationPointInfo(foundPoints[1]),
            new CalibrationPointInfo(foundPoints[2]), new CalibrationPointInfo(foundPoints[3])};
        
        /**
         * Funtion findLeft will find which points are on the left side
         */
        findLeft(pts);
        
        /**
         * Funtion findTop will find which points are on the top
         */ 
        findTop(pts);

        //Assign each point to its corresponding calibration marker
        this.calibrationTL = findAtPos(pts, true, true);
        this.calibrationTR = findAtPos(pts, true, false);
        this.calibrationBL = findAtPos(pts, false, true);
        this.calibrationBR = findAtPos(pts, false, false);
    }
    
    /**
     * Function findLeft increments through the four calibration points and compares their x position. Points are set to true
     * if in the left corners and set to false if in the right corners. Two points will set to true and two points set to false.
     * Find points on the left side and set the isLeft attribute appropriately
     * A point is on the left if its X coord is less than at least 2 other points
     * 
     * @param pts
     */
   
    private void findLeft(CalibrationPointInfo[] pts) {
        for(CalibrationPointInfo p : pts) {
            int ltCount = 0;
            for(int i = 0; i < 4; i++) {
                if(!p.equals(pts[i])) {
                    if(p.point.x <= pts[i].point.x) {
                        ltCount++;
                    }
                }
            }

            if(ltCount >= 2) {
                p.isLeft = true;
            } else {
                p.isLeft = false;
            }
        }
    }

    /**
     * Function findTop increments through the four calibration points and compares their y position. Points are set to true
     * if in the Top corners and set to false if in the bottom corners. Two points will be set to true and two points to false.
     * Find points on the top and set isTop accordingly
     * A point is on the top if its Y coord is less than at least 2 other points
     *
     * @param pts
     */
    private void findTop(CalibrationPointInfo[] pts) {
        for(CalibrationPointInfo p : pts) {
            int ltCount = 0;
            for(int i = 0; i < 4; i++) {
                if(!p.equals(pts[i])) {
                    if(p.point.y <= pts[i].point.y) {
                        ltCount++;
                    }
                }
            }

            if(ltCount >= 2) {
                p.isTop = true;
            } else {
                p.isTop = false;
            }
        }
    }

    /**
     * Function findAtPos will increment throught the pts objects and based on the passed booleans, true or false,
     * they will be assigned to the appropriate calibration points calibrationTL, calibrationTR, calibrationBL, or calibration BR.
     *
     * @param pts
     * @param findingTop
     * @param findingLeft
     * @return
     * @throws java.io.IOException
     */
    private Point findAtPos(CalibrationPointInfo[] pts, boolean findingTop, boolean findingLeft) throws IOException {
        for(int i = 0; i < 4; i++) {
            if(pts[i].isLeft == findingLeft && pts[i].isTop == findingTop) {
                return pts[i].point;
            }
        }

        //Should never get here
        throw new IOException("Could not find point at given location");
    }

    /**
     * Function findRows will use the x and y coordinates of the top left calibration point and the bottom left calibration point.
     * findRows will move along the y axis locating black pixels until it reaches the bottom left calibration point.
     * findRows will add to our rows list with the add member function if we have reached the bottom left calibration point.
     *
     */
    private void findRows() {
        //loop from calibrationTL to calibration BL
        int y = calibrationTL.y;

        int calX = calibrationTL.x;

        //loop until getting past black on calibration point
        while(isBlack(inputImage.getRGB(calX, ++y))) {
        }

        int MaxWhitePixels = 0;

        for(; y < calibrationBL.y; y++) {
            if(isBlack(inputImage.getRGB(calX, y))) {
                int lineStart = y;
                int whitePixels = 0;

                while(isBlack(inputImage.getRGB(calX, ++y)) || (++whitePixels < MaxWhitePixels)) {
                }

                int height = y - lineStart;

                if(Math.abs(height - avgCalibrationPointSize / 2) < 10) {
                    rows.add(lineStart + height / 2);
                    rowCount++;
                }
            }
        }
    }

    /**
     * Function findColumss will use the x and y coordinates of the bottom left calibration point and the bottom right calibration point.
     * findColumns will move along the x axis locating black pixels until it reaches the bottom right calibration point.
     * findColumns will add to our columns list with the add member function until we have reached the bottom right calibration point.
     *
     */
    private void findColumns() {
        //loop from calibrationTL to calibration BL
        int calY = calibrationBL.y;

        int x = calibrationBL.x;

        //loop until getting past black on calibration poing
        while(isBlack(inputImage.getRGB(++x, calY))) {
        }

        int MaxWhitePixels = 0; // possibly adjust due to sample data

        for(; x < calibrationBR.x; x++) {
            if(isBlack(inputImage.getRGB(x, calY))) {
                int colStart = x;
                int whitePixels = 0;

                while(isBlack(inputImage.getRGB(++x, calY)) || (++whitePixels < MaxWhitePixels)) {
                }

                int width = x - colStart;

                if(Math.abs(width - avgCalibrationPointSize / 2) < 10) {
                    columns.add(colStart + width / 2);
                    columnCount++;
                }
            }
        }
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    private PointDescriptor processXY(int row, int col) {
        PointDescriptor ret = new PointDescriptor();

        int imgX = columns.get(col);
        int imgY = rows.get(row);

        int IMAGE_WIDTH = inputImage.getWidth();
        int IMAGE_HEIGHT = inputImage.getHeight();


        //check each pixel going through the 4 quadtrants
        //by switching between incrementing/decrementing x and y values

        //As checking pixels, count the number of isGrey pixels vs total pixels

        //Once all pixels have been checked, compute isGrey/totalPixels,
        //and if percentage is high enough return true

        int MaxWhitePixels = 0;
        int whitePixelsY = 0;
        int whitePixelsX = 0;

        int markedCount = 0;
        int checkedCount = 0;

        //start at center point
        int x = imgX;

        for(int y=imgY; y>=0 && whitePixelsY<=MaxWhitePixels; y--) {
            if(!isGrey(inputImage.getRGB(x, y))) {
                whitePixelsY++;
                checkedCount++;
            } else {
                whitePixelsX = 0;
                for(x=imgX; x>=0 && whitePixelsX<=MaxWhitePixels; x--) {
                    checkedCount++;
                    if(isGrey(inputImage.getRGB(x, y))) {
                        whitePixelsY = 0;
                        whitePixelsX = 0;

                        markedCount++;
                    } else {
                        whitePixelsX++;
                    }
                }
            }
            x = imgX;
        }

        whitePixelsY = 0;
        whitePixelsX = 0;

        for(int y=imgY; y>=0 && whitePixelsY<=MaxWhitePixels; y--) {
            if(!isGrey(inputImage.getRGB(x, y))) {
                whitePixelsY++;
                checkedCount++;
            } else {
                whitePixelsX = 0;
                for(x=imgX; x<=IMAGE_WIDTH && whitePixelsX<=MaxWhitePixels; x++) {
                    checkedCount++;
                    if(isGrey(inputImage.getRGB(x, y))) {
                        whitePixelsY = 0;
                        whitePixelsX = 0;

                        markedCount++;
                    } else {
                        whitePixelsX++;
                    }
                }
            }
            x = imgX;
        }

        whitePixelsY = 0;
        whitePixelsX = 0;


        for(int y=imgY; y<=IMAGE_HEIGHT && whitePixelsY<=MaxWhitePixels; y++) {
            if(!isGrey(inputImage.getRGB(x, y))) {
                whitePixelsY++;
                checkedCount++;
            } else {
                whitePixelsX = 0;
                for(x=imgX; x>=0 && whitePixelsX<=MaxWhitePixels; x--) {
                    checkedCount++;
                    if(isGrey(inputImage.getRGB(x, y))) {
                        whitePixelsY = 0;
                        whitePixelsX = 0;

                        markedCount++;
                    } else {
                        whitePixelsX++;
                    }
                }
            }
            x = imgX;
        }

        whitePixelsY = 0;
        whitePixelsX = 0;

        for(int y=imgY; y<=IMAGE_HEIGHT && whitePixelsY<=MaxWhitePixels; y++) {
            if(!isGrey(inputImage.getRGB(x, y))) {
                whitePixelsY++;
                checkedCount++;
            } else {
                whitePixelsX = 0;
                for(x=imgX; x<=IMAGE_WIDTH && whitePixelsX<=MaxWhitePixels; x++) {
                    checkedCount++;
                    if(isGrey(inputImage.getRGB(x, y))) {
                        whitePixelsY = 0;
                        whitePixelsX = 0;

                        markedCount++;
                    } else {
                        whitePixelsX++;
                    }
                }
            }
            x = imgX;
        }


        if(markedCount/((float) checkedCount) > .8) {
            ret.isBubbled = true;
        }

        return ret;
    }
    /**
     * 
     */
    private class CalibrationPointInfo {

        public Point point;
        public boolean isLeft;
        public boolean isTop;

        CalibrationPointInfo(Point p) {
            point = p;
        }
    }
}
