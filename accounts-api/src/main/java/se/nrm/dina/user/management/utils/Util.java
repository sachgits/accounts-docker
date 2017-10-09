/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.utils;
 
import java.text.SimpleDateFormat;
import java.time.Instant; 
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter; 
import java.util.Date;  

/**
 *
 * @author idali
 */
public class Util { 
    
    private static Util instance = null;

    private final DateTimeFormatter FORMATTER_WITH_TIMESTAMP = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' HH.mm.ss");
    private final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy"); 

    public Util() { 
    }

    public static synchronized Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }

        return instance;
    }

    public String dateToString(Date date) {
        if (date != null) { 
            return SIMPLE_DATE_FORMAT.format(date);
        } else {
            return null;
        }
    }
      
    public String dateLongToString(long dateLong) { 
        return dateLongToLocalDateTime(dateLong).format(FORMATTER_WITH_TIMESTAMP);
    }   

    public LocalDateTime dateLongToLocalDateTime(long dateLong) {
        return Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
    
    public long getNowLong() {
        return getNow().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    
    public String getNowString() {
        return getNow().format(FORMATTER_WITH_TIMESTAMP);
    }
}
