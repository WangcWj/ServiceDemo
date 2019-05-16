package cn.wang.service.servicedemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class NewActivity extends AppCompatActivity implements View.OnClickListener {
    private MyServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        myServiceConnection = new MyServiceConnection();
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);

        Intent intent = new Intent(this, AidlService.class);
        startService(intent);
        Log.e("WANG", "NewActivity.onCreate.");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("WANG", "NewActivity.onStart.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("WANG", "NewActivity.onResume.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("WANG", "NewActivity.onPause.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("WANG", "NewActivity.onStop.");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AidlService.class);
        switch (v.getId()) {
            case R.id.btn1:
                startService(intent);
                break;
            case R.id.btn2:
                bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn3:
                stopService(intent);
                break;
            case R.id.btn4:
                unbindService(myServiceConnection);
                break;
            default:
                break;
        }
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("WANG", "MyServiceConnection.onServiceDisconnected.NewActivity" + name);
        }
    }
}
