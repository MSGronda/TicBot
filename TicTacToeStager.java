package com.jetbrains;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.jetbrains.TicBoard.NO_WIN;

// una chanchada esto
public class TicTacToeStager {
    private TicBoard board = new TicBoard();
    private TicBot bot = new TicBot(board);
    private boolean player = false;
    private static final boolean CROSS = false, CIRC = true;
    private static final String initialMsg = "Numbering:\n 0 | 1 | 2\n-----------\n 3 | 4 | 5\n-----------\n 6 | 7 | 8\n\n\n";

    public void play(){
        System.out.println(initialMsg);
        int win = board.checkIfWin();

        while(win==NO_WIN && board.getAmountMovesLeft()!=0){
            if(player==CROSS){
                System.out.println("\nCROSS plays\n");
                System.out.println(board);
                board.move(getPlayerMove(), TicBoard.CROSS);
            }
            else{
                System.out.println("\nCIRCLE plays\n");
                System.out.println(board);
                board.move(bot.move(), TicBoard.CIRC);
            }
            player = !player;
            win = board.checkIfWin();
        }
        System.out.println(board);
        switch(win){
            case TicBoard.CIRC -> System.out.println("\nCIRCLE wins!");
            case TicBoard.CROSS -> System.out.println("\nCROSS wins!");
            default -> System.out.println("\nDraw!");
        }
    }


    private int getPlayerMove(){                //Every day we stray further from god
        String s = "\nEnter valid position: ";
        boolean isValid = false;
        int pos = 0;
        Scanner in;
        while(!isValid){
            System.out.println(s);
            try {
                in = new Scanner(System.in);
                pos = in.nextInt();
                if(board.checkValidMove(pos)){
                    isValid = true;
                }
            }
            catch(InputMismatchException ignored){}
        }
        return pos;
    }
}
