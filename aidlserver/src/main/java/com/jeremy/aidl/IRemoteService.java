package com.jeremy.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class IRemoteService extends Service {

    private ArrayList<Person> mPersonArrayList;

    /**
     * 当客户端绑定到该服务的时候执行
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder3;
    }

    private IBinder mIBinder = new IMoocAidl.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("AIDL_TEST", "收到了远程的请求，输入的参数是" + num1 + "和" + num2);
            return num1 + num2;
        }
    };

    /**
     * 传递基本数据类型
     */
    private IBinder mIBinder2 = new IDataTransportServiceAidl.Stub() {
        @Override
        public List<String> basicTypes(byte aByte,
                                       int anInt,
                                       long aLong,
                                       boolean aBoolean,
                                       float aFloat,
                                       double aDouble,
                                       char aChar,
                                       String aString,
                                       List<String> aList) throws RemoteException {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("byte_" + aByte);
            strings.add("int_" + anInt);
            strings.add("long_" + aLong);
            strings.add("boolean_" + aBoolean);
            strings.add("float_" + aFloat);
            strings.add("double_" + aDouble);
            strings.add("char_" + aChar);
            strings.add("String" + aString);
            strings.addAll(aList);
            return strings;
        }
    };

    private IBinder mIBinder3 = new IMoocSelfDifineDataAidl.Stub() {
        @Override
        public List<Person> add(Person person) throws RemoteException {
            Log.d("AIDL_TEST","我收到了客户端传入的自定义Person:"+person.getName()+"--"+person.getAge());
            if (mPersonArrayList == null) {
                mPersonArrayList = new ArrayList<>();
            }
            mPersonArrayList.add(person);
            return mPersonArrayList;
        }
    };
}
