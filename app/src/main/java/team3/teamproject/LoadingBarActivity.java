package team3.teamproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Petr Makarov on 21.04.2018.
 * Loading bar that redirects to MapsActivity to avoid black
 * screen between two activities
 */


//Controlls the loading transition

public class LoadingBarActivity extends Activity {


    private final int WAIT_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_bar);
        ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.VISIBLE);
        pBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                this.Sleep(1000);
                Intent mapsIntent = new Intent(LoadingBarActivity.this,MapsActivity.class);
                LoadingBarActivity.this.startActivity(mapsIntent);
                LoadingBarActivity.this.finish();
            }
            private void Sleep(int i) {
            }
        }, WAIT_TIME);
    }

    /**
     * Back button listener.
     * Disables it to not confuse user if he accidentally use it.
     *
     * Created by Petr Makarov on 23.04.2018.
     */
    @Override
    public void onBackPressed() {
    }
}