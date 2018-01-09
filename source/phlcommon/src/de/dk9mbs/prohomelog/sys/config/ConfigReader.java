/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.sys.config;

import de.dk9mbs.prohomelog.sys.data.DBConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public class ConfigReader {
    private static final Logger logger=Logger.getLogger( ConfigReader.class.getName() );
    
    public static java.util.Properties readConfig() {
        return readConfig("");
    }

    public static java.util.Properties readConfig(String configFile) {
        
        if( configFile.equals("") ){
            configFile=System.getProperty("user.home")+"/.prohomelog";
        }
        
        java.io.File file=new java.io.File(configFile);
        if(! file.exists()) {
            logger.log(Level.SEVERE,"Config file not found! ("+configFile+")");
        }
        
        java.util.Properties prop = new java.util.Properties();
        prop.put("user.home", System.getProperty("user.home"));
        
        if (file.exists()) {
            try {
                prop.load(new java.io.BufferedInputStream(new java.io.FileInputStream(file)));
            } catch(java.io.IOException e) {
                System.out.printf(e.getMessage() );
                System.exit(1);
            }
        } else {
            prop.put("server", "192.168.45.179");
        }
        
        return prop;
    }
}
