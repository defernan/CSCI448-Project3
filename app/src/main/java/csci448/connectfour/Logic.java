package csci448.connectfour;

/**
 * Created by Dezmon on 4/15/15.
 */
public class Logic {
    //private Board board[][]
    private int rows;
    private int columns;
    //board
    private GamePiece board[][];
    //turn tracker
    private boolean p1Turn;
    public Logic(){
        this.rows = 6;
        this.columns = 7;
        this.board = new GamePiece [rows][columns];
        initializeGame();
    }

    public void initializeGame(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < columns; col++)
                board[row][col]=GamePiece.BLANK;
        }
        p1Turn = true;
    }
    public GamePiece getVal(int row, int col){
        return board[row][col];
    }
    public void markCell(int row, int col, GamePiece piece){
        board[row][col] = piece;
    }
    public int getBoardRows(){
        return this.rows;
    }
    public int getBoardColumns(){
        return this.columns;
    }

}
