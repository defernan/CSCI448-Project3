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

    public void changeTurn(){
        p1Turn = (!p1Turn);
    }
    //returns true if column not full
    public boolean isValidMove(int col){
        int maxPos = rows-1;
        if( board[maxPos][col] == GamePiece.BLANK) return true;
        return false;
    }
    //marks cell based on players turn
    public void markCell(int col){
        for(int row = 0; row < rows; row++){
            if(board[row][col] == GamePiece.BLANK){
                board[row][col] = playerPiece();
                return;
            }
        }
    }

    //getters and setters
    public GamePiece getVal(int row, int col){
        return board[row][col];
    }
    public GamePiece playerPiece(){
        if(p1Turn) return GamePiece.RED;
        else return GamePiece.BLACK;
    }
    public int getBoardRows(){
        return this.rows;
    }
    public int getBoardColumns(){
        return this.columns;
    }

}
