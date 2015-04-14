package csci448.connectfour;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Dezmon on 4/14/15.
 */
public class BoardView extends View {
    private Paint paint = new Paint();
    private Context cont;

    public BoardView(Context context){
        super(context);
        this.cont = context;
        this.setBackgroundColor(Color.WHITE);
    }
}

