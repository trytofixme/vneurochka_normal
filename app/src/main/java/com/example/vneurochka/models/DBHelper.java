package com.example.vneurochka.models;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class DBHelper extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }

    static final String DATABASE_NAME = "vneurochka.db";
    static String DATABASE_PATH;
    static final String USERS_TABLE_NAME = "users";
    static final String GROUP_TYPE_TABLE_NAME = "group_type";
    static final String GROUPS_TABLE_NAME = "groups";
    static final String CHANNEL_HISTORY_TABLE_NAME = "channel_history";
    static final String CHAT_HISTORY_TABLE_NAME = "chat_history";

    private Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
        DATABASE_PATH = context.getFilesDir().getPath() + DATABASE_NAME;
    }

     void create_db(){
         File file = new File(DATABASE_PATH);
         if (!file.exists()) {
             try(InputStream myInput = context.getAssets().open(DATABASE_NAME);
                 OutputStream myOutput = new FileOutputStream(DATABASE_PATH)) {
                 byte[] buffer = new byte[1024];
                 int length;
                 while ((length = myInput.read(buffer)) > 0) {
                     myOutput.write(buffer, 0, length);
                 }
                 myOutput.flush();
             }
             catch(IOException ex){
                 Log.d("DatabaseHelper", ex.getMessage());
             }
         }
     }
     public SQLiteDatabase open()throws SQLException {

         return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
     }
}