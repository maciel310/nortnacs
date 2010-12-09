package com.nortnacs;

import java.text.*;
import java.io.IOException;
/**
 *
 * @author Angela
 */
public class Results{
    private double [][] totalPercent;
    private int totalRows;
    private int totalColumns;
    private int totalForms;

    Results(Form forms[]) throws IOException{
        SurveyAnalyzer s1 = new SurveyAnalyzer(forms);

        //Makes assumption all forms are identical in setup, which is most likely
        totalRows = forms[0].getRows();
        totalColumns = forms[0].getColumns();
        totalForms = forms.length;
        System.out.println("Test Form Results: rows" + totalRows + " columns: " + totalColumns + " forms: " + totalForms);
        totalPercent = new double[totalRows][totalColumns];

        percentage(s1);
    }

    /*Calculate statistics of all questions*/
    private void percentage(SurveyAnalyzer s1){
        //iterate over every (column,row) tuple and calculate percentage across all forms
        for(int row=0; row<totalRows; row++){
            for(int column=0; column<totalColumns; column++){
                
                //get percent as decimal
                double totalMarked = (double)s1.getAnswer(row, column);
                double percent = totalMarked/totalForms;

                //convert to whole number
                totalPercent[row][column] = percent*100;

                //print out percentage to console
                DecimalFormat df = new DecimalFormat("##");
                System.out.print(df.format(percent) + "%");
            }
        }
    }
    
    /**Returns single percentage for 1 question*/
    public double getPercentage(int row, int column){
        return totalPercent[row][column];
    }
    
    public int getRows(){
        return totalRows;
    }
    
    public int getColumns(){
        return totalColumns;
    }

    public int getNumberOfForms(){
        return totalForms;
    }
    
    /**Print out percentage for 1 question*/
    /*
    public void singlePercent(int row, int column, SurveyAnalyzer s1 ){
       double part = s1.getAnswer(row, column);
       double percent = (double)part/(double)forms.length;
       totalPercent[row][column] = percent*100;
       
       DecimalFormat df = new DecimalFormat("##");
       System.out.print(df.format(percent) + "%");
    }
    */
}