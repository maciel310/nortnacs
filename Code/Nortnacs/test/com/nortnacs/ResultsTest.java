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
public class ResultsTest {

    public ResultsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
     
    /**
     * Test of percentange method, of class Results.
     */
    @Test
    public void testPercentange() {
        System.out.println("percentange");
        Results instance = new Results();
        int row = 0;
        int column = 0;
        instance.percentange();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPercentage method, of class Results.
     */
    @Test
    public void testGetPercentage() {
        System.out.println("getPercentage");
        int row = 1;
        int column = 1;
        Results instance = new Results();
        double expResult = 0.10;
        double result = instance.getPercentage(row, column);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of singlePercent method, of class Results.
     */
    @Test
    public void testSinglePercent() {
        System.out.println("singlePercent");
        int row = 1;
        int column = 1;
        Results instance = new Results();
        instance.singlePercent(row, column);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}