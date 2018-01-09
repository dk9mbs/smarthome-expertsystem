/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author buehler
 */
public class Tools {
    private static final Logger logger=Logger.getLogger(Tools.class.getName() );

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis); 
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public static String getUniqueClientId() {
        String guid=java.util.UUID.randomUUID().toString();
        guid=guid.replace("-", "").substring(0,22) ;
        return guid;
    }
}
