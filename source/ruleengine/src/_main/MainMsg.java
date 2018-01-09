/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _main;

import de.dk9mbs.prohomelog.eventbus.main.IBusMessageArrived;
import de.dk9mbs.prohomelog.ruleengine.AbstractEngine;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public class MainMsg implements IBusMessageArrived {
    private static final Logger logger = Logger.getLogger(MainMsg.class.getName());

    @Override 
    public void onBusData(String t, String m) {
        try {
            String[] topics;
            String statusC;
            String value=m;
            String status;
            String tag="";
            String[] temp;
            
            topics=t.split("/");
            statusC=topics[topics.length-1];

            temp=statusC.split("-");
            status=temp[0];
            if(temp.length>1) tag=temp[1];
            
            AbstractEngine re =  de.dk9mbs.prohomelog.ruleengine.tools.RuleEngineFactory.createEngine();

            //Den Status setzen
            value=parseValue(status,value);
            re.setStatus(status, value);
        } catch(Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }    

    private String parseValue(String status, String value) {
        /*
        Parse a JSON string:
        */
        //logger.log(Level.INFO, "-->"+value);
        if(value.startsWith("{")) {
            org.json.JSONObject json=new org.json.JSONObject(value) ;
            switch (json.getString("_type")) {
                case "location":
                    value="["+Double.toString(json.getDouble("lat"))+","
                            +Double.toString(json.getDouble("lon"))+","
                            +"'"+json.getString("device")+"'"
                            +"]" ;
                    break;
                default:
                    value="!!!ERROR!!!";
            }
        }
        
        //logger.log(Level.INFO, "<--"+value);
        return value;
    }

}
