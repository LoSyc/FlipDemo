package org.losyc.android.flipcopy.ui.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import org.losyc.android.flipcopy.R;
import org.losyc.android.flipcopy.ui.having.fragment.HavingPagerFragment;
import org.losyc.android.flipcopy.ui.home.fragment.HomeFragment;
import org.losyc.android.flipcopy.ui.person.fragment.PersonFragment;
import org.losyc.android.flipcopy.ui.search.fragment.SearchFragment;
import org.losyc.android.flipcopy.ui.warn.fragment.WarnFragment;
import org.losyc.android.flipcopy.util.TopTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Losyc on 2015/5/12.
 * Modified by LoSyc on 22:03
 */
public class HomePagerActivity extends FragmentActivity implements View.OnClickListener {
    public final static String TAG = "HomePagerActivity";
    private List<Fragment> mListBeans = new ArrayList<Fragment>();
    private List<TopTabView> mTopTabs = new ArrayList<TopTabView>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        FragmentManager fm = getSupportFragmentManager();

        initView();

        initListBean();

        BindAdapter(fm, mListBeans);
    }

    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.homeViewPagerContainer);

        TopTabView homeTab = (TopTabView) findViewById(R.id.topbar_home);
        TopTabView havingTab = (TopTabView) findViewById(R.id.topbar_having);
        TopTabView searchTab = (TopTabView) findViewById(R.id.topbar_search);
        TopTabView warnTab = (TopTabView) findViewById(R.id.topbar_warn);
        TopTabView personTab = (TopTabView) findViewById(R.id.topbar_person);
        mTopTabs.add(homeTab);
        mTopTabs.add(havingTab);
        mTopTabs.add(searchTab);
        mTopTabs.add(warnTab);
        mTopTabs.add(personTab);

        for (TopTabView topTabView : mTopTabs) {
            topTabView.setOnClickListener(this);
        }

        homeTab.setCustomAlpha(1.0F);
    }

    protected void initListBean() {
        mListBeans.add(HomeFragment.newInstance());
        mListBeans.add(HavingPagerFragment.newInstance());
        mListBeans.add(SearchFragment.newInstance());
        mListBeans.add(WarnFragment.newInstance());
        mListBeans.add(PersonFragment.newInstance());
    }

    protected void BindAdapter(FragmentManager fm, final List<Fragment> Beans) {
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public int getCount() {
                return Beans.size();
            }

            @Override
            public Fragment getItem(int pos) {
                Log.i(TAG, "------pos:" + pos);
                return Beans.get(pos);
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
    }

    @Override
    public void onClick(View v) {
        onClickTab(v);
    }

    private void onClickTab(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.topbar_home:
                mTopTabs.get(0).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.topbar_having:
                mTopTabs.get(1).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.topbar_search:
                mTopTabs.get(2).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.topbar_warn:
                mTopTabs.get(3).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
            case R.id.topbar_person:
                mTopTabs.get(4).setCustomAlpha(1.0f);
                mViewPager.setCurrentItem(4, false);
                break;
        }
    }

    private void resetOtherTabs() {
        for (TopTabView topTabView : mTopTabs) {
            topTabView.setCustomAlpha(0);
        }
    }
}
