/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.player.Message;
import model.player.Online;
import model.player.Player;
import model.player.Turn;
import view.client.ChessBoard;
import view.client.GameView;
import view.client.HomeView;
import view.client.LoginView;
import view.client.Piece;

/**
 *
 * @author LENOVO
 */
public class ClientControl {

    private LoginView loginView;
    private HomeView homeView;
    private GameView gameView;
    private ChessBoard b;
    private Socket clientSocket;
    private String serverHost = "localhost";
    private int serverPort = 5555;
    private int isMyTurn;
    private Turn t;
    private int confirm;
    private String name1,name2;
    private ArrayList<Player> onlineList = new ArrayList<>();

    public ClientControl() {
        openConnection();
        ClientThread clientThread = new ClientThread(clientSocket);
        clientThread.start();
        this.loginView = new LoginView();
        loginView.setVisible(true);
        loginView.addLoginListener(new LoginListener());

    }
//Action o HomeView

    class HomeListener implements ActionListener {

        public HomeListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(homeView.getBtnRefresh())) {
                try {
                    Message sendMsg = new Message();
                    sendMsg.setMessage("refreshPlayer");
                    sendMsg.setObject(null);
                    sendData(sendMsg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (e.getSource().equals(homeView.getBtnInvite())) {
                try {
                    Player player = homeView.getPlayerInvite();
                    Message sendMsg = new Message();
                    sendMsg.setMessage("Invite");
                    sendMsg.setObject(player);
                    sendData(sendMsg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
// Action o LoginView

    class LoginListener implements ActionListener {

        public LoginListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Player player = loginView.getPlayer();
                Message sendMsg = new Message();
                sendMsg.setMessage("Login");
                sendMsg.setObject(player);
                sendData(sendMsg);
            } catch (Exception ex) {
                loginView.showMessage(ex.getStackTrace().toString());
            }
        }
    }
    
// Where is it ? Is it where ?
    class ClickGameListener implements MouseMotionListener {
        public ClickGameListener(){
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if(b.selectedPiece!=null){
                    b.selectedPiece.x=e.getX()-32;
                    b.selectedPiece.y=e.getY()-32;
                    b.frame.repaint();
                }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        
    }
    class MoveGameListener implements MouseListener{
        public MoveGameListener(){
            
        }
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            b.selectedPiece= b.getPiece(e.getX(), e.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            /*ObjectInputStream ois
                    = new ObjectInputStream(clientSocket.getInputStream());
            Message rcv_message = (Message) ois.readObject();
            if (rcv_message.getMessage() == "Next move"){*/
            //System.out.println("Day la mot luot");
            /*Piece c = b.getPiece(5,0);
            c.x = 5*64;
            c.y = 4*64;
            b.frame.repaint();
            if (c == null) System.out.println("Null ne");
            else System.out.println(c.name);*/
            //c.move(b,4,4);
            if (b.selectedPiece.isAvailableMove(b,e.getX()/64, e.getY()/64) == true && b.selectedPiece.isWhite == (b.colour == 0)){
                    isMyTurn++;
                    t = new Turn(isMyTurn,b.selectedPiece.xp,b.selectedPiece.yp,e.getX()/64, e.getY()/64);
                    b.selectedPiece.move(b,e.getX()/64, e.getY()/64);
                    b.frame.repaint();
                    confirm = 1;
                    b.removeGameListener();
                    Message send_message = new Message();
                    send_message.setMessage("Movement");
                    send_message.setObject(t);
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(clientSocket.getOutputStream());
                    oos2.writeObject(send_message);
                } catch (IOException ex) {
                    Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                                
                    //System.exit(0);
                } else{
                    b.selectedPiece.x = b.selectedPiece.xp * 64;
                    b.selectedPiece.y = b.selectedPiece.yp * 64;
                    b.frame.repaint();
                }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
//Open Connection

    public Socket openConnection() {
        try {
            clientSocket = new Socket(serverHost, serverPort);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientSocket;
    }

    public boolean sendData(Message message) {
        try {
            ObjectOutputStream oos
                    = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(message);
            System.out.println("Send message : " + message.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    class ClientThread extends Thread {

        private Socket socket;
        private Player player;

        public ClientThread(Socket serverSocket) {
            this.socket = serverSocket;
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
                            if (rcv_message.getObject() != null) {

                                this.player = (Player) rcv_message.getObject();
                                loginView.dispose();
                                ObjectOutputStream oos
                                        = new ObjectOutputStream(socket.getOutputStream());

                                send_message.setMessage("showPlayer");
                                send_message.setObject(null);
                                oos.writeObject(send_message);
                            } else {
                                loginView.showMessage("Wrong username or password !!!");
                            }
                            break;

                        case "showPlayer":
                            if (rcv_message.getObject() != null) {
                                if (b!= null) b.frame.dispose();
                                ArrayList<Player> list = (ArrayList<Player>) rcv_message.getObject();
                                onlineList = list;
                                homeView = new HomeView(player, list);
                                homeView.addActionListener(new HomeListener());
                                homeView.setVisible(true);
                            }
                            break;
                        case "refreshPlayer":
                            if (homeView.isVisible()) {
                                ArrayList<Player> list = (ArrayList<Player>) rcv_message.getObject();
                                homeView.setListOnline(list);
                                homeView.showOnline();
                            }
                            break;
                        case "Invite":
                            Player player_1 = (Player) rcv_message.getObject();
                            if (homeView.confirmInvite(player_1)) {
                                homeView.dispose();
                                b = new ChessBoard(0);
                                isMyTurn = 0;
                                //name1 = player_1.getUsername();
                                //b.frame.setTitle("Player 2");
                                //b.addGameListener(new ClickGameListener(), new MoveGameListener());
                                send_message.setMessage("Accept");
                                send_message.setObject(player_1);
                                ObjectOutputStream oos1
                                        = new ObjectOutputStream(socket.getOutputStream());
                                oos1.writeObject(send_message);
                            }
                            break;

                        case "Accept":
                            homeView.dispose();
                            b = new ChessBoard(1);
                            isMyTurn = 0;
                            //b.frame.setTitle("Player 1");
                            send_message.setMessage("Movement");
                            send_message.setObject(null);
                            ObjectOutputStream oos1
                                        = new ObjectOutputStream(socket.getOutputStream());
                            oos1.writeObject(send_message);
                            break;
                            
                        case "Next move":
                            t = (Turn) rcv_message.getObject();
                            if (t != null){
                                Piece p = b.getPiece((7-t.getOld_x())*64,(7-t.getOld_y())*64);
                                p.move(b,(7-t.getNew_x()),(7-t.getNew_y()));
                                b.frame.repaint();
                                if (b.checkMate(b,b.colour)==true) 
                                    JOptionPane.showMessageDialog(b.frame, "Your king is in danger","Check mate",JOptionPane.WARNING_MESSAGE);
                            }
                            if (b.isKingLost(b, b.colour)){
                                JOptionPane.showMessageDialog(b.frame, "You lose");
                                send_message.setMessage("Lose");
                                send_message.setObject(null);
                                ObjectOutputStream oos2
                                        = new ObjectOutputStream(socket.getOutputStream());
                                oos2.writeObject(send_message);
                                int res = JOptionPane.showConfirmDialog(b.frame,"Continue?","A Silly Question",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if (res == JOptionPane.YES_OPTION){
                                    send_message.setMessage("Restart yes");
                                    send_message.setObject(null);
                                    ObjectOutputStream oos3
                                        = new ObjectOutputStream(socket.getOutputStream());
                                    oos3.writeObject(send_message);
                                }
                                else {
                                    send_message.setMessage("Restart no");
                                    send_message.setObject(null);
                                    ObjectOutputStream oos4
                                        = new ObjectOutputStream(socket.getOutputStream());
                                    oos4.writeObject(send_message);
                                    b.frame.dispose();
                                }
                            } else
                            b.addGameListener(new ClickGameListener(), new MoveGameListener());
                            break;
                            
                        case "Win":
                            JOptionPane.showMessageDialog(b.frame, "Victory");
                            int res = JOptionPane.showConfirmDialog(b.frame,"Continue?","A Silly Question",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if (res == JOptionPane.YES_OPTION){
                                send_message.setMessage("Restart yes");
                                send_message.setObject(null);
                                ObjectOutputStream oos2
                                        = new ObjectOutputStream(socket.getOutputStream());
                                oos2.writeObject(send_message);
                            }
                            else {
                                send_message.setMessage("Restart no");
                                send_message.setObject(null);
                                ObjectOutputStream oos2
                                        = new ObjectOutputStream(socket.getOutputStream());
                                oos2.writeObject(send_message);
                                b.frame.dispose();
                            }
                            break;
                        
                        case "New game":
                            b.frame.dispose();
                            b = new ChessBoard(1-b.colour);
                            if (b.colour == 1){
                                send_message.setMessage("Movement");
                                send_message.setObject(null);
                                oos1= new ObjectOutputStream(socket.getOutputStream());
                                oos1.writeObject(send_message);
                            }
                            break;
                        default:
                            System.out.println(rcv_message.getMessage());
                            break;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
