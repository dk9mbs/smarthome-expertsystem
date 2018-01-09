/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.status;

/**
 *
 * @author mbuehler
 */
public interface IPersistStatus {
    public boolean setStatus(String name, String value);
    public String getStatus(String name, String defaultValue);
}
