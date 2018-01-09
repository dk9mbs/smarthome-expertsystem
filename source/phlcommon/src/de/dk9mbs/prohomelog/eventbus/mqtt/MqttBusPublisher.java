/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.mqtt;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import de.dk9mbs.prohomelog.eventbus.main.*;
/**
 *
 * @author buehler
 */
public class MqttBusPublisher extends AbstractBusPublisher {
    private static final Logger logger = Logger.getLogger(MqttBusPublisher.class.getName());
    protected String _server="";
    protected String _clientId="";
    protected MqttClient _client=null;
    protected String _uid="";
    protected String _pwd="";
    
    public MqttBusPublisher(AbstractBusParams para) {
        super(para);
        this.setPara(para);
    }

    public MqttBusPublisher() {
    }
    
    public void setPara(AbstractBusParams para) {
        super.setPara(para);
        this._server = para.getServer();
        this._clientId = para.getClientId();
        this._uid=para.getUid();
        this._pwd=para.getPwd();
        this._busParams=para;
    }
    
    @Override
    public boolean connect() {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setKeepAliveInterval(0);
            
            if(this._uid.length()>0) {
                connOpts.setUserName(this._uid);
                connOpts.setPassword(this._pwd.toCharArray());
            }
            
            //_client = new MqttClient("tcp://" + this._server , "homebus_publisher" , persistence);
            _client = new MqttClient("tcp://" + this._server , this._clientId , null);
            _client.connect(connOpts);
        } catch (MqttException e) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        }
        return true;
    }

    @Override
    public boolean publish(String topic, String msg, int qos, boolean retained) {
        try{
            if(this._client==null || !this._client.isConnected()) this.connect();
            //MqttMessage message = new MqttMessage(msg.getBytes());
            //this._client.publish(topic , message);
            this._client.publish(topic, msg.getBytes(),qos,retained);
            this._client.disconnect();
            this._client.close();
        } catch(org.eclipse.paho.client.mqttv3.MqttException e) {
            logger.log(Level.SEVERE, this._clientId+" => "+de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        }
        
        return true; 
    }
    
    @Override
    public boolean publish(String topic, String msg) {
        return this.publish(topic, msg, 0, true);
    }
    
}
