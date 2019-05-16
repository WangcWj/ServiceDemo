package cn.wang.service.servicedemo;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        myServiceConnection = new MyServiceConnection();

        Log.e("WANG", "MainActivity.onCreate." + getCurProcessname(this));
    }

    private String getCurProcessname(Context context) {
        int i = Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (null != manager) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = manager.getRunningAppProcesses();
            if (null != runningAppProcesses && runningAppProcesses.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo info : runningAppProcesses) {
                    if (info.pid == i) {
                        return info.processName;
                    }
                }
            }
        }
        return "";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("WANG", "MainActivity.onResume.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("WANG", "MainActivity.onDestroy.");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AidlService.class);
        switch (v.getId()) {
            case R.id.btn1:
                startService(intent);
                break;
            case R.id.btn2:
                stopService(intent);
                break;
            case R.id.btn3:
                bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn4:
                unbindService(myServiceConnection);
                break;
            case R.id.btn5:
                startService(intent);
                bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn6:
                stopService(intent);
                unbindService(myServiceConnection);
                break;
            case R.id.btn7:
                Intent intent1 = new Intent(this, NewActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn8:
                Intent intent2 = new Intent();
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent2.setComponent(new ComponentName("demo.wang.cn.servicetwodemo",
//                        "demo.wang.cn.servicetwodemo.MainActivity"));
//                intent2.setPackage("demo.wang.cn.servicetwodemo.MainActivity");
//                bindService(intent2, myServiceConnection, Context.BIND_AUTO_CREATE);
//                startService(intent2);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("WANG", "MyServiceConnection.onServiceConnected." + service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("WANG", "MyServiceConnection.onServiceDisconnected." + name);
        }
    }
}
