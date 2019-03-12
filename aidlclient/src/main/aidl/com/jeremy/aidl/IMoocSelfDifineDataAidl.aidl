// IMoocSelfDifineDataAidl.aidl
package com.jeremy.aidl;

// Declare any non-default types here with import statements
//如果仅仅定义这个AIDL文件，这里面放入了Person，系统无法识别，需要在定义一个名为Person.aidl文件
import com.jeremy.aidl.Person;

interface IMoocSelfDifineDataAidl {
    List<Person> add(in Person person);
}
