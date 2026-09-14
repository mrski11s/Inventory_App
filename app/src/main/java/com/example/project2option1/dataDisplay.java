package com.example.project2option1;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dataDisplay extends AppCompatActivity {
    TextView username;
    Button logOut, sms;
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
        Button btnAddRow = findViewById(R.id.Add);
        logOut = findViewById(R.id.logOut);
        sms = findViewById(R.id.SMS);
        username = findViewById(R.id.Username);
        String user = getIntent().getStringExtra("USERNAME");
        username.setText("Welcome " + user);

        btnAddRow.setOnClickListener(v -> addNewRow());

        logOut.setOnClickListener(v -> LogOut());
        sms.setOnClickListener(v -> {
            Intent intent = new Intent(dataDisplay.this, SMS.class);
            startActivity(intent);
        });
    }

    private void LogOut() {
        Intent intent = new Intent(dataDisplay.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void addNewRow() {
        // create a new row
        final TableRow tableRow = new TableRow(this);
        // Check and remove the child from its existing parent
        if (tableLayout.getParent() != null) {
            ((ViewGroup) tableLayout.getParent()).removeView(tableLayout);
        }
        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        //edit name for new row
        EditText name = findViewById(R.id.name_edit_text);

        String itemName = name.getText().toString().trim();
        // Validation
        if (itemName.isEmpty()) {
            Toast.makeText(dataDisplay.this, "Please enter some text first", Toast.LENGTH_SHORT).show();
            return;
        }
        TextView rowName = new TextView(this);
        rowName.setText(itemName);

        //edit quantity for new row
        TextView rowNumber = new TextView(this);
        EditText num = findViewById(R.id.quantity_edit_text);
        String number = num.getText().toString().trim();
        rowNumber.setText(" " + number);

        //rowData.setPadding(10, 10, 10, 10);
        tableRow.addView(rowName); // 1st column
        tableRow.addView(rowNumber); // 2nd column
        // delete button
        Button btnDelete = findViewById(R.id.Delete);
        btnDelete.setText("Delete");

        // set the click listener to remove this specific row
        btnDelete.setOnClickListener(v -> tableLayout.removeView(tableRow));
        tableRow.addView(btnDelete); // 3rd column

        // add the completed row to the TableLayout
        tableLayout.addView(tableRow);

    }

}
