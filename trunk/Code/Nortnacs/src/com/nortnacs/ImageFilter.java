package com.nortnacs;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String fileName = f.getName();
        if (fileName != null) {
            if (fileName.endsWith(".tiff") ||
                fileName.endsWith(".tif") ||
                fileName.endsWith(".gif") ||
                fileName.endsWith(".jpeg") ||
                fileName.endsWith(".jpg") ||
                fileName.endsWith(".bmp") ||
                fileName.endsWith(".png")) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Images Only";
    }
}