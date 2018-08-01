package com.tencent.hotfix.tinker;
import android.content.Context;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * Created by renzhiqiang on 17/4/27.
 * 对Tinker的所有api做一层封装
 */
@SuppressWarnings("unused")

public class TinkerManager {

    private static boolean isInstalled = false;//表示Tinker是否已经安装

    private static ApplicationLike mAppLike;


    /**
     * 完成Tinker的初始化
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }

        TinkerInstaller.install(mAppLike); //完成tinker初始化
        isInstalled = true;
    }

    /**
     * 完成Patch文件的加载
     *
     * @param path patch 文件的路径
     */
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), path);
        }
    }

    /**
     * 通过ApplicationLike获取Context
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}
