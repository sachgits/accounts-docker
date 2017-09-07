/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.utils;

import java.util.Arrays;
import java.util.List;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author idali
 */
public class AccountStatusTest {
     
    
    public AccountStatusTest() {
    }
 
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of values method, of class AccountStatus.
     */ 
    @Test
    public void testValues() {
        System.out.println("values");
         
        List<AccountStatus> enumValues = Arrays.asList(AccountStatus.values());
         
        assertEquals(enumValues.size(), 7); 
    }

    /**
     * Test of valueOf method, of class AccountStatus.
     */ 
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        
        String name = "Pending";
        AccountStatus expResult = AccountStatus.Pending;
        AccountStatus result = AccountStatus.valueOf(name);
        assertEquals(expResult, result); 
    }

    /**
     * Test of getText method, of class AccountStatus.
     */ 
    @Test
    public void testGetPendingText() {
        System.out.println("testGetPendingText");
        
        String expResult = "pending";
        String result = AccountStatus.Pending.getText();
        assertEquals(expResult, result); 
    }
    
        /**
     * Test of getText method, of class AccountStatus.
     */ 
    @Test
    public void testGetNewText() {
        System.out.println("testGetNewText");
        
        String expResult = "new";
        String result = AccountStatus.New.getText();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText method, of class AccountStatus.
     */
    @Test
    public void testGetRejectText() {
        System.out.println("testGetRejectText");

        String expResult = "reject";
        String result = AccountStatus.Reject.getText();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText method, of class AccountStatus.
     */
    @Test
    public void testGetEnabledText() {
        System.out.println("testGetEnabledText");

        String expResult = "enabled";
        String result = AccountStatus.Enabled.getText();
        assertEquals(expResult, result);
    }
}
