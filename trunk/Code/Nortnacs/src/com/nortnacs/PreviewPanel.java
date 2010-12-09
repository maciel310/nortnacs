/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.plaf.BorderUIResource.BevelBorderUIResource;

/**
 *
 * @author Scott
 */
class PreviewPanel extends JPanel{
    JPanel parentPanel;
    BufferedImage mainImage;
    private double scale = .75;

    int sizeX;
    int sizeY;
    int midX;
    int midY;
    
    public PreviewPanel(JPanel mainPanel) {
        parentPanel = mainPanel;

        this.setSize(525, 475);
        this.setLocation(20,20 );
        this.setBorder(new BevelBorderUIResource(BevelBorderUIResource.LOWERED));
        this.setVisible(true);
    }

    public PreviewPanel(JPanel mainPanel, BufferedImage mImage) {
        parentPanel = mainPanel;
        this.mainImage = mImage;

        sizeX =(int) (mImage.getWidth() * scale);
        sizeY = (int) (mImage.getHeight() * scale);
        midX = (int)((mainPanel.getWidth() / 2.0) - sizeX);
        midY = (int)((mainPanel.getHeight() / 2.0) - sizeY);

        this.setSize(525,675);
        this.setLocation(20,20 );
        this.setBorder(new BevelBorderUIResource(BevelBorderUIResource.LOWERED));
        this.setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //get dimensions
        int imgHeight = mainImage.getHeight();
        int imgWidth = mainImage.getWidth();
        //clear panel
        int parentPanelWidth = parentPanel.getWidth();
        int parentPanelHeight = parentPanel.getHeight();
        //decide how to draw based on dimension
        int scaleX = (int)(sizeX*.90);
        int scaleY = (int)(sizeY*.90);
        Image scaledImg = mainImage.getScaledInstance(scaleX, scaleY, Image.SCALE_DEFAULT);
        g.drawImage(scaledImg,((this.getWidth()/2))-(scaleX/2),((this.getHeight()/2)-(scaleY/2)),null);
    }

    @Override
    public String toString(){
        return mainImage.toString();
    }

    void setImage(BufferedImage image) {
        mainImage = image;
        repaint();
    }


}
