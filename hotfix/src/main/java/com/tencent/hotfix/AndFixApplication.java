package com.tencent.hotfix;

import android.app.Application;

/**
 * author: Jeremy
 * date: 2018/7/24
 * desc:
 */
public class AndFixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化AndFix
        initAndFix();
    }

    /**
     * 初始化AndFix
     */
    private void initAndFix() {
        AndFixPatchManager.getInstance().initPatch(this);
    }
}
