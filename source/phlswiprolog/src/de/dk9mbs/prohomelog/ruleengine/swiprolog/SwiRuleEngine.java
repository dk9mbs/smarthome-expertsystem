/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine.swiprolog;

import de.dk9mbs.prohomelog.eventbus.main.*; 
import de.dk9mbs.prohomelog.eventbus.tools.BusConnectorFactory;
//import java.util.ArrayList;
//import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpl.*;
//import org.jpl7.*;
import de.dk9mbs.prohomelog.ruleengine.AbstractEngine;
import de.dk9mbs.prohomelog.sys.data.Global;
import de.dk9mbs.prohomelog.eventbus.main.TopicTools;
import de.dk9mbs.prohomelog.ruleengine.*;
/**
 *
 * @author mbuehler
 */
public class SwiRuleEngine extends AbstractEngine {
    private static final Logger logger = Logger.getLogger(SwiRuleEngine.class.getName());
    @Override public synchronized boolean init() {
        return true;
    }
    
    @Override 
    public  boolean initOnce() {
        synchronized(this){
            JPL.init();

            String fileName;
            String path=Global.getClientInfo().getParams().getProperty("Prolog_Root_Path", "");
            if(!path.endsWith("/")) path=path+"/";
            path=path+"main.pl";

            logger.log(Level.INFO,"Prolog consult => "+path);
            Query.oneSolution("consult('"+path+"').");
            this.notifyAll();
            return true;
        }
    }

    @Override
    public String getStatus(String name, String defaultValue) {
        return "";
    }

    @Override 
    public synchronized boolean setStatus(String status, String value) {
        //Den Status als wert betrachten und speichern
        this._setStatus(status, value);
        String rule="busmsg("+status+","+value+",default,StatusOut,ValueOut,RoutingOut)";
        Hashtable result[] = Query.allSolutions(rule );

        String routingOut="default";
        //java.util.Map<String, EngineValue> values=new java.util.HashMap<String, EngineValue>();
        java.util.ArrayList<EngineValue> values=new java.util.ArrayList<EngineValue>();
        EngineValue val;
        
        for(int l=0;l<result.length;l++) {
            
            Hashtable tab=result[l];

            String statusOut;
            String valueOut;

            routingOut="default";
            valueOut="";
            statusOut="";
            
            if( !(tab.get("StatusOut") == null) ) {
                statusOut=(String)tab.get("StatusOut").toString();
            }
            
            if( !(tab.get("ValueOut") == null) ) {
                valueOut=(String)tab.get("ValueOut").toString();
            }
            
            
            if( !(tab.get("RoutingOut") == null) ) {
                routingOut=(String)tab.get("RoutingOut").toString();
            }
            
            val=new EngineValue();
            val.setValue(valueOut);
            val.setStatus(statusOut);
            val.setRouting(routingOut);
            
            values.add(val);
            this._setStatus(statusOut, valueOut);
        }
        EngineTools.execCommands(values);
        //ToDo: auslagern!!!
        /*
        if(!routingOut.equals("default")) {
            String json="{";
            for(String key: values.keySet()) {
                json+="\""+key+"\""+" : "+"\""+values.get(key).replace("'", "")+"\",";
            }
            json+="}";
            AbstractBusPublisher pub=BusConnectorFactory.createBusPublisher();
            pub.publish(TopicTools.getPubCommandTopic()+routingOut, json);
        }
        */
        return true;
    }
    
    @Override 
    public synchronized Hashtable[] executeAll(String query) {
        
        try {
            Hashtable[] table=Query.allSolutions(query);
            return table;
        } catch(PrologException e) {
            logger.log(Level.SEVERE,"Error => "+e.getMessage());
        }
        return null;
    }
    
    
    private synchronized boolean _setStatus(String name, String value){
        this.executeAll("retract(status("+name+",X))");
        this.executeAll("asserta(status("+name+","+value+"))");
        
        if(this.getPersistStatus()!=null) {
            this.getPersistStatus().setStatus(name, value);
        }
        
        return true;
    }
    
}
