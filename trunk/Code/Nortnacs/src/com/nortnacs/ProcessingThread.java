/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;


/**
 *
 * @author Scott
 */
public class ProcessingThread implements Runnable{
    Form[] forms;
    int progress;
    ProgressPane pb;
    ProcessingThread(Form[] list, ProgressPane pb){
        this.forms = list;
        this.progress = 0;
        this.pb = pb;
        this.pb.setVisible(true);
        this.pb.updateValue(this.progress) ;
    }

    public void run() {
        for(Form form : forms){
            System.out.println("Going Through Form List, List length is: " + forms.length);
            try {
                form.process();
                
                progress++;
                System.out.println("progress: " + progress);
                pb.updateValue(forms.length/progress);
            } catch (IOException ex) {
                System.out.println("Error Processing Image " + form);
            }
        }

        //Finished
        pb.setVisible(false);
    }

    public Form[] getForms() {
        return forms;
    }

}
