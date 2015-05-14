package org.losyc.android.flipcopy.ui.person.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Losyc on 2015/5/13.
 * Modified by LoSyc on 15:18
 */
public class PersonFragment extends Fragment {
    public final static String CONTENT = "KEY";
    public final static String TAG = "PersonFragment";

    public static PersonFragment newInstance() {
        Bundle args = new Bundle();
        args.putString(CONTENT, "PersonFragment");
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setText(getArguments().getString(CONTENT));
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        Log.i(TAG, getArguments().getString(CONTENT));
        return textView;
    }
}
