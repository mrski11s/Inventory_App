package com.example.project2option1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SMS extends AppCompatActivity {
    Button BackToGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        BackToGrid = findViewById(R.id.BackToGrid);
        BackToGrid.setOnClickListener(v -> BackToGrid());
    }

    private void BackToGrid() {
        Intent passThrough = getIntent();
        String user = passThrough.getStringExtra("USERNAME");
        Intent intent = new Intent(SMS.this, dataDisplay.class);
        intent.putExtra("USERNAME", user);
        startActivity(intent);
    }
}