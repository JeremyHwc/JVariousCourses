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

### 二.AIDL远程调用案例
### 三.AIDL原理简单剖析

