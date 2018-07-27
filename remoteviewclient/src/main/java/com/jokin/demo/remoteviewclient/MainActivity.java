package com.jokin.demo.remoteviewclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity:Client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLayoutRemote(R.layout.layout_remote);
            }
        });

        findViewById(R.id.btnText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextRemote(R.id.text2, "hello android!");
            }
        });
    }

    private void setLayoutRemote(int remote_layout_id) {
        Log.d(TAG, "setLayoutRemote() called with: remote_layout_id = [" + remote_layout_id + "]");
        Intent intent = new Intent(Constants.ACTION);
        intent.putExtra(Constants.EXTRA_OP, Constants.OP_SET_LAYOUT);
        intent.putExtra(Constants.EXTRA_LAYOUT_ID, remote_layout_id);
        intent.putExtra(Constants.EXTRA_PACKAGE_NAME, getPackageName());
        sendBroadcast(intent);
    }

    private void setTextRemote(int id, String text) {
        Log.d(TAG, "setTextRemote() called with: id = [" + id + "], text = [" + text + "]");
        Intent intent = new Intent(Constants.ACTION);
        intent.putExtra(Constants.EXTRA_OP, Constants.OP_SET_TEXT);
        intent.putExtra(Constants.EXTRA_RESOURCE_ID, id);
        intent.putExtra(Constants.EXTRA_RESOURCE_VALUE, text);
        sendBroadcast(intent);
    }

}
