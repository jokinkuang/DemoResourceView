package com.jokin.demo.remoteviewclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by jokin on 2018/7/27 14:49.
 */

public class CView extends FrameLayout {
    public CView(@NonNull Context context) {
        super(context);
    }

    public CView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
