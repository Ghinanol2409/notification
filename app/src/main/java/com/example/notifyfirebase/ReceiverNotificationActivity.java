package com.example.notifyfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReceiverNotificationActivity extends AppCompatActivity {

    TextView categoryTV, branchTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_notification);

        View();

        if (getIntent().hasExtra("Category")){
            String category = getIntent().getStringExtra("Category");
            String branch = getIntent().getStringExtra("branchID");
            categoryTV.setText(category);
            branchTV.setText(branch);
        }
    }

    private void View() {
        categoryTV = findViewById(R.id.category);
        branchTV = findViewById(R.id.branch);
    }
}
