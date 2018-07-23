# JVariousCourses

## 一、重识Activity 

    Activity本质是什么？
    
    Activity生命周期
    
        一个Activity生命周期
            onCreate()-->created
            -->onStart()-->started(visible)
            -->onResume()-->Resumed(visible)
            -->onPause()-->paused(partially visible)
            -->onStop()-->stoped(hidden)
            -->onDestroy();
            
        多个Activity交互的生命周期
        启动Activity A，
        
        Activity生命周期交互设计思想
        Activity的横竖屏切换
        生命周期应用场景
        
    Activity启动的方式
    
        直接启动
        间接启动
        
    Activity之间的数据交互
    
    如何启动系统的Activity
    
    Activity启动模式