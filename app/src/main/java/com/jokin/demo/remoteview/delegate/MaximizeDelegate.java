package com.jokin.demo.remoteview.delegate;

import android.view.ViewGroup;

/**
 * Created by jokin on 2018/7/19 15:01.
 */

public class MaximizeDelegate {
    private static final String TAG = "MinimizeDelegate";

    private IWindow mWindow;
    private IWindow.LayoutParams mParams;

    public MaximizeDelegate(IWindow target) {
        mWindow = target;
    }

    public void maximize() {
        mParams = mWindow.getWindowLayoutParams();
        mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mWindow.setWindowLayoutParams(mParams);
    }
}
