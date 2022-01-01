package com.uxi.fragmentsdemo.Controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.uxi.fragmentsdemo.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class ThemeActivity extends AppCompatActivity {
    SwitchMaterial switchMaterial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        switchMaterial = findViewById(R.id.switchTheme);

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                //Toast.makeText(ThemeActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                //Toast.makeText(ThemeActivity.this, "Un Checked", Toast.LENGTH_SHORT).show();
            }

        });

    }
}