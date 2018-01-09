/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.tools;
import de.dk9mbs.prohomelog.eventbus.main.*;
import de.dk9mbs.prohomelog.sys.data.Global;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mbuehler
 */
public class BusConnectorFactory {
    private static final Logger logger = Logger.getLogger(BusConnectorFactory.class.getName());
    
    public static AbstractBusPublisher createBusPublisher() {
        Object c;
        AbstractBusPublisher pub;
        AbstractBusParams para;
        
        String classname=de.dk9mbs.prohomelog.sys.data.Global.getClientInfo().getParams().getProperty("BusPublisher_Class");
        //logger.log(Level.FINEST ,"BusPublisher:"+classname);
        
        try {
            c=Class.forName(classname).newInstance();
            para=createBusParam();
            pub=(AbstractBusPublisher)c;
            pub.setPara(para);
            return pub;
        } catch(java.lang.ClassNotFoundException e){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        } catch(java.lang.InstantiationException e1) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e1));
        } catch(java.lang.IllegalAccessException e2){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e2));
        }
        
        return null;
    }
    
    public static AbstractBusSubscriber createBusSubscriber() {
        Object c;
        String classname=de.dk9mbs.prohomelog.sys.data.Global.getClientInfo().getParams().getProperty("BusSubscriber_Class");
        logger.log(Level.FINEST ,"BusSubscriber:"+classname);

        try {
            c=Class.forName(classname).newInstance();
            return (AbstractBusSubscriber)c;
        } catch(java.lang.ClassNotFoundException e){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        } catch(java.lang.InstantiationException e1) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e1));
        } catch(java.lang.IllegalAccessException e2){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e2));
        }
        
        return null;
    }
    
    public static AbstractBusParams createBusParam() {
        Object c;
        String classname=de.dk9mbs.prohomelog.sys.data.Global.getClientInfo().getParams().getProperty("BusParams_Class");
        logger.log(Level.FINEST ,"BusSubscriber:"+classname);

        try {
            c=Class.forName(classname).newInstance();
            AbstractBusParams para=(AbstractBusParams)c;
            para.setServer(Global.getClientInfo().getParams().getProperty("Broker",""));
            para.setUid(Global.getClientInfo().getParams().getProperty("Broker_uid",""));
            para.setPwd(Global.getClientInfo().getParams().getProperty("Broker_pwd",""));
            para.setClientId("prohomelog_"+new java.util.Random().nextInt() );
            
            return para;
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
