/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.dina.user.management.json.impl;

import java.math.BigDecimal;
import java.util.Date;
import javax.json.JsonObjectBuilder;
import se.nrm.dina.user.management.utils.Util;

/**
 *
 * @author idali
 */
class JsonConvertHelper {
    
    void addAttributes(JsonObjectBuilder attBuilder, String key, Object value) {

        if (key != null && value != null) {
            if (value instanceof Integer) {
                attBuilder.add(key, (int) value);
            } else if (value instanceof Short) {
                attBuilder.add(key, (Short) value);
            } else if (value instanceof Date) {
                attBuilder.add(key, Util.getInstance().dateToString((Date) value));
            } else if (value instanceof java.util.Date) {
                attBuilder.add(key, Util.getInstance().dateToString((java.util.Date) value));
            } else if (value instanceof BigDecimal) {
                attBuilder.add(key, (BigDecimal) value);
            } else if (value instanceof Boolean) { 
                attBuilder.add(key, (Boolean) value);
            } else if (value instanceof Double) {
                attBuilder.add(key, (Double) value);
            } else if (value instanceof Float) {
                attBuilder.add(key, (Float) value);
            } else if (value instanceof Long) {
                attBuilder.add(key, (Long) value);
            } else if(value instanceof java.util.LinkedList) {
                attBuilder.add(key, value.toString());
            } else {
                attBuilder.add(key, (String) value);
            }
        } 
    } 
    
}
