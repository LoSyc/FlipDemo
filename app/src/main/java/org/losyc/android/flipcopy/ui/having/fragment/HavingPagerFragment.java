package org.losyc.android.flipcopy.ui.having.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.losyc.android.flipcopy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Losyc on 2015/5/13.
 * Modified by LoSyc on 15:19
 */
public class HavingPagerFragment extends Fragment {
    public final static String CONTENT = "KEY";
    public final static String TAG = "HavingFragment";
    private final List<Fragment> mListBeans = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private FragmentManager fm;

    public static HavingPagerFragment newInstance() {
        Bundle args = new Bundle();
        args.putString(CONTENT, "HavingPagerFragment");
        HavingPagerFragment fragment = new HavingPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "------------onCreateView");
        View view = inflater.inflate(R.layout.fragment_viewpager_container, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.havingViewPagerContainer);
        fm = getActivity().getSupportFragmentManager();

        if(mListBeans.isEmpty()) {
            AllingFragment allingFragment = AllingFragment.newInstance();
            mListBeans.add(allingFragment);
            UserFragment userFragment = UserFragment.newInstance();
            mListBeans.add(userFragment);
            AccountFragment accountFragment = AccountFragment.newInstance();
            mListBeans.add(accountFragment);
        }
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Log.i(TAG, "------pos:" + position);
                return mListBeans.get(position);
            }

            @Override
            public int getCount() {
                return mListBeans.size();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "------------DestroyView");
        fm.beginTransaction().detach(mListBeans.get(0))
                .detach(mListBeans.get(1))
                .detach(mListBeans.get(2)).commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "------------onAttach");
    }
}
