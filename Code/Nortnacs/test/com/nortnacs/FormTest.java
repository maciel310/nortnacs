/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nortnacs;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Scott
 */
public class FormTest {

    public FormTest() {
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
     * Test of process method, of class Form.
     */
    @Test
    public void testProcess_0args() throws Exception {
        System.out.println("process");
        Form instance = null;
        instance.process();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of process method, of class Form.
     */
    @Test
    public void testProcess_int() throws Exception {
        System.out.println("process");
        int sensitivity = 0;
        Form instance = null;
        instance.process(sensitivity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarked method, of class Form.
     */
    @Test
    public void testIsMarked_int_int() {
        System.out.println("isMarked");
        int row = 0;
        int col = 0;
        Form instance = null;
        boolean expResult = false;
        boolean result = instance.isMarked(row, col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMarked method, of class Form.
     */
    @Test
    public void testIsMarked_Point() {
        System.out.println("isMarked");
        Point p = null;
        Form instance = null;
        boolean expResult = false;
        boolean result = instance.isMarked(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPointDescriptor method, of class Form.
     */
    @Test
    public void testGetPointDescriptor() {
        System.out.println("getPointDescriptor");
        int row = 0;
        int col = 0;
        Form instance = null;
        PointDescriptor expResult = null;
        PointDescriptor result = instance.getPointDescriptor(row, col);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRows method, of class Form.
     */
    @Test
    public void testGetRows() {
        System.out.println("getRows");
        Form instance = null;
        int expResult = 0;
        int result = instance.getRows();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColumns method, of class Form.
     */
    @Test
    public void testGetColumns() {
        System.out.println("getColumns");
        Form instance = null;
        int expResult = 0;
        int result = instance.getColumns();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}