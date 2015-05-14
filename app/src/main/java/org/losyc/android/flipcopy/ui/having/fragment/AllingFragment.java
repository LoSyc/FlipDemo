package org.losyc.android.flipcopy.ui.having.fragment;

import android.app.Activity;
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
 * Created by Administrator on 15-5-13.
 */
public class AllingFragment extends Fragment {
    public final static String CONTENT = "KEY";
    public final static String TAG = "AllingFragment";

    public static AllingFragment newInstance() {
        Bundle args = new Bundle();
        args.putString(CONTENT, "AllingFragment");
        AllingFragment fragment = new AllingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "------------onCreateView");
        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setText(getArguments().getString(CONTENT));
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        Log.i(TAG, getArguments().getString(CONTENT));
        return textView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "------------DestroyView");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "------------onAttach");
    }
}
