/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.mqtt;

import de.dk9mbs.prohomelog.eventbus.main.*;
import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public class MqttBusParams extends de.dk9mbs.prohomelog.eventbus.main.AbstractBusParams {
    private static final Logger logger = Logger.getLogger(MqttBusParams.class.getName());

    private String _Server;
    private String _uid;
    private String _pwd;
    private String _clientId="homebus_base";
    private String _topic;
    
    public MqttBusParams(String server, String uid, String pwd) {
        super(server,uid,pwd);
        this._Server = server;
        this._uid = uid;
        this._pwd = pwd;
    }

    public MqttBusParams() {
        super();
    }
    
    public String getTopic() {
        return _topic;
    }

    public void setTopic(String _topic) {
        this._topic = _topic;
    }

    public String getServer() {
        return _Server;
    }

    public void setServer(String _Server) {
        this._Server = _Server;
    }

    public String getUid() {
        return _uid;
    }

    public void setUid(String _uid) {
        this._uid = _uid;
    }

    public String getPwd() {
        return _pwd;
    }

    public void setPwd(String _pwd) {
        this._pwd = _pwd;
    }

    public String getClientId() {
        return _clientId;
    }

    public void setClientId(String _clientId) {
        this._clientId = _clientId;
    }
    
}
