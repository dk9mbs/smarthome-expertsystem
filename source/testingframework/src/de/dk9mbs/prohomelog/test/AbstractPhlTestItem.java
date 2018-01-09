/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test;
import java.util.*;
import de.dk9mbs.prohomelog.eventbus.main.*;
import de.dk9mbs.prohomelog.eventbus.mqtt.*;
/**
 *
 * @author buehler
 */
public abstract class AbstractPhlTestItem extends javax.swing.JPanel {
    protected String _title="";
    private AbstractBusPublisher _publisher;
    private AbstractBusSubscriber _subscriber;
    private HashMap<String, String> _props=new HashMap();
    
    public abstract void szenarioTick(int tick);
    
    public void AbstractPhlTestItem() {
        //this._publisher = de.dk9mbs.prohomelog.test.eventbus.mqtt
    }
    
    public void setTitle(String title) {
        this._title=title;
    }

    public String getTitle() {
        return this._title;
    }
    
    public boolean init() {
        AbstractBusParams para=new MqttBusParams(this.getProp("broker", "")
                , this.getProp("broker_uid", ""), this.getProp("broker_pwd", ""));
        para.setClientId("pub_"+this.getProp("broker_clientid", ""));
        this._publisher=new de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusPublisher (para);
        this._publisher.connect();
        this._publisher.publish("dk9mbs/test","hallo welt");
        
        para=new MqttBusParams(this.getProp("broker", "")
                , this.getProp("broker_uid", ""), this.getProp("broker_pwd", ""));
        para.setTopic(this.getProp("broker_sub_topic", ""));
        para.setClientId("sub_"+this.getProp("broker_clientid", ""));
        this._subscriber=new de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusSubscriber(para, null);
        this._subscriber.connect();
        this._subscriber.subscribe();
        
        return true;
    }
    
    public boolean  start() {
        
        return true;
    }
    
    public void setProp(String name, String value) {
        this._props.put(name, value);
    }
    
    public String getProp(String name, String defaultVal) {
        return this._props.getOrDefault(name, defaultVal);
    }
    
    public AbstractBusPublisher getPublisher() {
        return this._publisher;
    }

    public AbstractBusSubscriber getSubscriber() {
        return this._subscriber;
    }
    
}
