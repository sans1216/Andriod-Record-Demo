package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.MainActivity;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Log.e("tag", "finished");
        Button fab = findViewById(R.id.btn_login);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Login.this, MainActivity1.class);
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(Login.this, "user_info3.db", null, 1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                db.execSQL(MyDatabaseHelper.CREATE_DB);
                startActivity(intent);
            }
        });
    }

}