package com.example.vneurochka.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;
import com.example.vneurochka.models.DBHelper;
import com.example.vneurochka.models.User;
import com.example.vneurochka.models.UserViewModel;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    SimpleCursorAdapter userAdapter;
    DBHelper dbHelper;
    SQLiteDatabase db;
    private final UserViewModel userViewModel;
    public RegistrationActivity() {
        this.userViewModel = new UserViewModel();
    }

    public EditText login, password, rpt_password, e_mail;
    public Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registration_activity);
            login = findViewById(R.id.login);
            password = findViewById(R.id.password); //Rezinka007
            rpt_password = findViewById(R.id.rpt_password);
            e_mail = findViewById(R.id.e_mail);
            btn = findViewById(R.id.btn);
            dbHelper = new DBHelper(this);
            db = dbHelper.getWritableDatabase();
            Cursor cr_user = db.rawQuery("select login, password from users where login =?", new String[]{login.getText().toString()});
            String[] columns = new String[]{"login", "password"};
            userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cr_user, columns, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            userAdapter.getFilter().filter(login.getText().toString());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Character.isDigit(password.getText().toString().charAt(0))) || (password.getText().toString().length() < 7)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setTitle(R.string.error_password_title)
                                .setMessage(R.string.error_password_text)
                                .setCancelable(false)
                                .setPositiveButton(R.string.error_password_pb, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else if ((Character.isDigit(login.getText().toString().charAt(0))) || (login.getText().toString().length() < 4)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setTitle(R.string.error_login_title)
                                .setMessage(R.string.error_login_text)
                                .setCancelable(false)
                                .setPositiveButton(R.string.error_login_pb, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else if (password.getText().toString() == rpt_password.getText().toString()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setTitle(R.string.error_rpt_title)
                                .setMessage(R.string.error_rpt_text)
                                .setCancelable(false)
                                .setPositiveButton(R.string.error_rpt_pb, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        User user = new User(login.getText().toString(), password.getText().toString(), e_mail.getText().toString());
                        userViewModel.addUser(user);
                        Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                }
            });
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        }

    }
}
