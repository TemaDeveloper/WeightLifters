package com_gym.java_gym.weightlifters.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;


import java.util.Locale;

import com_gym.java_gym.weightlifters.BuildConfig;
import com_gym.java_gym.weightlifters.R;


public class BottomSheetInfoFragment extends BottomSheetDialogFragment {

    private LinearLayout linQuit;
    private Spinner spinnerLanguage;
    private RelativeLayout linFeedBack, linShareApp;
    private String[] languages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_info, container, false);

        linFeedBack = view.findViewById(R.id.lin_feed_back);
        linQuit = view.findViewById(R.id.lin_quit);
        linShareApp = view.findViewById(R.id.lin_share_app);
        spinnerLanguage = view.findViewById(R.id.spinner_languages);

        String changeLanguage = getContext().getResources().getString(R.string.language_changing);
        String english = getContext().getResources().getString(R.string.english);
        String ukranian = getContext().getResources().getString(R.string.ukranian);
        String japanese = getContext().getResources().getString(R.string.japanese);
        String russian = getContext().getResources().getString(R.string.russian);

        languages = new String[]{changeLanguage, english, ukranian, japanese, russian};

        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, languages);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterLanguage);
        spinnerLanguage.setSelection(0);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                if (selectedLanguage.equals("English")){
                    setLocal(getActivity(), "en");
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }else if(selectedLanguage.equals("Український")){
                    setLocal(getActivity(), "ua");
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }else if(selectedLanguage.equals("日本")){
                    setLocal(getActivity(), "jp");
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }else if(selectedLanguage.equals("Русский")){
                    setLocal(getActivity(), "ru");
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        linShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Weight Lifters");
                    String shareMessage = "\n " + getResources().getString(R.string.let_me_recommend) + " \n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "APP"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        linQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getActivity(), R.style.ThemeDialog);

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.fragment_alert_dialog_quit);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                MaterialButton btnNo = dialog.findViewById(R.id.btn_no), btnYes = dialog.findViewById(R.id.btn_yes);

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().moveTaskToBack(true);
                        getActivity().finish();
                        System.exit(0);
                        dialog.cancel();
                    }
                });

                dialog.show();


            }
        });

        linFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"freshart666@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback));
                startActivity(Intent.createChooser(feedbackEmail, getResources().getString(R.string.send_feedback)));
            }
        });

        return view;
    }


    private void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

}