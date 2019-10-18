package com.org.moodleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.org.moodleapp.R;
import com.org.moodleapp.SideMenus.BlankFragment;
import com.org.moodleapp.SideMenus.CourseFragment;
import com.org.moodleapp.SideMenus.FormFragment;
import com.org.moodleapp.SideMenus.HomeFragment;
import com.org.moodleapp.SideMenus.TimeTableFragment;
import com.org.moodleapp.database.DataManager;
import com.org.moodleapp.database.Student;
import com.org.moodleapp.SideMenus.EventFragment;
import com.org.moodleapp.SideMenus.GallaryFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    NavigationView navigationView;
    TextView usernameTextView;
    Toolbar toolbar;

    TextView loginSignUpTextView;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        CommonUtils.inflateFragment(new HomeFragment(), getSupportFragmentManager(), R.id.frameLayout_Home, true, true);
        navigationView.getMenu().getItem(0).setChecked(true);

        onPrepareOptionsMenu();

        // check if user is logged in
        Student loggedInUser = DataManager.getInstance().getUser();

        if (loggedInUser != null) {
            loginSignUpTextView.setText(R.string.logout_text);
            usernameTextView.setText(loggedInUser.getStudentName());

        } else {
            loginSignUpTextView.setText(R.string.login_signup);
        }
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        loginSignUpTextView = toolbar.findViewById(R.id.login_signup_textview);
        loginSignUpTextView.setOnClickListener(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        usernameTextView = (TextView) header.findViewById(R.id.textView_name);


        builder = new AlertDialog.Builder(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onPrepareOptionsMenu() {
        Menu menuNav = navigationView.getMenu();

        MenuItem subjectList = menuNav.findItem(R.id.nav_subject_list);
        MenuItem courseList = menuNav.findItem(R.id.nav_course_list);
        MenuItem grade = menuNav.findItem(R.id.nav_grades);

        if (DataManager.getInstance().getUser() != null) {
            subjectList.setVisible(true);
            courseList.setVisible(false);
            grade.setVisible(true);
        } else {
            subjectList.setVisible(false);
            courseList.setVisible(true);
            grade.setVisible(false);
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment= new HomeFragment();

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                fragment= new HomeFragment();
                break;
            case R.id.nav_course_list:
                fragment=new CourseFragment();
                break;
            case R.id.nav_subject_list:
                fragment=new BlankFragment();
                break;
            case R.id.nav_time_table:
                fragment=new TimeTableFragment();
                break;
            case R.id.nav_event:
                fragment=new EventFragment();
                break;
            case R.id.nav_forms:
                fragment=new FormFragment();
                break;
            case R.id.nav_gallary:
                fragment=new GallaryFragment();
                break;
            case R.id.nav_notifications:
                fragment=new BlankFragment();
                break;
            case R.id.nav_grades:
                fragment=new BlankFragment();
                break;
            default:
                break;
        }

        CommonUtils.inflateFragment(fragment, getSupportFragmentManager(), R.id.frameLayout_Home, true, true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_signup_textview:

                if (loginSignUpTextView.getText().toString().equals(getString(R.string.logout_text))) {

                    builder.setMessage("Do you want to Logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // logout actions
                                    DataManager.getInstance().setUser(null);
                                    recreate();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();

                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                // open login screen
                break;
            default:

        }
    }
}
