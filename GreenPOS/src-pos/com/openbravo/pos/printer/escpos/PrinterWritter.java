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

package com.openbravo.pos.printer.escpos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class PrinterWritter {
    
    private boolean initialized = false;

    private ExecutorService exec;
    
    public PrinterWritter() {
        exec = Executors.newSingleThreadExecutor();
    }
    
    protected abstract void internalWrite(byte[] data);
    protected abstract void internalFlush();
    protected abstract void internalClose();
    
    public void init(final byte[] data) {
        if (!initialized) {
            write(data);
            initialized = true;
        }
    }
       
    public void write(String sValue) {
        write(sValue.getBytes());
    }

    public void write(final byte[] data) {
        exec.execute(new Runnable() {
            public void run() {
                internalWrite(data);
            }
        });
    }
    
    public void flush() {
        exec.execute(new Runnable() {
            public void run() {
                internalFlush();
            }
        });
    }
    
    public void close() {
        exec.execute(new Runnable() {
            public void run() {
                internalClose();
            }
        });
        exec.shutdown();
    }
}
