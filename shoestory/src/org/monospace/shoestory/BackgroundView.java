package org.monospace.shoestory;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class BackgroundView extends View {

	private TypedArray bgArray;
	static private int mAct = 0;
	private int mOffsetX = 0;
	private int mOffsetY = 0;
	private double mZoom = 1;
	
	public BackgroundView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Resources res = getResources();
		bgArray = res.obtainTypedArray(R.array.backgrounds);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Drawable bg = bgArray.getDrawable(mAct);
		// calculate position
		int rw = bg.getIntrinsicWidth();
		int rh = bg.getIntrinsicHeight();
		float wph = (float)rw / rh;
		if (wph * getHeight() < getWidth()) {
			int seth = (int) Math.ceil(getWidth() / wph);
			mOffsetY = (seth-getHeight())/2;
			bg.setBounds(0, -mOffsetY, getWidth(), mOffsetY+getHeight());
			mZoom = getWidth() / rw;
		} else {
			int setw = (int) Math.ceil(getHeight() * wph);
			mOffsetX = (setw-getWidth())/2;
			bg.setBounds(-mOffsetX, 0, mOffsetX+getWidth(), getHeight());
			mZoom = getHeight() / rh;
		}
		bg.draw(canvas);
	}
	
	public static int getAct() {
		return mAct;
	}

	public void setAct(int act) {
		if (act >= 0 && act < bgArray.length()) {
			mAct = act;
			invalidate(0, 0, getWidth(), getHeight());
		}
	}
}