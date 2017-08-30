package com.rj.testhook;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSystemService(ACTIVITY_SERVICE);
    }

    public void onClick(View view) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("data", "Jeozey"));

        ClipData cd = cm.getPrimaryClip();
        String msg = cd.getItemAt(0).getText().toString();
        Log.e(TAG, "testClipboard: " + msg);
        Toast.makeText(getBaseContext(), "msg:" + msg, Toast.LENGTH_SHORT).show();

    }
}
