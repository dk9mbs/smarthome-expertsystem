/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.sys.exception;

/**
 *
 * @author mbuehler
 */
public class ExceptionTools {
    public static String getString(Exception e) {
        java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
        java.io.PrintWriter w = new java.io.PrintWriter(cw);
        e.printStackTrace(w);
        w.close();
        String trace = cw.toString();        
        return trace;
    }
}
