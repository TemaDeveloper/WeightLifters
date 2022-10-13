package com_gym.java_gym.weightlifters.onBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.models.Slide;

public class OnBoardingAdapterSlider extends PagerAdapter {

    Context mContext;
    List<Slide> slides;

    public OnBoardingAdapterSlider(Context mContext, List<Slide> slides) {
        this.mContext = mContext;
        this.slides = slides;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.item_slide, null);

        LottieAnimationView lottieAnimationSlide = layoutScreen.findViewById(R.id.lottie_animation_slide);
        TextView title = layoutScreen.findViewById(R.id.text_title_slide);
        TextView num = layoutScreen.findViewById(R.id.text_num_slide);
        TextView description = layoutScreen.findViewById(R.id.text_description_slide);

        title.setText(slides.get(position).getTitle());
        num.setText(slides.get(position).getNum());
        description.setText(slides.get(position).getDescription());
        lottieAnimationSlide.setAnimation(slides.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;


    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
