/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.inventory;

import com.openbravo.data.loader.IKeyed;
import java.io.Serializable;

/**
 *
 * @author turkhar
 */
public class PiecesInfo implements Serializable, IKeyed {

    private String m_sID;
    private String m_sName;

    public PiecesInfo(String sID, String sName) {
        m_sID = sID;
        m_sName = sName;
    }

    public Object getKey() {
        return m_sID;
    }

    public void setID(String sID) {
        m_sID = sID;
    }

    public String getID() {
        return m_sID;
    }

    public String getName() {
        return m_sName;
    }

    public void setName(String sName) {
        m_sName = sName;
    }

    @Override
    public String toString(){
        return m_sName;
    }
}