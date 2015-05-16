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
import org.losyc.android.flipcopy.util.TopTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Losyc on 2015/5/13.
 * Modified by LoSyc on 15:19
 */
public class HavingPagerFragment extends Fragment implements View.OnClickListener {
    public final static String CONTENT = "KEY";
    public final static String TAG = "HavingFragment";
    private final List<Fragment> mListBeans = new ArrayList<Fragment>();
    private final List<TopTabView> mTopTabs = new ArrayList<TopTabView>();
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

        AllingFragment allingFragment = AllingFragment.newInstance();
        UserFragment userFragment = UserFragment.newInstance();
        AccountFragment accountFragment = AccountFragment.newInstance();
        mListBeans.add(allingFragment);
        mListBeans.add(userFragment);
        mListBeans.add(accountFragment);


        TopTabView allingTab = (TopTabView) view.findViewById(R.id.topbar_alling);
        TopTabView userTab = (TopTabView) view.findViewById(R.id.topbar_user);
        TopTabView accountTab = (TopTabView) view.findViewById(R.id.topbar_account);
        mTopTabs.add(allingTab);
        mTopTabs.add(userTab);
        mTopTabs.add(accountTab);

        for (TopTabView topTabView : mTopTabs) {
            topTabView.setOnClickListener(this);
        }

        allingTab.setCustomAlpha(1.0F);


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
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    TopTabView left = mTopTabs.get(position);
                    TopTabView right = mTopTabs.get(position + 1);

                    left.setCustomAlpha(1 - positionOffset);
                    right.setCustomAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
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

    @Override
    public void onClick(View v) {
        onClickTab(v);
    }

    private void onClickTab(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.topbar_alling:
                mTopTabs.get(0).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.topbar_user:
                mTopTabs.get(1).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.topbar_account:
                mTopTabs.get(2).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
        }
    }

    private void resetOtherTabs() {
        for (TopTabView topTabView : mTopTabs) {
            topTabView.setCustomAlpha(0);
        }
    }
}
