/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.client;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author dotav
 */
public class ChessBoard extends javax.swing.JFrame {
    public JFrame frame;
    public LinkedList<Piece> ps=new LinkedList<>();
    public Piece selectedPiece=null;
    public int colour;
    public ChessBoard(int color) throws IOException  {
        colour = color;
        BufferedImage all=ImageIO.read(new File("src\\chess.png"));
        Image imgs[]=new Image[12];
        int ind=0;
        for(int y=0;y<400;y+=200){
            for(int x=0;x<1200;x+=200){
                imgs[ind]=all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }    
        }
        if (color == 0){
            Piece brook=new Piece(0, 0, false, "rook", ps);
            Piece bkinght=new Piece(1, 0, false , "knight", ps);
            Piece bbishop=new Piece(2, 0, false , "bishop", ps);
            Piece bqueen=new Piece(4, 0, false , "queen", ps);
            Piece bking=new Piece(3, 0, false , "king", ps);
            Piece bbishop2=new Piece(5, 0, false , "bishop", ps);
            Piece bkight2=new Piece(6, 0, false, "knight", ps);
            Piece brook2=new Piece(7, 0, false, "rook", ps);
            Piece bpawn1=new Piece(1, 1, false, "pawn", ps);
            Piece bpawn2=new Piece(2, 1, false, "pawn", ps);
            Piece bpawn3=new Piece(3, 1, false, "pawn", ps);
            Piece bpawn4=new Piece(4, 1, false, "pawn", ps);
            Piece bpawn5=new Piece(5, 1, false, "pawn", ps);
            Piece bpawn6=new Piece(6, 1, false, "pawn", ps);
            Piece bpawn7=new Piece(7, 1, false, "pawn", ps);
            Piece bpawn8=new Piece(0, 1, false, "pawn", ps);

            Piece wrook=new Piece(0, 7, true, "rook", ps);
            Piece wkinght=new Piece(1,7, true, "knight", ps);
            Piece wbishop=new Piece(2,7, true, "bishop", ps);
            Piece wqueen=new Piece(4, 7, true, "queen", ps);
            Piece wking=new Piece(3, 7, true, "king", ps);
            Piece wbishop2=new Piece(5,7, true, "bishop", ps);
            Piece wkight2=new Piece(6, 7, true, "knight", ps);
            Piece wrook2=new Piece(7, 7, true, "rook", ps);
            Piece wpawn1=new Piece(1, 6, true, "pawn", ps);
            Piece wpawn2=new Piece(2, 6, true, "pawn", ps);
            Piece wpawn3=new Piece(3, 6, true, "pawn", ps);
            Piece wpawn4=new Piece(4, 6, true, "pawn", ps);
            Piece wpawn5=new Piece(5, 6, true, "pawn", ps);
            Piece wpawn6=new Piece(6, 6, true, "pawn", ps);
            Piece wpawn7=new Piece(7, 6, true, "pawn", ps);
            Piece wpawn8=new Piece(0, 6, true, "pawn", ps);
        }
        if (color == 1){
            Piece brook=new Piece(0, 0, true, "rook", ps);
            Piece bkinght=new Piece(1, 0, true , "knight", ps);
            Piece bbishop=new Piece(2, 0, true , "bishop", ps);
            Piece bqueen=new Piece(3, 0, true , "queen", ps);
            Piece bking=new Piece(4, 0, true , "king", ps);
            Piece bbishop2=new Piece(5, 0, true , "bishop", ps);
            Piece bkight2=new Piece(6, 0, true, "knight", ps);
            Piece brook2=new Piece(7, 0, true, "rook", ps);
            Piece bpawn1=new Piece(1, 1, true, "pawn", ps);
            Piece bpawn2=new Piece(2, 1, true, "pawn", ps);
            Piece bpawn3=new Piece(3, 1, true, "pawn", ps);
            Piece bpawn4=new Piece(4, 1, true, "pawn", ps);
            Piece bpawn5=new Piece(5, 1, true, "pawn", ps);
            Piece bpawn6=new Piece(6, 1, true, "pawn", ps);
            Piece bpawn7=new Piece(7, 1, true, "pawn", ps);
            Piece bpawn8=new Piece(0, 1, true, "pawn", ps);

            Piece wrook=new Piece(0, 7, false, "rook", ps);
            Piece wkinght=new Piece(1,7, false, "knight", ps);
            Piece wbishop=new Piece(2,7, false, "bishop", ps);
            Piece wqueen=new Piece(3, 7, false, "queen", ps);
            Piece wking=new Piece(4, 7, false, "king", ps);
            Piece wbishop2=new Piece(5,7, false, "bishop", ps);
            Piece wkight2=new Piece(6, 7, false, "knight", ps);
            Piece wrook2=new Piece(7, 7, false, "rook", ps);
            Piece wpawn1=new Piece(1, 6, false, "pawn", ps);
            Piece wpawn2=new Piece(2, 6, false, "pawn", ps);
            Piece wpawn3=new Piece(3, 6, false, "pawn", ps);
            Piece wpawn4=new Piece(4, 6, false, "pawn", ps);
            Piece wpawn5=new Piece(5, 6, false, "pawn", ps);
            Piece wpawn6=new Piece(6, 6, false, "pawn", ps);
            Piece wpawn7=new Piece(7, 6, false, "pawn", ps);
            Piece wpawn8=new Piece(0, 6, false, "pawn", ps);
            
            
        }
        frame = new JFrame();
        frame.setBounds(10, 10, 600, 600);
        //frame.setUndecorated(true);
        JPanel pn=new JPanel(){
            @Override
            public void paint(Graphics g) {
                boolean white=true;
                for(int y= 0;y<8;y++){
                    for(int x= 0;x<8;x++){
                        if(white){
                            g.setColor(new Color(235,235, 208));
                        }else{
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x*64, y*64, 64, 64);
                        white=!white;
                    }
                    white=!white;
                }
                for(Piece p: ps){
                    int ind=0;
                    if(p.name.equalsIgnoreCase("king")){
                        ind=0;
                    }
                    if(p.name.equalsIgnoreCase("queen")){
                        ind=1;
                    }
                    if(p.name.equalsIgnoreCase("bishop")){
                        ind=2;
                    }
                    if(p.name.equalsIgnoreCase("knight")){
                        ind=3;
                    }
                    if(p.name.equalsIgnoreCase("rook")){
                        ind=4;
                    }
                    if(p.name.equalsIgnoreCase("pawn")){
                        ind=5;
                    }
                    if(!p.isWhite){
                        ind+=6;
                    }
                    g.drawImage(imgs[ind], p.x, p.y, this);
                }
            } 
        };
        frame.add(pn);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
    
    public void addGameListener(MouseMotionListener log, MouseListener logg) {
            frame.addMouseMotionListener(log);
            frame.addMouseListener(logg);
    }
    
    public void removeGameListener(){
        for (MouseMotionListener al : frame.getMouseMotionListeners()) {
            frame.removeMouseMotionListener(al);
        }
        for (MouseListener al : frame.getMouseListeners()) {
            frame.removeMouseListener(al);
        }
    }
    
    public Piece getPiece(int x,int y){
        int xp=x/64;
        int yp=y/64;
        for(Piece p: ps){
            if(p.xp==xp&&p.yp==yp){
                return p;
            }
        }
        return null;
    }

    public boolean checkMate(ChessBoard b,int color){
        int xWK,yWK,xBK,yBK;
        if (color == 0){
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == true){
                    xWK = p.xp;
                    yWK = p.yp;
                    if (checkQueenandBishop(b,xWK,yWK,color) || checkQueenandRook(b,xWK,yWK,color)
                    || checkKnight(b,xWK,yWK,color) == true || (checkPawn(b,xWK,yWK,color) == true)) return true;
                }
            }
        }
        else {
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == false){
                    xBK = p.xp;
                    yBK = p.yp;
                    if (checkQueenandBishop(b,xBK,yBK,color) || checkQueenandRook(b,xBK,yBK,color) == true
                    || (checkPawn(b,xBK,yBK,color) == true) || checkKnight(b,xBK,yBK,color) == true) return true;
                }
            }
        }
        return false;
    }
    
    public boolean checkPawn(ChessBoard b,int x, int y, int color){
        if ((b.getPiece((x+1)*64,(y-1)*64)!= null && b.getPiece((x+1)*64,(y-1)*64).name == "pawn")){
            if (b.getPiece((x+1)*64,(y-1)*64).isWhite == false && color == 0) return true;
            else if (b.getPiece((x+1)*64,(y-1)*64).isWhite == true && color == 1) return true;
        }
        if (b.getPiece((x-1)*64,(y-1)*64)!= null && b.getPiece((x-1)*64,(y-1)*64).name == "pawn" ){
            if (b.getPiece((x-1)*64,(y-1)*64).isWhite == false && color == 0) return true;
            else if (b.getPiece((x-1)*64,(y-1)*64).isWhite == true && color == 1) return true;
        }
        return false;
    }
    
    public boolean checkQueenandBishop(ChessBoard b, int x, int y, int color){
        int x1 = x,y1 = y;
        while (x1 > 0) {
            x1--;
            y1--;
            if (y1 < 0) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (x1 > 0) {
            x1--;
            y1++;
            if (y1 > 7) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (x1 < 7) {
            x1++;
            y1++;
            if (y1 > 7) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (x1 < 7) {
            x1++;
            y1--;
            if (y1 < 0) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (y1 < 7) {
            y1--;
            x1++;
            if (x1 > 7) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (y1 < 7) {
            y1--;
            x1--;
            if (x1 < 0) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (y1 > 0) {
            y1--;
            x1++;
            if (x1 > 7) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        y1 = y;
        while (y1 > 0) {
            y1--;
            x1--;
            if (x1 < 0) break;
            Piece p = b.getPiece(x1*64,y1*64);
            if (p != null){
                if (p.name == "queen" || p.name == "bishop") {
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        } 
        return false;
    }
    
    public boolean checkKnight(ChessBoard b, int x, int y, int color){
        if (color == 1){
            if (b.getPiece((x+1)*64,(y+2)*64)!= null && b.getPiece((x+1)*64,(y+2)*64).name == "knight" && b.getPiece((x+1)*64,(y+2)*64).isWhite == true) return true;
            if (b.getPiece((x+1)*64,(y-2)*64)!= null && b.getPiece((x+1)*64,(y-2)*64).name == "knight" && b.getPiece((x+1)*64,(y-2)*64).isWhite == true) return true;
            if (b.getPiece((x-1)*64,(y+2)*64)!= null && b.getPiece((x-1)*64,(y+2)*64).name == "knight" && b.getPiece((x-1)*64,(y+2)*64).isWhite == true) return true;
            if (b.getPiece((x-1)*64,(y-2)*64)!= null && b.getPiece((x-1)*64,(y-2)*64).name == "knight" && b.getPiece((x-1)*64,(y-2)*64).isWhite == true) return true;
            if (b.getPiece((x+2)*64,(y+1)*64)!= null && b.getPiece((x+2)*64,(y+1)*64).name == "knight" && b.getPiece((x+2)*64,(y+1)*64).isWhite == true) return true;
            if (b.getPiece((x+2)*64,(y-1)*64)!= null && b.getPiece((x+2)*64,(y-1)*64).name == "knight" && b.getPiece((x+2)*64,(y-1)*64).isWhite == true) return true;
            if (b.getPiece((x-2)*64,(y+1)*64)!= null && b.getPiece((x-2)*64,(y+1)*64).name == "knight" && b.getPiece((x-2)*64,(y+1)*64).isWhite == true) return true;
            if (b.getPiece((x-2)*64,(y-1)*64)!= null && b.getPiece((x-2)*64,(y-1)*64).name == "knight" && b.getPiece((x-2)*64,(y-1)*64).isWhite == true) return true;
        }
        else {
            if (b.getPiece((x+1)*64,(y+2)*64)!= null && b.getPiece((x+1)*64,(y+2)*64).name == "knight" && b.getPiece((x+1)*64,(y+2)*64).isWhite == false) return true;
            if (b.getPiece((x+1)*64,(y-2)*64)!= null && b.getPiece((x+1)*64,(y-2)*64).name == "knight" && b.getPiece((x+1)*64,(y-2)*64).isWhite == false) return true;
            if (b.getPiece((x-1)*64,(y+2)*64)!= null && b.getPiece((x-1)*64,(y+2)*64).name == "knight" && b.getPiece((x-1)*64,(y+2)*64).isWhite == false) return true;
            if (b.getPiece((x-1)*64,(y-2)*64)!= null && b.getPiece((x-1)*64,(y-2)*64).name == "knight" && b.getPiece((x-1)*64,(y-2)*64).isWhite == false) return true;
            if (b.getPiece((x+2)*64,(y+1)*64)!= null && b.getPiece((x+2)*64,(y+1)*64).name == "knight" && b.getPiece((x+2)*64,(y+1)*64).isWhite == false) return true;
            if (b.getPiece((x+2)*64,(y-1)*64)!= null && b.getPiece((x+2)*64,(y-1)*64).name == "knight" && b.getPiece((x+2)*64,(y-1)*64).isWhite == false) return true;
            if (b.getPiece((x-2)*64,(y+1)*64)!= null && b.getPiece((x-2)*64,(y+1)*64).name == "knight" && b.getPiece((x-2)*64,(y+1)*64).isWhite == false) return true;
            if (b.getPiece((x-2)*64,(y-1)*64)!= null && b.getPiece((x-2)*64,(y-1)*64).name == "knight" && b.getPiece((x-2)*64,(y-1)*64).isWhite == false) return true;
        }
        return false;
    }
    
    public boolean checkQueenandRook(ChessBoard b, int x, int y, int color){
        int y1 = y;
        while (y1 > 0){
            y1--;
            Piece p = b.getPiece(x*64,y1*64);
            if (p!= null){
                if (p.name == "rook" || p.name == "queen"){
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        y1 = y;
        while (y1 < 7){
            y1++;
            Piece p = b.getPiece(x*64,y1*64);
            if (p!= null){
                if (p.name == "rook" || p.name == "queen"){
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        int x1 = x;
        while (x1 > 0){
            x1--;
            Piece p = b.getPiece(x1*64,y*64);
            if (p!= null){
                if (p.name == "rook" || p.name == "queen"){
                    if (p.isWhite == false  && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        x1 = x;
        while (x1 < 7){
            x1++;
            Piece p = b.getPiece(x1*64,y*64);
            if (p!= null){
                if (p.name == "rook" || p.name == "queen"){
                    if (p.isWhite == false && color == 0) return true;
                    else if (p.isWhite == true && color == 1) return true;
                }
                else break;
            }
        }
        return false;
    }
    
    public boolean isEnd(ChessBoard b, int color){
        int xWK,yWK,xBK,yBK;
        if (color == 0){
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == true){
                    xWK = p.xp;
                    yWK = p.yp;
                    if (cantMove(b,xWK,yWK,xWK-1,yWK+1,color) && cantMove(b,xWK,yWK,xWK,yWK+1,color) &&
                        cantMove(b,xWK,yWK,xWK+1,yWK+1,color) && cantMove(b,xWK,yWK,xWK+1,yWK,color) &&
                        cantMove(b,xWK,yWK,xWK+1,yWK-1,color) && cantMove(b,xWK,yWK,xWK,yWK-1,color) &&
                        cantMove(b,xWK,yWK,xWK-1,yWK-1,color) && cantMove(b,xWK,yWK,xWK-1,yWK,color)) return true;
                }
            }
        }
        else {
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == false){
                    xBK = p.xp;
                    yBK = p.yp;
                    if (cantMove(b,xBK,yBK,xBK-1,yBK+1,color) && cantMove(b,xBK,yBK,xBK,yBK+1,color) &&
                        cantMove(b,xBK,yBK,xBK+1,yBK+1,color) && cantMove(b,xBK,yBK,xBK+1,yBK,color) &&
                        cantMove(b,xBK,yBK,xBK+1,yBK-1,color) && cantMove(b,xBK,yBK,xBK,yBK-1,color) &&
                        cantMove(b,xBK,yBK,xBK-1,yBK-1,color) && cantMove(b,xBK,yBK,xBK-1,yBK,color)) return true;
                }
            }
        }
        return false;
    }
    
    public boolean cantMove(ChessBoard b,int O_x, int O_y, int x, int y, int color){
        Piece p = b.getPiece(O_x*64,O_y*64);
        boolean ok;
        if (x>= 0 && y >= 0 && x < 8 && y <8){
            p.xp = x;
            p.yp = y;
            p.x = x*64;
            p.y = y*64;
            if (checkMate(b,color) || getAround(b,x,y,color)) ok = true;
            else ok = false;
            p.xp = O_x;
            p.yp = O_y;
            p.x = O_x*64;
            p.y = O_y*64;
        } else ok = false;
        return ok;
    }
    
    public boolean getAround(ChessBoard b,int x, int y, int color){
        if (color == 0){
            if (b.getPiece(x-1, y+1) != null && b.getPiece(x-1, y+1).name == "king" && b.getPiece(x-1,y+1).isWhite == false) return true;
            if (b.getPiece(x, y+1) != null && b.getPiece(x, y+1).name == "king" && b.getPiece(x, y+1).isWhite == false) return true;
            if (b.getPiece(x+1, y+1) != null && b.getPiece(x+1, y+1).name == "king" && b.getPiece(x+1, y+1).isWhite==false) return true;
            if (b.getPiece(x+1, y) != null && b.getPiece(x+1, y).name == "king"&& b.getPiece(x+1, y).isWhite == false) return true;
            if (b.getPiece(x+1, y-1) != null && b.getPiece(x+1, y-1).name == "king"&& b.getPiece(x+1, y-1).isWhite == false) return true;
            if (b.getPiece(x, y-1) != null && b.getPiece(x, y-1).name == "king"&& b.getPiece(x, y-1).isWhite == false) return true;
            if (b.getPiece(x-1, y-1) != null && b.getPiece(x-1, y-1).name == "king" && b.getPiece(x-1, y-1).isWhite == false) return true;
            if (b.getPiece(x-1, y) != null && b.getPiece(x-1, y).name == "king" && b.getPiece(x-1, y).isWhite == false) return true;
        }
        else {
            if (b.getPiece(x-1, y+1) != null && b.getPiece(x-1, y+1).name == "king" && b.getPiece(x-1,y+1).isWhite == true) return true;
            if (b.getPiece(x, y+1) != null && b.getPiece(x, y+1).name == "king" && b.getPiece(x, y+1).isWhite == true) return true;
            if (b.getPiece(x+1, y+1) != null && b.getPiece(x+1, y+1).name == "king" && b.getPiece(x+1, y+1).isWhite==true) return true;
            if (b.getPiece(x+1, y) != null && b.getPiece(x+1, y).name == "king"&& b.getPiece(x+1, y).isWhite == true) return true;
            if (b.getPiece(x+1, y-1) != null && b.getPiece(x+1, y-1).name == "king"&& b.getPiece(x+1, y-1).isWhite == true) return true;
            if (b.getPiece(x, y-1) != null && b.getPiece(x, y-1).name == "king"&& b.getPiece(x, y-1).isWhite == true) return true;
            if (b.getPiece(x-1, y-1) != null && b.getPiece(x-1, y-1).name == "king" && b.getPiece(x-1, y-1).isWhite == true) return true;
            if (b.getPiece(x-1, y) != null && b.getPiece(x-1, y).name == "king" && b.getPiece(x-1, y).isWhite == true) return true;
        }
        return false;
    }
    
    public boolean isKingLost(ChessBoard b, int color){
        if (color == 0){
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == true){
                    return false;
                }
            }
        }
        else {
            for (Piece p: ps){
                if (p.name == "king" && p.isWhite == false){
                    return false;
                }
            }
        }
        return true;
    }
}

