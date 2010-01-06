/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.forms;

import com.openbravo.data.loader.IKeyed;

/**
 *
 * @author Tii
 */
class PiecesSetInfo implements IKeyed{
    private String id;
    private String name;

    public PiecesSetInfo(String string, String string0) {
        this.id = id;
        this.name = name;
    }

    public Object getKey() {
         return id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
