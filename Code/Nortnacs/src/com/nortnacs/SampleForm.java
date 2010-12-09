/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Anthony
 */
public class SampleForm implements Iterator {
    private Point[] points = {new Point(1,1), new Point(2,4), new Point(3,2), new Point(4,4), new Point(4,2)};
    private int curPoint = 0;
    private Point calibrationTL;
    private Point calibrationTR;
    private Point calibrationBL;
    private Point calibrationBR;
    private int columnCount;
    private int rowCount;
    private ArrayList<Integer> rows;
    private ArrayList<Integer> columns;

    SampleForm(String imgPath) {

    }

    SampleForm(BufferedImage img) {

    }

    public void process() throws IOException {
        process(5);
    }

    public void process(int sensitivity) throws IOException {
        findCalibrationPoints();

        findRows();

        findColumns();

        for(int row : rows) {
            for(int col : columns) {
                processXY(row, col);
            }
        }
    }

    public boolean isMarked(int x, int y) {
        boolean ret = false;
        for(int i=0; i<points.length; i++) {
            Point cur = points[i];
            ret = ret || (cur.x==x && cur.y==y);
        }
        return ret;
    }

    public boolean isMarked(Point p) {
        return isMarked(p.x, p.y);
    }

    public boolean hasNext() {
        return (curPoint<points.length);
    }

    public Object next() {
        return points[curPoint++];
    }

    public Object previous() {
        return points[curPoint--];
    }

    public void remove() {
        throw new UnsupportedOperationException("Don't do that!");
    }

    public int getRows() {
        return rowCount;
    }

    public int getColumns() {
        return columnCount;
    }

    private void findCalibrationPoints() {
        this.calibrationTL = new Point();
        this.calibrationTR = new Point();
        this.calibrationBL = new Point();
        this.calibrationBR = new Point();
    }

    private void findRows() {

    }

    private void findColumns() {

    }

    private void processXY(int x, int y) {

    }
}
