package cn.wang.service.servicedemo;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created to :
 *
 * @author WANG
 * @date 2019/5/9
 */
public class CusIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CusIntentService(String name) {
        super("CusIntentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

    }
}
