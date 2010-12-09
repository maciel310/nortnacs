/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Scott
 */
class ProgressPane extends JPanel {
    Graphics g = this.getGraphics();
    double progress;
    
    
    ProgressPane(){
        this.setSize(300, 150);
    }

    @Override
    public void paint(Graphics g) {
         System.out.println(progress+"%");
         g.setFont(new Font(Font.SANS_SERIF , Font.BOLD, 24));
         g.drawString(Double.toString(progress),(int)(this.getHeight()/2),(int)(this.getWidth()/2));
    }

    
    void updateValue(double progress) {
        this.progress = progress;
        repaint();
    }

}
