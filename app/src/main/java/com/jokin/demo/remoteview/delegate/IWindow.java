package com.jokin.demo.remoteview.delegate;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jokin on 2018/7/16 10:51.
 */

public interface IWindow {

    /**
     * @return the view of the window
     */
    View getContentView();

    void setWindowLayoutParams(IWindow.LayoutParams layoutParams);
    IWindow.LayoutParams getWindowLayoutParams();

    /**
     * 通知：窗口激活
     */
    void notifyActivated();


    /**
     * 通知：触摸事件
     */
    boolean onTouch(MotionEvent event);


    //////////////////////

    class LayoutParams extends ViewGroup.LayoutParams {
        public static final int INT_INVALID = -99999;
        public int flags = INT_INVALID;
        public int gravity = Gravity.LEFT|Gravity.TOP;
        public int x = 0;
        public int y = 0;
        public int width = WRAP_CONTENT;
        public int height = WRAP_CONTENT;

        public LayoutParams() {
            super(INT_INVALID, INT_INVALID);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        @Override
        public boolean equals(Object obj) {
            LayoutParams layoutParams = (LayoutParams) obj;
            if (layoutParams == null) {
                return false;
            }
            if (flags != layoutParams.flags) {
                return false;
            }
            if (gravity != layoutParams.gravity) {
                return false;
            }
            if (x != layoutParams.x) {
                return false;
            }
            if (y != layoutParams.y) {
                return false;
            }
            if (width != layoutParams.width) {
                return false;
            }
            if (height != layoutParams.height) {
                return false;
            }
            return true;
        }

        public static class Builder {
            private LayoutParams mLayoutParams = new LayoutParams();

            public Builder width(int width) {
                mLayoutParams.width = width;
                return this;
            }

            public Builder height(int height) {
                mLayoutParams.height = height;
                return this;
            }

            public Builder x(int x) {
                mLayoutParams.x = x;
                return this;
            }

            public Builder y(int y) {
                mLayoutParams.y = y;
                return this;
            }

            public Builder gravity(int gravity) {
                mLayoutParams.gravity = gravity;
                return this;
            }

            public Builder flags(int flags) {
                mLayoutParams.flags = flags;
                return this;
            }

            public LayoutParams build() {
                return mLayoutParams;
            }
        }
    }
}
