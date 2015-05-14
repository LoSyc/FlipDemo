package org.losyc.android.flipcopy.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Losyc on 2015/5/6.
 * Modified by Administrator on 13:35
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    /**
     * 初始化通用适配器
     * @param context       上下文环境,用于获得充气筒
     * @param datas         数据集
     * @param itemLayoutId  布局 id
     */
    public CommonAdapter(Context context, List<T> datas, int itemLayoutId) {
        mContext = context;
        //根据上下文获得充气筒
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = getViewHolder(position, convertView, parent);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    /**
     *
     * @param holder
     * @param item
     */
    public abstract void convert(ViewHolder holder, T item);

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }
}
