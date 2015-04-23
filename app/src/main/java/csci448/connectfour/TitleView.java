package csci448.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dezmon on 4/14/15.
 */
public class TitleView extends View {
    private Paint paint = new Paint();
    private Context cont;
    private Canvas canvas;
    private int boardWidth;
    private int cellDim;
    //game logic
    private Logic logic;
    //board dims
    private int columns;
    private int rows;

    private boolean winner;
    //used for drawing a line through the winning set
    private WinType winType = WinType.NONE;

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        cont = context;
        this.logic = new Logic();
        this.columns = logic.getBoardColumns();
        this.rows = logic.getBoardRows();
        this.setBackgroundColor(Color.YELLOW);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
        this.logic = new Logic();
        this.columns = logic.getBoardColumns();
        this.rows = logic.getBoardRows();
        this.setBackgroundColor(Color.YELLOW);
    }

    public TitleView(Context context) {
        super(context);
        cont = context;
        this.logic = new Logic();
        this.columns = logic.getBoardColumns();
        this.rows = logic.getBoardRows();
        this.setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int squareSize = getMeasuredWidth() * rows / columns;
        setMeasuredDimension(getMeasuredWidth(), squareSize);

    }

    /*=======================================================================
    = Draw logic
    ==========================================================================*/
    @Override
    public void onDraw(Canvas c) {
        this.canvas = c;
        if (canvas.getWidth() < canvas.getHeight()) {
            this.cellDim = canvas.getWidth() / columns;
            this.boardWidth = canvas.getWidth();
        } else {
            this.cellDim = canvas.getHeight() / columns;
            this.boardWidth = canvas.getHeight();
        }

        //draw board
        drawBoard();

        //draws win if there is one
        drawWin();

    }

    //Draws board
    public void drawBoard() {
        //playing = true;
        //listenerActivation();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                //do this to adjust for board drawn from top down
                int adjustedRow = rows - 1 - row;
                drawSquare(adjustedRow, col, logic.getVal(row, col));
            }
        }
    }

    //Draws square based on if piece has been played or not
    public void drawSquare(int y, int x, GamePiece identity) {
        //draw squares
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(x * cellDim, y * cellDim, (x + 1) * cellDim, (y + 1) * cellDim, paint);

        //draw pieces
        switch (identity) {
            case BLANK:
                paint.setColor(Color.CYAN);
                break;
            case RED:
                paint.setColor(Color.RED);
                break;
            case BLACK:
                paint.setColor(Color.BLACK);
                break;
            case GREEN:
                paint.setColor(Color.rgb(113,198,113));
                break;
            default:
                paint.setColor(Color.CYAN);
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x * cellDim + cellDim / 2, y * cellDim + cellDim / 2, cellDim / 2 - 4, paint);


    }
    public void drawWin(){
        if(this.winner){
            //make string
            String player;
            String won = " Won";
            //make path to draw along
            Path path = new Path();
            path.addCircle(columns / 2 * cellDim + 30, rows / 2 * cellDim + 100, 2 *  cellDim, Path.Direction.CW);
            //modify paint
            paint.setColor(Color.BLUE);
            paint.setTextSize(cellDim/2);
            paint.setFakeBoldText(true);
            //not sure why this is flipped, seems like invalidate is not finishing false enough
            if(logic.isP1Turn()) {
                player= "Player2";
            }else{
                player = "Player1";
            }
            //curved cool win, works on android dimensions off on nexus
            //canvas.drawTextOnPath(player+won, path, 494, 0, paint);
            //horizontal
            canvas.drawText((player+won),columns/3*cellDim,rows/2*cellDim,paint);
        }
    }


}