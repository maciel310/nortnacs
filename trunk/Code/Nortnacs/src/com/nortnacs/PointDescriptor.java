/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 *
 * @author Anthony
 */
public class PointDescriptor {
    public boolean isBubbled = false;
    public Raster imageData;
    public String barcodeData;

    public BufferedImage getImageAsBufferedImage() {
        BufferedImage img = new BufferedImage(imageData.getWidth(), imageData.getHeight(), BufferedImage.TYPE_INT_RGB);
        img.setData(imageData);
        return img;
    }
}
