package main.ylm.com.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import main.ylm.com.library.config.DtGuideConfig;
import main.ylm.com.library.config.HighlightConfig;
import main.ylm.com.library.view.DefaultGuideView;
import main.ylm.com.library.view.DtGuideView;

/**
 * Created by YLM on 2017/9/19.
 */

public class GuideHandler {

    private Context mContext;

    private DtGuideConfig mConfig;

    private View mTargetView;

    private DtGuideView dtGuideView;

    private View guideView;

    private Path mHighlightPath;

    private int[] center;

    private int[] location;

    private int height;

    private int width;

    private int offsetX,offsetY;

    private int radius;

    private int shape;

    private int mDirction = Dirction.BOTTOM;

    private boolean isShow;

    private View.OnClickListener listener;

    public static final int MIN_CLICK_DELAY_TIME = 1000;

    private long lastClickTime = 0;

    public static GuideHandler with(Context context){
        return new GuideHandler(context);
    }

    private GuideHandler(Context context){
        this.mContext = context;
        mConfig = DtGuideConfig.newBuilder(context).build();
    }

    private GuideHandler(Context context,DtGuideConfig config){
        this.mContext = context;
        this.mConfig = config;
    }

    public GuideHandler displayOn(View targetView){
        this.mTargetView = targetView;
        guideView = createGuideView();
        setupView();
        return this;
    }

    public void hide(){
        if (mTargetView == null){
            throw new NullPointerException("hide() must be used after displayOn()");
        }
        if (dtGuideView != null){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME){
                lastClickTime = currentTime;
                dtGuideView.startAnimation(AnimationUtils.loadAnimation(mContext,mConfig.getAnimationConfig().getExitAnim()));
                dtGuideView.setVisibility(View.GONE);
            }
        }
    }

    public void show(){
        if (mTargetView == null){
            throw new NullPointerException("show() must be used after displayOn()");
        }
        if (dtGuideView != null){
            if (dtGuideView.getParent()!=null){
                dtGuideView.setVisibility(View.VISIBLE);
                dtGuideView.startAnimation(AnimationUtils.loadAnimation(mContext,mConfig.getAnimationConfig().getEnterAnim()));
            }else{
                ((FrameLayout)((Activity)mContext).getWindow().getDecorView()).addView(dtGuideView);
                dtGuideView.startAnimation(AnimationUtils.loadAnimation(mContext,mConfig.getAnimationConfig().getEnterAnim()));
            }
        }else{
            isShow = true;
        }
    }

    public GuideHandler setGuideView(View guideView){
        this.guideView = guideView;
        return this;
    }

    public GuideHandler setDirction(@Dirction int dirction){
        this.mDirction = dirction;
        return this;
    }

    public GuideHandler setOffset(int x,int y){
        this.offsetX = x;
        this.offsetY = y;
        return this;
    }

    public GuideHandler setShape(@HighlightConfig.Shape int shape){
        this.shape = shape;
        return this;
    }

    public GuideHandler setRadius(int radius){
        this.radius = radius;
        return this;
    }

    public GuideHandler setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
        return this;
    }

    private View createGuideView(){
        return guideView == null ?
                new DefaultGuideView(mContext) :
                guideView;
    }

    private void setupView(){
        mTargetView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    //noinspection deprecation
                    mTargetView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mTargetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                location = new int[2];
                mTargetView.getLocationOnScreen(location);
                center = new int[2];
                center[0] = location[0] + mTargetView.getWidth()/2;
                center[1] = location[1] + mTargetView.getHeight()/2;
                width = mTargetView.getWidth();
                height = mTargetView.getHeight();
                if (radius==0){
                    radius = mConfig.getHighlightConfig().getRadius() == -1 ?
                            Math.max(width,height)/2: mConfig.getHighlightConfig().getRadius();
                }
                locateBackground();
                locateGuideView();
            }
        });
    }

    private void locateBackground(){
        mHighlightPath = createHighlight();
        dtGuideView = new DtGuideView(mContext,mConfig.getBackgroundColor(),mConfig.getHighlightConfig().getHighlightColor(),mHighlightPath);
        if (listener!=null){
            dtGuideView.setOnClickListener(listener);
        }
        if (isShow==true){
            ((FrameLayout)((Activity)mContext).getWindow().getDecorView()).addView(dtGuideView);
            dtGuideView.startAnimation(AnimationUtils.loadAnimation(mContext,mConfig.getAnimationConfig().getEnterAnim()));
        }

    }

    private void locateGuideView(){
        guideView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    guideView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    guideView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                int guideWidth = guideView.getWidth();
                int guideHeight = guideView.getHeight();
                RelativeLayout.LayoutParams guideViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                switch (mDirction){
                    case Dirction.BOTTOM:
                        guideViewParams.setMargins(center[0]-guideWidth/2+offsetX,center[1]+radius+offsetY,0,0);
                        break;
                    case Dirction.BOTTOM_LEFT:
                        guideViewParams.setMargins(center[0]-radius-guideWidth+offsetX,center[1]+radius+offsetY,0,0);
                        break;
                    case Dirction.BOTTOM_RIGHT:
                        guideViewParams.setMargins(center[0]+radius+offsetX,center[1]+radius+offsetY,0,0);
                        break;
                    case Dirction.LEFT:
                        guideViewParams.setMargins(center[0]-radius-guideWidth+offsetX,center[1]-guideHeight/2+offsetY,0,0);
                        break;
                    case Dirction.RIGHT:
                        guideViewParams.setMargins(center[0]+radius+offsetX,center[1]-guideHeight/2+offsetY,0,0);
                        break;
                    case Dirction.TOP:
                        guideViewParams.setMargins(center[0]-guideWidth/2+offsetX,center[1]-radius-guideHeight+offsetY,0,0);
                        break;
                    case Dirction.TOP_LEFT:
                        guideViewParams.setMargins(center[0]-radius-guideWidth+offsetX,center[1]-radius-guideHeight+offsetY,0,0);
                        break;
                    case Dirction.TOP_RIGHT:
                        guideViewParams.setMargins(center[0]+radius+offsetX,center[1]-radius-guideHeight+offsetY,0,0);
                        break;
                }
                guideView.setLayoutParams(guideViewParams);
            }
        });
        if (guideView!=null){
            dtGuideView.addView(guideView);
        }
    }

    private Path createHighlight(){
        Path path = new Path();
        shape = shape == 0 ? mConfig.getHighlightConfig().getShape(): shape;
        if (shape == HighlightConfig.Shape.CIRCLE){
            path.addCircle(center[0],center[1],radius, Path.Direction.CW);
        }else if (shape == HighlightConfig.Shape.SQUARE){
            path.addRect(center[0]-radius,center[1]-radius,center[0]+radius,center[1]+radius, Path.Direction.CW);
        }else if (shape == HighlightConfig.Shape.OUTLINE){
            path.addRect(location[0],location[1],location[0]+width,location[1]+height, Path.Direction.CW);
        }
        // TODO: 2017/9/19 custom shape
        return path;
    }


    @IntDef({Dirction.BOTTOM,Dirction.BOTTOM_LEFT,Dirction.BOTTOM_RIGHT,
            Dirction.TOP,Dirction.TOP_LEFT,Dirction.TOP_RIGHT,Dirction.LEFT,Dirction.RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Dirction{
        int TOP = 0;
        int BOTTOM = 1;
        int LEFT = 2;
        int RIGHT = 3;
        int TOP_LEFT = 4;
        int TOP_RIGHT = 5;
        int BOTTOM_LEFT = 6;
        int BOTTOM_RIGHT = 7;
    }

}
