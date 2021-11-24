package com.jetbrains;

public class TicMove {
    private int pos, player;

    public TicMove(int pos, int player){
        this.pos = pos;
        this.player = player;
    }
    public int getPos(){
        return pos;
    }
    public int getPlayer(){
        return player;
    }
    public void setPos(int pos){
        this.pos = pos;
    }
}
