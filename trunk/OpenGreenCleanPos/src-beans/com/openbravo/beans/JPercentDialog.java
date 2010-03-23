//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
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

package com.openbravo.beans;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.Icon;
import javax.swing.SwingUtilities;

/**
 *
 * @author  adrian
 */
public class JPercentDialog extends javax.swing.JDialog {
    
    private static LocaleResources m_resources;
    
    private Double m_value;
    
    /** Creates new form JNumberDialog */
    public JPercentDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        init();
    }
    
    /** Creates new form JNumberDialog */
    public JPercentDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        init();
    }
    
    private void init() {
        
        if (m_resources == null) {
            m_resources = new LocaleResources();
            m_resources.addBundleName("beans_messages");
        }
        
        initComponents();        
        getRootPane().setDefaultButton(jcmdOK);   
        
        m_jnumber.addEditorKeys(m_jKeys);
        m_jnumber.reset();
        m_jnumber.setDoubleValue(0.0);
        m_jnumber.activate();
        
        m_jPanelTitle.setBorder(RoundedBorder.createGradientBorder());

        m_value = 0.;
    }
    
    private void setTitle(String title, String message, Icon icon) {
        setTitle(title);
        m_lblMessage.setText(message);
        m_lblMessage.setIcon(icon);
    }
    
    public static Double showEditNumber(Component parent, String title) {
        return showEditNumber(parent, title, null, null);
    }
    public static Double showEditNumber(Component parent, String title, String message) {
        return showEditNumber(parent, title, message, null);
    }
    public static Double showEditNumber(Component parent, String title, String message, Icon icon) {
        
        Window window = SwingUtilities.windowForComponent(parent);
        
        JPercentDialog myMsg;
        if (window instanceof Frame) { 
            myMsg = new JPercentDialog((Frame) window, true);
        } else {
            myMsg = new JPercentDialog((Dialog) window, true);
        }
        
        myMsg.setTitle(title, message, icon);
        myMsg.setVisible(true);
        return myMsg.m_value;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jcmdOK = new javax.swing.JButton();
        jcmdCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanelGrid = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jBt10 = new javax.swing.JButton();
        jBt15 = new javax.swing.JButton();
        jBt20 = new javax.swing.JButton();
        jBt25 = new javax.swing.JButton();
        jBt50 = new javax.swing.JButton();
        m_jnumber = new com.openbravo.editor.JEditorDoublePositive();
        m_jPanelTitle = new javax.swing.JPanel();
        m_lblMessage = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jcmdOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/button_ok.png"))); // NOI18N
        jcmdOK.setText(m_resources.getString("button.ok")); // NOI18N
        jcmdOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdOKActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdOK);

        jcmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/button_cancel.png"))); // NOI18N
        jcmdCancel.setText(m_resources.getString("button.cancel")); // NOI18N
        jcmdCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdCancel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        m_jKeys.setMinimumSize(new java.awt.Dimension(222, 270));
        m_jKeys.setPreferredSize(new java.awt.Dimension(250, 300));
        m_jKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jKeysActionPerformed(evt);
            }
        });
        jPanel3.add(m_jKeys);

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel4);

        jBt10.setText("10");
        jBt10.setMaximumSize(new java.awt.Dimension(45, 45));
        jBt10.setMinimumSize(new java.awt.Dimension(25, 25));
        jBt10.setPreferredSize(new java.awt.Dimension(45, 45));
        jBt10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt10ActionPerformed(evt);
            }
        });
        jPanel5.add(jBt10);

        jBt15.setText("15");
        jBt15.setMaximumSize(new java.awt.Dimension(45, 45));
        jBt15.setMinimumSize(new java.awt.Dimension(25, 25));
        jBt15.setPreferredSize(new java.awt.Dimension(45, 45));
        jBt15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt15ActionPerformed(evt);
            }
        });
        jPanel5.add(jBt15);

        jBt20.setText("20");
        jBt20.setMaximumSize(new java.awt.Dimension(45, 45));
        jBt20.setMinimumSize(new java.awt.Dimension(25, 25));
        jBt20.setPreferredSize(new java.awt.Dimension(45, 45));
        jBt20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt20ActionPerformed(evt);
            }
        });
        jPanel5.add(jBt20);

        jBt25.setText("25");
        jBt25.setMaximumSize(new java.awt.Dimension(45, 45));
        jBt25.setMinimumSize(new java.awt.Dimension(25, 25));
        jBt25.setPreferredSize(new java.awt.Dimension(45, 45));
        jBt25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt25ActionPerformed(evt);
            }
        });
        jPanel5.add(jBt25);

        jBt50.setText("50");
        jBt50.setMaximumSize(new java.awt.Dimension(45, 45));
        jBt50.setMinimumSize(new java.awt.Dimension(25, 25));
        jBt50.setPreferredSize(new java.awt.Dimension(45, 45));
        jBt50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBt50ActionPerformed(evt);
            }
        });
        jPanel5.add(jBt50);

        jPanel3.add(jPanel5);

        jPanelGrid.add(jPanel3);

        m_jnumber.setPreferredSize(new java.awt.Dimension(250, 25));
        jPanelGrid.add(m_jnumber);

        jPanel2.add(jPanelGrid, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        m_jPanelTitle.setLayout(new java.awt.BorderLayout());

        m_lblMessage.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.darkGray), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        m_jPanelTitle.add(m_lblMessage, java.awt.BorderLayout.CENTER);

        getContentPane().add(m_jPanelTitle, java.awt.BorderLayout.NORTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-302)/2, (screenSize.height-536)/2, 302, 536);
    }// </editor-fold>//GEN-END:initComponents

    private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdOKActionPerformed


        m_value = m_jnumber.getDoubleValue();
        setVisible(false);
        dispose();

        
    }//GEN-LAST:event_jcmdOKActionPerformed

    private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdCancelActionPerformed

        setVisible(false);
        dispose();
        
    }//GEN-LAST:event_jcmdCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        setVisible(false);
        dispose();
        
    }//GEN-LAST:event_formWindowClosing

    private void m_jKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jKeysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_m_jKeysActionPerformed

    private void jBt10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt10ActionPerformed
       m_jnumber.setValueInteger(10);
       jcmdOKActionPerformed(evt);
    }//GEN-LAST:event_jBt10ActionPerformed

    private void jBt15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt15ActionPerformed
       m_jnumber.setValueInteger(15);
       jcmdOKActionPerformed(evt);
    }//GEN-LAST:event_jBt15ActionPerformed

    private void jBt20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt20ActionPerformed
        m_jnumber.setValueInteger(20);
       jcmdOKActionPerformed(evt);
    }//GEN-LAST:event_jBt20ActionPerformed

    private void jBt25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt25ActionPerformed
       m_jnumber.setValueInteger(25);
       jcmdOKActionPerformed(evt);
    }//GEN-LAST:event_jBt25ActionPerformed

    private void jBt50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBt50ActionPerformed
        m_jnumber.setValueInteger(50);
       jcmdOKActionPerformed(evt);
    }//GEN-LAST:event_jBt50ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBt10;
    private javax.swing.JButton jBt15;
    private javax.swing.JButton jBt20;
    private javax.swing.JButton jBt25;
    private javax.swing.JButton jBt50;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelGrid;
    private javax.swing.JButton jcmdCancel;
    private javax.swing.JButton jcmdOK;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JPanel m_jPanelTitle;
    private com.openbravo.editor.JEditorDoublePositive m_jnumber;
    private javax.swing.JLabel m_lblMessage;
    // End of variables declaration//GEN-END:variables
    
}
