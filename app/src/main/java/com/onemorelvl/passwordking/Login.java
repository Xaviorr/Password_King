package com.onemorelvl.passwordking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    TextView information;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        information = findViewById(R.id.tvInformation);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(password.getText().toString());
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInfo();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        password.getText().clear();
    }

    public void openInfo() {
        Toast.makeText(getApplicationContext(), "You're trying to get Information", Toast.LENGTH_LONG).show();
    }

    public void login(String password) {

        if (password.equals("password")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Password", password);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "You have entered the incorrect Password", Toast.LENGTH_LONG).show();
        }
    }

}