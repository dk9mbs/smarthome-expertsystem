/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.status.file;

import de.dk9mbs.prohomelog.ruleengine.tools.RuleEngineFactory;
import de.dk9mbs.prohomelog.status.*;
import de.dk9mbs.prohomelog.sys.data.Global;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mbuehler
 */
public class FilePersistStatus implements IPersistStatus {
    private static final Logger logger = Logger.getLogger(FilePersistStatus.class.getName());

    @Override
    public boolean setStatus(String name, String value) {
        String root=Global.getClientInfo().getParams().getProperty("File_Persist_Path", "/tmp/");
        String statusRoot=root;
        Writer fw;  
        
        try
        {
            fw = new FileWriter( statusRoot+name+".status" );
            fw.write( value );
            fw.close();
        } catch ( IOException e ) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }   
        
        return true;
    }
    
    @Override 
    public String getStatus(String name, String defaultValue) {
    
        return "";
    }
    
}
