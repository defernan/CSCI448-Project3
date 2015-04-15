package csci448.connectfour;

/**
 * Created by Dezmon on 4/15/15.
 */
public class Board {
    private int rows;
    private int columns;
    private GamePiece[][] board;

    public Board(){
        this.rows = 6;
        this.columns = 7;
        this.board = new GamePiece[rows][columns];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < columns; col++){
                board[row][col] = GamePiece.BLANK;
            }
        }
    }
    public Board(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.board = new GamePiece[rows][columns];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < columns; col++){
                board[row][col] = GamePiece.BLANK;
            }
        }
    }
    public void markCell(int row, int col, GamePiece piece){
        board[row][col] = piece;
    }
    public GamePiece getCellVal(int row, int col){
        return board[row][col];
    }
    public int getRows(){
        return this.rows;
    }
    public int getColumns(){
        return this.columns;
    }

}
