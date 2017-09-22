package main.ylm.com.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.RelativeLayout;

/**
 * Created by YLM on 2017/9/11.
 */

public class DtGuideView extends RelativeLayout{

    private Path mPath;

    private int mBackgroundColor;

    private int mHighlightColor;

    private Paint mBackgroundPaint;

    private Paint mHighlightPaint;



    public DtGuideView(Context context ,int backgroundColor,int highlightColor, Path path){
        super(context);
        this.mBackgroundColor = backgroundColor;
        this.mPath = path;
        this.mHighlightColor = highlightColor;
        init();
     }


    private void init(){
        this.setBackgroundColor(Color.argb(0,0,0,0));
        this.setClickable(true);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);
        mHighlightPaint = new Paint();
        mHighlightPaint.setColor(mHighlightColor);
        mHighlightPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }



    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawBackground(canvas);

    }

    private void drawBackground(Canvas canvas){
        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas tmp = new Canvas(bitmap);
        tmp.drawRect(0,0,tmp.getWidth(),tmp.getHeight(),mBackgroundPaint);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        mHighlightPaint.setXfermode(porterDuffXfermode);
        mHighlightPaint.setAntiAlias(true);
        tmp.drawPath(mPath,mHighlightPaint);
        canvas.drawBitmap(bitmap,0,0,mBackgroundPaint);
        bitmap.recycle();
    }


}
