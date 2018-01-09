package _main;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.dk9mbs.prohomelog.eventbus.main.AbstractBusSubscriber;
import de.dk9mbs.prohomelog.eventbus.mqtt.*;
import de.dk9mbs.prohomelog.eventbus.main.*;
import de.dk9mbs.prohomelog.ruleengine.AbstractEngine;
import de.dk9mbs.prohomelog.sys.config.ConfigReader;
import de.dk9mbs.prohomelog.sys.data.Global;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author mbuehler
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.util.Properties prop = ConfigReader.readConfig("");
        de.dk9mbs.prohomelog.sys.data.Global.init(prop);
        
        /*
        Once einmalig aufrufen
        */
        AbstractEngine re =  de.dk9mbs.prohomelog.ruleengine.tools.RuleEngineFactory.createEngine();
        re.initOnce();
        /*
        GUI
        */
        //de.dk9mbs.prohomelog.ruleengine.gui.PrologQuery frm = new de.dk9mbs.prohomelog.ruleengine.gui.PrologQuery();
        //frm.setVisible(true);
        /*
        MQTT
        */
        IBusMessageArrived msg=new MainMsg();
        MqttBusParams buspara=new MqttBusParams(Global.getClientInfo().getParams().getProperty("Broker","")
                                ,Global.getClientInfo().getParams().getProperty("Broker_uid","")
                                ,Global.getClientInfo().getParams().getProperty("Broker_pwd",""));
        //buspara.setClientId("xxx_prohomlog_core_xxx");
        buspara.setClientId(de.dk9mbs.prohomelog.eventbus.mqtt.Tools.getUniqueClientId());
        logger.log(Level.INFO, "Sub. Topic:"+de.dk9mbs.prohomelog.eventbus.main.TopicTools.getSubStatusTopic()+"#");
        buspara.setTopic(de.dk9mbs.prohomelog.eventbus.main.TopicTools.getSubStatusTopic()+"#" );
        AbstractBusSubscriber statusSubscriber=new MqttBusSubscriber(buspara, msg);
        statusSubscriber.connect();
        
    }
    
   

}
