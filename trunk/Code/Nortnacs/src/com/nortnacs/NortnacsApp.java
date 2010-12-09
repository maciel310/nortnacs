/*
 * NortnacsApp.java
 */

package com.nortnacs;

import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class NortnacsApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new NortnacsView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of NortnacsApp
     */
    public static NortnacsApp getApplication() {
        return Application.getInstance(NortnacsApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(NortnacsApp.class, args);

        /*
        try {
            Form f = new Form("C:\\Users\\Anthony\\Desktop\\ScantronProject\\Code\\TestImages\\ClassSurvey.png");

            long start = System.currentTimeMillis();
            f.process();
            System.out.println("Total processing time: " + (System.currentTimeMillis() - start));

            if(f.isMarked(0,0) && f.isMarked(1,3) && f.isMarked(2,2) &&
                    f.isMarked(3,3) && f.isMarked(3,1)) {
                System.out.println("Test passed!");
            } else {
                System.out.println("Test FAILED!");
            }

            for(int row=0; row<f.getRows(); row++) {
                for(int col=0; col<f.getColumns(); col++) {
                    if(f.isMarked(row, col))
                        System.out.println("Found point at " + (row+1) + ", " + (col+1));
                }
            }
        } catch(IOException e) {
            System.out.println("ERROR: An error occurred while processing the form.\n\n" + e.getMessage());
        }
         *
         */
    }
}
