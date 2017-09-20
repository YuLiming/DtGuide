package main.ylm.com.library.config;

import main.ylm.com.library.R;

/**
 * Created by YLM on 2017/9/14.
 */

public class AnimationConfig {

    private int enterAnim;

    private int exitAnim;

    public AnimationConfig(Builder builder){
        enterAnim =
                builder.enterAnim == 0 ?
                        R.anim.alpha_show:
                        builder.enterAnim;

        exitAnim =
                builder.exitAnim == 0 ?
                        R.anim.alpha_hide:
                        builder.exitAnim;
    }

    public int getEnterAnim(){
        return enterAnim;
    }

    public int getExitAnim(){
        return exitAnim;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private int enterAnim;

        private int exitAnim;

        public Builder setEnterAnim(int enterAnim){
            this.enterAnim = enterAnim;
            return this;
        }

        public Builder setExitAnim(int exitAnim){
            this.exitAnim = exitAnim;
            return this;
        }

        public AnimationConfig build(){
            return new AnimationConfig(this);
        }

    }
}
