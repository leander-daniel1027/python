package com.mypackage;

import android.app.Application;


public class MyApplication extends Application
{

   public static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static MyApplication getInstance(){
        if(myApplication==null){
            myApplication=new MyApplication();
        }

        return myApplication;
    }

}
