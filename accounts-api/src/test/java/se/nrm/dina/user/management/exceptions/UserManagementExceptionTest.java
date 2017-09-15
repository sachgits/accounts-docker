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
    private int errorCode;
    private String errorType;
    private String errorMessage;
    
    public UserManagementExceptionTest() {
    }
 
    @Before
    public void setUp() {
        errorCode = 500;
        errorType = "Bad request";
        errorMessage = "Bad request from client";
        
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
         
        int result1 = instance1.getErrorCode();
        int result2 = instance2.getErrorCode();
        assertEquals(errorCode, result1); 
        assertEquals(errorCode, result2);
    }

    /**
     * Test of getErrorType method, of class UserManagementException.
     */
    @Test
    public void testGetErrorType() {
        System.out.println("getErrorType"); 
         
        String result1 = instance1.getErrorType();
        String result2 = instance2.getErrorType();
        assertEquals(errorType, result1); 
        assertEquals(errorType, result2); 
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
        assertEquals(errorMessage, result.get(0));  
    }
    
    
    /**
     * Test of getErrorMessageList method, of class UserManagementException.
     */
    @Test
    public void testGetErrorMessageList() {
        System.out.println("getErrorMessageList");    
        
        List<String> result = instance2.getErrorMessageList();
        assertNotNull(result); 
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), errorMessage);
    }
    
    
    /**
     * Test of getErrorMessageList method, of class UserManagementException.
     */
    @Test
    public void testGetErrorMessageListWithEmptyList() {
        System.out.println("getErrorMessageList");  
        
        List<String> list = new ArrayList();
        instance2 = new UserManagementException(errorCode, errorType, list);
        List<String> result = instance2.getErrorMessageList();
        assertNotNull(result); 
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), null);
    }

    /**
     * Test of getErrorMessageList method, of class UserManagementException.
     */
    @Test
    public void testGetErrorMessageListWithNull() {
        System.out.println("getErrorMessageList");  
        List<String> nullList = null;
        instance2 = new UserManagementException(errorCode, errorType, nullList);
        List<String> result = instance2.getErrorMessageList();
        assertNotNull(result); 
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), null);
    }
    
}
