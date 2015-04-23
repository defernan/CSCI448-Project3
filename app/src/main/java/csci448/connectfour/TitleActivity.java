package csci448.connectfour;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class TitleActivity extends ActionBarActivity {
    Button singlePlayerGame;
    Button twoPlayerGame;
    Button exitGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title, menu);
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

    public void singlePlayerClicked(View view){
//        startActivity(new Intent(TitleActivity.this, MainActivity.class));
        Intent intent = new Intent(TitleActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("gameType", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void twoPlayerClicked(View view) {

        Intent intent = new Intent(TitleActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("gameType", 0);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void exitClicked(View view) {
        finish();
        System.exit(0);//closes app
    }
}
