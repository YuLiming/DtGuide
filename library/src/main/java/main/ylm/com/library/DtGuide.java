package main.ylm.com.library;

import android.content.Context;

import main.ylm.com.library.config.DtGuideConfig;


/**
 * Created by YLM on 2017/9/14.
 */

public class DtGuide {

    private Context mContext;

    private static DtGuide mInstance;

    private DtGuide(){
    }

    // Initializes DtGuide with default config.
    public static void initialize(Context context){
        initialize(context,null);
    }

    // Initializes DtGudie with the specified config.
    public static void initialize(Context context , DtGuideConfig dtGuideConfig){
        if (dtGuideConfig == null){
            DtGuideFactory.initialize(context);
        }else{
            DtGuideFactory.initialize(context,dtGuideConfig);
        }
    }

    public DtGuideFactory getDtGuideFactory(){
        return DtGuideFactory.getInstance();
    }

}
