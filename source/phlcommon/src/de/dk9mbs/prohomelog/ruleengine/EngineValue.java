/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.ruleengine;

/**
 *
 * @author mbuehler
 */
public class EngineValue {
    private String _value;
    private String _status;
    private String _routing;

    public String getValue() {
        return _value;
    }

    public void setValue(String _value) {
        this._value = _value;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public String getRouting() {
        return _routing;
    }

    public void setRouting(String _routing) {
        this._routing = _routing;
    }
    
    
}
