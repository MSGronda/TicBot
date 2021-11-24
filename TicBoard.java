package com.jetbrains;

import java.util.Arrays;

public class TicBoard {
    private static final int BOARD_DIM = 9, EMPTY=0, BOARD_LEN = 3;
    public static final int NO_WIN=0, CROSS = 1, CIRC = 4;                   // did i do good??
    private int[] board = new int[BOARD_DIM];
    private Stack<TicMove> moves = new Stack<>();
    private int movesLeft=9;


    /*-----------GAME-------------*/
    public void move(int pos, int sign){
        if(sign == CROSS || sign == CIRC){
            if(checkValidMove(pos)) {
                board[pos] = sign;
                moves.push(new TicMove(pos, sign));
                movesLeft--;
            }
        }
    }
    public void undoMove(){
        if(!moves.isEmpty()){
            TicMove old = moves.pop();
            board[old.getPos()] = EMPTY;
            movesLeft++;
        }
    }

    /*----------VALIDATION------------*/
    public int checkIfWin(){
        return Math.max(Math.max(checkLinear(1),checkLinear(3)),
                Math.max(checkDiagonal(2,2),checkDiagonal(0,4)));
    }
    public boolean checkValidMove(int pos){
        if(pos >= 0 && pos < BOARD_DIM){
            return board[pos] == EMPTY;
        }
        return false;
    }

    // Start = 0, inc = 4  -  Start = 2, inc = 2
    private int checkDiagonal(int start, int inc){
        int sum =0;
        for(int i=start; i <= start + 2 * inc; i+=inc){
            sum += board[i];
        }
        return sumIsWin(sum);
    }
    // Inc = 1 => diagonal  -   Inc = 3 => horizontal
    private int checkLinear(int inc){
        int sum = 0, res, notInc = inc==1?3:1;
        for(int i=0; i<BOARD_LEN * notInc; i += notInc, sum=0){
            for(int j=0; j<BOARD_LEN * inc; j += inc) {
                sum += board[i + j];
            }
            res = sumIsWin(sum);
            if(res!=NO_WIN){
                return res;
            }
        }
        return NO_WIN;
    }
    private int sumIsWin(int sum){
        return switch (sum) {
            case CROSS * BOARD_LEN -> CROSS;
            case CIRC * BOARD_LEN -> CIRC;
            default -> NO_WIN;
        };
    }


    /*------------GETTERS--------------*/
    public int[] getBoard(){
        return board;
    }
    public int[] getLegalMoves(){
        int[] resp = new int[BOARD_DIM];
        int size=0;
        for(int i=0; i<BOARD_DIM; i++){
            if(board[i]==EMPTY){
                resp[size++] = i;
            }
        }
        return Arrays.copyOf(resp, size);
    }
    public int getAmountMovesLeft(){
        return movesLeft;
    }

    /*------------DISPLAY-------------*/
    private char toChar(int i){
        return switch (i) {
            case CROSS -> 'x';
            case CIRC -> 'o';
            default -> ' ';
        };
    }
    @Override
    public String toString(){
        StringBuilder resp = new StringBuilder();
        int h = 0;
        for(int i=0; i<BOARD_LEN; i++){
            for(int j=0; j<BOARD_LEN; j++, h++){
                resp.append(toChar(board[h]));
                if(j<BOARD_LEN-1){
                    resp.append(" | ");
                }
            }
            resp.append('\n');
            if(i<BOARD_LEN-1){
                resp.append("---------\n");
            }
        }
        resp.append("\nPossible moves: ").append(Arrays.toString(getLegalMoves()));
        return resp.toString();
    }
}
