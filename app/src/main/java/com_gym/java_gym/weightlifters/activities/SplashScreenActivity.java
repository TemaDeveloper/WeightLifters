package com_gym.java_gym.weightlifters.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.onBoarding.OnBoardingActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView textTitleCreator, textAppName;
    private CircleImageView imageLogo;
    private SharedPreferences mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        textTitleCreator = findViewById(R.id.text_creator_name_title);
        textAppName = findViewById(R.id.text_app_name);
        imageLogo = findViewById(R.id.image_logo);

        Animation animationAlphaForStart = new AlphaAnimation(0.0f, 1.0f);
        animationAlphaForStart.setDuration(2000);

        Animation animationAlphaForEnd = new AlphaAnimation(1.0f, 0.0f);
        animationAlphaForEnd.setDuration(2000);


        textTitleCreator.startAnimation(animationAlphaForStart);
        textAppName.startAnimation(animationAlphaForStart);
        imageLogo.startAnimation(animationAlphaForStart);

        animationAlphaForStart.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textTitleCreator.startAnimation(animationAlphaForEnd);
                textAppName.startAnimation(animationAlphaForEnd);
                imageLogo.startAnimation(animationAlphaForEnd);

                animationAlphaForEnd.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textTitleCreator.setVisibility(View.GONE);
                        textAppName.setVisibility(View.GONE);
                        imageLogo.setVisibility(View.GONE);

                        mSharedPref = getSharedPreferences("SP_ONBOARDING", MODE_PRIVATE);
                        boolean isFirstTime = mSharedPref.getBoolean("firstTime", true);

                        if(isFirstTime){
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putBoolean("firstTime", false);
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
                        }else
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}