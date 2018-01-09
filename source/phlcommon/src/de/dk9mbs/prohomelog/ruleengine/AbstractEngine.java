/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine;

import de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusPublisher;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.dk9mbs.prohomelog.status.IPersistStatus;
/**
 *
 * @author mbuehler
 */
public abstract class AbstractEngine {
    private static final Logger logger = Logger.getLogger(AbstractEngine.class.getName());

    private IPersistStatus _persistStatus;
    
    public abstract boolean initOnce();
    public abstract boolean init();
    
    public abstract Hashtable[] executeAll(String query); 

    public abstract boolean setStatus(String name, String value);
    public abstract String getStatus(String name, String defaultValue);

    public IPersistStatus getPersistStatus() {
        return this._persistStatus;
    }
    
    public boolean setPersistStatus(IPersistStatus status){
        this._persistStatus=status;
        return true;
    }
}
