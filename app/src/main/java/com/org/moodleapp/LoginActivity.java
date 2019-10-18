package com.org.moodleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.org.moodleapp.R;
import com.org.moodleapp.database.AppDatabase;
import com.org.moodleapp.database.DataManager;
import com.org.moodleapp.database.Student;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText userNameEditText;
    private TextInputEditText passwordEditText;

    AppDatabase myDatabse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        myDatabse=AppDatabase.getAppDatabase(this);

    }

    /**
     * initialise widgets
     */
    private void initView() {

        userNameEditText = findViewById(R.id.username_edittext_login);
        passwordEditText = findViewById(R.id.password_edittext_login);
    }

    /**
     * login handling for valid and invalid user
     */
    public void loginHandling(View view) {

        // retrieve values form input box
        String username = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //TODO: get values form DB and compare

        Student student = myDatabse.userDao().findByStudentId(username);

        if (student!= null && password.equals(student.getPassword())) {
            DataManager.getInstance().setUser(student);
            finish();

        } else {
            Toast.makeText(this, R.string.incorrect_cred_error_msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * sign up button click handling
     *
     * @param v view id
     */
    public void signUp(View v) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
