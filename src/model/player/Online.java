/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.player;

import java.net.Socket;

/**
 *
 * @author LENOVO
 */
public class Online {
    private Socket client;
    private Player player;

    public Online() {
    }

    public Online(Socket client, Player player) {
        this.client = client;
        this.player = player;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
}
