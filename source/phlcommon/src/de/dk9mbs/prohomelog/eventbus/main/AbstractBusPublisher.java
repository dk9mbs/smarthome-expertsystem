/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.main;
import de.dk9mbs.prohomelog.sys.data.Global;
import java.util.logging.Logger;
/**
 *
 * @author buehler
 */
public abstract class AbstractBusPublisher {
    private static final Logger logger=Logger.getLogger(AbstractBusPublisher.class.getName() );
    protected AbstractBusParams _busParams;

    public AbstractBusPublisher(AbstractBusParams para) {
        this._busParams=para;
    }

    public AbstractBusPublisher() {
    }
    
    public void setPara(AbstractBusParams para) {
        this._busParams=para;
    }
    
    public boolean connect() {return true;}
    public boolean publish(String topic, String msg) {return true;}
    public boolean publish(String topic, String msg, int qos, boolean retained) {return true;}
    
}
