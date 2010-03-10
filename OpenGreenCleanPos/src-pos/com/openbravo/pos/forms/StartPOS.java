//    GreenPressing POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://code.google.com/p/openbravocustom/
//
//    This file is part of GreenPressing POS.
//
//    GreenPressing POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    GreenPressing POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with GreenPressing POS.  If not, see <http://www.gnu.org/licenses/>.


package com.openbravo.pos.forms;

import java.util.Locale;
import javax.swing.UIManager;
import com.openbravo.format.Formats;
import com.openbravo.pos.instance.InstanceQuery;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceSkin;

/**
 *
 * @author adrianromero
 */
public class StartPOS {


    private static Logger logger = Logger.getLogger("com.openbravo.pos.forms.StartPOS");
    
    /** Creates a new instance of StartPOS */
    private StartPOS() {
    }
    
    
    public static boolean registerApp() {
                       
        // vemos si existe alguna instancia        
        InstanceQuery i = null;
        try {
            i = new InstanceQuery();
            i.getAppMessage().restoreWindow();
            return false;
        } catch (Exception e) {
            return true;
        }  
    }
    
    public static void main (final String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                if (!registerApp()) {
                    System.exit(1);
                }
                
                AppConfig config = new AppConfig(args);
                config.load();

                // set SSH.
                String sshuser = config.getProperty("ssh.user");
                String sshpassword = config.getProperty("ssh.password");
                String sshlport = config.getProperty("ssh.lport");
                String sshrport = config.getProperty("ssh.rport");
                String sshurl = config.getProperty("ssh.URL");
                if (sshuser != null && !sshuser.equals("") && sshlport != null && sshurl != null) {
                    //create SSH
                }

                // set Locale.
                String slang = config.getProperty("user.language");
                String scountry = config.getProperty("user.country");
                String svariant = config.getProperty("user.variant");
                if (slang != null && !slang.equals("") && scountry != null && svariant != null) {                                        
                    Locale.setDefault(new Locale(slang, scountry, svariant));
                }
                
                // Set the format patterns
                Formats.setIntegerPattern(config.getProperty("format.integer"));
                Formats.setDoublePattern(config.getProperty("format.double"));
                Formats.setCurrencyPattern(config.getProperty("format.currency"));
                Formats.setPercentPattern(config.getProperty("format.percent"));
                Formats.setDatePattern(config.getProperty("format.date"));
                Formats.setTimePattern(config.getProperty("format.time"));
                Formats.setDateTimePattern(config.getProperty("format.datetime"));               
                
                // Set the look and feel.
                try {             
                    
                    Object laf = Class.forName(config.getProperty("swing.defaultlaf")).newInstance();
                    
                    if (laf instanceof LookAndFeel){
                        UIManager.setLookAndFeel((LookAndFeel) laf);
                    } else if (laf instanceof SubstanceSkin) {                      
                        SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);                   
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Cannot set look and feel", e);
                }
                
                String screenmode = config.getProperty("machine.screenmode");
                if ("fullscreen".equals(screenmode)) {
                    JRootKiosk rootkiosk = new JRootKiosk();
                    rootkiosk.initFrame(config);
                } else {
                    JRootFrame rootframe = new JRootFrame(); 
                    rootframe.initFrame(config);
                }

            }
        });
    }    
}
