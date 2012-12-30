package org.monospace.shoestory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EntranceActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_entrance, menu);
        return true;
    }
}
