/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.inventory;
import com.openbravo.data.loader.IKeyed;

/**
 *
 * @author turkhar
 */
public class PiecesSetInfo implements IKeyed {

    private String id;
    private String pieces;

    public PiecesSetInfo(String id, String pieces) {
        this.id = id;
        this.pieces = pieces;
    }

    public Object getKey() {
        return id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return pieces;
    }

    @Override
    public String toString() {
        return pieces;
    }

}
