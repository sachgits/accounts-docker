/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author idali
 */
public class UserManagementExceptionTest {
    
    private UserManagementException instance1;
    private UserManagementException instance2;
    
    public UserManagementExceptionTest() {
    }
 
    @Before
    public void setUp() {
        int errorCode = 500;
        String errorType = "Bad request";
        String errorMessage = "Bad request from client";
        
        List<String> errormessages = new ArrayList();
        errormessages.add(errorMessage);
        instance1 = new UserManagementException(errorCode, errorType, errorMessage);
        instance2 = new UserManagementException(errorCode, errorType, errormessages);
    }
    
    @After
    public void tearDown() {
        instance1 = null;
        instance2 = null;
    }

    /**
     * Test of getErrorCode method, of class UserManagementException.
     */
    @Test
    public void testGetErrorCode() {
        System.out.println("getErrorCode"); 
        
        int expResult = 500;
        int result = instance1.getErrorCode();
        assertEquals(expResult, result); 
    }

    /**
     * Test of getErrorType method, of class UserManagementException.
     */
    @Test
    public void testGetErrorType() {
        System.out.println("getErrorType"); 
        
        String expResult = "Bad request";
        String result = instance1.getErrorType();
        assertEquals(expResult, result); 
    }

    /**
     * Test of getErrorMessages method, of class UserManagementException.
     */
    @Test
    public void testGetErrorMessages() {
        System.out.println("getErrorMessages"); 
        
        int expResult = 1;
        List<String> result = instance2.getErrorMessages();
        assertEquals(expResult, result.size()); 
    }

    /**
     * Test of getErrorMessageList method, of class UserManagementException.
     */
    @Test
    public void testGetErrorMessageList() {
        System.out.println("getErrorMessageList");  
        List<String> result = instance2.getErrorMessageList();
        assertNotNull(result); 
    }
    
}
