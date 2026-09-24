package com.example.project2option1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dataDisplay extends AppCompatActivity {
    TextView username;
    Button logOut, sms, btnAddRow;
    RegisterActivity registerActivity;
    ItemDB itemDB;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_display);
        registerActivity = new RegisterActivity(this);
        itemDB = new ItemDB(this);
        tableLayout = findViewById(R.id.table);
        btnAddRow = findViewById(R.id.Add);
        logOut = findViewById(R.id.logOut);
        sms = findViewById(R.id.SMS);
        username = findViewById(R.id.Username);
        Intent intent = getIntent();

        // Check the intent data to avoid NullPointerExceptions
        if (intent != null) {
            String user = intent.getStringExtra("USERNAME");

            if (user == null) {
                user = "";
            }
            username.setText("Welcome " + user);
        }

        btnAddRow.setOnClickListener(v -> addNewRow());
        logOut.setOnClickListener(v -> LogOut());
        sms.setOnClickListener(v -> SMS());
        // 2. Load data from DB and fill rows
        loadDB();
    }

    private void SMS() {
            Intent intent = new Intent(dataDisplay.this, SMS.class);
            startActivity(intent);
    }


    private void LogOut() {
        Intent intent = new Intent(dataDisplay.this, MainActivity.class);
        startActivity(intent);
        //Blocks back button from logging back in
        finish();
    }

    private void loadDB() {
        int childCount = tableLayout.getChildCount();
        Toast.makeText(dataDisplay.this, "In loadDB " + childCount, Toast.LENGTH_SHORT).show();
        Cursor cursor = itemDB.getAllData();

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            do {

                // Create a new row for each record
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                row.setPadding(0, 5, 0, 5);

                // Extract data from cursor
                int Id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("ITEM"));
                int number = cursor.getInt(cursor.getColumnIndexOrThrow("QUANTITY"));
                /*
                // Column 1: ID isn't shown
                TextView rowID = new TextView(this);
                rowID.setText(String.valueOf(Id));
                rowID.setPadding(10, 10, 10, 10);
                row.addView(rowID);
                */
                // Column 2: Name
                TextView rowName = new TextView(this);
                rowName.setText(name);
                rowName.setPadding(10, 10, 10, 10);
                row.addView(rowName);

                // Column 3: Quantity
                TextView rowNumber = new TextView(this);
                rowNumber.setText(String.valueOf(number));
                rowNumber.setPadding(10, 10, 10, 10);
                row.addView(rowNumber);

                // delete button
                Button btnDelete = new Button(this);
                btnDelete.setText("Delete");

                // set the click listener to remove this specific row
                btnDelete.setOnClickListener(v -> {
                    tableLayout.removeView(row);
                    itemDB.deleteRow(Id);
                });

                row.addView(btnDelete);
                // Add the built row into the TableLayout container
                tableLayout.addView(row);

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void addNewRow() {
        EditText name = findViewById(R.id.name_edit_text);
        String itemName = name.getText().toString().trim();
        if (itemName.isEmpty()) {
            Toast.makeText(dataDisplay.this, "Please enter an item name", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText num = findViewById(R.id.quantity_edit_text);
        Integer number = Integer.valueOf(num.getText().toString());

        //inserts and checks that it was successful
        if (itemDB.insert(itemName, number)) {
            //synchronize table without switching screens
            int childCount = tableLayout.getChildCount();
            if (childCount > 2) {
                // keeps headers and removes previous table
                tableLayout.removeViews(2, childCount - 2);
            }
            loadDB();
        }
    }


}
