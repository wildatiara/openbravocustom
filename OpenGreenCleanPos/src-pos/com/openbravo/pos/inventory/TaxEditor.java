//    GreenPOS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://code.google.com/p/openbravocustom/
//
//    This file is part of GreenPOS.
//
//    GreenPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    GreenPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with GreenPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.inventory;

import java.awt.Component;
import java.util.UUID;
import javax.swing.*;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import java.util.ArrayList;
import java.util.List;

public class TaxEditor extends JPanel implements EditorRecord {
    
    private Object m_oId;
    
    private SentenceList taxcatsent;
    private ComboBoxValModel taxcatmodel;    
    
    private SentenceList taxcustcatsent;
    private ComboBoxValModel taxcustcatmodel;   
    
    private SentenceList taxparentsent;
    private ComboBoxValModel taxparentmodel;    
    
    /** Creates new form taxEditor */
    public TaxEditor(AppView app, DirtyManager dirty) {
        
        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
        initComponents();
        
        taxcatsent = dlSales.getTaxCategoriesList();
        taxcatmodel = new ComboBoxValModel();        
        
        taxcustcatsent = dlSales.getTaxCustCategoriesList();
        taxcustcatmodel = new ComboBoxValModel();    
        
        taxparentsent = dlSales.getTaxList();
        taxparentmodel = new ComboBoxValModel();    

        m_jName.getDocument().addDocumentListener(dirty);
        m_jTaxCategory.addActionListener(dirty);
        m_jCustTaxCategory.addActionListener(dirty);
        m_jTaxParent.addActionListener(dirty);
        m_jRate.getDocument().addDocumentListener(dirty);
        jCascade.addActionListener(dirty);
        jOrder.getDocument().addDocumentListener(dirty);
        
        writeValueEOF();
    }
    
    public void activate() throws BasicException {
        
        List a = taxcatsent.list();
        taxcatmodel = new ComboBoxValModel(a);
        m_jTaxCategory.setModel(taxcatmodel);
        
        a = taxcustcatsent.list();
        a.add(0, null); // The null item
        taxcustcatmodel = new ComboBoxValModel(a);
        m_jCustTaxCategory.setModel(taxcustcatmodel);    
        
       
    }
    
    public void refresh() {
        
        List a;
        
        try {
            a = taxparentsent.list();
        } catch (BasicException eD) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotloadlists"), eD);
            msg.show(this);
            a = new ArrayList();
        }
        
        a.add(0, null); // The null item
        taxparentmodel = new ComboBoxValModel(a);
        m_jTaxParent.setModel(taxparentmodel);    
    }
    
    public void writeValueEOF() {
        m_oId = null;
        m_jName.setText(null);
        taxcatmodel.setSelectedKey(null);
        taxcustcatmodel.setSelectedKey(null);
        taxparentmodel.setSelectedKey(null);
        m_jRate.setText(null);
        jCascade.setSelected(false);
        jOrder.setText(null);
        
        m_jName.setEnabled(false);
        m_jTaxCategory.setEnabled(false);
        m_jCustTaxCategory.setEnabled(false);
        m_jTaxParent.setEnabled(false);
        m_jRate.setEnabled(false);
        jCascade.setEnabled(false);
        jOrder.setEnabled(false);
    }
    public void writeValueInsert() {
        m_oId = UUID.randomUUID().toString();
        m_jName.setText(null);
        taxcatmodel.setSelectedKey(null);
        taxcustcatmodel.setSelectedKey(null);
        taxparentmodel.setSelectedKey(null);
        m_jRate.setText(null);
        jCascade.setSelected(false);
        jOrder.setText(null);
        
        m_jName.setEnabled(true);
        m_jTaxCategory.setEnabled(true);
        m_jCustTaxCategory.setEnabled(true);
        m_jTaxParent.setEnabled(true);        
        m_jRate.setEnabled(true);
        jCascade.setEnabled(true);    
        jOrder.setEnabled(true);
    }
    public void writeValueDelete(Object value) {

        Object[] tax = (Object[]) value;
        m_oId = tax[0];
        m_jName.setText(Formats.STRING.formatValue(tax[1]));
        taxcatmodel.setSelectedKey(tax[2]);
        taxcustcatmodel.setSelectedKey(tax[3]);
        taxparentmodel.setSelectedKey(tax[4]);        
        m_jRate.setText(Formats.PERCENT.formatValue(tax[5]));
        jCascade.setSelected((Boolean) tax[6]);
        jOrder.setText(Formats.INT.formatValue(tax[7]));
        
        m_jName.setEnabled(false);
        m_jTaxCategory.setEnabled(false);
        m_jCustTaxCategory.setEnabled(false);
        m_jTaxParent.setEnabled(false);
        m_jRate.setEnabled(false);
        jCascade.setEnabled(false);
        jOrder.setEnabled(false);
    }    
    public void writeValueEdit(Object value) {

        Object[] tax = (Object[]) value;
        m_oId = tax[0];
        m_jName.setText(Formats.STRING.formatValue(tax[1]));
        taxcatmodel.setSelectedKey(tax[2]);
        taxcustcatmodel.setSelectedKey(tax[3]);
        taxparentmodel.setSelectedKey(tax[4]);        
        m_jRate.setText(Formats.PERCENT.formatValue(tax[5]));
        jCascade.setSelected((Boolean) tax[6]);
        jOrder.setText(Formats.INT.formatValue(tax[7]));
        
        m_jName.setEnabled(true);
        m_jTaxCategory.setEnabled(true);
        m_jCustTaxCategory.setEnabled(true);
        m_jTaxParent.setEnabled(true);        
        m_jRate.setEnabled(true);
        jCascade.setEnabled(true);
        jOrder.setEnabled(true);
    }

    public Object createValue() throws BasicException {
        
        Object[] tax = new Object[8];

        tax[0] = m_oId;
        tax[1] = m_jName.getText();
        tax[2] = taxcatmodel.getSelectedKey();
        tax[3] = taxcustcatmodel.getSelectedKey(); 
        tax[4] = taxparentmodel.getSelectedKey(); 
        tax[5] = Formats.PERCENT.parseValue(m_jRate.getText());
        tax[6] = Boolean.valueOf(jCascade.isSelected());
        tax[7] = Formats.INT.parseValue(jOrder.getText());
        
        return tax;
    }    
     
    public Component getComponent() {
        return this;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        m_jRate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCascade = new javax.swing.JCheckBox();
        m_jTaxCategory = new javax.swing.JComboBox();
        m_jTaxParent = new javax.swing.JComboBox();
        m_jCustTaxCategory = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jOrder = new javax.swing.JTextField();

        setLayout(null);
        add(m_jName);
        m_jName.setBounds(240, 20, 200, 19);

        jLabel2.setText(AppLocal.getIntString("Label.Name")); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(20, 20, 220, 15);

        jLabel3.setText(AppLocal.getIntString("label.dutyrate")); // NOI18N
        add(jLabel3);
        jLabel3.setBounds(20, 140, 220, 15);
        add(m_jRate);
        m_jRate.setBounds(240, 140, 60, 19);

        jLabel1.setText(AppLocal.getIntString("label.taxcategory")); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(20, 50, 220, 15);

        jLabel4.setText(AppLocal.getIntString("label.custtaxcategory")); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(20, 80, 220, 15);

        jLabel5.setText(AppLocal.getIntString("label.taxparent")); // NOI18N
        add(jLabel5);
        jLabel5.setBounds(20, 110, 220, 15);

        jCascade.setText(AppLocal.getIntString("label.cascade")); // NOI18N
        add(jCascade);
        jCascade.setBounds(320, 140, 110, 23);
        add(m_jTaxCategory);
        m_jTaxCategory.setBounds(240, 50, 200, 24);
        add(m_jTaxParent);
        m_jTaxParent.setBounds(240, 110, 200, 24);
        add(m_jCustTaxCategory);
        m_jCustTaxCategory.setBounds(240, 80, 200, 24);

        jLabel6.setText(AppLocal.getIntString("label.order")); // NOI18N
        add(jLabel6);
        jLabel6.setBounds(20, 170, 220, 15);
        add(jOrder);
        jOrder.setBounds(240, 170, 60, 19);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCascade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jOrder;
    private javax.swing.JComboBox m_jCustTaxCategory;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jRate;
    private javax.swing.JComboBox m_jTaxCategory;
    private javax.swing.JComboBox m_jTaxParent;
    // End of variables declaration//GEN-END:variables
    
}
