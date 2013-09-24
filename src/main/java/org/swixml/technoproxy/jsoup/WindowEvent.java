/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package org.swixml.technoproxy.jsoup;

import java.util.EventObject;


public class WindowEvent extends EventObject {

    /**
     * 
     */
    private static final long serialVersionUID = 9020370331250531915L;
    private int oldState;
    private int newState;
    private int id;
    private Object opposite;



    public int getId () {
        return id;
    }


    public void setId (int id) {
        this.id = id;
    }


    public Object getOpposite () {
        return opposite;
    }


    public void setOpposite (Object opposite) {
        this.opposite = opposite;
    }


    public void setOldState (int oldState) {
        this.oldState = oldState;
    }


    public void setNewState (int newState) {
        this.newState = newState;
    }


    public WindowEvent(Object source, int id, Object opposite) {
        this(source, id, opposite, 0, 0);
    }


    public WindowEvent(Object source, int id, int oldState, int newState) {
        this(source, id, null, oldState, newState);
    }

    public WindowEvent(Object source, int id) {
        this(source, id, null, 0, 0);
    }

    public WindowEvent (Object source, int id, Object opposite, int oldState2,
            int newState2) {
        super(source);
        this.id = id;
        this.oldState = oldState2;
        this.newState = newState2;
        this.opposite = opposite;
    }

    public Object getWindow() {
        return (source instanceof Object) ? (Object)source : null;
    }


    public int getOldState() {
        return oldState;
    }
    public int getNewState() {
        return newState;
    }

}
