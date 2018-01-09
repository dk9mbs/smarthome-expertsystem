/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.mqtt;

//import de.dk9mbs.homebus.sys.data.ClientInfo;
//import de.dk9mbs.homebus.sys.data.Global;
import de.dk9mbs.prohomelog.eventbus.main.AbstractBusParams;
import de.dk9mbs.prohomelog.eventbus.*;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import de.dk9mbs.prohomelog.eventbus.main.*;
/**
 *
 * @author buehler
 */
public class MqttBusSubscriber extends AbstractBusSubscriber implements MqttCallback {
    private static final Logger logger=Logger.getLogger(MqttBusSubscriber.class.getName() );

    protected String _server="";
    protected String _clientId="";
    protected MqttClient _client=null;
    protected Boolean _connected=false;
    protected String _topic="";
    protected String _uid="";
    protected String _pwd="";
    
    public MqttBusSubscriber(AbstractBusParams para, IBusMessageArrived msg) {
        super(para, msg);
        this._server = para.getServer();
        this._clientId = para.getClientId();
        this._topic=para.getTopic();
        this._uid=para.getUid();
        this._pwd=para.getPwd();
        this._msg=msg;
    }

    @Override
    public void setOnBusEventObject(IBusMessageArrived msg) {
        this._msg=msg;
    }
    
    @Override
    public void connectionLost(Throwable throwable) { 
        this.connect();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken t) {}
    
    @Override
    public void messageArrived(String t, MqttMessage m) throws Exception { 
        if (this._msg!=null) {
            this._msg.onBusData(t, m.toString());
        }
    }
    
    @Override
    public boolean connect() {
        this._connected=false;
        do {
            try {
                MemoryPersistence persistence = new MemoryPersistence();
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setKeepAliveInterval(0);
                
                if(this._uid.length()>0) {
                    connOpts.setUserName(this._uid);
                    connOpts.setPassword(this._pwd.toCharArray());
                }
                
                //_client = new MqttClient("tcp://" + this._server , this._clientId, persistence);
                _client = new MqttClient("tcp://" + this._server , this._clientId, null);
                
                _client.setCallback(this);
                _client.connect(connOpts);
                
                this.subscribe();
                
                this._connected=true;
            } catch (MqttException e) {
                logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
            } catch (Exception e1) {
                logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e1));
            }
            
            if (!this._connected) {
                de.dk9mbs.prohomelog.eventbus.mqtt.Tools.sleep(5000);
            }
            
        } while (this._connected==false);
        
        return true;
    }

    @Override
    public boolean subscribe() {
        try {
            _client.subscribe(this._topic, 2);
        } catch(Exception e ) {
            logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e) );
        }
        
        return true;
    }
}
