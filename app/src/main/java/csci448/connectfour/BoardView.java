package csci448.connectfour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Dezmon on 4/14/15.
 */
public class BoardView extends View {
    private Paint paint = new Paint();
    private Context cont;
    private Canvas canvas;
    private int boardWidth;
    private int cellDim;
    //board dims
    private int columns = 7;
    private int rows = 6;

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        cont = context;
        this.setBackgroundColor(Color.YELLOW);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cont = context;
        this.setBackgroundColor(Color.YELLOW);
    }

    public BoardView(Context context) {
        super(context);
        cont = context;
        this.setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int squareSize = getMeasuredWidth()*rows/columns;
        setMeasuredDimension(getMeasuredWidth(), squareSize);
    }

    @Override
    public void onDraw(Canvas c){
        this.canvas = c;
        //use 6 for 6 columns
        if (canvas.getWidth() < canvas.getHeight()) {
            this.cellDim = canvas.getWidth() / columns;
            this.boardWidth = canvas.getWidth();
        }
        else {
            this.cellDim = canvas.getHeight() / columns;
            this.boardWidth = canvas.getHeight();
        }

        //Initialize board
        drawBoard();

    }
    //Draws board
    public void drawBoard(){
        //playing = true;
        //listenerActivation();

        for (int i=0; i < columns; i++){
            for (int j=0; j < rows; j++) {
                //LATER WILL BE OUR 2D ARRAY OF THE GAME PIECE VALUE
                drawSquare(i, j, GamePiece.BLANK);
            }
        }
    }
    //Draws square based on if piece has been played or not
    public void drawSquare(int x, int y, GamePiece identity){
        //draw squares
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(x*cellDim, y*cellDim, (x+1)*cellDim, (y+1)*cellDim, paint);

        //draw pieces
        switch(identity){
            case BLANK:
                paint.setColor(Color.CYAN);
                break;
            case RED:
                paint.setColor(Color.RED);
                break;
            case BLACK:
                paint.setColor(Color.BLACK);
                break;
            default:
                paint.setColor(Color.CYAN);
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x*cellDim + cellDim/2, y*cellDim + cellDim/2, cellDim/2 - 4, paint);


    }
}


