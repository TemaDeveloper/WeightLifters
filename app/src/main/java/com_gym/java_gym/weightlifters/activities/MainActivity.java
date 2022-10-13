package com_gym.java_gym.weightlifters.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.button.MaterialButton;

import com_gym.java_gym.weightlifters.R;
import com_gym.java_gym.weightlifters.databinding.ActivityMainBinding;
import com_gym.java_gym.weightlifters.dialogs.BottomSheetInfoFragment;
import com_gym.java_gym.weightlifters.ui.programs.ProgramsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialButton btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnInfo = findViewById(R.id.btn_info);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetInfoFragment bottomSheetInfoFragment = new BottomSheetInfoFragment();
                bottomSheetInfoFragment.show(getSupportFragmentManager(), "MyCustomDialog");
            }
        });

    }
}