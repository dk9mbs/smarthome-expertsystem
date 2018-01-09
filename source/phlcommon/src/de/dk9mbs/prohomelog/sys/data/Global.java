package de.dk9mbs.prohomelog.sys.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author mbuehler
 */
public class Global {
    private static ClientInfo _clientinfo=null;

    public static boolean init(java.util.Properties prop){
        _clientinfo = new ClientInfo(prop);
        //return _clientinfo.init();
        return true;
    }
    
    public static ClientInfo getClientInfo() {
        return _clientinfo;
    }

}
