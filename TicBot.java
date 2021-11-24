package com.jetbrains;
/*
*       Made by: MSGronda - Marcos Gronda
*       Related uni class: Programacion Orientada a Objetos (OOP)
 */

/*
*   Evaluate first, then move strategy.
*   The first move and the subsequent moves
*   will be judged differently.
*/

/*
 *      Numbering:
 *      0 | 1 | 2
 *     -----------
 *      3 | 4 | 5
 *     -----------
 *      6 | 7 | 8
 */

public class TicBot {
    private static final int BOARD_DIM = 9, BOARD_LEN = 3, EMPTY=0, CIRC = 4;                       //Board data
    private static final int WIN = 1000, DEFEND = 500,D_UP =50, P_DEFEND = 25, S_VAL =10, NO_VAL=0; //Move values
    private static final int CENTRE = 4, TL_COR = 0, TR_COR = 2;                                    //Board positions
    private static final int COLUMN = 3, ROW = 1, D1_INC = 2, D2_INC = 4;                           //Increases

    private TicBoard board;

    public TicBot(TicBoard board){
        this.board = board;
    }

    public int move(){
        if(board.getAmountMovesLeft()==0){
            throw new IllegalStateException();
        }
        //First move
        if(board.getAmountMovesLeft()==8){
            return firstMove();
        }
        return evaluate();
    }


    /*----------EVALUATORS--------*/

    // it'll just pick the last, best move
    private int evaluate(){
        int[] localBoard = board.getBoard();
        MoveValuePair move = new MoveValuePair(NO_VAL, 0, CIRC);

        //checks columns for moves
        for(int i=0; i<BOARD_LEN; i++){
            checkLinear(i, COLUMN, move, localBoard);
        }
        //checks rows for moves
        for(int i=0; i<BOARD_DIM; i+=BOARD_LEN){
            checkLinear(i,ROW, move, localBoard);
        }
        //check diagonals for moves
        checkLinear(TL_COR, D2_INC, move, localBoard);
        checkLinear(TR_COR,D1_INC,move, localBoard);

        return move.getPos();

    }
    private void checkLinear(int start, int inc, MoveValuePair move, int[] localBoard){
        int sum=0, freePos = 0;
        for(int i=start; i <= start + 2 *inc; i+= inc){
            sum+= localBoard[i];
            if(localBoard[i] == EMPTY){
                freePos = i;
            }
        }
        int value = convertSum(sum);
        if(move.isBetterMove(value)){
            move.setValue(value);
            move.setPos(freePos);
        }
    }

    /*
     *   Sum      Name              Points
     *    8    |   win            | 1000
     *    2    |   defend         | 500
     *    4    |   double up      | 50
     *    1    |   partial defend | 25
     *   0/5   |   slight val     | 10
     *    ?    |   no val         | 0
     */
    private int convertSum(int sum){
        return switch (sum){
            case 8 -> WIN;
            case 2 -> DEFEND;
            case 4 -> D_UP;
            case 1 -> P_DEFEND;
            case 0, 5 -> S_VAL;
            default -> NO_VAL;
        };
    }

    private int firstMove(){
        int[] localBoard = board.getBoard();
        if(localBoard[CENTRE]!=0){
            return TL_COR;
        }
        for(int i=0; i<9; i+=2){
            if(localBoard[i]!=0){
                return CENTRE;
            }
        }
        return CENTRE;
    }

    /*------------INNER_CLASS-------------*/
    private static class MoveValuePair extends TicMove{
        private int value;
        public MoveValuePair(int value, int pos, int player){
            super(pos,player);
            this.value = value;
        }
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
        public boolean isBetterMove(int val){
            return value < val;
        }
    }
}
