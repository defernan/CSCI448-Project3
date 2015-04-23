package csci448.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
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
    float boardWidth;
    float cellDim;

    //offset for board draw
    private float offsetX;
    private float offsetY;

    //board dims
    private int columns = 7;
    private int rows = 6;

    //resize board factor
    private float factor = .75f;
    private boolean winner;
    //used for drawing a line through the winning set
    private WinType winType = WinType.NONE;

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        cont = context;
        this.setBackgroundColor(Color.BLUE);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
        this.setBackgroundColor(Color.BLUE);
    }

    public TitleView(Context context) {
        super(context);
        cont = context;
        this.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int squareSize = getMeasuredWidth() * rows / columns;
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());

    }

    /*=======================================================================
    = Draw logic
    ==========================================================================*/
    @Override
    public void onDraw(Canvas c) {
        this.canvas = c;
        if (canvas.getWidth() < canvas.getHeight()) {
            this.cellDim = (canvas.getWidth() / columns) * factor;
            this.boardWidth = canvas.getWidth() * factor;
        } else {
            this.cellDim = (canvas.getHeight() / columns) * factor;
            this.boardWidth = canvas.getHeight() * factor;
        }
        //do this to move board on canvas
        this.offsetX = cellDim * .75f;
        this.offsetY = cellDim * 1.5f;

        //Typeface style = Typeface.createFromAsset(getAssets(), )
        paint.setColor(Color.BLACK);
        paint.setTextSize(cellDim);
        canvas.drawText("Play",boardWidth*.4f, cellDim, paint);
        //draw board
        drawBoard();

        //draws win if there is one
        drawWin();

    }

    //Draws board
    public void drawBoard() {
        System.out.println(cellDim);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                //do this to adjust for board drawn from top down
                int adjustedRow = rows - 1 - row;
                drawSquare(adjustedRow, col, GamePiece.BLANK);
            }
        }
        //fill pieces fo demo
        drawSquare(adjustRow(0), 2, GamePiece.RGREEN);
        drawSquare(adjustRow(1), 3, GamePiece.RGREEN);
        drawSquare(adjustRow(2), 4, GamePiece.RGREEN);
        drawSquare(adjustRow(3), 5, GamePiece.RGREEN);
        drawSquare(adjustRow(0), 0, GamePiece.BLACK);
        drawSquare(adjustRow(1), 1, GamePiece.BLACK);
        drawSquare(adjustRow(0), 3, GamePiece.BLACK);
        drawSquare(adjustRow(0), 4, GamePiece.BLACK);
        drawSquare(adjustRow(1), 4, GamePiece.BLACK);
        drawSquare(adjustRow(0), 4, GamePiece.BLACK);
        drawSquare(adjustRow(1), 5, GamePiece.BLACK);
        drawSquare(adjustRow(0), 6, GamePiece.BLACK);
        drawSquare(adjustRow(0), 1, GamePiece.RED);
        drawSquare(adjustRow(0), 5, GamePiece.RED);
        drawSquare(adjustRow(2), 5, GamePiece.RED);
        drawSquare(adjustRow(3), 4, GamePiece.RED);
    }
    public int adjustRow(int row){ return rows - 1 - row;}

    //Draws square based on if piece has been played or not
    public void drawSquare(int y, int x, GamePiece identity) {
        //draw squares
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(x * cellDim + offsetX, y * cellDim + offsetY, (x + 1) * cellDim + offsetX, (y + 1) * cellDim + offsetY, paint);

        //draw pieces
        paint.setStyle(Paint.Style.FILL);
        switch (identity) {
            case BLANK:
                paint.setColor(Color.CYAN);
                drawCircle(y, x, cellDim / 2 - 4);
                break;
            case RED:
                paint.setColor(Color.RED);
                drawCircle(y, x, cellDim / 2 - 4);
                break;
            case BLACK:
                paint.setColor(Color.BLACK);
                drawCircle(y, x, cellDim / 2 - 4);
                break;
            case RGREEN:
                paint.setColor(Color.RED);
                drawCircle(y, x, cellDim / 2 - 4);
                paint.setColor(Color.GREEN);
                drawCircle(y, x, cellDim / 8);
                break;
            case BGREEN:
                paint.setColor(Color.BLACK);
                drawCircle(y, x, cellDim / 2 - 4);
                paint.setColor(Color.GREEN);
                drawCircle(y, x, cellDim / 8);
                break;
            default:
                paint.setColor(Color.CYAN);
                drawCircle(y, x, cellDim / 2 - 4);
                break;
        }
    }
    public void drawCircle(int y, int x, float radius){
        canvas.drawCircle(x * cellDim + cellDim / 2 + offsetX, y * cellDim + cellDim / 2 + offsetY, radius, paint);
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
            if(true) {
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