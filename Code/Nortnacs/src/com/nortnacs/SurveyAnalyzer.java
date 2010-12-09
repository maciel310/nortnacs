package com.nortnacs;

import java.io.IOException;

/**
 *
 * @author Angela
 */
public class SurveyAnalyzer {

    private int[][] answers;
    Form forms[];
    int totalRows;
    int totalColumns;

    SurveyAnalyzer(Form forms[]) throws IOException {
        //initialize class variables
        this.forms = forms;
        //these could be passed in too
        totalRows = forms[0].getRows();
        totalColumns = forms[0].getColumns();
        answers = new int[totalRows][totalColumns];

        //run query which initializes answers array upon object creation
        query();
    }

    private void query() {
        //initialize some variables
        int initialCount = 0;

        //run analyses
        for (int form_num = 0; form_num < forms.length; form_num++) {
            for (int rowNumber = 0; rowNumber < totalRows; rowNumber++) {
                for (int columnNumber = 0; columnNumber < totalColumns; columnNumber++) {
                    int count = 0;
                    initialCount = getAnswer(rowNumber, columnNumber);
                    if (forms[form_num].isMarked(rowNumber, columnNumber)) {
                        count++;
                    }
                    answers[rowNumber][columnNumber] = initialCount + count;
                }
            }
        }
    }

    public int getAnswer(int row, int column) {
        return answers[row][column];
    }
}
