package com.jokin.demo.remoteview;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity:Server";

    private ViewGroup mRootView;
    private View mRemoteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcast();

        mRootView = findViewById(R.id.rootView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dump();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }

    ///////////////////////////////

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter(Constants.ACTION);
        registerReceiver(mReceiver, filter);
    }

    private void unregisterBroadcast() {
        unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            if (! Constants.ACTION.equalsIgnoreCase(intent.getAction())) {
                return;
            }
            String op = intent.getStringExtra(Constants.EXTRA_OP);
            if (Constants.OP_SET_LAYOUT.equalsIgnoreCase(op)) {
                int id = intent.getIntExtra(Constants.EXTRA_LAYOUT_ID, -1);
                String pkgName = intent.getStringExtra(Constants.EXTRA_PACKAGE_NAME);
                if (id == -1) {
                    return;
                }
                setLayout(id, pkgName);
            } else if (Constants.OP_SET_TEXT.equalsIgnoreCase(op)) {
                int id = intent.getIntExtra(Constants.EXTRA_RESOURCE_ID, -1);
                if (id == -1) {
                    return;
                }
                setText(id, intent.getStringExtra(Constants.EXTRA_RESOURCE_VALUE));
            }
        }
    };

    private void dump() {
        Context c = prepareContext(this, "com.tencent.weread");
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = inflater.cloneInContext(c);
        try {
            mRemoteView = inflater.inflate(R.layout.activity_main, mRootView, false);
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
        Log.d(TAG, "dump: Context:"+c);
        Log.d(TAG, "package code path:" + c.getPackageCodePath());
        Log.d(TAG, "package cache dir path:" + c.getCacheDir().getPath());
        Log.d(TAG, "package string0 :" + c.getString(R.string.app_name));
        Log.d(TAG, "package string1 :" + c.getString(R.string.app_name+1));
        Log.d(TAG, "package string2 :" + c.getString(R.string.app_name+2));
        Log.d(TAG, "package string3 :" + c.getString(R.string.app_name+3));
        Log.d(TAG, "package drawable0:" + c.getDrawable(R.drawable.ic_launcher_background));
        Log.d(TAG, "package drawable1:" + c.getDrawable(R.drawable.ic_launcher_background+1));
        Log.d(TAG, "package drawable2:" + c.getDrawable(R.drawable.ic_launcher_background+2));
    }

    private void setLayout(int layoutID, String pkgName) {
        Log.d(TAG, "setLayout() called with: layoutID = [" + layoutID + "]");

        Context c = prepareContext(this, pkgName);
        LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = inflater.cloneInContext(c);
        mRemoteView = inflater.inflate(layoutID, mRootView, false);
        Log.d(TAG, "setLayout: mRemoteView="+mRemoteView);
        if (mRemoteView != null) {
            mRootView.addView(mRemoteView);
        }

        mRemoteView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
    }

    private void setText(int viewId, String text) {
        Log.d(TAG, "setText() called with: viewId = [" + viewId + "], text = [" + text + "]");
        TextView textView = mRootView.findViewById(viewId);
        Log.d(TAG, "setText: textView:"+textView);
        if (textView != null) {
            textView.setText(text);
        }
    }

    private Context prepareContext(Context context, String pkgName) {
        Context c;
        String packageName = pkgName;

        if (packageName != null) {
            try {
                c = context.createPackageContext(packageName, Context.CONTEXT_INCLUDE_CODE|Context.CONTEXT_IGNORE_SECURITY);
                // c = context.createPackageContextAsUser(
                //         packageName, Context.CONTEXT_RESTRICTED, mUser);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Package name " + packageName + " not found");
                c = context;
            }
        } else {
            c = context;
        }

        return c;
    }
}
