package com.onemorelvl.passwordking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextView createLogin;
    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createLogin = findViewById(R.id.tvCreateAccount);
        username = findViewById(R.id.editTextUserName);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(username.getText().toString(), password.getText().toString());
            }
        });

        createLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLogin();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        username.getText().clear();
        password.getText().clear();
    }

    public void createLogin() {
        Toast.makeText(getApplicationContext(), "You're trying to create a new Account", Toast.LENGTH_LONG).show();
    }

    public void login(String username, String password) {

        if (username.equals("user") && password.equals("password")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "You have entered the incorrect Username or Password", Toast.LENGTH_LONG).show();
        }
    }

}