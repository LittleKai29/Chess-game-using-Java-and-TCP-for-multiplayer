/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.client;

import java.util.LinkedList;

/**
 *
 * @author Khouiled
 */
public class Piece {
    public int xp;
    public int yp;
    public int x;
    public int y;
    public boolean isWhite;
    LinkedList<Piece> ps;
    public String name;
    public Piece(int xp, int yp, boolean isWhite,String n, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        x=xp*64;
        y=yp*64;
        this.isWhite = isWhite;
        this.ps=ps;
        name=n;
        ps.add(this);
    }
    
    public void move(ChessBoard b,int xp,int yp){
        Piece p = b.getPiece(xp*64, yp*64);
        if (canGo(b,xp,yp) == true) {
            if (p == null && checkBarrier(b,this.xp,this.yp,xp,yp)==true){
                this.xp=xp;
                this.yp=yp;
                x=xp*64;
                y=yp*64;
            }
            //System.out.println("Wellcome");
            else if (p!= null && checkBarrier(b,this.xp,this.yp,xp,yp) == true) {
                if(p.isWhite!=isWhite){
                    p.kill();
                    this.xp=xp;
                    this.yp=yp;
                    x=xp*64;
                    y=yp*64;
                }else{
                    x=this.xp*64;
                    y=this.yp*64;
                }
            }
            else if (checkBarrier(b,this.xp,this.yp,xp,yp) == false){
                x=this.xp*64;
                y=this.yp*64;
            }
        }
        else{
            x=this.xp*64;
            y=this.yp*64;
        }
    }
      
    public boolean checkBarrier(ChessBoard b, int x1, int y1, int x2, int y2){
        Piece p = b.getPiece(x2*64, y2*64);
        if (name == "king"){
            return true;
        }
        else if (name == "queen"){
            if (x1 == x2){
                for (int i = Math.min(y1,y2)+1; i <= Math.max(y1, y2)-1; i++)
                    if (b.getPiece(x1*64,i*64) != null ) return false;
                return true;
            }
            else if (y1 == y2){
                for (int i = Math.min(x1,x2)+1; i <= Math.max(x1, x2)-1; i++)
                    if (b.getPiece(i*64,y1*64) != null ) return false;
                return true;
            }
            else if (Math.abs(x1-x2)==Math.abs(y1-y2)){
                if (y1 < y2){
                    if (x1 < x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt < x2-1){
                            xt++;
                            yt++;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                    else if (x1 > x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt > x2+1){
                            xt--;
                            yt++;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                }
                else if (y1>y2){
                    if (x1 < x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt < x2-1){
                            xt++;
                            yt--;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                    else if (x1 > x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt > x2+1){
                            xt--;
                            yt--;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                }
            }
        }
        else if (name == "rook"){
            if (x1 == x2){
                for (int i = Math.min(y1,y2)+1; i <= Math.max(y1, y2)-1; i++)
                    if (b.getPiece(x1*64,i*64) != null ) return false;
                return true;
            }
            else if (y1 == y2){
                for (int i = Math.min(x1,x2)+1; i <= Math.max(x1, x2)-1; i++)
                    if (b.getPiece(i*64,y1*64) != null ) return false;
                return true;
            }
        }
        else if (name == "pawn"){
            if (x1 == x2){
                if ((y1 == 1 || y1 == 6) && (Math.abs(y1 - y2) == 2)){
                    if (y1 < y2){
                        if (b.getPiece(x1*64,(y1+1)*64) == null) return true;
                        return false;
                    }
                    else {
                        if (b.getPiece(x1*64,(y1-1)*64) == null) return true;
                        return false;
                    }
                }
                else if (p == null) return true;
            }
            else if (x1!= x2 && p != null) return true;
        }
        else if (name == "bishop"){
            if (y1 < y2){
                    if (x1 < x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt < x2-1){
                            xt++;
                            yt++;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                    else if (x1 > x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt > x2+1){
                            xt--;
                            yt++;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                }
                else if (y1>y2){
                    if (x1 < x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt < x2-1){
                            xt++;
                            yt--;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                    else if (x1 > x2){
                        int xt = x1;
                        int yt = y1;
                        while (xt > x2+1){
                            xt--;
                            yt--;
                            if (b.getPiece(xt*64,yt*64) != null ) return false;
                        }
                        return true;
                    }
                }
        }
        else if (name == "knight"){
            return true;
        }
        return false;
    }
    
    public boolean canGo(ChessBoard b, int xp, int yp){
        if (this.name == "king" ){
            if ( Math.abs(xp - this.xp) <= 1 && Math.abs(yp- this.yp) <= 1) return true;
        }
        else if (this.name == "queen"){
            if (( xp == this.xp) || ( yp == this.yp ) || ( Math.abs(xp-this.xp) == Math.abs(yp-this.yp)) ) return true;
        }
        else if (this.name == "rook"){
            if (( xp == this.xp) || ( yp == this.yp )) return true;
        }
        else if (this.name == "pawn"){
            if ((this.yp == 1 || this.yp == 6) && (Math.abs(this.yp - yp) <= 2) && (xp == this.xp) ) return true;
            else if (b.colour == 0){
                if (this.isWhite == true && (this.yp - yp == 1) && (Math.abs(xp-this.xp) <= 1)) return true;
                else if (this.isWhite == false && (yp - this.yp == 1) && (Math.abs(xp-this.xp) <= 1)) return true;
            }
            else if (b.colour == 1){
                if (this.isWhite == false && (this.yp - yp == 1) && (Math.abs(xp-this.xp) <= 1)) return true;
                else if (this.isWhite == true && (yp - this.yp == 1) && (Math.abs(xp-this.xp) <= 1)) return true;
            }
        }
        else if (this.name == "bishop"){
            if ( Math.abs(xp-this.xp) == Math.abs(yp-this.yp)) return true;
        }
        else if (this.name == "knight"){
            if ( ( Math.abs(xp-this.xp)==1 && Math.abs(yp-this.yp) == 2) || ( Math.abs(xp-this.xp)==2 && Math.abs(yp-this.yp) == 1)) return true;
        }
        return false;
    }
    
    public void kill(){
        ps.remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getYp() {
        return yp;
    }

    public void setYp(int yp) {
        this.yp = yp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isIsWhite() {
        return isWhite;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public LinkedList<Piece> getPs() {
        return ps;
    }

    public void setPs(LinkedList<Piece> ps) {
        this.ps = ps;
    }

    public boolean isAvailableMove(ChessBoard b,int xp,int yp){
        Piece p = b.getPiece(xp*64, yp*64);
        if (canGo(b,xp,yp) == true) {
            if (p == null && checkBarrier(b,this.xp,this.yp,xp,yp)==true){
                return true;
            }
            //System.out.println("Wellcome");
            else if (p!= null && checkBarrier(b,this.xp,this.yp,xp,yp) == true) {
                if(p.isWhite!=isWhite){
                    return true;
                }else{
                    return false;
                }
            }
            else if (checkBarrier(b,this.xp,this.yp,xp,yp) == false){
                return false;
            }
        }
        else{
            return false;
        }
        return false;
    }
    
}
