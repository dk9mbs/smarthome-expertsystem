/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.status.tools;
import de.dk9mbs.prohomelog.status.IPersistStatus ;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public class PersistFactory {
    private static final Logger logger = Logger.getLogger(PersistFactory.class.getName());
    
    public static IPersistStatus createPersist() {
        Object c;
        IPersistStatus status;
        
        String classname=de.dk9mbs.prohomelog.sys.data.Global.getClientInfo().getParams().getProperty("Persist_Class");
        logger.log(Level.FINEST ,"PersistStatus:"+classname);
        
        try {
            c=Class.forName(classname).newInstance();
            status=(IPersistStatus)c;
            return status;
        } catch(java.lang.ClassNotFoundException e){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        } catch(java.lang.InstantiationException e1) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e1));
        } catch(java.lang.IllegalAccessException e2){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e2));
        }
        
        return null;
    }
    
}
