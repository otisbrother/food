package com.example.foodorderapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodorderapp.R;

public class introActivity extends AppCompatActivity {  // Sửa tên class để đúng quy tắc Java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ ID, sửa ConstraintLayout thành Button
        Button startBtn = findViewById(R.id.startBtn);

        // Dùng lambda thay vì Anonymous class
        startBtn.setOnClickListener(v -> startActivity(new Intent(introActivity.this, MainActivity.class)));
    }
}
