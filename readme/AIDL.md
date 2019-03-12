# AIDL

跨进程如何传递数据？
.两个进程无法直接通信；
.通过Android系统底层间接通信。

## 目录

### 一.AIDL的基本理论
1. AIDL:Android Interface Definition Language,即Android接口定义语言，在Android中，一个进程无法直接访问
    另外一个进程的数据，所以，他们需要将他们想要传递的数据打包成操作系统能够认识的基本数据。
    
    (1)AIDL IPC,多个应用程序，多线程
    (2)Binder 只有IPC,没有多线程，多个应用程序
    (3)Messenger 只有IPC,没有多线程
    只有允许不同应用的客户端用 IPC 方式访问服务，并且想要在服务中处理多线程时，才有必要使用 AIDL。 如
    果您不需要执行跨越不同应用的并发 IPC，就应该通过实现一个 Binder 创建接口；或者，如果您想执行 IPC，
    但根本不需要处理多线程，则使用 Messenger 类来实现接口。无论如何，在实现 AIDL 之前，请您务必理解绑定服务。

### 二.AIDL远程调用案例
1. 步骤1
    在服务端创建aidl文件，并创建远程服务IRemoteService继承自Service。
```aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements

interface IMoocAidl {
    int add(int num1,int num2);
}
```

```java
public class IRemoteService extends Service {
    /**
     * 当客户端绑定到该服务的时候执行
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private IBinder mIBinder = new IMoocAidl.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("AIDL_TEST", "收到了远程的请求，输入的参数是" + num1 + "和" + num2);
            return num1 + num2;
        }
    };
}
```
将IRemoteService在服务端Manifest进行注册，并设置属性exported="true",并在服务端启动的时候启动IRemoteService.

2. 步骤2
    在客户端创建与服务端一直的aidl文件，需要保持包名一致，以下为客户端aidl文件定义
```aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements

interface IMoocAidl {
    int add(int num1,int num2);
}
```
    而后在客户端启动时绑定服务端的服务，
```java
public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    private IMoocAidl mIMoocAidl;
    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            Log.d("AIDL_TEST","onServiceConnected");
            mIMoocAidl = IMoocAidl.Stub.asInterface(service);

        }

        //断开服务的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("AIDL_TEST","onServiceDisconnected");
            mIMoocAidl = null;
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
        try {
            mButton.setText(mIMoocAidl.add(Integer.valueOf(mEditText1.getText().toString().trim()),Integer.valueOf(mEditText2.getText().toString().trim()))+"");
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
```
### 三.AIDL数据传递-基本类型
    默认支持的数据类型：基本数据类型，String,CharSequence,List,Map,Parcelable
    
    服务端定义：
```aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements

interface IDataTransportServiceAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //注意short类型是不支持的
     //对于集合类型的数据，我们必须指定in/out，
    List<String> basicTypes(byte aByte,
            int anInt,
            long aLong,
            boolean aBoolean,
            float aFloat,
            double aDouble,
             char aChar,
             String aString, in List<String> aList);
}
```

```java
public class IRemoteService extends Service {
    /**
     * 当客户端绑定到该服务的时候执行
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder2;
    }

    private IBinder mIBinder = new IMoocAidl.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("AIDL_TEST", "收到了远程的请求，输入的参数是" + num1 + "和" + num2);
            return num1 + num2;
        }
    };

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
}
```
    客户端定义：
```aidl
// IDataTransportService.aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements

interface IDataTransportServiceAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     //注意short类型是不支持的
     //对于集合类型的数据，我们必须指定in/out，
    List<String> basicTypes(byte aByte,
            int anInt,
            long aLong,
            boolean aBoolean,
            float aFloat,
            double aDouble,
             char aChar,
             String aString, in List<String> aList);
}
```

```java
public class MainActivity extends AppCompatActivity {

    private EditText mEditText1;
    private EditText mEditText2;
    private IDataTransportServiceAidl mIMoocAidl;
    private ServiceConnection conn = new ServiceConnection() {
        //绑定上服务的时候调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            Log.d("AIDL_TEST","onServiceConnected");
            mIMoocAidl = IDataTransportServiceAidl.Stub.asInterface(service);

        }

        //断开服务的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("AIDL_TEST","onServiceDisconnected");
            mIMoocAidl = null;
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
        try {
//            mButton.setText(mIMoocAidl.add(Integer.valueOf(mEditText1.getText().toString().trim()),Integer.valueOf(mEditText2.getText().toString().trim()))+"");
            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList.add("cccccccc");
            stringArrayList.add("dddddddd");
            List<String> resList = mIMoocAidl.basicTypes((byte) 0x00, 2, 3l, true, 1.2f, 1.23, 'a', "b", stringArrayList);
            for (int i = 0; i < resList.size(); i++) {
                Log.d("AIDL_TEST",resList.get(i));
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
```

打印结果如下：
```
    byte_0
    int_2
    long_3
    boolean_true
    float_1.2
    double_1.23
    char_a
    Stringb
    cccccccc
    dddddddd
```
### 四.AIDL数据传递-自定义数据类型
    以下我们来传递自定的Person对象，服务端代码需要创建Person.java文件以及Person.aidl和相应功能的
    aidl文件，这里叫IMoocSelfDifineDataAidl.aidl。
```java
package com.jeremy.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(Parcel source) {
        //这里读取的顺序和必须和写入的顺序保持一致
        this.name = source.readString();
        this.age = source.readInt();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //将Person里面的字段写入Parcel
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}

```
```aidl
// IMoocAidl.aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements

parcelable Person;

```

```aidl
// IMoocSelfDifineDataAidl.aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements
//如果仅仅定义这个AIDL文件，这里面放入了Person，系统无法识别，需要在定义一个名为Person.aidl文件
import com.jeremy.aidl.Person;

interface IMoocSelfDifineDataAidl {
    List<Person> add(in Person person);
}

```

客户端同样需要在相同的包名路径下创建Person.java文件，同样在客户端的aidl文件夹里面需要定义Person.aidl
以及IMoocSelfDifineDataAidl.aidl


### 五.AIDL原理简单剖析
<img "https://github.com/JeremyHwc/JVariousCourses/blob/master/aidlclient/src/main/res/drawable/AIDL.jpg" width=600 height=500/>


