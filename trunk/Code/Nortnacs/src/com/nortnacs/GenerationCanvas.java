/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.*;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author Scott
 */
public class GenerationCanvas extends Canvas implements Printable{
    int calibrationCornerSize  = 32;
    int formGenGeneratedHeight = 0;
    int formGenGeneratedWidth  = 0;
    PrinterJob printJob;
    JTextField rows, cols;
    JToggleButton isUsingAlpha;
    int rowCount = 0;
    int[] arrYCoords;
    boolean drawDummies = false;



    GenerationCanvas(JTextField rows, JTextField cols, JToggleButton useAlpha){
        double sizeMultiplier = 0.78;
        this.setSize((int)(800*sizeMultiplier),(int)(1100*sizeMultiplier));
        this.rows = rows;
        this.cols = cols;
        this.isUsingAlpha = useAlpha;
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        createYcoordsArray();
        updateRows(g2d,rows);
        updateColumns(g2d,cols);
        drawFinalCalibrationMark(g2d);
        if(drawDummies){
            drawDummyBubbles(g2d);
        }else{
            drawBubbles(g2d);
        }
    }

    public void createPNG(String filename){
        BufferedImage bImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
        Graphics bufferGraphics =  bImage.createGraphics();
        this.paint(bufferGraphics);
        try {
            ImageIO.write((RenderedImage)bImage, "PNG", new File(filename));
        } catch (IOException ex) {
            System.out.println("Failed to output form");
        }
    }

    public void createDummyForms(){
        //draw bubbles but with a random on filled in
    }

    private void createYcoordsArray() {
        try {
            rowCount = Integer.parseInt(this.rows.getText());
        } catch (NumberFormatException e) {
            rowCount = 0;
        }
        arrYCoords = new int[rowCount];
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        //assume the page exists until proven otherwise
        int retval = Printable.PAGE_EXISTS;

        //We only want to deal with the first page.
        //The first page is numbered '0'
        if (pageIndex > 0) {
            retval = Printable.NO_SUCH_PAGE;
        } else {
            //setting up the Graphics object for printing
            g.translate((int) (pf.getImageableX()), (int) (pf.getImageableY()));
            //populate the Graphics object from paint() method
            paint(g);
        }

        return retval;
    }

    private void updateRows(Graphics g, JTextField txtRows) {
        //read in values and set up variables
        int numRows = 0;
        int fullHeight = calibrationCornerSize / 2;
        int fullWidth = calibrationCornerSize;
        int gap = (int) (.7 * fullHeight);
        int calCornerPlusGap = calibrationCornerSize + gap;
        int pageLeftAlign = 0;

        //check if value is number
        try {
            numRows = Integer.parseInt(txtRows.getText());
        } catch (NumberFormatException e) {
            numRows = 0;
        }

        //use number to generate N columns marks
        //draw row markers and calibration points
        g.setColor(Color.black);
        for(int i = 0; i < numRows; i++) {
            //set up coords for current mark being drawn
            int x = pageLeftAlign;
            int y = (int) (calCornerPlusGap + ((fullHeight + gap) * i));
            //draw row marker
            g.fillRect(x, y, (fullWidth), (fullHeight));
            //hackish way of storing coords for drawing of bubbles...replace
            arrYCoords[i] = y;
            //if on last iteration draw calibration points
            if (i == (numRows - 1)) {
                //Draw top calibration point
                g.fillRect(0, 0, calibrationCornerSize, calibrationCornerSize);
                //Draw bottom calibration point
                g.fillRect( pageLeftAlign,(int) ((calCornerPlusGap + ((fullHeight + gap) * i)) + gap + fullHeight), calibrationCornerSize, calibrationCornerSize);
                //store generated height
                formGenGeneratedHeight = (int) ((calCornerPlusGap + ((fullHeight + gap) * i)) + gap + fullHeight);
            }
        }
    }

    private void updateColumns(Graphics g, JTextField txtCols) {
        //read in values and set up variables
        int numCols = 0;
        int fullWidth = calibrationCornerSize / 2;
        int fullHeight = calibrationCornerSize;
        int gap = (int) (.7 * fullWidth);
        int pageHeight = formGenGeneratedHeight;
        int initX = calibrationCornerSize + gap ;

        //check if value  is number
        try {
            numCols = Integer.parseInt(txtCols.getText());
        } catch (NumberFormatException e) {
            numCols = 0;
        }

        //setup
        g.setColor(Color.black);

        //draw column markers and calibration points, with extra space in
        //beginning for numbering of rows
        for (int i = 0; i <= numCols; i++) {
            //set up coords for current mark being drawn
            //initial x = calibrationCornerSize + gap + fullWidth + gap;
            int x = (int) (initX + ((fullWidth + gap) * i));
            int y = pageHeight;

            //The first marker area should be blank
            if (i == 0) {
                continue;
            }

            //draw column marker
            g.fillRect(x, y, (fullWidth), (fullHeight));
            //if on last iteration draw calibration points
            if (i == (numCols)) {
                //draw bottom left calibration mark
                g.fillRect(0, pageHeight, calibrationCornerSize, calibrationCornerSize);
                //draw bottom right calibation point
                g.fillRect((int) (((calibrationCornerSize + gap) + ((fullWidth + gap) * i)) + gap + fullWidth), pageHeight, calibrationCornerSize, calibrationCornerSize);
                //store generated width
                formGenGeneratedWidth = (int) (((calibrationCornerSize + gap) + ((fullWidth + gap) * i)) + gap + fullWidth);
            }
        }
    }

    private void drawBubbles(Graphics g){
        int row = 0;
        int col = 0;
        try {
            row = Integer.parseInt(rows.getText());
            col = Integer.parseInt(cols.getText());
        } catch (NumberFormatException e) {
            row = 0;
            col = 0;
        }

        for(int i = 0; i < row ; i++){
            drawBubbleSet(g, 0, arrYCoords[i], i, col);
        }
    }

    private void drawBubbleSet(Graphics g, int x, int y, int index, int numCols){
        char columnNamesStart = 'A';
        //set up start points
        int startX = x+calibrationCornerSize+10;
         int gap = (int) (.7 * (calibrationCornerSize/2));
        //set the color 
        g.setColor(Color.green);
        //draw row label
        index+=1;
        int txtYCorrection = ((3*calibrationCornerSize)/8)+1;
        int txtXCorrection = (calibrationCornerSize/4)-3;

        g.setFont(new Font(Font.SANS_SERIF , Font.BOLD, 14));
        g.drawString(index+" ", startX, y+txtYCorrection);
        g.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 12));

        //draw bubbles
        for(int i = 0 ; i < numCols; i++){
            int xVal = startX+((i+1)*(x+gap+calibrationCornerSize/2));
            g.drawOval(xVal, y, calibrationCornerSize/2, calibrationCornerSize/2);
            
            String colName = isUsingAlpha.isSelected()
                            ? Character.toString(columnNamesStart)
                            : Integer.toString((columnNamesStart-'A'));

            g.drawString(colName, colName.length()<2?xVal+txtXCorrection:xVal+txtXCorrection-4, y+txtYCorrection);

            columnNamesStart += 1;
        }

        columnNamesStart = 'A';
       
        //reset color
        g.setColor(Color.black);
    }

    private void drawFinalCalibrationMark(Graphics g){
        g.setColor(Color.black);
        g.fillRect(formGenGeneratedWidth, 0, calibrationCornerSize, calibrationCornerSize);
    }

    private void drawDummyBubbles(Graphics2D g2d) {
        int row = 0;
        int col = 0;
        try {
            row = Integer.parseInt(rows.getText());
            col = Integer.parseInt(cols.getText());
        } catch (NumberFormatException e) {
            row = 0;
            col = 0;
        }

        for(int i = 0; i < row ; i++){
            drawDummyBubbleSet(g2d, 0, arrYCoords[i], i, col);
        }
    }
    //yes this dummy bubble stuff shouldnt be repeated but I have 10 minutes till meeting and just want something
    private void drawDummyBubbleSet(Graphics g, int x, int y, int index, int numCols){
        char columnNamesStart = 'A';
        //set up start points
        int startX = x+calibrationCornerSize+10;
         int gap = (int) (.7 * (calibrationCornerSize/2));
        //set the color 
        g.setColor(Color.green);
        //draw row label
        index+=1;
        int txtYCorrection = ((3*calibrationCornerSize)/8)+1;
        int txtXCorrection = (calibrationCornerSize/4)-3;

        g.setFont(new Font(Font.SANS_SERIF , Font.BOLD, 14));
        g.drawString(index+" ", startX, y+txtYCorrection);
        g.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 12));
        
        //calculate random bubble draw
        Random dice = new Random();
        int toBubble = dice.nextInt(numCols);
        //draw bubbles
        for(int i = 0 ; i < numCols; i++){
            if(i == toBubble){

                int xVal = startX + ((i + 1) * (x + gap + calibrationCornerSize / 2));
                
                String colName = isUsingAlpha.isSelected()
                        ? Character.toString(columnNamesStart)
                        : Integer.toString((columnNamesStart - 'A'));

                
                g.drawString(colName, colName.length() < 2 ? xVal + txtXCorrection : xVal + txtXCorrection - 4, y + txtYCorrection);
                
                
                g.drawOval(xVal, y, calibrationCornerSize / 2, calibrationCornerSize / 2);
                g.setColor(Color.gray);
                g.fillOval(xVal+1, y+1, (calibrationCornerSize / 2) - 1, (calibrationCornerSize / 2)-1);
                g.setColor(Color.green);

                

                columnNamesStart += 1;
            }else{
                int xVal = startX + ((i + 1) * (x + gap + calibrationCornerSize / 2));
                g.drawOval(xVal, y, calibrationCornerSize / 2, calibrationCornerSize / 2);

                String colName = isUsingAlpha.isSelected()
                        ? Character.toString(columnNamesStart)
                        : Integer.toString((columnNamesStart - 'A'));

                g.drawString(colName, colName.length() < 2 ? xVal + txtXCorrection : xVal + txtXCorrection - 4, y + txtYCorrection);

                columnNamesStart += 1;
            }
        }

        columnNamesStart = 'A';
       
        //reset color
        g.setColor(Color.black);
    }
    
    public boolean getDummy(){
        return drawDummies;
    }
    public void setDummy(boolean dummy){
        drawDummies = dummy;
    }
}