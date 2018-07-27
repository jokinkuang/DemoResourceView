package com.jokin.demo.remoteview.delegate;

import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by jokin on 2018/7/19 10:36.
 */

public class ScaleDelegate {
    private static final String TAG = "ScaleDelegate";
    private IWindow mWindow;
    private IWindow.LayoutParams mParams;
    private ViewGroup.LayoutParams mContentParams;

    private final int WINDOW_WIDTH = 1920;
    private final int WINDOW_HEIGHT = 1080;
    private final int WINDOW_WIDTH_MIN = 200;
    private final int WINDOW_HEIGHT_MIN = 200;

    private int mTouchLastX;
    private int mTouchLastY;

    public ScaleDelegate(IWindow target) {
        mWindow = target;
    }

    public void handleEvent(MotionEvent event) {
        Log.d(TAG, "handleEvent() called with: event = [" + event + "]");
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        Log.d(TAG, "handleEvent: x="+x+"|y="+y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "handleEvent: last x="+x+",y="+y);
                mTouchLastX = x;
                mTouchLastY = y;
                onStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - mTouchLastX;
                int offsetY = y - mTouchLastY;
                Log.d(TAG, "handleEvent: offset ox="+offsetX+",oy="+offsetY);
                onContinue(offsetX, offsetY);
                // a new move with new width.
                mTouchLastX = x;
                mTouchLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                // offsetX = x - mTouchLastX;
                // offsetY = y - mTouchLastY;
                onEnd(x, y);
                // do not break;
            default:
                break;
        }
    }

    private void onStart(int x, int y) {
        Log.d(TAG, "onStart() called with: x = [" + x + "], y = [" + y + "]");
        mParams = mWindow.getWindowLayoutParams();
        mParams.width = mWindow.getContentView().getWidth();
        mParams.height = mWindow.getContentView().getHeight();
        Log.d(TAG, "onStart: "+mParams);
        mContentParams = mWindow.getContentView().getLayoutParams();
    }

    private void onContinue(int x, int y) {
        if (x == 0 && y == 0) {
            return;
        }
        // scaleParams(mParams, x, y);
        // scaleParams(mContentParams, x, y);
        Log.d(TAG, "onContinue: old width="+mParams.width+", height="+mParams.height);
        mParams.width += x;
        mParams.height += y;
        Log.d(TAG, "onContinue: new width="+mParams.width+", height="+mParams.height);
        mWindow.setWindowLayoutParams(mParams);
        // mWindow.getContentView().setLayoutParams(mContentParams);
    }

    private void scaleParams(ViewGroup.LayoutParams params, int x, int y) {
        Log.d(TAG, "scaleParams x = [" + x + "], y = [" + y + "]");
        params.width += x;
        params.height += y;
        params.width = mParams.width < WINDOW_WIDTH_MIN
                ? WINDOW_WIDTH_MIN : mParams.width;
        params.height = mParams.height < WINDOW_HEIGHT_MIN
                ? WINDOW_HEIGHT_MIN : mParams.height;

        params.width = mParams.width > WINDOW_WIDTH - mParams.x
                ? WINDOW_WIDTH - mParams.x : mParams.width;
        params.height = mParams.height > WINDOW_HEIGHT - mParams.y
                ? WINDOW_HEIGHT - mParams.y : mParams.height;
    }

    private void onEnd(int x, int y) {
        Log.d(TAG, "onEnd() called with: x = [" + x + "], y = [" + y + "]");

        // mContentParams.width = x;
        // mContentParams.height = y;
        // mWindow.getContentView().setLayoutParams(mContentParams);

        // mParams.width = x;
        // mParams.height = y;
        // mWindow.setWindowLayoutParams(mParams);
    }
}
