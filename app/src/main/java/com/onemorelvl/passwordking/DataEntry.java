package com.onemorelvl.passwordking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DataEntry extends AppCompatActivity {
    private EditText etCompanyName;
    private EditText etUserName;
    private EditText etPassword;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        setTitle("Add/Edit Account");
        etCompanyName = findViewById(R.id.etComanyName);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);


        Intent intent1 = getIntent();
        if (intent1.hasExtra("ID")) {
            id = intent1.getIntExtra("ID", -1);
            String companyName = intent1.getStringExtra("CompanyName");
            String userName = intent1.getStringExtra("UserName");
            String password = intent1.getStringExtra("Password");
            etCompanyName.setText(companyName);
            etUserName.setText(userName);
            etPassword.setText(password);
        }

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (id >= 0) {
                    intent.putExtra("ID", id);
                }
                intent.putExtra("CompanyName", etCompanyName.getText().toString());
                intent.putExtra("UserName", etUserName.getText().toString());
                intent.putExtra("Password", etPassword.getText().toString());
                setResult(1, intent);

                finish();
            }
        });

    }
}