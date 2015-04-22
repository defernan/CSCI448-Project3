package csci448.connectfour;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Dezmon on 4/15/15.
 */
public class Logic {
    //private Board board[][]
    private int rows;
    private int columns;
    //keeps track of last row where piece was placed
    private int lastRow;
    //board
    private GamePiece board[][];
    //turn tracker
    private boolean p1Turn;
    //private ArrayList<Pair> winningPieces;
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
                this.lastRow = row;
                System.out.println(col + ", " + lastRow);
                return;
            }
        }
    }
    //win logic
    public boolean checkForWinner(int col) {
        boolean win = false;
        int winCount = 4;
        int horizontal = 1 + checkLeft(lastRow, col - 1) + checkRight(lastRow, col + 1);
        int vertical = 1 + checkUp(lastRow + 1, col) + checkDown(lastRow - 1, col);
        int diag1 = 1 + checkDownLeft(lastRow - 1, col - 1) + checkUpRight(lastRow + 1, lastRow + 1);
        int diag2 = 1 + checkDownRight(lastRow - 1, col + 1) + checkUpLeft(lastRow + 1, col - 1);

        if (horizontal == winCount) {
            markWinHorizontalL(lastRow, col - 1);
            markWinHorizontalR(lastRow, col + 1);
            win = true;
        }
        if (vertical == winCount) {
            markWinVertUp(lastRow + 1, col);
            markWinVertDown(lastRow - 1, col);
            win = true;
        }
        if (diag1 == winCount) {
            markWinDownLeft(lastRow - 1, col - 1);
            markWinUpRight(lastRow + 1, col + 1);
            win = true;
        }
        if (diag2 == winCount) {
            markWinDownRight(lastRow - 1, col + 1);
            markWinUpLeft(lastRow + 1, col - 1);
            win = true;
        }

        if(win){
            board[lastRow][col] = GamePiece.GREEN;
        }
        return win;
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
    //mark winning pieces
    public void markWinHorizontalL(int row, int col){
        if (col < 0) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }else{
            board[row][col] = GamePiece.GREEN;
            markWinHorizontalL(row, col - 1);
        }
    }
    public void markWinHorizontalR(int row, int col){
        if (col >= columns) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }else{
            board[row][col] = GamePiece.GREEN;
            markWinHorizontalR(row, col + 1);
        }
    }

    public void markWinVertUp(int row, int col) {
        if (row >= rows) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }
        else {
            board[row][col] = GamePiece.GREEN;
            markWinVertUp(row + 1, col);
        }
    }

    public void markWinVertDown(int row, int col) {
        if (row < 0) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }
        else {
            board[row][col] = GamePiece.GREEN;
            markWinVertDown(row - 1, col);
        }
    }

    public void markWinDownLeft(int row, int col) {
        if( row < 0 || col < 0){
            return;
        }else if(board[row][col]!=playerPiece()){
            return;
        }else{
            board[row][col] = GamePiece.GREEN;
            markWinDownLeft(row - 1, col - 1);
        }
    }

    public void markWinDownRight(int row, int col) {
        if ((row < 0) || (col >= columns)) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }
        else {
            board[row][col] = GamePiece.GREEN;
            markWinDownRight(row - 1, col + 1);
        }
    }

    public void markWinUpLeft(int row, int col) {
        if ((row >= rows) || (col < 0)) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }
        else {
            board[row][col] = GamePiece.GREEN;
            markWinUpLeft(row + 1, col - 1);
        }
    }

    public void markWinUpRight(int row, int col) {
        if ((row >= rows) || (col >= columns)) {
            return;
        }
        else if (board[row][col] != playerPiece()) {
            return;
        }
        else {
            board[row][col] = GamePiece.GREEN;
            markWinUpRight(row + 1, col + 1);
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
    public boolean isP1Turn(){
        return this.p1Turn;
    }
    public int getBoardRows(){
        return this.rows;
    }
    public int getBoardColumns(){
        return this.columns;
    }

}
