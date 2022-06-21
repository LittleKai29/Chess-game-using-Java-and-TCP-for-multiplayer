/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author dotav
 */
public class Turn implements Serializable{
    private int myturn;
    private int old_x;
    private int old_y;
    private int new_x;
    private int new_y;
    
    
    public Turn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getOld_x() {
        return old_x;
    }

    public void setOld_x(int old_x) {
        this.old_x = old_x;
    }

    public int getOld_y() {
        return old_y;
    }

    public void setOld_y(int old_y) {
        this.old_y = old_y;
    }

    public int getNew_x() {
        return new_x;
    }

    public void setNew_x(int new_x) {
        this.new_x = new_x;
    }

    public int getNew_y() {
        return new_y;
    }

    public void setNew_y(int new_y) {
        this.new_y = new_y;
    }

    public Turn(int myturn,int old_x, int old_y, int new_x, int new_y) {
        this.myturn = myturn;
        this.old_x = old_x;
        this.old_y = old_y;
        this.new_x = new_x;
        this.new_y = new_y;
    }

    public Turn(int myturn) {
        this.myturn = myturn;
    }

    public int getMyturn() {
        return myturn;
    }

    public void setMyturn(int myturn) {
        this.myturn = myturn;
    }
}
