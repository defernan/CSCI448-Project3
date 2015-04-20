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
    //win logic
    public boolean checkForWinner(int row, int col) {
        int win = 4;
        int horizontal = 1 + checkLeft(row, col - 1) + checkRight(row, col + 1);
        int vertical = 1 + checkUp(row + 1, col) + checkDown(row - 1, col);
        int diag1 = 1 + checkDownLeft(row - 1, col - 1) + checkUpRight(row + 1, row + 1);
        int diag2 = 1 + checkDownRight(row - 1, col + 1) + checkUpLeft(row + 1, col - 1);

        if (horizontal == win) {
            //winType = WinType.HORIZONTAL;
            //winOffsetY = y;
            return true;
        }
        if (vertical == win) {
            //winType = WinType.VERTICAL;
            //winOffsetX = x;
            return true;
        }
        if (diag1 == win) {
            //winType = WinType.DIAGONALUP;
            return true;
        }
        if (diag2 == win) {
            //winType = WinType.DIAGONALDOWN;
            return true;
        }

        return false;
    }

    int checkLeft(int row, int col) {
        if (col < 0) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkLeft(row, col - 1);
        }
    }

    int checkRight(int row, int col) {
        if (col >= columns) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkRight(row, col + 1);
        }
    }

    int checkUp(int row, int col) {
        if (row >= rows) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkUp(row + 1, col);
        }
    }

    int checkDown(int row, int col) {
        if (row < 0) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkDown(row-1, col);
        }
    }

    int checkDownLeft(int row, int col) {
        if( row < 0 || col < 0){
            return 0;
        }else if(board[row][col]!=playerPiece()){
            return 0;
        }else{
            return 1+checkDownLeft(row-1,col-1);
        }
    }

    int checkDownRight(int row, int col) {
        if ((row < 0) || (col >= columns)) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkDownRight(row - 1, col + 1);
        }
    }

    int checkUpLeft(int row, int col) {
        if ((row >= rows) || (col < 0)) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkUpLeft(row + 1, col - 1);
        }
    }

    int checkUpRight(int row, int col) {
        if ((row >= rows) || (col >= columns)) {
            return 0;
        }
        else if (board[row][col] != playerPiece()) {
            return 0;
        }
        else {
            return 1 + checkUpRight(row + 1, col + 1);
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
