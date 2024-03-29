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


package com.openbravo.data.gui;

import javax.swing.*;
import com.openbravo.data.loader.QBFCompareEnum;
/**
 *
 * @author  adrian
 */
public class ListQBFModelNumber extends AbstractListModel implements ComboBoxModel {
    
    private Object[] m_items;
    private Object m_sel;
    
    /** Creates a new instance of ListQBFModelNumber */
    public ListQBFModelNumber() {

    m_items = new Object[] {
            QBFCompareEnum.COMP_NONE,
            QBFCompareEnum.COMP_EQUALS,
            QBFCompareEnum.COMP_RE,
            QBFCompareEnum.COMP_DISTINCT,
            QBFCompareEnum.COMP_GREATER, 
            QBFCompareEnum.COMP_LESS,
            QBFCompareEnum.COMP_GREATEROREQUALS, 
            QBFCompareEnum.COMP_LESSOREQUALS,
            QBFCompareEnum.COMP_ISNULL,
            QBFCompareEnum.COMP_ISNOTNULL,
        };
        m_sel = m_items[0];
    }
    
    public Object getElementAt(int index) {
        return m_items[index];
    }
   
    public int getSize() {
        return m_items.length;
    }
    
    public Object getSelectedItem() {
        return m_sel;
    }
     
    public void setSelectedItem(Object anItem) {
        m_sel = anItem;
    }
}
