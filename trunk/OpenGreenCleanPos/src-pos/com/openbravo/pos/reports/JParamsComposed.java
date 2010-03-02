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

package com.openbravo.pos.reports;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteComposed;
import com.openbravo.pos.forms.AppView;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

public class JParamsComposed extends javax.swing.JPanel implements ReportEditorCreator {
    
    private List<ReportEditorCreator> editors = new ArrayList<ReportEditorCreator>();
    
    /** Creates new form JParamsComposed */
    public JParamsComposed() {
        initComponents();   
    }

    public void init(AppView app) {
        for (ReportEditorCreator qbff : editors) {
            qbff.init(app);
        }
    }

    public void activate() throws BasicException {
        for (ReportEditorCreator qbff : editors) {
            qbff.activate();
        }
    }

    public SerializerWrite getSerializerWrite() {
        
        SerializerWriteComposed sw = new SerializerWriteComposed();
        
        for (ReportEditorCreator qbff : editors) {
            sw.add(qbff.getSerializerWrite());
        }        
    
        return sw;
    }

    public Component getComponent() {
        return this;
    }  
    
    public Object createValue() throws BasicException {
        
        Object[] value = new Object[editors.size()];
        
        for(int i = 0; i < editors.size(); i++) {
            value[i] = editors.get(i).createValue();
        }
        
        return value;
    } 
    
    public void addEditor(ReportEditorCreator c) {
        editors.add(c);
        add(c.getComponent());
    }
    
    public boolean isEmpty() {
        
        return editors.isEmpty();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

    }// </editor-fold>//GEN-END:initComponents
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}