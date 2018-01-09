/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.sys.data;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mbuehler
 */
public class ClientInfo {
    private DBConnection _dbcnn=null;
    private Hashtable<String, Object> _environment = new Hashtable<String, Object>();    
    private Properties _params= new Properties() ;
    
    public ClientInfo(Properties dbparams) {
        this._params=dbparams;
    }
    
    public boolean init() {
        this._dbcnn = new DBConnection(this._params);
        this._dbcnn.open();
        return true;
    }

    public Properties getParams() {
        return _params;
    }
    
    public String getEnvironmentString(String name,String defaultvalue){
        String result=(String)this._environment.getOrDefault(name, defaultvalue);
        result=Objects.toString(result, defaultvalue);
        return result;
    }
    
    public boolean setEnvironmentString(String name, String value){
        this._environment.put(name, Objects.toString(value, ""));
        return true;
    }
    
    /**
     * Get the value of getDBConnection
     *
     * @return the value of getDBConnection
     */
     public DBConnection getGetDBConnection() {
        this.init();
        return _dbcnn;
    }

    /**
     * Set the value of getDBConnection
     *
     * @param dbcnn new value of getDBConnection
     */
    public void setGetDBConnection(DBConnection dbcnn) {
        this._dbcnn = dbcnn;
    }

}
