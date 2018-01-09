/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine;

import de.dk9mbs.prohomelog.eventbus.main.AbstractBusPublisher;
import de.dk9mbs.prohomelog.eventbus.main.TopicTools;
import de.dk9mbs.prohomelog.eventbus.tools.BusConnectorFactory;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author mbuehler
 */
public class EngineTools {
    public static boolean execCommands(ArrayList<EngineValue> values) {
        
        for (Iterator<EngineValue> it = values.iterator(); it.hasNext();) {
            EngineValue value=it.next();
            if(value.getRouting().equals("default")) {
                AbstractBusPublisher pub=BusConnectorFactory.createBusPublisher();
                pub.publish(TopicTools.getPubCommandTopic()+value.getStatus() , value.getValue() );
            }
        }
        return true;
    }
}
