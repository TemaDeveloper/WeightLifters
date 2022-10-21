package com_gym.java_gym.weightlifters.onBoarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.activities.MainActivity;
import com_gym.java_gym.weightlifters.models.Slide;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager viewPagerSlide;
    private OnBoardingAdapterSlider adapterSliderSlides;
    private TextView skip;
    private MaterialButton btnStart;
    private TabLayout tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        initSlides();

        final List<Slide> mList = new ArrayList<>();
        mList.add(new Slide(getResources().getString(R.string.title_1), getResources().getString(R.string.description_1), R.raw.lottie_anim_bridge_female, "01."));
        mList.add(new Slide(getResources().getString(R.string.title_2), getResources().getString(R.string.description_2), R.raw.lottie_anim_plank_male, "02."));
        mList.add(new Slide(getResources().getString(R.string.title_3), getResources().getString(R.string.description_3), R.raw.lottie_anim_deadbug_female, "03."));


        adapterSliderSlides = new OnBoardingAdapterSlider(this, mList);
        viewPagerSlide.setAdapter(adapterSliderSlides);

        // setup tablayout with viewpager
        tabIndicator.setupWithViewPager(viewPagerSlide);
        viewPagerSlide.setAdapter(adapterSliderSlides);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // tablayout add change listener


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    loaddLastScreen();

                } else {
                    btnStart.setVisibility(View.GONE);
                    skip.setVisibility(View.VISIBLE);
                    tabIndicator.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get Started button click listener

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                finish();
            }
        });

    }

    private void initSlides() {
        viewPagerSlide = findViewById(R.id.viewpager_slides);
        skip = findViewById(R.id.skip_text);
        tabIndicator = findViewById(R.id.tabIndicator);
        btnStart = findViewById(R.id.btn_lets_go);
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {
        btnStart.setVisibility(View.VISIBLE);
        skip.setVisibility(View.GONE);
        tabIndicator.setVisibility(View.GONE);
    }

}