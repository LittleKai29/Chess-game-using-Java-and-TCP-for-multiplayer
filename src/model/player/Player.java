/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.io.Serializable;

/**
 *
 * @author LENOVO
 */
public class Player implements Serializable{
    private int id;
    private String fullname;
    private String username;
    private String password;
    private String status;
    private float point;
    private int opponentCount;
    private int moveCount;
    private int matchWin;
    private int matchLose;

    public Player() {
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Player(int id, String fullname, String username, String password, String status, float point, int opponentCount, int moveCount, int matchWin, int matchLose) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.status = status;
        this.point = point;
        this.opponentCount = opponentCount;
        this.moveCount = moveCount;
        this.matchWin = matchWin;
        this.matchLose = matchLose;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public int getOpponentCount() {
        return opponentCount;
    }

    public void setOpponentCount(int opponentCount) {
        this.opponentCount = opponentCount;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int getMatchWin() {
        return matchWin;
    }

    public void setMatchWin(int matchWin) {
        this.matchWin = matchWin;
    }

    public int getMatchLose() {
        return matchLose;
    }

    public void setMatchLose(int matchLose) {
        this.matchLose = matchLose;
    }

    
    public Object[] toObject(){
        return new Object[]{
            username, point, status
        };
    }
}
