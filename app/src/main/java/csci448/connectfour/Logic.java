package csci448.connectfour;

/**
 * Created by Dezmon on 4/15/15.
 */
public class Logic {
    //private Board board[][]
    //private int rows;
    //private int columns;
    //board
    private Board board;
    //turn tracker
    private boolean p1Turn;
    public Logic(){
        this.board = new Board();
        initializeGame();
    }

    public void initializeGame(){
        for(int row = 0; row < board.getRows(); row++){
            for(int col = 0; col < board.getColumns(); col++)
                board.markCell(row, col, GamePiece.BLANK);
        }
        p1Turn = true;
    }
    public GamePiece getVal(int row, int col){
        return board.getCellVal(row, col);
    }

    public int getBoardRows(){
        return board.getRows();
    }
    public int getBoardColumns(){
        return board.getColumns();
    }

}
