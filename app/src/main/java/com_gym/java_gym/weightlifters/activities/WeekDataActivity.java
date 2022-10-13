package com_gym.java_gym.weightlifters.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.database.DatabaseHelper;
import com_gym.java_gym.weightlifters.dialogs.BottomSheetAddDayToWeekFragment;
import com_gym.java_gym.weightlifters.models.Day;
import com_gym.java_gym.weightlifters.recyclerViewAdapter.AdapterDays;

public class WeekDataActivity extends AppCompatActivity implements BottomSheetAddDayToWeekFragment.OnInputSelected {

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private Uri imageUri;

    //widgets
    private ImageView imgBack, imgSnapInCardView, imgCamera;
    private RelativeLayout relativeNothing;
    private TextView textMotivationQuote, textTitleNumWeek;
    private MaterialButton btnUploadSnap, btnAddDay, btnWeekDone;
    private ViewPager2 viewPagerDays;
    private TabLayout tabsDays;
    //Lists for ViewPager
    private String numOfWeek, weekDone, imageIntent;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_data);

        db = new DatabaseHelper(WeekDataActivity.this);
        init();

        btnAddDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetAddDayToWeekFragment bottomSheetAddDayToWeekFragment = new BottomSheetAddDayToWeekFragment();
                bottomSheetAddDayToWeekFragment.show(getSupportFragmentManager(), "MyCustomDialog");
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getIntentFromAdapter();
        chooseRandomQuote();

        showDaysInViewPager();


        tabsDays.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerDays.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPagerDays.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabsDays.selectTab(tabsDays.getTabAt(position));
            }
        });

        //handle button
        btnUploadSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        setWeekDone();
    }

    private void getIntentFromAdapter() {
        numOfWeek = getIntent().getStringExtra("numOfWeek");
        weekDone = getIntent().getStringExtra("weekDone");
        imageIntent = getIntent().getStringExtra("image");
        textTitleNumWeek.setText(getResources().getString(R.string.week) + " " + numOfWeek);

        if (imageIntent == null) {
            imgCamera.setVisibility(View.VISIBLE);
        } else {
            imgCamera.setVisibility(View.GONE);
            Glide.with(WeekDataActivity.this)
                    .load(imageIntent)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.noAnimation())
                    .thumbnail(0.3f)
                    .into(imgSnapInCardView);
        }

        if (weekDone.equals(getResources().getString(R.string.week_done_text)))
            btnWeekDone.setEnabled(false);
    }


    private void init() {
        //widgets init
        textTitleNumWeek = findViewById(R.id.text_title_num_week);
        relativeNothing = findViewById(R.id.relative_layout_nothing);
        //cardViewDays = findViewById(R.id.card_view_days);
        btnWeekDone = findViewById(R.id.btn_week_done);
        btnUploadSnap = findViewById(R.id.btn_upload_snap);
        imgBack = findViewById(R.id.img_back);
        imgCamera = findViewById(R.id.img_camera);
        textMotivationQuote = findViewById(R.id.text_motivation_quote);
        tabsDays = findViewById(R.id.tab_layout_days);
        imgSnapInCardView = findViewById(R.id.img_snap);
        viewPagerDays = findViewById(R.id.recycler_view_program_in_days);
        btnAddDay = findViewById(R.id.btn_add_day);
    }

    private void setWeekDone() {
        DatabaseHelper db = new DatabaseHelper(WeekDataActivity.this);
        btnWeekDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String img = imageUri + "";
                if (imageUri == null) {
                    Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.add_image_to_finish), Snackbar.LENGTH_LONG);
                    snackbar.setTextColor(getResources().getColor(R.color.white));
                    snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                    snackbar.show();
                } else {
                    boolean result = db.updateWeek(numOfWeek, getResources().getString(R.string.week_done_text), img);
                    btnWeekDone.setEnabled(false);
                    if(result == true){
                        startActivity(new Intent(WeekDataActivity.this, MainActivity.class));
                    }else{
                        Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.smth_went_wrong), Snackbar.LENGTH_LONG);
                        snackbar.setTextColor(getResources().getColor(R.color.white));
                        snackbar.setBackgroundTint(getResources().getColor(R.color.red));
                        snackbar.show();
                    }
                }
            }
        });
    }

    private void chooseRandomQuote() {
        Random random = new Random();
        String[] quotes = getResources().getStringArray(R.array.quotes_motivation);
        textMotivationQuote.setText(quotes[random.nextInt(quotes.length)]);
    }

    void chooseImage() {
        // create an instance of the
        // intent of the type image
        Intent i;
        if (Build.VERSION.SDK_INT < 19) {
            i = new Intent();
            i.setAction(Intent.ACTION_GET_CONTENT);
            i.setType("*/*");
            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        } else {
            i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(i, SELECT_PICTURE);
        }

    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                imageUri = data.getData();
                if (null != imageUri) {
                    // update the preview image in the layout
                    imgSnapInCardView.setImageURI(imageUri);
                    imgCamera.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void sendInput(String dayOfWeek, String titleProgram) {
        for (int i = 0; i < db.getEveryProgram().size(); i++) {
            if (db.getEveryProgram().get(i).getNamingOfProgram().equals(titleProgram)) {

                int idProgram = db.getEveryProgram().get(i).getId();
                Log.d("ID", idProgram + " ID_PROGRAM");

                Day day = new Day(0, Integer.parseInt(numOfWeek), db.getEveryProgram().get(i).getId(), dayOfWeek, titleProgram);
                String weekDayIteration = day.getDayOfWeek().substring(0, 3) + ".";
                tabsDays.addTab(tabsDays.newTab().setText(weekDayIteration));
                db.addDay(day);

                AdapterDays adapter = new AdapterDays(db.getEveryDay(Integer.parseInt(numOfWeek)), WeekDataActivity.this);
                viewPagerDays.setAdapter(adapter);
            }
        }
    }

    private void showDaysInViewPager() {
        db = new DatabaseHelper(WeekDataActivity.this);
        AdapterDays adapter = new AdapterDays(db.getEveryDay(Integer.parseInt(numOfWeek)), WeekDataActivity.this);
        viewPagerDays.setAdapter(adapter);
        //iteration, add new Tab
        for (int iterator = 0; iterator < db.getEveryDay(Integer.parseInt(numOfWeek)).size(); iterator++) {
            String weekDayIteration = db.getEveryDay(Integer.parseInt(numOfWeek)).get(iterator).getDayOfWeek().substring(0, 3) + ".";
            tabsDays.addTab(tabsDays.newTab().setText(weekDayIteration));

            if (db.getEveryDay(Integer.parseInt(numOfWeek)).get(iterator).getClass() == null) {
                relativeNothing.setVisibility(View.VISIBLE);
                tabsDays.setVisibility(View.GONE);
                viewPagerDays.setVisibility(View.GONE);
            } else {
                relativeNothing.setVisibility(View.GONE);
                tabsDays.setVisibility(View.VISIBLE);
                viewPagerDays.setVisibility(View.VISIBLE);
            }

        }


    }

}