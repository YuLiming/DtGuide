package main.ylm.com.library.config;

import android.graphics.Color;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by YLM on 2017/9/14.
 */

public class HighlightConfig {

    private int mRadius;

    private int mHighlightColor;

    private int shape;

    public HighlightConfig(Builder builder){
        mRadius =
                builder.mRadius == 0 ?
                        -1:builder.mRadius;

        mHighlightColor =
                builder.mHighlightColor == 0?
                        Color.argb(0,0,0,0):builder.mHighlightColor;

        shape =
                builder.shape == 0?
                        Shape.CIRCLE:builder.shape;
    }

    public int getRadius(){
        return mRadius;
    }

    public int getHighlightColor(){
        return mHighlightColor;
    }

    public int getShape(){
        return shape;
    }


    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private int mRadius;

        private int mHighlightColor;

        private int shape;

        public Builder setRadius(int radius){
            mRadius = radius;
            return this;
        }

        public Builder setHighlightColor(int color){
            mHighlightColor = color;
            return this;
        }

        public Builder setShape(@Shape int shape){
            this.shape = shape;
            return this;
        }

        public HighlightConfig build(){
            return new HighlightConfig(this);
        }

    }

    @IntDef({Shape.CIRCLE,Shape.SQUARE,Shape.CUSTOM,Shape.OUTLINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape{
        int SQUARE = 0;
        int CIRCLE  = 1;
        int CUSTOM = 2;
        int OUTLINE = 3;
    }
}
