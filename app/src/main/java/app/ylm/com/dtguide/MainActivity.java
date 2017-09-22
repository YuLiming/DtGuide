package app.ylm.com.dtguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import main.ylm.com.library.DtGuide;
import main.ylm.com.library.GuideHandler;
import main.ylm.com.library.config.HighlightConfig;

public class MainActivity extends AppCompatActivity {

    private GuideHandler dtGuide;
    public GuideHandler dtGuide2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView)findViewById(R.id.text);
        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        DtGuide.initialize(this);
        dtGuide = GuideHandler.with(this)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dtGuide.hide();
                    }
                })
                .setDirction(GuideHandler.Dirction.LEFT)
                .setOffset(13,100)
                .displayOn(textView);
        dtGuide.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtGuide.show();
            }
        });

        TextView textView1 =  new TextView(this);
        textView1.setText("asdf");
        dtGuide2 = GuideHandler.with(this)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dtGuide2.hide();
                    }
                })
                .setGuideView(textView1)
                .setShape(HighlightConfig.Shape.SQUARE)
                .displayOn(textView);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dtGuide2.show();
            }
        });


    }

}
