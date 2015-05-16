package org.losyc.android.flipcopy.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Created by Losyc on 2015/5/12.
 * Modified by LoSyc on 12:24
 */

/**
 * 使用 ViewPager 的通用 Activity 抽象类
 * @param <Params> 与 ViewPager 绑定的数据集的类型
 * @param <DefindViewPager> ViewPager 控件的类型,可自定义 ViewPager
 */
public abstract class SinglePagerFragmentActivity<Params, DefindViewPager extends ViewPager> extends FragmentActivity {

    private ViewPager mViewPager;
    private List<Params> mListBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mViewPager = (DefindViewPager) findViewById(getViewPagerResId());

        FragmentManager fm = getSupportFragmentManager();
        initViewBean();
        mListBeans = initListBean();

        BindAdapter(mViewPager, fm, mListBeans);

    }

    /**
     * 初始化子类视图
     */
    protected abstract void initViewBean();

    /**
     * 获取子类中配置的布局 id ,实现灵活布局
     * @return 布局id
     */
    protected abstract int getLayoutResId();

    /**
     * 获取子类中配置的控件id
     * @return ViewPager 控件id
     */
    protected abstract int getViewPagerResId();

    /**
     * 将适配器延迟到子类配置
     * @param viewPager 需绑定 adapterd 的 viewpager
     * @param fm 用于设置 adapter 的 FragmentManager
     * @param Beans 设置 adapter 的数据集
     */
    protected abstract void BindAdapter(ViewPager viewPager, FragmentManager fm, final List<Params> Beans);

    /**
     * 初始化数据集
     * @return 数据集
     */
    protected abstract List<Params> initListBean();


}
