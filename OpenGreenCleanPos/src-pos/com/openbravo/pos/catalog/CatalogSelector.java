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

package com.openbravo.pos.catalog;

import java.awt.Component;
import java.awt.event.ActionListener;
import com.openbravo.basic.BasicException;

/**
 *
 * @author adrianromero
 */
public interface CatalogSelector {
    
    public void loadCatalog() throws BasicException;
    public void showCatalogPanel(String id);
    public void setComponentEnabled(boolean value);
    public Component getComponent();
    
    public void addActionListener(ActionListener l);  
    public void removeActionListener(ActionListener l);    
}
