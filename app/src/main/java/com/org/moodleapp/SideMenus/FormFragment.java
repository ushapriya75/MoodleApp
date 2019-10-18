package com.org.moodleapp.SideMenus;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.org.moodleapp.CommonUtils;
import com.org.moodleapp.R;
import com.org.moodleapp.database.AppDatabase;
import com.org.moodleapp.database.Form;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment implements View.OnClickListener {

    private static int RESULT_CODE = 100;
    private AppDatabase myDatabase;

    Spinner formRequestSpinner;
    Button submit;
    TextInputEditText name, studentId, course, year, emailId;

    Activity myActivity;

    String[] array = {"College letter", "Attendance letter"};

    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        myActivity = getActivity();

        formRequestSpinner = view.findViewById(R.id.spinner_request);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_spinner_dropdown_item, array);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        formRequestSpinner.setAdapter(adapter);

        myDatabase = AppDatabase.getAppDatabase(getActivity());

        initViews(view);

        return view;
    }

    private void initViews(View v) {

        name = v.findViewById(R.id.form_name_text_input_edittext);
        studentId = v.findViewById(R.id.form_studentid_text_input_edittext);
        emailId = v.findViewById(R.id.form_emailid_text_input_edittext);
        course = v.findViewById(R.id.form_course_text_input_edittext);
        year = v.findViewById(R.id.form_year_text_input_edittext);
        submit = v.findViewById(R.id.submit_form_button);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit_form_button) {
            String mName, mStudentId, mCourse, mYear, mReason, mEmailId;

            mName = name.getText().toString();
            mStudentId = studentId.getText().toString();
            mEmailId = emailId.getText().toString();
            mCourse = course.getText().toString();
            mYear = year.getText().toString();
            mReason = formRequestSpinner.getSelectedItem().toString();

            String[] toEmail = {mEmailId};

            if (mName.equals("")) {
                Toast.makeText(myActivity, "Name can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mStudentId.equals("")) {
                Toast.makeText(myActivity, "Student Id can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }
            if ( !CommonUtils.isEmailValid(mEmailId)) {
                Toast.makeText(myActivity, "Email ID is invalid or  blank!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mCourse.equals("")) {
                Toast.makeText(myActivity, "Course can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mYear.equals("")) {
                Toast.makeText(myActivity, "Year can not be blank!", Toast.LENGTH_SHORT).show();
                return;
            }

            //save form in database
            Form mForm = new Form(mName, mStudentId, mCourse, mYear, mReason, mEmailId);

            try {
                // save student detail in database
                myDatabase.formDao().insertAll(mForm);

                String subject = "Request for " + mReason;
                String mailBody = getString(R.string.form_reg_mail_body, mReason);

                // open mail application with pre-fill detail
                Intent mailIntent = CommonUtils.sendMailIntent(toEmail, subject, mailBody);
                startActivityForResult(mailIntent, RESULT_CODE);

            } catch (SQLiteConstraintException exception) {
                Toast.makeText(getActivity(), "Request failed", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Something wrong with Gmail APP", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetFields() {

        name.setText("");
        studentId.setText("");
        course.setText("");
        year.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RESULT_CODE) {
            Toast.makeText(getActivity(), R.string.form_saved_msg, Toast.LENGTH_SHORT).show();
            resetFields();
        }
    }
}
