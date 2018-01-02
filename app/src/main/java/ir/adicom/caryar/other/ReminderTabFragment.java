package ir.adicom.caryar.other;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.adicom.caryar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderTabFragment extends Fragment {


    public ReminderTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder_tab, container, false);
    }

}
