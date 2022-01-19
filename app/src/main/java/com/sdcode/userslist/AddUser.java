package com.sdcode.userslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddUser extends AppCompatActivity {

    TextInputLayout firstNameInputLayout, lastNameInputLayout, userNameInputLayout, emailInputLayout, phoneInputLayout, date_picker_InputLayout;
    TextInputEditText firstNameInputEditText, lastNameInputEditText, userNameInputEditText, emailInputEditText, phoneInputEditText, date_picker_InputEditText;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton, maleRadioButton, femaleRadioButton;
    MaterialCheckBox chk_cricket, chk_movies, chk_reading, chk_writing;
    Button btnAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
//        getSupportActionBar().setTitle("Users");
        initialization();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Firstname = firstNameInputEditText.getText().toString().trim();
                String Lastname = lastNameInputEditText.getText().toString().trim();
                String Username = userNameInputEditText.getText().toString().trim();
                String Email = emailInputEditText.getText().toString().trim();
                String Mono = phoneInputEditText.getText().toString().trim();
                Integer gender_id = 1;

                if (Firstname.isEmpty() || Lastname.isEmpty() || Username.isEmpty() || Email.isEmpty() || Mono.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all details!", Toast.LENGTH_SHORT).show();
                } /* else if (!maleRadioButton.isSelected() && !femaleRadioButton.isSelected()) {
                    Messagee.message(getApplicationContext(), "Please select Gender");
                } */else {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    selectedRadioButton = findViewById(selectedId);

                    if (selectedId == R.id.radio_male) {
                        gender_id = 1;
                    } else {
                        gender_id = 2;
                    }

                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getWritableDatabase();

                    /*
CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(50),LNAME VARCHAR(50),UNAME VARCHAR(50),EMAIL VARCHAR(255),PHONE VARCHAR(255),GENDER_id INTEGER,BDATE DATETIME, FOREIGN KEY (GENDER_id) REFERENCES gender(_id))";
create table hobbies(user_id INTEGER, hobbie VARCHAR(50), FOREIGN KEY (user_id) REFERENCES AllUsers(_id))";
                     */
                    ContentValues values = new ContentValues();
                    values.put("FNAME", Firstname);
                    values.put("LNAME", Lastname);
                    values.put("UNAME", Username);
                    values.put("EMAIL", Email);
                    values.put("PHONE", Mono);
                    values.put("GENDER_id", gender_id);

                    long user_id = database.insert("AllUsers", null, values);

                    user_id = Integer.parseInt(String.valueOf(user_id));

                    String[] hobbies = new String[4];
                    int lastEntered = 0;
                    if(chk_cricket.isChecked()){
                        hobbies[0] = "cricket";
                        lastEntered = 1;
                    }

                    if(chk_movies.isChecked()){
                        if(lastEntered == 0){
                            hobbies[0] = "movies";
                            lastEntered = 1;
                        }else{
                            hobbies[1] = "movies";
                            lastEntered = 2;
                        }
                    }

                    if(chk_reading.isChecked()){
                        if(lastEntered == 0){
                            hobbies[0] = "reading";
                            lastEntered = 1;
                        }else if(lastEntered == 1){
                            hobbies[1] = "reading";
                            lastEntered = 2;
                        }else{
                            hobbies[2] = "reading";
                            lastEntered = 3;
                        }
                    }

                    if(chk_writing.isChecked()){
                        if(lastEntered == 0){
                            hobbies[0] = "writing";
                        }else if(lastEntered == 1){
                            hobbies[1] = "writing";
                        }else if(lastEntered == 2){
                            hobbies[2] = "writing";
                        }else{
                            hobbies[3] = "writing";
                        }
                    }

                    ContentValues valuesHobbies = new ContentValues();

                    for (String hobby : hobbies) {
                        valuesHobbies.put("user_id", user_id);
                        valuesHobbies.put("hobbie", hobby);
                        database.insert("hobbies", null, valuesHobbies);
                        valuesHobbies.clear();
                    }

                    Toast.makeText(getApplicationContext(), "User created!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private void initialization() {
        firstNameInputLayout = findViewById(R.id.firstNameInputLayout);
        lastNameInputLayout = findViewById(R.id.lastNameInputLayout);
        userNameInputLayout = findViewById(R.id.userNameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        phoneInputLayout = findViewById(R.id.phoneInputLayout);
        date_picker_InputLayout = findViewById(R.id.date_picker_InputLayout);


        firstNameInputEditText = findViewById(R.id.firstNameInputEditText);
        lastNameInputEditText = findViewById(R.id.lastNameInputEditText);
        userNameInputEditText = findViewById(R.id.userNameInputEditText);
        emailInputEditText = findViewById(R.id.emailInputEditText);
        phoneInputEditText = findViewById(R.id.phoneInputEditText);
        date_picker_InputEditText = findViewById(R.id.date_picker_InputEditText);

        radioGroup = findViewById(R.id.radioGroup);
        maleRadioButton = findViewById(R.id.radio_male);
        femaleRadioButton = findViewById(R.id.radio_female);
//        int selectedId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(selectedId);
//
//        Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_LONG).show();

        chk_cricket = findViewById(R.id.chk_cricket);
        chk_movies = findViewById(R.id.chk_movies);
        chk_reading = findViewById(R.id.chk_reading);
        chk_writing = findViewById(R.id.chk_writing);

        btnAddUser = findViewById(R.id.btn_add_user);
    }

}