package org.losyc.android.flipcopy.ui.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import org.losyc.android.flipcopy.R;
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
public class HomePagerActivity extends SinglePagerFragmentActivity<Fragment, ViewPager> {
    public final static String TAG = "HomePagerActivity";

    @Override
    protected List<Fragment> initListBean() {
        List<Fragment> Beans = new ArrayList<Fragment>();
        Beans.add(HomeFragment.newInstance());
        Beans.add(HavingPagerFragment.newInstance());
        Beans.add(SearchFragment.newInstance());
        Beans.add(WarnFragment.newInstance());
        Beans.add(PersonFragment.newInstance());
        return Beans;
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
//        viewPager.setOffscreenPageLimit(4);
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
    }
}
