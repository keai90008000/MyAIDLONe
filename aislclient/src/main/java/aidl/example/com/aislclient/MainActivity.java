package aidl.example.com.aislclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.Aidl.IImoocSidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button but_add;
    private EditText et_num1;
    private EditText et_num2;
    private EditText et_res;
    private IImoocSidl iImoocSidl;

    ServiceConnection conn = new ServiceConnection() {


        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
          //拿到了远程的服务
            iImoocSidl = IImoocSidl.Stub.asInterface(service);
        }
        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
         //回收资源
          iImoocSidl= null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        //软件一启动就计算
        binderService();
    }
private void initview() {
        but_add = (Button) findViewById(R.id.but_add);
        et_num1 = (EditText) findViewById(R.id.et_num1);
        et_num2 = (EditText) findViewById(R.id.et_num2);
        et_res = (EditText) findViewById(R.id.et_res);
         but_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int num1 = Integer.parseInt(et_num1.getText().toString().trim());
        int num2 = Integer.parseInt(et_num2.getText().toString().trim());
        try {
            //掉用远程的服务
            int res = iImoocSidl.add(num1, num2);
            et_res.setText(res+"");
        } catch (RemoteException e) {
            e.printStackTrace();
            et_res.setText("错误了");
        }
    }

    private void binderService() {
        //获取到服务端

        Intent intent = new Intent();
        //新版本 必须 显示Intent启动 绑定服务
        intent.setComponent(new ComponentName("com.example.Aidl","com.example.Aidl.IRemoteSrvice"));
        //intent.setAction("com.example.Aidl.IRemoteSrvice");
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}

