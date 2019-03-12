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
