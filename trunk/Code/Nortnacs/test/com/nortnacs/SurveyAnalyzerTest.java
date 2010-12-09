/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Angela
 */
public class SurveyAnalyzerTest {

    public SurveyAnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        int [][]answers={{1,4,3,2,5},
                    {5,6,3,1,2},
                    {11,2,3,2,1},
                    {10,1,4,2,0},
                    {1,5,3,7,2},
                    {2,1,6,3,1},
                    {0,2,1,4,6},
                    {8,0,5,0,2},
                    {9,2,1,4,1},
                    {1,8,3,5,2},
                    {6,2,3,1,5},
                    {9,1,0,2,0}};
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    

    /**
     * Test of query method, of class SurveyAnalyzer.
     */
    @Test
    public void testQuery() {
        System.out.println("query");
        SurveyAnalyzer instance = new SurveyAnalyzer();
        instance.query();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAnswer method, of class SurveyAnalyzer.
     */
    @Test
    public void testGetAnswer() {
        System.out.println("getAnswer");
        int row = 0;
        int column = 0;
        SurveyAnalyzer instance = new SurveyAnalyzer();
        int expResult = 0;
        int result = instance.getAnswer(row, column);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}