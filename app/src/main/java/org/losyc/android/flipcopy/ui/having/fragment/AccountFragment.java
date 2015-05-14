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
public class AccountFragment extends Fragment{
    public final static String KEY = "KEY";
    public final static String TAG = "AccountFragment";

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        args.putString(KEY, "AccountFragment");
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "------------onCreateView");
        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setText(getArguments().getString(KEY));
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        Log.i(TAG, getArguments().getString(KEY));
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
