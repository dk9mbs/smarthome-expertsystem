/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.main;

import org.eclipse.paho.client.mqttv3.*;
import java.util.logging.Logger;

/**
 *
 * @author buehler
 */
public abstract class AbstractBusSubscriber  {
    private static final Logger logger=Logger.getLogger(AbstractBusSubscriber.class.getName() );
    protected IBusMessageArrived _msg;
    
    public AbstractBusSubscriber(AbstractBusParams para, IBusMessageArrived msg){}
    public abstract void setOnBusEventObject (IBusMessageArrived msg);
    public abstract boolean connect() ;
    public abstract boolean subscribe() ;
    
}
