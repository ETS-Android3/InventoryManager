package com.snhu.p2_guilherme_pereira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    /*** Fields ***/
    private Button BTN_login, BTN_signup;
    private EditText EDT_username, EDT_password;
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input_user = EDT_username.getText().toString().trim();
            String input_pass = EDT_password.getText().toString().trim();

            BTN_login.setEnabled(!input_user.isEmpty() && !input_pass.isEmpty());
            BTN_signup.setEnabled(!input_user.isEmpty() && !input_pass.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing
        }
    }; // Checks if EDT fields are filled

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*** View IDs (onCreate) ***/
        BTN_login = findViewById(R.id.Button_login);
        BTN_signup = findViewById(R.id.Button_signup);
        EDT_username = findViewById(R.id.EditText_username);
        EDT_password = findViewById(R.id.EditText_password);

        /*** Button Listeners (onCreate) ***/
        EDT_username.addTextChangedListener(textWatcher);
        EDT_password.addTextChangedListener(textWatcher);

        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user;
                DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);
                String str_user = EDT_username.getText().toString().trim();
                String str_pass = EDT_password.getText().toString().trim();

                boolean checkCredentials = dbHelper.checkAccount(str_user, str_pass);

                if (checkCredentials){ // Credentials match - Login successful
                    try {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                } else { // Credentials do not match - Login unsuccessful
                    Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel user;
                DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);

                String str_user = EDT_username.getText().toString().trim();
                String str_pass = EDT_password.getText().toString().trim();

                EDT_username.addTextChangedListener(textWatcher);
                EDT_password.addTextChangedListener(textWatcher);

                boolean checkUser = dbHelper.checkUserName(str_user);

                if (!checkUser){ // Account does not exist - Create user
                    System.out.println("Registering account");
                    try {
                        Toast.makeText(LoginActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        user = new UserModel(-1, str_user, str_pass);
                        System.out.println(user.toString());
                        dbHelper.addUser(user);
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        System.out.println("Registering unsuccessful");
                    }
                } else { // Account exists - Do not create user
                    Toast.makeText(LoginActivity.this, "User already exists - Create another", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}