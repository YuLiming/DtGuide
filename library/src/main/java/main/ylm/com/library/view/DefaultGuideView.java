package main.ylm.com.library.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import main.ylm.com.library.R;

/**
 * Created by YLM on 2017/9/15.
 */

public class DefaultGuideView extends RelativeLayout{

    private TextView guideText;

    private ImageView backImage;

    public DefaultGuideView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.dtguide_default_guideview,this);
        guideText = (TextView)findViewById(R.id.guide_text);
        backImage = (ImageView)findViewById(R.id.guide_back);
    }

}
