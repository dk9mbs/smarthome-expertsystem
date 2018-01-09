/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.eventbus.main;

/**
 *
 * @author mbuehler
 */
public interface IBusMessageArrived {
    public void onBusData(String topic, String payload);
}
