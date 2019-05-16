package cn.wang.service.servicedemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.wang.service.servicedemo.ipc.IMyAidlInterface;
import cn.wang.service.servicedemo.ipc.IntentBean;

public class AidlService extends Service {


    List<IntentBean> datas;

    @Override
    public void onCreate() {
        super.onCreate();
        datas = new ArrayList<>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("WANG", "AidlService.onBind.已经绑定成功!");
        initData();

        return iMyAidlInterface;
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            IntentBean bean = new IntentBean();
            bean.setName("wang");
            bean.setAge(i);
            datas.add(bean);
        }
    }

    IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public List<IntentBean> getBeans() throws RemoteException {
            String processname = getCurProcessname(AidlService.this);
            return datas;
        }

        @Override
        public void addBeansUserIn(IntentBean intent) throws RemoteException {
            if (!datas.contains(intent)) {
                intent.setName("服务端更改的我更改的  in");
                datas.add(intent);
                Log.e("WANG", "AidlService.addBeans." + intent.toString());
            }
        }

        @Override
        public void addBeansUserOut(IntentBean intent) throws RemoteException {
            if (!datas.contains(intent)) {
                intent.setName("服务端我更改的  out");
                datas.add(intent);
                Log.e("WANG", "AidlService.addBeans." + intent.toString());
            }
        }

        @Override
        public void addBeansUserInOut(IntentBean intent) throws RemoteException {
            if (!datas.contains(intent)) {
                intent.setName("服务端我更改的  inOut");
                datas.add(intent);
                Log.e("WANG", "AidlService.addBeans." + intent.toString());
            }
        }


    };

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

}
