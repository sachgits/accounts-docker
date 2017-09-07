/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.utils;

import java.util.Date;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author idali
 */
public class UtilTest {
    
    public UtilTest() {
    }
 
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Util.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
 
        Util result = Util.getInstance();
        assertNotNull(result); 
    }

    /**
     * Test of dateToString method, of class Util.
     */
    @Test
    public void testDateToString() {
        System.out.println("dateToString");
 
        Date date = new Date();  
        String result = Util.getInstance().dateToString(date);
   
        assertNotNull(result);  
    }
    
    @Test
    public void testDateToStringWithNull() {
        System.out.println("testDateToStringWithNull");
 
        Date date = null;  
        String result = Util.getInstance().dateToString(date);
   
        assertNull(result);  
    }

    /**
     * Test of dateLongToString method, of class Util.
     */
    @Test
    public void testDateLongToString() {
        System.out.println("dateLongToString");
        
        long dateLong = 0L; 
        String expResult = "Jan 01, 1970 at";
        String result = Util.getInstance().dateLongToString(dateLong);
        assertTrue(result.startsWith(expResult)); 
    } 
}
