package com.sdcode.userslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;

public class ViewUser extends AppCompatActivity {

    TextView userNameTV,fnameTV,lnameTV,genderTV,emailTV,phoneTV,bDateTV,hobie_1TV;
    ImageView avatarImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        avatarImg = findViewById(R.id.avatarImg);
        userNameTV = findViewById(R.id.userNameTV);
        fnameTV = findViewById(R.id.fnameTV);
        lnameTV = findViewById(R.id.lnameTV);
        genderTV = findViewById(R.id.genderTV);
        emailTV = findViewById(R.id.emailTV);
        phoneTV = findViewById(R.id.phoneTV);
        bDateTV = findViewById(R.id.bDateTV);
        hobie_1TV = findViewById(R.id.hobie_1TV);


        Intent i = getIntent();
        final String username = i.getStringExtra("username");
        userNameTV.setText("Uid " + username);

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM AllUsers", null);
//CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(50),LNAME VARCHAR(50),UNAME VARCHAR(50),EMAIL VARCHAR(255),PHONE VARCHAR(255),GENDER_id INTEGER,BDATE DATETIME, FOREIGN KEY (GENDER_id) REFERENCES gender(_id))";
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data!", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                if (Integer.parseInt(username) == cursor.getInt(0)) {

                    String fname = cursor.getString(1);
                    String lname = cursor.getString(2);
                    String uname = cursor.getString(3);
                    Integer genderId = cursor.getInt(6);
                    String email = cursor.getString(4);
                    String phone = cursor.getString(5);
                    String bDate = cursor.getString(7);
//                    hobie_1TV = findViewById(R.id.hobie_1TV);

                    fnameTV.setText(fname);
                    lnameTV.setText(lname);
                    userNameTV.setText(uname);
                    bDateTV.setText("dt - " + bDate);
                    if(genderId == 1){
                        genderTV.setText("Male");
                        avatarImg.setImageDrawable(getResources().getDrawable(R.drawable.avatar_male));
                    }else{
                        genderTV.setText("Female");
                        avatarImg.setImageDrawable(getResources().getDrawable(R.drawable.avatar_female));
                    }

                    emailTV.setText(email);
                    phoneTV.setText(phone);

                }
            }
            cursor.close();
        }

    }
}