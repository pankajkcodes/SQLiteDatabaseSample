package com.pankajkcodes.sqlitedatabasesample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2,ep1;
    Button i1,d1,v1,u1;
    DatabaseSample sampleDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
        }
        e1 = findViewById(R.id.edit1);
        e2 = findViewById(R.id.edit2);
        ep1 = findViewById(R.id.editPass);
        i1 = findViewById(R.id.insert);
        v1 = findViewById(R.id.view);
        u1 = findViewById(R.id.update);
        d1 = findViewById(R.id.delete);

        sampleDb = new DatabaseSample(this);
        // SQLiteDatabase sqLiteDatabase = sampleDb.getReadableDatabase();

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e1.getText().toString();
                String username = e2.getText().toString();
                String password = ep1.getText().toString();
                if (name.equals("") || username.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter All The Fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean i = sampleDb.insert_data(name, username, password);
                    if (i == true) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                }
                e1.setText("");
                e2.setText("");
                ep1.setText("");
            }
        });
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor varC = sampleDb.getInfo();
                if (varC.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }else {
                    StringBuffer buffer = new StringBuffer();
                    while (varC.moveToNext()) {
                        buffer.append("Name :" + varC.getString(1) + "\n");
                        buffer.append("Username :" + varC.getString(2) + "\n");
                        buffer.append("Password :" + varC.getString(3) + "\n\n");
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Users Data");
                    builder.setMessage(buffer).toString();
                    builder.show();
            }
        }
        });
        u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = e1.getText().toString();
                String username = e2.getText().toString();
                String password = ep1.getText().toString();

                boolean i = sampleDb.update_data(name,username,password);
                if (i==true){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }


            }
        });
        d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = e2.getText().toString();
                boolean i = sampleDb.delete_data(username);
                if (i==true){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
                e2.setText("");

            }
        });
    }

}