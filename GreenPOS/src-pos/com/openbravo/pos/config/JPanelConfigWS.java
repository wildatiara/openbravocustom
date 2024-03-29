//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://code.google.com/p/openbravocustom/
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import java.awt.Component;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.AltEncrypter;
import javax.swing.JComboBox;

/**
 *
 * @author adrianromero
 */
public class JPanelConfigWS extends javax.swing.JPanel implements PanelConfig {

    private DirtyManager dirty = new DirtyManager();
        
    /** Creates new form JPanelConfigERP */
    public JPanelConfigWS() {
        initComponents();
        
        jtxtId.getDocument().addDocumentListener(dirty);
        jtxtName.getDocument().addDocumentListener(dirty);
        jtxtPassword.getDocument().addDocumentListener(dirty);
        jtxtUrl.getDocument().addDocumentListener(dirty);
        jtxtPaymentID.getDocument().addDocumentListener(dirty);
        jtxtTimeOut.getDocument().addDocumentListener(dirty);
        jComboBoxWS.addItem("OFF");
        jComboBoxWS.addItem("Virtuemart 1.3");
        jComboBoxWS.addItem("Virtuemart 2.0");
        //TODO : UTF / ISO selector
    }
    
    public boolean hasChanged() {
        return dirty.isDirty();
    }    
    
    public Component getConfigComponent() {
        return this;
    }
   
    public void loadProperties(AppConfig config) {

        jtxtUrl.setText(config.getProperty("ws.URL"));
        jtxtId.setText(config.getProperty("ws.posid"));
        jtxtTimeOut.setText(config.getProperty("ws.timeout"));
        jchkWSUsersStart.setSelected(Boolean.valueOf(config.getProperty("ws.userstart")).booleanValue());
        jchkWSProductsPre.setSelected(Boolean.valueOf(config.getProperty("ws.productpre")).booleanValue());
        jchkWSUsersPre.setSelected(Boolean.valueOf(config.getProperty("ws.userpre")).booleanValue());
        jchkWSProductsStart.setSelected(Boolean.valueOf(config.getProperty("ws.productstart")).booleanValue());
        jchkWSDeleteRT.setSelected(Boolean.valueOf(config.getProperty("ws.deletert")).booleanValue());
        
        try {
            String wstype = config.getProperty("ws.type");

            if (wstype.trim().equals("") || wstype==null)
                jComboBoxWS.setSelectedIndex(0);
            else
                jComboBoxWS.setSelectedItem(wstype);
        } catch (NullPointerException nppe) {
            jComboBoxWS.setSelectedIndex(0);
        }
        String sERPUser = config.getProperty("ws.user");
        String sERPPassword = config.getProperty("ws.password");
        if (sERPUser != null && sERPPassword != null && sERPPassword.startsWith("crypt:")) {
            // La clave esta encriptada.
            AltEncrypter cypher = new AltEncrypter("cypherkey" + sERPUser);
            sERPPassword = cypher.decrypt(sERPPassword.substring(6));
        }

        jtxtName.setText(sERPUser);
        jtxtPassword.setText(sERPPassword);    
        jtxtPaymentID.setText(config.getProperty("ws.payid"));

        dirty.setDirty(false);
    }
    
    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }
    
    public void saveProperties(AppConfig config) {
        
        config.setProperty("ws.URL", jtxtUrl.getText());
        config.setProperty("ws.posid", jtxtId.getText());
        config.setProperty("ws.timeout", jtxtTimeOut.getText());
        config.setProperty("ws.userstart", Boolean.toString(jchkWSUsersStart.isSelected()));
        config.setProperty("ws.productpre", Boolean.toString(jchkWSProductsPre.isSelected()));
        config.setProperty("ws.userpre", Boolean.toString(jchkWSUsersPre.isSelected()));
        config.setProperty("ws.productstart", Boolean.toString(jchkWSProductsStart.isSelected()));
        config.setProperty("ws.deletert", Boolean.toString(jchkWSDeleteRT.isSelected()));

        config.setProperty("ws.type", comboValue( jComboBoxWS.getSelectedItem()));
        
        config.setProperty("ws.user", jtxtName.getText());
        AltEncrypter cypher = new AltEncrypter("cypherkey" + jtxtName.getText());             
        config.setProperty("ws.password", "crypt:" + cypher.encrypt(new String(jtxtPassword.getPassword())));

        String tmpPayID = jtxtPaymentID.getText();
        if (tmpPayID.equals(""))
            tmpPayID="1";
        else
            config.setProperty("ws.payid", tmpPayID);

        dirty.setDirty(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlabelUrl = new javax.swing.JLabel();
        jtxtUrl = new javax.swing.JTextField();
        jLabelId = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jLabelProperties = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JPasswordField();
        jLabelPayID = new javax.swing.JLabel();
        jtxtPaymentID = new javax.swing.JTextField();
        jtxtTimeOut = new javax.swing.JTextField();
        jLabelTimeout = new javax.swing.JLabel();
        jchkWSProductsStart = new javax.swing.JCheckBox();
        jchkWSUsersPre = new javax.swing.JCheckBox();
        jchkWSProductsPre = new javax.swing.JCheckBox();
        jchkWSUsersStart = new javax.swing.JCheckBox();
        jchkWSDeleteRT = new javax.swing.JCheckBox();
        jComboBoxWS = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("label.wsconfig")));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("erp_messages"); // NOI18N
        jlabelUrl.setText(bundle.getString("label.wsurl")); // NOI18N

        jLabelId.setText(bundle.getString("label.wsid")); // NOI18N

        jtxtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtIdActionPerformed(evt);
            }
        });

        jLabelName.setText(bundle.getString("label.wsuser")); // NOI18N

        jLabelProperties.setText(bundle.getString("label.wspassword")); // NOI18N

        jtxtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtPasswordActionPerformed(evt);
            }
        });

        jLabelPayID.setText(bundle.getString("label.wspayid")); // NOI18N

        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabelTimeout.setText(bundle1.getString("label.wstimeout")); // NOI18N

        jchkWSProductsStart.setLabel(bundle1.getString("label.wsproductstart")); // NOI18N
        jchkWSProductsStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkWSProductsStartActionPerformed(evt);
            }
        });

        jchkWSUsersPre.setActionCommand("PopUpAttr");
        jchkWSUsersPre.setLabel(bundle1.getString("label.wsuserpre")); // NOI18N
        jchkWSUsersPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkWSUsersPreActionPerformed(evt);
            }
        });

        jchkWSProductsPre.setActionCommand("PopUpAttr");
        jchkWSProductsPre.setLabel(bundle1.getString("label.wsproductpre")); // NOI18N
        jchkWSProductsPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkWSProductsPreActionPerformed(evt);
            }
        });

        jchkWSUsersStart.setActionCommand("PopUpAttr");
        jchkWSUsersStart.setLabel(bundle1.getString("label.wsuserstart")); // NOI18N
        jchkWSUsersStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkWSUsersStartActionPerformed(evt);
            }
        });

        jchkWSDeleteRT.setText("Delete returned tickets");
        jchkWSDeleteRT.setActionCommand(bundle1.getString("DeleteRT")); // NOI18N
        jchkWSDeleteRT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkWSDeleteRTActionPerformed(evt);
            }
        });

        jComboBoxWS.setEditable(true);
        jComboBoxWS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWSActionPerformed(evt);
            }
        });

        jLabel1.setText("WebService");

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UTF", "ISO" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchkWSDeleteRT)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlabelUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelProperties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelPayID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jtxtTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jtxtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jchkWSProductsStart, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jchkWSUsersStart)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchkWSUsersPre, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                            .addComponent(jchkWSProductsPre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelId, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jComboBoxWS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(212, 212, 212)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxWS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabelUrl)
                    .addComponent(jtxtUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelProperties)
                    .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPayID)
                    .addComponent(jtxtPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkWSProductsStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchkWSProductsPre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkWSUsersStart)
                    .addComponent(jchkWSUsersPre, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchkWSDeleteRT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTimeout)
                    .addComponent(jtxtTimeOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabelId.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtPasswordActionPerformed

    private void jchkWSProductsStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkWSProductsStartActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jchkWSProductsStartActionPerformed

    private void jchkWSUsersPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkWSUsersPreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkWSUsersPreActionPerformed

    private void jchkWSProductsPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkWSProductsPreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkWSProductsPreActionPerformed

    private void jchkWSDeleteRTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkWSDeleteRTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkWSDeleteRTActionPerformed

    private void jchkWSUsersStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkWSUsersStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchkWSUsersStartActionPerformed

private void jComboBoxWSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxWSActionPerformed
    
    JComboBox cb = (JComboBox)evt.getSource();
    Object newItem = cb.getSelectedItem();
    if (newItem.toString().equals("OFF")) {
        jtxtId.setEnabled(false);
        jtxtName.setEnabled(false);
        jtxtPassword.setEnabled(false);
        jtxtUrl.setEnabled(false);
        jtxtPaymentID.setEnabled(false);
        jtxtTimeOut.setEnabled(false);
        jchkWSDeleteRT.setEnabled(false);
        jchkWSProductsPre.setEnabled(false);
        jchkWSProductsStart.setEnabled(false);
        jchkWSUsersPre.setEnabled(false);
        jchkWSUsersStart.setEnabled(false);
    } else {
        jtxtId.setEnabled(true);
        jtxtName.setEnabled(true);
        jtxtPassword.setEnabled(true);
        jtxtUrl.setEnabled(true);
        jtxtPaymentID.setEnabled(true);
        jtxtTimeOut.setEnabled(true);
        jchkWSDeleteRT.setEnabled(true);
        jchkWSProductsPre.setEnabled(true);
        jchkWSProductsStart.setEnabled(true);
        jchkWSUsersPre.setEnabled(true);
        jchkWSUsersStart.setEnabled(true);
        
    }
        
}//GEN-LAST:event_jComboBoxWSActionPerformed

private void jtxtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtIdActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_jtxtIdActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBoxWS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelPayID;
    private javax.swing.JLabel jLabelProperties;
    private javax.swing.JLabel jLabelTimeout;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox jchkWSDeleteRT;
    private javax.swing.JCheckBox jchkWSProductsPre;
    private javax.swing.JCheckBox jchkWSProductsStart;
    private javax.swing.JCheckBox jchkWSUsersPre;
    private javax.swing.JCheckBox jchkWSUsersStart;
    private javax.swing.JLabel jlabelUrl;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JPasswordField jtxtPassword;
    private javax.swing.JTextField jtxtPaymentID;
    private javax.swing.JTextField jtxtTimeOut;
    private javax.swing.JTextField jtxtUrl;
    // End of variables declaration//GEN-END:variables
    
}
