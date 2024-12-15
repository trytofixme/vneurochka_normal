package com.example.vneurochka.pages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;
import com.example.vneurochka.models.DBHelper;
import com.example.vneurochka.models.User;
import com.example.vneurochka.models.UserViewModel;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    DBHelper dbHelper;
    public RegistrationActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registration_activity);

            String loginText = ((EditText)findViewById(R.id.login)).getText().toString();
            String passwordText = ((EditText)findViewById(R.id.password)).getText().toString(); //Rezinka007
            String rptPasswordText = ((EditText)findViewById(R.id.rpt_password)).getText().toString();
            String mailText = ((EditText)findViewById(R.id.e_mail)).getText().toString();
            Button registrationButton = findViewById(R.id.btn);

            dbHelper = new DBHelper(this);
            dbHelper.create_db();

            registrationButton.setOnClickListener(v -> {
                // Password validation
                if ((Character.isDigit(passwordText.charAt(0))) || (passwordText.length() < 7)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle(R.string.error_password_title)
                        .setMessage(R.string.error_password_text)
                        .setCancelable(false)
                        .setPositiveButton(R.string.error_password_pb, (dialog, which) -> dialog.cancel());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                // Login validation
                } else if ((Character.isDigit(loginText.charAt(0))) || (loginText.length() < 4)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle(R.string.error_login_title)
                        .setMessage(R.string.error_login_text)
                        .setCancelable(false)
                        .setPositiveButton(R.string.error_login_pb, (dialog, which) -> dialog.cancel());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                // Validating password matching
                } else if (passwordText.equals(rptPasswordText)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle(R.string.error_rpt_title)
                        .setMessage(R.string.error_rpt_text)
                        .setCancelable(false)
                        .setPositiveButton(R.string.error_rpt_pb, (dialog, which) -> dialog.cancel());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    User user = new User(loginText, passwordText, mailText);
                    UserViewModel.addUser(user);
                    Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            });
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
