package com.devseok.viewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devseok.viewbinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.text.setText("테스트");

        binding.testText.setText("__ 테스트");
        
    }
}