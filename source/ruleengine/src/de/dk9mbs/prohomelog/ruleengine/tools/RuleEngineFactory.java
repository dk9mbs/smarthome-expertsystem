/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine.tools;
import de.dk9mbs.prohomelog.ruleengine.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.dk9mbs.prohomelog.status.IPersistStatus;
import de.dk9mbs.prohomelog.status.tools.PersistFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 *
 * @author mbuehler
 */
public class RuleEngineFactory {
    private static final Logger logger = Logger.getLogger(RuleEngineFactory.class.getName());
    private static AbstractEngine _engine=null;
    
    public static AbstractEngine createEngine() {
        if(_engine != null) return _engine;
        
        Object c;
        String classname=de.dk9mbs.prohomelog.sys.data.Global.getClientInfo().getParams().getProperty("RuleEngine");
        //String jarFile="file://"+System.getProperty("user.dir")+"/lib/phljiprolog.jar";
        String jarFile="file://"+System.getProperty("user.dir")+"/lib/phlswiprolog.jar";
        //String jarFile1="file://"+System.getProperty("user.dir")+"/lib/jiprolog-4.1.5.1.jar";
        String jarFile1="file://"+System.getProperty("user.dir")+"/lib/jpl.jar";


        logger.log(Level.INFO ,"Rulengine loading:"+classname+" (jar:"+jarFile+")");
        
        try {
            URL classUrl;
            URL classUrl1;
            classUrl = new URL(jarFile);            
            classUrl1 = new URL(jarFile1);            
            URL[] classUrls = { classUrl, classUrl1 };
            URLClassLoader ucl = new URLClassLoader(classUrls);
            //c=Class.forName(classname).newInstance();
            c = ucl.loadClass(classname).newInstance();
            //AbstractEngine engine=(AbstractEngine)c;
            _engine=(AbstractEngine)c;
            IPersistStatus status=PersistFactory.createPersist();
            _engine.setPersistStatus(status);
            
            return _engine;
        } catch(java.lang.ClassNotFoundException e){
            logger.log(Level.SEVERE, e.getMessage(),e );
        } catch (MalformedURLException e3 ) {
            logger.log(Level.SEVERE, e3.getMessage(),e3);
        } catch( InstantiationException e4) {
            logger.log(Level.SEVERE,e4.getMessage(),e4);
        } catch (IllegalAccessException e5) {
            logger.log(Level.SEVERE,e5.getMessage(),e5);
        }

        /*
        } catch(java.lang.InstantiationException e1) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e1));
        } catch(java.lang.IllegalAccessException e2){
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e2));
        */
        
        return null;
    }
}
