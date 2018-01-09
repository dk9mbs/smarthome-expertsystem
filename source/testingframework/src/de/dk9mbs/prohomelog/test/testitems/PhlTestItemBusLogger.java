/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test.testitems;

import de.dk9mbs.prohomelog.test.AbstractPhlTestItem;
import java.util.logging.Logger;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import de.dk9mbs.prohomelog.sys.data.Global;

/**
 *
 * @author buehler
 */
public class PhlTestItemBusLogger extends AbstractPhlTestItem implements de.dk9mbs.prohomelog.eventbus.main.IBusMessageArrived  {
    private static final Logger logger = Logger.getLogger(PhlTestItemBusLogger.class.getName());
    private Map<String, String> _status=new java.util.HashMap<String, String>();
    /**
     * Creates new form PhlTestItemThermostatAktor
     */
    public PhlTestItemBusLogger() {
        initComponents();
    }

    @Override
    public void szenarioTick(int tick) {
        this.labTitle.setText(this.getTitle()+" ("+String.valueOf(tick)+" Ticks)");

        if(tick==0) this.labStart.setText( new Date().toString() );
        this.labEnd.setText( new Date().toString() );

        String line;
        line=String.valueOf(tick)+"\t"
                +this._status.getOrDefault("temp_wohnzimmer", "0")+"\t"
                +this._status.getOrDefault("temp_aussen", "0")+"\t"
                +this._status.getOrDefault("temp_vorlauf", "0")+"\t"
                +this._status.getOrDefault("heizung","off");
        this.getPublisher().publish("prohomelog/debug", line);
        
        try {
            String logPath=Global.getClientInfo().getParams().getProperty("Log_Path", "");
            FileWriter fw = new FileWriter(logPath+"szenario.log",true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(line);
            String n = System.getProperty("line.separator");
            bw.write(n);
            bw.close();        
        } catch(IOException e) {
            logger.log(Level.SEVERE,e.getMessage(),e);
        }
        
    }

    @Override
    public void onBusData(String t, String m) {
        String[] temp=t.split("/");
        String status=temp[temp.length-1];
        String sub=temp[temp.length-2];
        if(sub.equals("status")) this.labBus.setText(status+" => "+m);
        if(sub.equals("command")) this.labBusCommand.setText(status+" => "+m);
        this._status.put(status, m);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labTitle = new javax.swing.JLabel();
        labBus = new javax.swing.JLabel();
        labStart = new javax.swing.JLabel();
        labEnd = new javax.swing.JLabel();
        labBusCommand = new javax.swing.JLabel();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labTitle.setText("jLabel1");

        labBus.setText("jLabel1");

        labStart.setText("Start Time");

        labEnd.setText("End Time");

        labBusCommand.setText("Command");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
            .addComponent(labBus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labBusCommand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labStart, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labBus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labBusCommand)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labEnd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public boolean init() {
        super.init();
        this.labTitle.setText(this.getTitle());
        this.getSubscriber().setOnBusEventObject(this);
        return true;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labBus;
    private javax.swing.JLabel labBusCommand;
    private javax.swing.JLabel labEnd;
    private javax.swing.JLabel labStart;
    private javax.swing.JLabel labTitle;
    // End of variables declaration//GEN-END:variables
}
