package com.tencent.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jeremy.aidl.IMoocSelfDifineDataAidl;
import com.jeremy.aidl.Person;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    //    private IDataTransportServiceAidl mIMoocAidl;
    private IMoocSelfDifineDataAidl mIMoocAid2;
    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            Log.d("AIDL_TEST", "onServiceConnected");
            mIMoocAid2 = IMoocSelfDifineDataAidl.Stub.asInterface(service);

        }

        //断开服务的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("AIDL_TEST", "onServiceDisconnected");
            mIMoocAid2 = null;
        }
    };
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText1 = findViewById(R.id.et_num1);
        mEditText2 = findViewById(R.id.et_num2);
        mButton = findViewById(R.id.bt_res);
        bindService();
    }

    public void onAdd(View view) {
//        try {
////            mButton.setText(mIMoocAidl.add(Integer.valueOf(mEditText1.getText().toString().trim()),Integer.valueOf(mEditText2.getText().toString().trim()))+"");
//            ArrayList<String> stringArrayList = new ArrayList<>();
//            stringArrayList.add("cccccccc");
//            stringArrayList.add("dddddddd");
//            List<String> resList = mIMoocAidl.basicTypes((byte) 0x00, 2, 3l, true, 1.2f, 1.23, 'a', "b", stringArrayList);
//            for (int i = 0; i < resList.size(); i++) {
//                Log.d("AIDL_TEST", resList.get(i));
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

        try {
            mIMoocAid2.add(new Person("zhangsan",18));
            List<Person> personList = mIMoocAid2.add(new Person("lisi", 19));
            for (int i = 0; i < personList.size(); i++) {
                Person person = personList.get(i);
                Log.i("AIDL_TEST",person.getName()+"------"+person.getAge());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindService() {
        //获取到服务端
        Intent intent = new Intent();
        //5.0以后必须显示Intent启动绑定服务
        intent.setComponent(new ComponentName("com.jeremy.aidl", "com.jeremy.aidl.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
