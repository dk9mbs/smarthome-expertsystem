/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.sys.data;

import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author mbuehler
 */
public class Msgbox  {
    private static final Logger logger=Logger.getLogger( Msgbox.class.getName() );
    
    public static void show(java.awt.Component parent, String text, String title) {
        _show (parent, text, title);    
    }
    
    public static void show(java.awt.Component parent, String text) {
        _show (parent, text, "");    
    }
    
    private static void _show(java.awt.Component parent, String text, String title) {
        
        logger.warning(text);
        /*
        JOptionPane.showMessageDialog(parent
                , text
                , title
                , JOptionPane.ERROR_MESSAGE); */
    }

}
