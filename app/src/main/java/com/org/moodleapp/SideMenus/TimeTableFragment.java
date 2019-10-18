package com.org.moodleapp.SideMenus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.org.moodleapp.CommonUtils;
import com.org.moodleapp.R;
import com.org.moodleapp.database.DataManager;
import com.org.moodleapp.database.Student;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * * interface.
 */
public class TimeTableFragment extends Fragment {

    private int mColumnCount = 1;

    private List<Integer> array = Arrays.asList(R.drawable.business, R.drawable.computer_multimedia, R.drawable.health_care,R.drawable.child_care);

    MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TimeTableFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            Student student = DataManager.getInstance().getUser();
            if (student != null) {
                String course = student.getCourse();
                int timeTable = array.get(CommonUtils.courseArray.indexOf(course)-1);

                //set required timetable
                array = Arrays.asList(timeTable);
            }

            myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(array);
            recyclerView.setAdapter(myItemRecyclerViewAdapter);
        }
        return view;
    }
}
