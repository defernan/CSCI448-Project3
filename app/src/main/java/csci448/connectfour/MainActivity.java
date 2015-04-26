package csci448.connectfour;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    BoardView boardView;
    Button newGame;
    Button exitGame;
    int gameType;
    AlertDialog newGameDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardView = (BoardView)findViewById(R.id.board);
        newGame = (Button) findViewById(R.id.buttonNewGame);
        exitGame = (Button) findViewById(R.id.buttonExitGame);
        final TextView playerString = (TextView) findViewById(R.id.player);
        Bundle b = getIntent().getExtras();
        gameType = b.getInt("gameType");
        boardView.newGame(gameType);
        if (gameType == 0) {
            playerString.setText("Player vs Player");
        }
        else if (gameType == 1) {
            playerString.setText("Player vs Computer");
        }

        newGameDialog = new AlertDialog.Builder(this).create(); // dialog for new game
        newGameDialog.setTitle("New Game");
        newGameDialog.setMessage("Who would you like to play against?");
        newGameDialog.setButton("Person", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boardView.newGame(0); //initializes new game with another player
                playerString.setText("Player vs Player");
            }
        });
        newGameDialog.setButton2("Computer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boardView.newGame(1); //initializes new game with computer
                playerString.setText("Player vs Computer");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void newClicked(View view) {
        newGameDialog.show();
    }

    public void exitCurrentClicked(View view) {
//        finish();
//        System.exit(0);//closes app
        startActivity(new Intent(MainActivity.this, TitleActivity.class));
    }
}
