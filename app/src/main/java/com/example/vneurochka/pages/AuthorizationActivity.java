package com.example.vneurochka.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;
import com.example.vneurochka.models.User;
import com.example.vneurochka.models.UserViewModel;

import java.util.List;

public class AuthorizationActivity extends AppCompatActivity {

    private final UserViewModel userViewModel;
    public AuthorizationActivity() {
        this.userViewModel = new UserViewModel();
    }
    EditText login;
    EditText password;
    Button btn, registration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        login = findViewById(R.id.login);
        registration = findViewById(R.id.registration);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(login.getText().toString(), password.getText().toString());
                List<User> users = UserViewModel.getUsers();
                User findedUser = null;
                for (int i = 0; i < users.toArray().length; i++) {
                    if (users.get(i).getLogin() == user.getLogin()) {
                        findedUser = users.get(i);
                        break;
                    }
                }
                if (  (findedUser != null) && (findedUser.getPassword() == user.getPassword())  ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AuthorizationActivity.this);
                    builder.setTitle(R.string.error_reg_title)
                            .setMessage(R.string.error_reg_text)
                            .setCancelable(false)
                            .setPositiveButton(R.string.error_reg_pb, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(AuthorizationActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}