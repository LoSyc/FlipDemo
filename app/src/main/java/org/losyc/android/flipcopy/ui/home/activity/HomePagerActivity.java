package org.losyc.android.flipcopy.ui.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import org.losyc.android.flipcopy.R;
import org.losyc.android.flipcopy.ui.customview.TopTabView;
import org.losyc.android.flipcopy.ui.having.fragment.HavingPagerFragment;
import org.losyc.android.flipcopy.ui.home.fragment.HomeFragment;
import org.losyc.android.flipcopy.ui.person.fragment.PersonFragment;
import org.losyc.android.flipcopy.ui.search.fragment.SearchFragment;
import org.losyc.android.flipcopy.ui.warn.fragment.WarnFragment;
import org.losyc.android.flipcopy.util.SinglePagerFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Losyc on 2015/5/12.
 * Modified by LoSyc on 22:03
 */
public class HomePagerActivity extends SinglePagerFragmentActivity<Fragment, ViewPager> implements View.OnClickListener {
    public final static String TAG = "HomePagerActivity";
    private List<Fragment> mBeans = new ArrayList<Fragment>();
    private List<TopTabView> mTopTabs = new ArrayList<TopTabView>();
    private ViewPager mViewPager;

    @Override
    protected List<Fragment> initListBean() {

        TopTabView one = (TopTabView) findViewById(R.id.topbar_home);
        TopTabView two = (TopTabView) findViewById(R.id.topbar_having);
        mTopTabs.add(one);
        mTopTabs.add(two);

        one.setOnClickListener(this);
        two.setOnClickListener(this);

        one.setCustomAlpha(1.0F);


        mBeans.add(HomeFragment.newInstance());
        mBeans.add(HavingPagerFragment.newInstance());
        mBeans.add(SearchFragment.newInstance());
        mBeans.add(WarnFragment.newInstance());
        mBeans.add(PersonFragment.newInstance());
        return mBeans;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected int getViewPagerResId() {
        return R.id.homeViewPagerContainer;
    }

    @Override
    protected void BindAdapter(ViewPager viewPager, FragmentManager fm, final List<Fragment> Beans) {
        mViewPager = viewPager;
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new FragmentPagerAdapter(fm) {
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

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        }
    }

    private void resetOtherTabs() {
        for (TopTabView topTabView : mTopTabs) {
            topTabView.setCustomAlpha(0);
        }
    }
}
