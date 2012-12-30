package org.monospace.shoestory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Handler;
import android.os.Message;

class StoryView extends SurfaceView implements SurfaceHolder.Callback {
	class StoryThread extends Thread {
		private SurfaceHolder mSurfaceHolder;
		private Context mContext;
		private Handler mHandler;
		private Drawable mEntranceBg;
		
		public StoryThread(SurfaceHolder holder, Context context,
				Handler handler) {
			mSurfaceHolder = holder;
			mContext = context;
			mHandler = handler;
			
			Resources res = context.getResources();
			mEntranceBg = res.getDrawable(R.drawable.entrance_bg);
		}
		
		@Override
		public void run() {
			
		}

		public void setSurfaceSize(int width, int height) {
			// TODO Auto-generated method stub

		}

		public void setRunning(boolean b) {
			// TODO Auto-generated method stub

		}
	}

	private StoryThread thread;

	public StoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		thread = new StoryThread(holder, context, new Handler() {
			@Override
			public void handleMessage(Message m) {
				// TODO
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
}