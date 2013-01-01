package org.monospace.shoestory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class EntranceActivity extends Activity {
	
	private BackgroundView mBgView;
//	private ImageButton mNextButton;
//	private ImageButton mPrevButton;
	private int mAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        mAct = 0;
        mBgView = (BackgroundView) findViewById(R.id.background);
//        mNextButton = (ImageButton) findViewById(R.id.button_next);
//        mPrevButton = (ImageButton) findViewById(R.id.button_prev);
    }
    public void nextAct(View view) {
    	if (mAct<9) {
        	mAct++;
        	mBgView.setAct(mAct);
    	}
    }
    public void prevAct(View view) {
    	if (mAct>0) {
    		mAct--;
        	mBgView.setAct(mAct);
    	}
    }
}
