/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dk9mbs.prohomelog.test.testitems;

import de.dk9mbs.prohomelog.eventbus.mqtt.MqttBusParams;
import de.dk9mbs.prohomelog.test.AbstractPhlTestItem;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author buehler
 */
public class PhlTestItemLinTempSensor extends AbstractPhlTestItem implements de.dk9mbs.prohomelog.eventbus.main.IBusMessageArrived {
    private static final Logger logger = Logger.getLogger(PhlTestItemLinTempSensor.class.getName());
    private double _temp=19;
    private String _heizungStatus="off";
    private boolean _changeStatus=false;
    
    /**
     * Creates new form PhlTestItemThermostatAktor
     */
    public PhlTestItemLinTempSensor() {
        initComponents();
    }

    @Override
    public void szenarioTick(int tick) {
        if( (this._temp>-10 && this._temp<25) || this._changeStatus ) {
            this._changeStatus=false;

            if(this._heizungStatus.equals("off")) {
                this._temp-=0.2;
            } else {
                this._temp+=0.2;
            }
            
            this._temp=(Math.round(this._temp*100.0))/100.0;
       }    
        
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtHeizungStatus = new javax.swing.JTextField();
        chkHeizungStatus = new javax.swing.JCheckBox();

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        labTitle.setText("jLabel1");

        jLabel1.setText("Heizung Status");

        jLabel2.setText("Temperatur");

        txtHeizungStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHeizungStatusActionPerformed(evt);
            }
        });

        chkHeizungStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHeizungStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHeizungStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkHeizungStatus))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(labTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(chkHeizungStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtHeizungStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkHeizungStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHeizungStatusActionPerformed
        this._changeStatus=true;
        if(this.chkHeizungStatus.isSelected()) {
            this._heizungStatus="on";
        } else {
            this._heizungStatus="off";
        }
    }//GEN-LAST:event_chkHeizungStatusActionPerformed

    private void txtHeizungStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHeizungStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHeizungStatusActionPerformed

    
    @Override
    public boolean init() {
        super.init();
        this.labTitle.setText(this.getTitle());
        this.getSubscriber().setOnBusEventObject(this);
        
        return true;
    }
    
    @Override
    public boolean start() {
        RunTimer timer=new RunTimer(this);
        timer.start();
        return true;
    }
    
    @Override
    public void onBusData(String topic, String payload){
        this._heizungStatus=payload;
        this.chkHeizungStatus.setSelected(payload.equals("on"));
        this._changeStatus=true;
    }
    
    private class RunTimer extends java.lang.Thread {
        private PhlTestItemLinTempSensor _form;
        
        RunTimer(AbstractPhlTestItem form) {
            this._form=(PhlTestItemLinTempSensor)form;
        }
        
        @Override 
        public void run() {
            try {
                while(true) {
                    /*
                    if( (this._form._temp>-10 && this._form._temp<25) || this._form._changeStatus ) {
                        this._form._changeStatus=false;
                        
                        if(this._form._heizungStatus.equals("off")) {
                            this._form._temp-=1;
                        } else {
                            this._form._temp+=1;
                        }
                    }    
                    */
                    this._form.txtHeizungStatus.setText( String.valueOf(this._form._temp) );
                    //this._form.txtTemp.setText( this._form._heizungStatus);
                    this._form.chkHeizungStatus.setSelected((this._form._heizungStatus.equals("on")));

                    this._form.getPublisher().publish(this._form.getProp("broker_pub_topic", "dummy/dummy") , String.valueOf(this._form._temp));
                    java.lang.Thread.sleep(1312);
                }
            } catch(Exception e) {
                logger.log(Level.SEVERE, de.dk9mbs.prohomelog.sys.exception.ExceptionTools.getString(e));
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkHeizungStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labTitle;
    private javax.swing.JTextField txtHeizungStatus;
    // End of variables declaration//GEN-END:variables
}