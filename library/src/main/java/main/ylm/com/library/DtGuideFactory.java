package main.ylm.com.library;

import android.content.Context;

import main.ylm.com.library.config.DtGuideConfig;

/**
 * Created by YLM on 2017/9/14.
 */

public class DtGuideFactory {

    private Context mContext;

    private static DtGuideFactory sInstance = null;

    private DtGuideConfig mConfig;

    public static void initialize(Context context , DtGuideConfig dtGuideConfig){
        sInstance = new DtGuideFactory(context,dtGuideConfig);
    }

    public static void initialize(Context context){
        initialize(context,DtGuideConfig.newBuilder(context).build());
    }

    public static DtGuideFactory getInstance(){
        if (sInstance==null){
            throw new NullPointerException("DtGuideFactory was not initialized");
        }else {
            return sInstance;
        }
    }

    public DtGuideFactory(Context context,DtGuideConfig dtGuideConfig){
        mConfig = dtGuideConfig;
        mContext = context;
    }

    public DtGuideConfig getDtGuideConfig(){
        return mConfig;
    }

}
