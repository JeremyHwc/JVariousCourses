package com.tencent.hotfix;

import android.content.Context;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * author: Jeremy
 * date: 2018/7/24
 * desc: 静态内部类单例模式，性能优越，完成懒汉式延迟加载，线程安全
 */
public class AndFixPatchManager {
    private AndFixPatchManager() {

    }

    public static AndFixPatchManager getInstance() {
        return Holder.AND_FIX_PATCH_MANAGER;
    }

    private static class Holder {
        private static final AndFixPatchManager AND_FIX_PATCH_MANAGER = new AndFixPatchManager();
    }

    private static PatchManager mPacthManager;

    /**
     * 初始化 AndFix
     */
    public void initPatch(Context context){
        try {
            mPacthManager=new PatchManager(context);
            mPacthManager.init(context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName);
            mPacthManager.loadPatch();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载我们的pacth文件
     */
    public void addPatch(String path){
            try {
                if (mPacthManager!=null){
                    mPacthManager.addPatch(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

}
