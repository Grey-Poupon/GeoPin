package team3.teamproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * Created by Petr Makarov on 21.04.2018.
 */

public class LoadingBarActivity extends Activity {


    private final int WAIT_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_bar_layout);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

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
}