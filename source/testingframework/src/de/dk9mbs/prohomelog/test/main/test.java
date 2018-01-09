/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test.main;

import de.dk9mbs.prohomelog.sys.config.ConfigReader;
import de.dk9mbs.prohomelog.test.AbstractPhlTestSzenario;
import de.dk9mbs.prohomelog.test.gui.PhlTestForm;
import de.dk9mbs.prohomelog.test.PhlTestSzenarioHeizung;

/**
 *
 * @author buehler
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.util.Properties prop = ConfigReader.readConfig("");
        de.dk9mbs.prohomelog.sys.data.Global.init(prop);

        PhlTestForm frm = new PhlTestForm();

        AbstractPhlTestSzenario szenario=new PhlTestSzenarioHeizung();
        szenario.init("");
        frm.setSzenario(szenario);
        
        frm.setVisible(true);
    }
    
}
