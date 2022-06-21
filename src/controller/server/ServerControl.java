/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.server;

import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.player.Message;
import model.player.Online;
import model.player.Player;
import model.player.Turn;

/**
 *
 * @author LENOVO
 */
public class ServerControl {

    private Connection con;
    private ServerSocket serverSocket;
    private Socket clientSocket1;
    private Socket clientSocket2;
    private int client1Restart ;
    private int client2Restart ;
    private int client1Move;
    private int client2Move;
    private int serverPort = 5555;
    private int id1,id2;
    private ArrayList<Online> onlineList = new ArrayList<>();

    public ServerControl() {
        getDBConnection("chess", "root", "");
        openServer(serverPort);
        while (true) {
            listenning();
        }
    }

    private void getDBConnection(String dbName, String username,
            String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            con = DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openServer(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenning() {
        try {
            System.out.println("Server is listenning...");
            Socket clientSocket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(clientSocket);
            serverThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkLogin(Player player) {
        String query = "select * from player where username = '"
                + player.getUsername() + "' and password = '" + player.getPassword() + "'";
        try {

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                player.setId(rs.getInt("id"));
                player.setFullname(rs.getString("fullname"));
                player.setStatus("Online");
                player.setPoint(rs.getFloat("point"));
                player.setOpponentCount(rs.getInt("opponentCount"));
                player.setMoveCount(rs.getInt("moveCount"));
                player.setMatchWin(rs.getInt("matchWin"));
                player.setMatchLose(rs.getInt("matchLose"));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<Player> showPlayer() {
        ArrayList<Player> list = new ArrayList<>();
        for (Online onl : onlineList) {
            list.add(onl.getPlayer());
        }
        return list;
    }

    class ServerThread extends Thread {

        private Socket socket;
        private Player player;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois
                            = new ObjectInputStream(socket.getInputStream());

                    Message rcv_message = (Message) ois.readObject();
                    Message send_message = new Message();
                    switch (rcv_message.getMessage()) {
                        case "Login":
                            Player pl = (Player) rcv_message.getObject();
                            if (checkLogin(pl)) {
                                send_message.setMessage("Login");
                                send_message.setObject(pl);
                                this.player = pl;
                                Online online = new Online(socket, pl);
                                onlineList.add(online);
                            } else {
                                send_message.setMessage("Login");
                                send_message.setObject(null);
                            }
                            ObjectOutputStream oos
                                    = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(send_message);
                            break;
                        case "showPlayer":
                            ArrayList<Player> list = showPlayer();
                            send_message.setMessage("showPlayer");
                            send_message.setObject(list);
                            ObjectOutputStream oos1
                                    = new ObjectOutputStream(socket.getOutputStream());
                            oos1.writeObject(send_message);
                            for (Online onl : onlineList) {
                                if (onl.getClient() != socket) {
                                    send_message.setMessage("refreshPlayer");
                                    ObjectOutputStream oos2
                                            = new ObjectOutputStream(socket.getOutputStream());
                                    oos2.writeObject(send_message);
                                }
                            }
                            break;
                        case "refreshPlayer":
                            ArrayList<Player> list1 = showPlayer();
                            send_message.setMessage("refreshPlayer");
                            send_message.setObject(list1);
                            ObjectOutputStream oos3
                                    = new ObjectOutputStream(socket.getOutputStream());
                            oos3.writeObject(send_message);
                            break;
                        case "Invite":
                            clientSocket1 = socket;
                            Player player_2 = (Player) rcv_message.getObject();
                            id2 = player_2.getId();
                            Player player_1 = null;
                            for (Online onl : onlineList) {
                                if (socket == onl.getClient()) {
                                    player_1 = onl.getPlayer();
                                    id1 = player_1.getId();
                                    break;
                                }
                            }
                            for (Online cred : onlineList) {
                                if (cred.getPlayer().getId() == player_2.getId()) {
                                    send_message.setMessage("Invite");
                                    send_message.setObject(player_1);
                                    clientSocket2 = cred.getClient();
                                    ObjectOutputStream oos4 = new ObjectOutputStream(cred.getClient().getOutputStream());
                                    oos4.writeObject(send_message);
                                    break;
                                }
                            }
                            break;

                        case "Accept":
                            client1Restart = 0;
                            client2Restart = 0;
                            Player fromPlayer = (Player) rcv_message.getObject();
                            for(Online onl: onlineList){
                                if(onl.getPlayer().getId() == fromPlayer.getId()){
                                    send_message.setMessage("Accept");
                                    send_message.setObject(null);
                                    ObjectOutputStream oos5 = new ObjectOutputStream(onl.getClient().getOutputStream());
                                    oos5.writeObject(send_message);
                                    break;
                                }
                            }
                            break;
                       
                        case "Movement":
                            Turn t = (Turn) rcv_message.getObject();
                            send_message.setMessage("Next move");
                            send_message.setObject(t);
                            if (socket == clientSocket2){
                                if (t!= null) client2Move = t.getMyturn();
                                ObjectOutputStream oos7 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                oos7.writeObject(send_message);
                            }
                            else if (socket == clientSocket1){
                                if (t!= null) client1Move = t.getMyturn();
                                ObjectOutputStream oos8 = new ObjectOutputStream(clientSocket2.getOutputStream());
                                oos8.writeObject(send_message);
                            }
                            break;
                            
                        case "Lose":
                            if (socket == clientSocket1){
                                updatePoint(id2,id1,client2Move,client1Move);
                                send_message.setMessage("Win");
                                send_message.setObject(null);
                                ObjectOutputStream oos7 = new ObjectOutputStream(clientSocket2.getOutputStream());
                                oos7.writeObject(send_message);
                            }
                            else {
                                updatePoint(id1,id2,client1Move,client2Move);
                                send_message.setMessage("Win");
                                send_message.setObject(null);
                                ObjectOutputStream oos7 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                oos7.writeObject(send_message);
                            }
                            break;
                            
                        case "Restart yes":
                            if (socket == clientSocket1){
                                client1Restart = 1;
                            }
                            else if (socket == clientSocket2) {
                                client2Restart = 1;
                            }
                            if (client1Restart == client2Restart && client2Restart == 1){
                                client1Restart = 0;
                                client2Restart = 0;
                                send_message.setMessage("New game");
                                send_message.setObject(null);
                                ObjectOutputStream oos7 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                oos7.writeObject(send_message);
                                ObjectOutputStream oos8 = new ObjectOutputStream(clientSocket2.getOutputStream());
                                oos8.writeObject(send_message);
                            }
                            if (client1Restart != client2Restart && client1Restart != 0 && client2Restart != 0){
                                list = showPlayer();
                                send_message.setMessage("showPlayer");
                                send_message.setObject(list);
                                if (client1Restart == 1){
                                    oos1 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                    oos1.writeObject(send_message);
                                }
                                else if (client2Restart == 1){
                                    oos1 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                    oos1.writeObject(send_message);
                                }
                            }
                            break;
                            
                        case "Restart no":
                            list = showPlayer();
                            send_message.setMessage("showPlayer");
                            send_message.setObject(list);
                            oos1 = new ObjectOutputStream(socket.getOutputStream());
                            oos1.writeObject(send_message);
                            if (socket == clientSocket1){
                                list = showPlayer();
                                send_message.setMessage("showPlayer");
                                send_message.setObject(list);
                                oos3 = new ObjectOutputStream(clientSocket2.getOutputStream());
                                oos3.writeObject(send_message);
                            }
                            else if (socket == clientSocket2){
                                list = showPlayer();
                                send_message.setMessage("showPlayer");
                                send_message.setObject(list);
                                oos3 = new ObjectOutputStream(clientSocket1.getOutputStream());
                                oos3.writeObject(send_message);
                            }
                            break;
                            
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void updatePoint(int id1, int id2, int winMove, int loseMove) {
            int point,en_move,move,win,lose;
            String query = "update player "
                    + "set point = point+1, moveCount =" + winMove +",matchWin = matchWin+1 "
                    + "where id = "+id1+";";
            System.out.println(query);
            String query1 = "update player "
                    + "set moveCount =" + loseMove + ",matchLose = matchLose+1 "
                    + "where id = "+id2+";";
            System.out.println(query1);
            try {
                PreparedStatement ps = con.prepareStatement(query);
                int rowAffected = ps.executeUpdate();
                PreparedStatement ps1 = con.prepareStatement(query1);
                int rowAffected1 = ps1.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
