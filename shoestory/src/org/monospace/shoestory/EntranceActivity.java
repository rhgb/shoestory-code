package org.monospace.shoestory;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
//import android.widget.ImageButton;
import android.widget.LinearLayout;

public class EntranceActivity extends Activity {

	public final static int maxAct = 9;
	private BackgroundView mBgView;
	//	private ImageButton mNextButton;
	//	private ImageButton mPrevButton;
	private LinearLayout mNavLayout;
	private MediaPlayer mVoicePlayer = new MediaPlayer();
	private ImageButton mReplayButton;
	private int mAct = 0;
	private int mVoice = 0;
	private boolean mHasSound = true;
	private boolean mAutoPlayVoice = true;
	private boolean mPlayOnPrepared = false;
	private OnClickListener mOnClickBg = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int toAct = -1;
			switch (mAct) {
			case 0:
			case 1:
				toAct = mAct + 1;
				break;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				toAct = 2;
				break;
			}
			if (toAct >= 0) {
				setAct(toAct);
			}
		}
	};

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
		mBgView.setOnClickListener(mOnClickBg);
		mNavLayout = (LinearLayout) findViewById(R.id.navigator);
		mReplayButton = (ImageButton) findViewById(R.id.replay);
		mVoicePlayer.setVolume(1.0f, 1.0f);
		mVoicePlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				if (mPlayOnPrepared) {
					mVoicePlayer.start();
					mPlayOnPrepared = false;
				}
			}
		});
	}
	@Override
	public void onPause() {
		super.onPause();
		if(mVoicePlayer.isPlaying()) {
			mVoicePlayer.stop();
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		if (mVoice != 0) {
			try {
				mVoicePlayer.prepare();

			} catch (Exception e) {

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

	public void toggleVolume(View view) {
		ImageButton volButton = (ImageButton) view;
		try {
			if (mHasSound) {
				mVoicePlayer.setVolume(0.0f, 0.0f);
				volButton.setImageResource(R.drawable.button_volume_mute);
				mHasSound = false;
			} else {
				mVoicePlayer.setVolume(1.0f, 1.0f);
				volButton.setImageResource(R.drawable.button_volume_high);
				mHasSound = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void replayVoice(View view) {
		if (mVoice != 0) {
			if (mVoicePlayer.isPlaying()) {
				mVoicePlayer.seekTo(0);
			} else {
				try {
					mVoicePlayer.start();
//					mPlayOnPrepared = true;
//					mVoicePlayer.prepareAsync();

				} catch (IllegalStateException e) {
					mVoicePlayer.reset();
					try {
						mVoicePlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://org.monospace.shoestory/"+mVoice));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void scene1(View view) { setAct(3); }
	public void scene2(View view) { setAct(4); }
	public void scene3(View view) { setAct(5); }
	public void scene4(View view) { setAct(6); }
	public void scene5(View view) { setAct(7); }

	public void setAct(int act) {
		if(act != mAct && act>=0 && act<=maxAct) {
			mAct = act;
			mBgView.setAct(act);
			//set nav
			if (act == 2) {
				mNavLayout.setVisibility(View.VISIBLE);
			} else if (mNavLayout.getVisibility() != View.GONE) {
				mNavLayout.setVisibility(View.GONE);
			}

			mVoice = 0;
			
			// act-specific settings
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
				try {
					mVoicePlayer.reset();
					mVoicePlayer.setDataSource(getApplicationContext(), Uri.parse("android.resource://org.monospace.shoestory/"+mVoice));
					if (mAutoPlayVoice) {
						mPlayOnPrepared = true;
					}
					mVoicePlayer.prepareAsync();

				} catch (Exception e) {
					e.printStackTrace();
				}
				mReplayButton.setVisibility(View.VISIBLE);
				
			} else {
				mReplayButton.setVisibility(View.INVISIBLE);
				if (mVoicePlayer.isPlaying()) {
					mVoicePlayer.stop();
				}
			}
		}
	}
}
