/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test;
import de.dk9mbs.prohomelog.eventbus.main.AbstractBusParams;
import de.dk9mbs.prohomelog.eventbus.main.AbstractBusSubscriber;
import de.dk9mbs.prohomelog.eventbus.main.TopicTools;
import de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusParams;
import de.dk9mbs.prohomelog.sys.data.Global;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author buehler
 */
public abstract class AbstractPhlTestSzenario implements java.lang.Runnable, de.dk9mbs.prohomelog.eventbus.main.IBusMessageArrived  {
    private static final Logger logger = Logger.getLogger(AbstractPhlTestSzenario.class.getName());
    private ArrayList<AbstractPhlTestItem> _testItems=new ArrayList() ;
    private int _tick=0;
    private int _maxticks=0;
    private int _tickinterval=1000;
    private AbstractBusSubscriber _subscriber;

    public abstract boolean init(String Source);

    public void setMaxTicks(int ticks) {
        this._maxticks=ticks;
    }
    public void setTickInterval(int interval) {
        this._tickinterval=interval;
    }

    
    @Override
    public abstract void onBusData(String t, String m);    

    @Override
    public void run() {
        this.szenarioTick();
    }
    
    public void szenarioTick() {
        try {
            boolean run=true;
            while(run) {
                java.util.Iterator i=this._testItems.iterator();
                AbstractPhlTestItem item;
                while(i.hasNext()) {
                    item=(AbstractPhlTestItem)i.next();
                    item.szenarioTick(this._tick);
                }
                this._tick+=1;
                java.lang.Thread.sleep(this._tickinterval);
                if (this._maxticks>0 && this._tick>this._maxticks) return;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
    }
    
    
    public boolean startTest(){
        this._tick=0;
        String broker=Global.getClientInfo().getParams().getProperty("Broker", "");
        String brokerUid=Global.getClientInfo().getParams().getProperty("Broker_uid", "");
        String brokerPwd=Global.getClientInfo().getParams().getProperty("Broker_pwd", "");
        String topicRootSub=TopicTools.getSubCommandTopic();
        
        AbstractBusParams para=new MqttBusParams(broker
                , brokerUid, brokerPwd);
        para.setTopic(topicRootSub);
        para.setClientId("aa1234567890ff");
        this._subscriber=new de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusSubscriber(para, null);
        this._subscriber.connect();
        this._subscriber.subscribe();
        this._subscriber.setOnBusEventObject(this);
        /*
        
        */
        
        
        AbstractPhlTestItem testItem;
        String logPath=Global.getClientInfo().getParams().getProperty("Log_Path", "");
        
        java.io.File file = new java.io.File(logPath+"szenario.log");
        if(file.exists()){
            file.delete();
        }
        
        java.util.Iterator i=this._testItems.iterator();
        while(i.hasNext()) {
            testItem=(AbstractPhlTestItem)i.next();
            System.out.println(testItem.toString());
            testItem.start();
        }
        
        /*
        Sync. Timer für ein Szenario starten
        */
        java.lang.Thread t=new java.lang.Thread(this);
        t.start();
        
        return true;
    }

    public boolean addTestItem(AbstractPhlTestItem item) {
        this._testItems.add(item);
        return true;
    }
    
    public ArrayList<AbstractPhlTestItem> getTestItems() {
        return this._testItems;
    }
}
