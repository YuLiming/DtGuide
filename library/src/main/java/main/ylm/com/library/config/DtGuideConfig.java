package main.ylm.com.library.config;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by YLM on 2017/9/14.
 */

public class DtGuideConfig {

    private static DtGuideConfig mInstance;

    private int mBackgroundColor;

    private AnimationConfig mAnimationConfig;

    private HighlightConfig mHighlightConfig;


    public DtGuideConfig(Builder builder){
        mAnimationConfig =
                builder.mAnimationConfig == null ?
                        getDefaultAnimationConfig() :
                        builder.mAnimationConfig;


        mHighlightConfig =
                builder.mHighlightConfig == null ?
                        getDefaultHighlightConfig() :
                        builder.mHighlightConfig;

        mBackgroundColor =
                builder.mBackgroundColor == 0 ?
                        getDefaultBackgroundColor() :
                        builder.mBackgroundColor;

        mInstance = this;
    }

    public int getBackgroundColor(){
        return mBackgroundColor;
    }

    public AnimationConfig getAnimationConfig(){
        return mAnimationConfig;
    }

    public HighlightConfig getHighlightConfig(){
        return mHighlightConfig;
    }


    private int getDefaultBackgroundColor(){
        return Color.argb(180,0,0,0);
    }

    private AnimationConfig getDefaultAnimationConfig(){
        return AnimationConfig.newBuilder().build();
    }

    private HighlightConfig getDefaultHighlightConfig(){
        return HighlightConfig.newBuilder().build();
    }

    public static Builder newBuilder(Context context){
        return new Builder(context);
    }

    public static class Builder{

        private Context mContext;

        private int mBackgroundColor;

        private AnimationConfig mAnimationConfig;

        private HighlightConfig mHighlightConfig;


        private Builder(Context context){
            mContext = context;
        }

        public Builder setBackgroundColor(int backgroundColor){
            mBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setAnimationConfig(AnimationConfig animationConfig){
            mAnimationConfig = animationConfig;
            return this;
        }

        public Builder setHighlightConfig(HighlightConfig highlightConfig){
            mHighlightConfig = highlightConfig;
            return this;
        }


        public DtGuideConfig build(){
            return new DtGuideConfig(this);
        }
    }
}
