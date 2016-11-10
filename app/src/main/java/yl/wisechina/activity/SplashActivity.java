package yl.wisechina.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import yl.wisechina.R;

public class SplashActivity extends Activity {

    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rlRoot = (RelativeLayout) findViewById(R.id.rl);
        startAnim();


    }

    private void startAnim() {
        AnimationSet set = new AnimationSet(false);
        ///旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        rotate.setDuration(3000);
        rotate.setFillAfter(true);//保持动画状态

        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(3000);
        scale.setFillAfter(true);

        //透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);

        //添加集合
        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        rlRoot.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    /**
     * 跳转到下一个页面
     */
    private void jumpNextPage() {
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean userguide = sp.getBoolean("is_user_guide_showed", false);
        if (userguide) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        }

    }


}
