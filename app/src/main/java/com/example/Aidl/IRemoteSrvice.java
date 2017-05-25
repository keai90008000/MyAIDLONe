package com.example.Aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 陈驰 on 2017/5/25.
 */

public class IRemoteSrvice extends Service {
   /*
   * 当客户端绑定到该服务的时候会执行
   * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private  IBinder iBinder =  new IImoocSidl.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {
           Log.d("sss","收到了远程的请求，输入的num1和num2");
            return num1+num2;
        }
    };
}
