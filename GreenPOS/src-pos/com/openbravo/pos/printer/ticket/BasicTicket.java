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

package com.openbravo.pos.printer.ticket;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BasicTicket implements PrintItem {

    protected static Font BASEFONT;
    protected static int FONTHEIGHT;
    protected static double IMAGE_SCALE;

    protected java.util.List<PrintItem> m_aCommands;
    protected PrintItemLine pil;
    protected int m_iBodyHeight;

    static {
        BASEFONT = new Font("Monospaced", Font.PLAIN, 12).deriveFont(AffineTransform.getScaleInstance(1.0, 1.40));
        FONTHEIGHT = 20;
        IMAGE_SCALE = 1.0;
    }

    /** Creates a new instance of AbstractTicket */
    public BasicTicket() {
        m_aCommands = new ArrayList<PrintItem>();
        pil = null;
        m_iBodyHeight = 0;
    }

    public int getHeight() {
        return m_iBodyHeight;
    }

    public void draw(Graphics2D g2d, int x, int y, int width) {

        int currenty = y;
        for (PrintItem pi : m_aCommands) {
            pi.draw(g2d, x, currenty, width);
            currenty += pi.getHeight();
        }
    }

    public java.util.List<PrintItem> getCommands() {
        return m_aCommands;
    }

    // INTERFAZ PRINTER 2
    public void printImage(BufferedImage image) {

        PrintItem pi = new PrintItemImage(image, IMAGE_SCALE);
        m_aCommands.add(pi);
        m_iBodyHeight += pi.getHeight();
    }

    public void printBarCode(String type, String position, String code) {

        PrintItem pi = new PrintItemBarcode(type, position, code, IMAGE_SCALE);
        m_aCommands.add(pi);
        m_iBodyHeight += pi.getHeight();
    }

    public void beginLine(int iTextSize) {
        pil = new PrintItemLine(iTextSize, BASEFONT, FONTHEIGHT);
    }

    public void printText(int iStyle, String sText) {
        if (pil != null) {
            pil.addText(iStyle, sText);
        }
    }

    public void endLine() {
        if (pil != null) {
            m_aCommands.add(pil);
            m_iBodyHeight += pil.getHeight();
            pil = null;
        }
    }
}
