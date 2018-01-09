/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test;

import de.dk9mbs.prohomelog.sys.data.Global;
import de.dk9mbs.prohomelog.test.testitems.PhlTestItemSchalterSensor;
import de.dk9mbs.prohomelog.test.testitems.PhlTestItemSliderSensor;
import de.dk9mbs.prohomelog.test.testitems.PhlTestItemLinTempSensor;
import java.util.logging.Logger;
import de.dk9mbs.prohomelog.eventbus.main.TopicTools;

/**
 *
 * @author buehler
 */
public class PhlTestSzenarioHeizung extends AbstractPhlTestSzenario {
    private static final Logger logger = Logger.getLogger(PhlTestSzenarioHeizung.class.getName());

    @Override
    public void onBusData(String t, String m) {
        //for(tes)
    }    

    @Override
    public boolean init(String Source) {
        this.setMaxTicks(288);
        this.setTickInterval(350);

        AbstractPhlTestItem item;
        String broker=Global.getClientInfo().getParams().getProperty("Broker", "");
        String brokerUid=Global.getClientInfo().getParams().getProperty("Broker_uid", "");
        String brokerPwd=Global.getClientInfo().getParams().getProperty("Broker_pwd", "");
        String topicRootPub=TopicTools.getPubStatusTopic();
        String topicRootSub=TopicTools.getSubCommandTopic();

        
        item=new de.dk9mbs.prohomelog.test.testitems.PhlTestItemFensterSensor();
        item.setTitle("Fenster Wohnzimmer 1");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", topicRootSub+"fenster_wohnzimmer");
        item.setProp("broker_pub_topic", topicRootPub+"fenster_wohnzimmer");
        item.setProp("broker_clientid","testframework0");
        item.setProp("item_value_on", "open");
        item.setProp("item_value_off", "close");
        item.setProp("time_on", "162");
        item.setProp("time_off", "0");
        item.init();
        this.addTestItem(item);

        item=new de.dk9mbs.prohomelog.test.testitems.PhlTestItemNachtSensor();
        item.setTitle("Nachtabsenkung");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", topicRootSub+"heizung_nacht");
        item.setProp("broker_pub_topic", topicRootPub+"heizung_nacht");
        item.setProp("broker_clientid","testframework1");
        item.setProp("item_value_on", "on");
        item.setProp("item_value_off", "off");
        item.setProp("time_on", "0;240");
        item.setProp("time_off", "48");
        item.init();
        this.addTestItem(item);

        item=new de.dk9mbs.prohomelog.test.testitems.PhlTestItemAnwesendSensor();
        item.setTitle("Anwesenheit Bewohner");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", topicRootSub+"anwesend");
        item.setProp("broker_pub_topic", topicRootPub+"anwesend");
        item.setProp("broker_clientid","testframework2");
        item.setProp("item_value_on", "yes");
        item.setProp("item_value_off", "no");
        item.setProp("time_on", "0;156");
        item.setProp("time_off", "108");
        item.init();
        this.addTestItem(item);
        
        
        item=new PhlTestItemSliderSensor();
        item.setTitle("Aussen Temp.");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", topicRootSub+"temp_aussen");
        item.setProp("broker_pub_topic", topicRootPub+"temp_aussen");
        item.setProp("broker_clientid","testframework3");
        item.init();
        this.addTestItem(item);
        
        item=new PhlTestItemLinTempSensor();
        item.setTitle("Wohnzimmer Temp.");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", topicRootSub+"heizung");
        item.setProp("broker_pub_topic", topicRootPub+"temp_wohnzimmer");
        item.setProp("broker_clientid","testframework4");
        item.init();
        this.addTestItem(item);
        
        item=new de.dk9mbs.prohomelog.test.testitems.PhlTestItemBusLogger();
        item.setTitle("Logger");
        item.setProp("broker", broker);
        item.setProp("broker_uid", brokerUid);
        item.setProp("broker_pwd", brokerPwd);
        item.setProp("broker_sub_topic", "prohomelog/dk9mbs/#");//Status ueberwachen
        item.setProp("broker_pub_topic", topicRootPub+"XXXXXXXX");
        item.setProp("broker_clientid","testframework5");
        item.init();
        this.addTestItem(item);
        
        
        return true;
    }
}
