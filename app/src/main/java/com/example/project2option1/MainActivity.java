package com.example.project2option1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login, register, skip;
    RegisterActivity registerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerActivity = new RegisterActivity(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        skip = findViewById(R.id.skip);

        skip.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, dataDisplay.class);
            intent.putExtra("USERNAME", "skipped");
            startActivity(intent);
            finish();
        });

        login.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                String loggedIn = registerActivity.verifyLogin(user, pass);
                if (loggedIn != null) {
                    Intent intent = new Intent(MainActivity.this, dataDisplay.class);
                    intent.putExtra("USERNAME", loggedIn);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //if they don't already have an account
        register.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                if (registerActivity.insert(user, pass)) {
                    Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, dataDisplay.class);
                    intent.putExtra("USERNAME", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Username taken", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
