package com.org.moodleapp;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.org.moodleapp.R;
import com.org.moodleapp.database.AppDatabase;
import com.org.moodleapp.database.Student;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mNameEditText, nStudentidEditText, mStudentEmailId, mYearEditText, mPasswordEditText, mConfirmPasswordEditText;

    Spinner mCourseListSpinner;
    private static int RESULT_CODE = 101;

    private Button mSignUpButton;

    private AppDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

        myDatabase = AppDatabase.getAppDatabase(this);

    }

    // initialize views
    private void initViews() {
        mNameEditText = findViewById(R.id.name_text_input_edittext);
        nStudentidEditText = findViewById(R.id.studentid_text_input_edittext);
        mStudentEmailId = findViewById(R.id.student_email_id_text_input_edittext);
        mCourseListSpinner = findViewById(R.id.spinner_courseList);
        mYearEditText = findViewById(R.id.year_text_input_edittext);
        mPasswordEditText = findViewById(R.id.password_text_input_edittext);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_text_input_edittext);

        mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CommonUtils.courseArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mCourseListSpinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_up_button) {

            String  name, password, confirmPass, studentID, emailId, course,year;

            year = mYearEditText.getText().toString().trim();
            name= mNameEditText.getText().toString().trim();
            password = mPasswordEditText.getText().toString().trim();
            confirmPass = mConfirmPasswordEditText.getText().toString().trim();
            studentID = nStudentidEditText.getText().toString().trim();
            emailId = mStudentEmailId.getText().toString().trim();
            course = mCourseListSpinner.getSelectedItem().toString().trim();



            if (name.equals("")) {
                Toast.makeText(this, "Name can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (studentID.equals("")) {
                Toast.makeText(this, "Student ID can not be blank", Toast.LENGTH_SHORT).show();
                return;
            }

            if ( !CommonUtils.isEmailValid(emailId)) {
                Toast.makeText(this, "Email ID is invalid or  blank!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals("") || !password.equals(confirmPass)) {
                Toast.makeText(this, "Password and confirm password mismatch", Toast.LENGTH_SHORT).show();
                return;
            }

            if (year.equals("")) {
                Toast.makeText(this, "Year can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }


            Student student = new Student();

            student.setStudentName(name);
            student.setStudentId(studentID);
            student.setCourse(course);
            student.setYear(year);
            student.setPassword(password);
            student.setEmail(emailId);

            String [] toEmail={getString(R.string.collage_admin_email),emailId};

            try {
                // save student detail in database
                myDatabase.userDao().insertAll(student);

                Intent mailIntent = CommonUtils.sendMailIntent(toEmail, getString(R.string.new_registration_mail_subject), getString(R.string.reg_req_mail_body_text));

                startActivityForResult(mailIntent, RESULT_CODE);

            } catch (SQLiteConstraintException exception) {
                Toast.makeText(this, "This student ID already registered", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Something wrong with Gmail APP", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RESULT_CODE) {
            Toast.makeText(this, "Student registered successfully.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
