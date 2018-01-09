/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.main;

import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public abstract class AbstractBusParams {
    private static final Logger logger = Logger.getLogger(AbstractBusParams.class.getName());

    
    public AbstractBusParams(String _Server, String _uid, String _pwd) {
    }

    public AbstractBusParams() {
    }
    
    public String getTopic() {
        return "";
    }

    public void setTopic(String _topic) {
    }

    public String getServer() {
        return "";
    }

    public void setServer(String _Server) {
    }

    public String getUid() {
        return "";
    }

    public void setUid(String _uid) {
    }

    public String getPwd() {
        return "";
    }

    public void setPwd(String _pwd) {
    }

    public String getClientId() {
        return "";
    }

    public void setClientId(String _clientId) {
    }
    
}
