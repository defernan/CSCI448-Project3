package csci448.connectfour;

import java.util.ArrayList;

/**
 * Created by Dezmon on 4/15/15.
 */
public class Logic {
    //private Board board[][]
    private int rows;
    private int columns;
    //keeps track of last row where piece was placed
    private int lastRow;
    private int lastCol;
    //keeps track of the computers last move
    private int lastCompRow;
    private int lastCompCol;
    //board
    private GamePiece board[][];
    //turn tracker
    private boolean p1Turn;
    //Gamepiece win tracker
    GamePiece winPiece;
    //number of turns
    private int turns = 0;
    //list of computers next blocking move
    private ArrayList<Cell> compBlockList = new ArrayList<Cell>();
    //list of computers winning moves
    private ArrayList<Cell> compWinList = new ArrayList<Cell>();
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
        turns = 0;
        compBlockList = new ArrayList<Cell>();
        compWinList = new ArrayList<Cell>();
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
                turns += 1;
                if (!p1Turn) {
                    lastCompCol = col;
                    lastCompRow = row;
                }
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
        int diag1 = 1 + checkDownLeft(lastRow - 1, col - 1) + checkUpRight(lastRow + 1, col + 1);
        int diag2 = 1 + checkDownRight(lastRow - 1, col + 1) + checkUpLeft(lastRow + 1, col - 1);

        if (horizontal >= winCount) {
            if(p1Turn) {
                winPiece = GamePiece.RGREEN;
            }else{
                winPiece = GamePiece.BGREEN;
            }
            markWinHorizontalL(lastRow, col - 1);
            markWinHorizontalR(lastRow, col + 1);
            win = true;
        }
        if (vertical >= winCount) {
            if(p1Turn) {
                winPiece = GamePiece.RGREEN;
            }else{
                winPiece = GamePiece.BGREEN;
            }
            markWinVertUp(lastRow + 1, col);
            markWinVertDown(lastRow - 1, col);
            win = true;
        }
        if (diag1 >= winCount) {
            if(p1Turn) {
                winPiece = GamePiece.RGREEN;
            }else{
                winPiece = GamePiece.BGREEN;
            }
            markWinDownLeft(lastRow - 1, col - 1);
            markWinUpRight(lastRow + 1, col + 1);
            win = true;
        }
        if (diag2 >= winCount) {
            if(p1Turn) {
                winPiece = GamePiece.RGREEN;
            }else{
                winPiece = GamePiece.BGREEN;
            }
            markWinDownRight(lastRow - 1, col + 1);
            markWinUpLeft(lastRow + 1, col - 1);
            win = true;
        }

        if(win){
            board[lastRow][col] = winPiece;
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
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
            markWinVertDown(row - 1, col);
        }
    }

    public void markWinDownLeft(int row, int col) {
        if( row < 0 || col < 0){
            return;
        }else if(board[row][col]!=playerPiece()){
            return;
        }else{
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
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
            board[row][col] = winPiece;
            markWinUpRight(row + 1, col + 1);
        }
    }
    //computer player logic
    public void computerNextMove(int lastPlayerColumn) {
        System.out.println("Computer Move");
        boolean madeMove = false;
        changeTurn();
        int left = 1 + checkLeft(lastRow, lastPlayerColumn - 1);
        int right = 1 + checkRight(lastRow, lastPlayerColumn + 1);
        int horizontal = left + right - 1;
        int down = 1 + checkDown(lastRow - 1, lastPlayerColumn);
        int downLeft = 1 + checkDownLeft(lastRow - 1, lastPlayerColumn - 1);
        int upRight = 1 + checkUpRight(lastRow + 1, lastPlayerColumn + 1);
        int diagUp = downLeft + upRight - 1;
        int downRight = 1 + checkDownRight(lastRow - 1, lastPlayerColumn + 1);
        int upLeft = 1 + checkUpLeft(lastRow + 1, lastPlayerColumn - 1);
        int diagDown = downRight + upLeft - 1;
        changeTurn();
        int compLeft = 1 + checkLeft(lastCompRow, lastCompCol - 1);
        int compRight = 1 + checkRight(lastCompRow, lastCompCol + 1);
        int compHorizontal = compLeft + compRight - 1;
        int compDown = 1+ checkDown(lastCompRow - 1, lastCompCol);
        int compDownLeft = 1 + checkDownLeft(lastCompRow - 1, lastCompCol - 1);
        int compUpRight = 1 + checkUpRight(lastCompRow + 1, lastCompCol + 1);
        int compDiagUp = compDownLeft + compUpRight - 1;
        int compDownRight = 1 + checkDownRight(lastCompRow - 1, lastCompCol + 1);
        int compUpLeft = 1 + checkUpLeft(lastCompRow + 1, lastCompCol - 1);
        int compDiagDown = compDownRight + compUpLeft - 1;

        if (turns <= 1) {
            if (columns - lastPlayerColumn > 3) {
                markCell(lastPlayerColumn + 1);
            }
            else {
                markCell(lastPlayerColumn - 1);
            }
            return;
        }

        //Computer Logic to win
        if (compLeft == 3) {
            if ((lastCompCol != columns - 1) && (board[lastCompRow][lastCompCol + 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow, lastCompCol + 1));
            }
        }
        if (compRight == 3) {
            if ((lastCompCol != 0) && (board[lastCompRow][lastCompCol - 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow, lastCompCol - 1));
            }
        }
        if (compDown == 3) {
            if ((lastCompRow != rows - 1) && (board[lastCompRow][lastCompCol + 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow + 1, lastCompCol));
            }
        }
        if (compDownLeft == 3) {
            if ((lastCompCol != columns - 1) && (lastCompRow != rows - 1) && (board[lastCompRow + 1][lastCompCol + 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow + 1, lastCompCol + 1));
            }
        }
        if (compDownRight == 3) {
            if ((lastCompCol != 0) && (lastCompRow != rows - 1) && (board[lastCompRow + 1][lastCompCol - 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow + 1, lastCompCol - 1));
            }
        }
        if (compUpLeft == 3) {
            if ((lastCompCol != columns - 1) && (lastCompRow != 0) && (board[lastCompRow - 1][lastCompCol + 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow - 1, lastCompCol + 1));
            }
        }
        if (compUpRight == 3) {
            if ((lastCompCol != 0) && (lastCompRow != 0) && (board[lastCompRow - 1][lastCompCol - 1] == GamePiece.BLANK)) {
                compWinList.add(new Cell(lastCompRow - 1, lastCompCol - 1));
            }
        }
        if (!compWinList.isEmpty()) {
            for (Cell c : compWinList) {
                if ((board[c.getRow()][c.getCol()] == GamePiece.BLANK) && (c.getRow() != 0) && (board[c.getRow() - 1][c.getCol()] != GamePiece.BLANK)) {
                    markCell(c.getCol());
                    compWinList.remove(c);
                    return;
                }
            }
        }

        //Computer Logic to block 3 in a row
        if (left == 3) {
            if ((lastPlayerColumn != columns - 1) && (board[lastRow][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow, lastPlayerColumn + 1));
            }
        }
        if (right == 3) {
            if ((lastPlayerColumn != 0) && (board[lastRow][lastPlayerColumn - 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow, lastPlayerColumn - 1));
            }
        }
        if (down == 3) {
            if ((lastRow != rows - 1) && (board[lastRow + 1][lastPlayerColumn] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow + 1, lastPlayerColumn));
            }
        }
        if (downLeft == 3) {
            if ((lastPlayerColumn != columns - 1) && (lastRow != rows -1) && (board[lastRow + 1][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow + 1, lastPlayerColumn + 1));
            }
        }
        if (downRight == 3) {
            if ((lastPlayerColumn != 0) && (lastRow != rows -1) && (board[lastRow + 1][lastPlayerColumn - 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow - 1, lastPlayerColumn - 1));

            }
        }
        if (upLeft == 3) {
            if ((lastPlayerColumn != 6) && (lastRow != 0) && (board[lastRow - 1][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow - 1, lastPlayerColumn + 1));
            }
        }
        if (upRight == 3) {
            if ((lastPlayerColumn != 0) && (lastRow != 0) && (board[lastRow - 1][lastPlayerColumn - 1] == GamePiece.BLANK)) {
                compBlockList.add(new Cell(lastRow - 1, lastPlayerColumn - 1));
            }
        }
        if (!compBlockList.isEmpty()) {
            for (Cell c : compBlockList) {
                if ((board[c.getRow()][c.getCol()] == GamePiece.BLANK) && (c.getRow() != 0) && (board[c.getRow() - 1][c.getCol()] != GamePiece.BLANK)) {
                    markCell(c.getCol());
                    compBlockList.remove(c);
                    return;
                }
            }
        }

        //Computer Logic to get to 3 in a row
        ArrayList<Cell> tempWinList = new ArrayList<Cell>();
        if (compLeft == 2) {
            if ((lastCompCol != columns - 1) && (board[lastCompRow][lastCompCol + 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow, lastCompCol + 1));
            }
        }
        if (compRight == 2) {
            if ((lastCompCol != 0) && (board[lastCompRow][lastCompCol - 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow, lastCompCol - 1));
            }
        }
        if (compDown == 2) {
            if ((lastCompRow != rows - 1) && (board[lastCompRow][lastCompCol + 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow + 1, lastCompCol));
            }
        }
        if (compDownLeft == 2) {
            if ((lastCompCol != columns - 1) && (lastCompRow != rows - 1) && (board[lastCompRow + 1][lastCompCol + 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow + 1, lastCompCol + 1));
            }
        }
        if (compDownRight == 2) {
            if ((lastCompCol != 0) && (lastCompRow != rows - 1) && (board[lastCompRow + 1][lastCompCol - 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow + 1, lastCompCol - 1));
            }
        }
        if (compUpLeft == 2) {
            if ((lastCompCol != columns - 1) && (lastCompRow != 0) && (board[lastCompRow - 1][lastCompCol + 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow - 1, lastCompCol + 1));
            }
        }
        if (compUpRight == 2) {
            if ((lastCompCol != 0) && (lastCompRow != 0) && (board[lastCompRow - 1][lastCompCol - 1] == GamePiece.BLANK)) {
                tempWinList.add(new Cell(lastCompRow - 1, lastCompCol - 1));
            }
        }
        if (!tempWinList.isEmpty()) {
            for (Cell c : tempWinList) {
                if ((board[c.getRow()][c.getCol()] == GamePiece.BLANK) && (c.getRow() != 0) && (board[c.getRow() - 1][c.getCol()] != GamePiece.BLANK)) {
                    markCell(c.getCol());
                    tempWinList.remove(c);
                    return;
                }
            }
        }

        //Computer Logic to block 2 in a row
        ArrayList<Cell> tempMovesList = new ArrayList<Cell>();
        if (left == 2) {
            if ((lastPlayerColumn != 6) && (board[lastRow][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                tempMovesList.add(new Cell(lastRow, lastPlayerColumn + 1));
            }
        }
        if (right == 2) {
            if ((lastPlayerColumn != 0) && (board[lastRow][lastPlayerColumn - 1] == GamePiece.BLANK)) {
                tempMovesList.add(new Cell(lastRow, lastPlayerColumn - 1));
            }
        }
        if (down == 2) {
            if ((lastRow != rows - 1) && (board[lastRow + 1][lastPlayerColumn] == GamePiece.BLANK)) {
                tempMovesList.add(new Cell(lastRow + 1, lastPlayerColumn));
            }
        }
        if (downLeft == 2) {
            if ((lastPlayerColumn != 6) && (lastRow != rows -1) && (board[lastRow + 1][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                tempMovesList.add(new Cell(lastRow + 1, lastPlayerColumn + 1));
            }
        }
        if (downRight == 2) {
            if ((lastPlayerColumn != 0) && (lastRow != rows -1) && (board[lastRow + 1][lastPlayerColumn - 1] == GamePiece.BLANK)) {
                if (board[lastRow][lastPlayerColumn - 1] != GamePiece.BLANK) {
                    tempMovesList.add(new Cell(lastRow - 1, lastPlayerColumn - 1));
                }
            }
        }
        if (upLeft == 2) {
            if ((lastPlayerColumn != 6) && (lastRow != 0) && (board[lastRow - 1][lastPlayerColumn + 1] == GamePiece.BLANK)) {
                tempMovesList.add(new Cell(lastRow - 1, lastPlayerColumn + 1));
            }
        }
        if (upRight == 2) {
            if ((lastPlayerColumn != 0) && (lastRow != 0) && (board[lastRow - 1][lastPlayerColumn - 1] == GamePiece.BLANK)) {

                tempMovesList.add(new Cell(lastRow - 1, lastPlayerColumn - 1));

            }
        }
        if (!tempMovesList.isEmpty()) {
            for (Cell c : tempMovesList) {
                if ((board[c.getRow()][c.getCol()] == GamePiece.BLANK) && (c.getRow() != 0) && (board[c.getRow() - 1][c.getCol()] != GamePiece.BLANK)) {
                    markCell(c.getCol());
                    tempMovesList.remove(c);
                    return;
                }
            }
        }

        //If there are no good moves this hits at the end to place the piece
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (board[row][col] == GamePiece.BLANK) {
                    markCell(col);
                    return;
                }
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
    public boolean isP1Turn(){
        return this.p1Turn;
    }
    public int getBoardRows(){
        return this.rows;
    }
    public int getBoardColumns(){
        return this.columns;
    }
    public int getLastCompRow() {
        return lastCompRow;
    }
    public int getLastCompCol() {
        return lastCompCol;
    }
}
