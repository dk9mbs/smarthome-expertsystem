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
 * @author mbuehler
 */
public class TopicTools {
    private static final Logger logger = Logger.getLogger(TopicTools.class.getName());
    
    public static String getPubCommandTopic() {
        String cmd=Global.getClientInfo().getParams().getProperty("Broker_command_topic", "prohomelog/debug");
        //cmd=cmd.replace("{session}", java.util.UUID.randomUUID().toString());
        cmd=cmd.replace("{session}", "0");
        return cmd;
    }

    public static String getSubCommandTopic() {
        String cmd=Global.getClientInfo().getParams().getProperty("Broker_command_topic", "prohomelog/debug");
        cmd=cmd.replace("{session}", "+");
        return cmd;
    }
    
    public static String getPubStatusTopic() {
        String cmd=Global.getClientInfo().getParams().getProperty("Broker_status_topic", "prohomelog/debug");
        //cmd=cmd.replace("{session}", java.util.UUID.randomUUID().toString());
        cmd=cmd.replace("{session}", "0");
        return cmd;
    }

    public static String getSubStatusTopic() {
        String cmd=Global.getClientInfo().getParams().getProperty("Broker_status_topic", "prohomelog/debug");
        cmd=cmd.replace("{session}", "+");
        return cmd;
    }
    
}
