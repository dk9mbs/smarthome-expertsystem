/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine.swiprolog;

import de.dk9mbs.prohomelog.eventbus.main.*; 
import de.dk9mbs.prohomelog.eventbus.tools.BusConnectorFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpl.*;
//import org.jpl7.*;
import de.dk9mbs.prohomelog.ruleengine.AbstractEngine;
import de.dk9mbs.prohomelog.sys.data.Global;
import de.dk9mbs.prohomelog.eventbus.main.TopicTools;
/**
 *
 * @author mbuehler
 */
public class SwiRuleEngineOld extends AbstractEngine {
    private static final Logger logger = Logger.getLogger(SwiRuleEngineOld.class.getName());
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
        //Prolog Hook ausfuehren
        //String rule="Tag='_',"+status+"(Tag,Return)";
        String rule=status+"(Return)";
        Hashtable result[] = Query.allSolutions(rule );
        //java.util.Map<String, Term> result[]=Query.allSolutions(ruleName);
        
        for(int l=0;l<result.length;l++) {
            Hashtable tab=result[l];
            //java.util.Map tab=result[l];
            
            Enumeration e = tab.keys();
            while(e.hasMoreElements()) {
                String key=(String)e.nextElement();
                logger.log(Level.INFO, "Key returned:"+key+" => "+tab.get(key));
            } 

            //String tag = ((Atom)tab.get("Tag")).toString();
            Term term = (Term)tab.get("Return");
            ArrayList list=new ArrayList();
            _compoundReader(term, list);

            java.util.Iterator<String> it = list.iterator();
            int keyvaluetype=1;
            String key="";
            value="";

            while( it.hasNext() ) {
                String temp=(String)it.next() ;
                if(keyvaluetype==1) {
                    keyvaluetype=2;
                    key=temp;
                    value="";
                } else if (keyvaluetype==2) {
                    keyvaluetype=1;
                    value=temp;
                    this._setStatus(key, value);

                    //Send the result commands 
                    AbstractBusPublisher pub=BusConnectorFactory.createBusPublisher();
                    pub.publish(TopicTools.getPubCommandTopic()+key, value);
                    
                }
            }
        }
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

    private static boolean _compoundReader(Term term, ArrayList list) {
        for (Term oneTerm : term.args()) {
            if( (!oneTerm.isCompound() || oneTerm.isAtom()) && !oneTerm.toString().equals("[]") ) { 
                list.add(oneTerm.toString());
            }
            if(oneTerm.isCompound() && !oneTerm.isAtom() ) _compoundReader(oneTerm, list);
        }        
        return true;
    }
    
}
