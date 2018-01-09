/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test;

/**
 *
 * @author mbuehler
 */
public class PropTools {
    public static boolean existItem(String list, String value){
        String[] values=list.split(";");
        for(int x=0;x<values.length;x++) {
            if(values[x].equals(value) ) {
                return true;
            }
        }
        return false;
    }
}
