package org.monospace.shoestory;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
//import android.widget.ImageButton;

public class EntranceActivity extends Activity {
	
	public final static int maxAct = 9;
	private BackgroundView mBgView;
//	private ImageButton mNextButton;
//	private ImageButton mPrevButton;
	private MediaPlayer mVoicePlayer = null;
	private int mAct = 0;
	private int mVoice = 0;
	private boolean mAutoPlayVoice = true;

	public class MusicIntentReceiver extends android.content.BroadcastReceiver {
		   @Override
		   public void onReceive(Context ctx, Intent intent) {
		      if (intent.getAction().equals(
		                    android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
		          // signal your service to stop playback
		          // (via an Intent, for instance)
		    	  if(mVoicePlayer.isPlaying()) {
		    		  mVoicePlayer.stop();
		    	  }
		    	  mAutoPlayVoice = false;
		      }
		   }
		}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        mBgView = (BackgroundView) findViewById(R.id.background);
//        mNextButton = (ImageButton) findViewById(R.id.button_next);
//        mPrevButton = (ImageButton) findViewById(R.id.button_prev);
    }
    @Override
    public void onPause() {
    	super.onPause();
    	if (mVoicePlayer != null) {
	    	  if(mVoicePlayer.isPlaying()) {
	    		  mVoicePlayer.stop();
	    	  }
    	}
    }
    @Override
    public void onResume() {
    	super.onResume();
    	if (mVoicePlayer != null) {
        	try {
    			mVoicePlayer.prepare();
    			
    		} catch (IllegalStateException e) {
    			Log.d("entrance", "onResume 1");
    			mVoicePlayer.release();
    			mVoicePlayer = MediaPlayer.create(getApplicationContext(), mVoice);
    			
    		} catch (Exception e) {
    			Log.d("entrance", "onResume 2");
    			mVoicePlayer = MediaPlayer.create(getApplicationContext(), mVoice);
    			
    		}

    	}
    }
    public void nextAct(View view) {
    	if (mAct<maxAct) {
        	setAct(mAct+1);
    	}
    }
    public void prevAct(View view) {
    	if (mAct>0) {
    		setAct(mAct-1);
    	}
    }
    public void setAct(int act) {
    	if(act != mAct && act>=0 && act<=maxAct) {
    		mAct = act;
    		mBgView.setAct(act);
    		mVoice = 0;
    		switch (act) {
			case 0:
				break;
			case 1:
				mVoice = R.raw.track1;
				break;
			case 2:
				break;
			case 3:
				mVoice = R.raw.track2;
				break;
			case 4:
				mVoice = R.raw.track3;
				break;
			case 5:
				mVoice = R.raw.track4;
				break;
			case 6:
				mVoice = R.raw.track5;
				break;
			case 7:
				mVoice = R.raw.track6;
				break;
			case 8:
				break;
			case 9:
				break;

			default:
				break;
			}
    		if(mVoice != 0) {
    			if(mVoicePlayer != null) {
    				try {
						mVoicePlayer.release();
					} catch (Exception e) {
						// TODO: handle exception
					}
    			}
    			mVoicePlayer = MediaPlayer.create(getApplicationContext(), mVoice);
    			if (mAutoPlayVoice) {
        			mVoicePlayer.start();
    			}
    		}
    	}
    }
}
