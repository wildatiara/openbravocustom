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

package com.openbravo.pos.printer;

import javax.swing.JComponent;

public interface DeviceDisplay {

    // INTERFAZ DESCRIPCION
    public String getDisplayName();
    public String getDisplayDescription();
    public JComponent getDisplayComponent();
    
    // INTERFAZ VISOR    
    public void writeVisor(int animation, String sLine1, String sLine2);
    public void writeVisor(String sLine1, String sLine2);
    public void clearVisor();
}
