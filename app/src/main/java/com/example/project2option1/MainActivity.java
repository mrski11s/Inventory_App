package com.example.project2option1;

import static androidx.core.content.ContextCompat.startActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity {
//start app on login screen
    Button login = findViewById(R.id.login);
    login.setOnClickListener(new View.OnClickListener() {
        void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, data_display.class);
            startActivity(intent);
        }
    });
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login.setEnabled(false);
    }
    Button SMS;
    Button BackToGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms);
        BackToGrid = findViewById(R.id.BackToGrid);

    }
    @Override
    protected void onClick(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_display);
        SMS = findViewById(R.id.SMS);

    }
}
