package org.losyc.android.flipcopy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.losyc.android.flipcopy.R;
import org.losyc.android.flipcopy.service.ImageLoader;
import org.losyc.android.flipcopy.service.ImageLoader.Type;

/**
 * Created by losyc on 2015/5/6.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosititon;
    private View mConvertView;

    /**
     * ViewHolder 构造方法初始化 ViewHolder
     * @param context   上下文环境,用于获取相关的工具,如布局充气筒
     * @param parent    指定布局时的视图组(父视图)
     * @param layoutId  布局 id
     * @param posititon listView 的参数
     */
    public ViewHolder(Context context, ViewGroup parent, int layoutId, int posititon) {
        this.mPosititon = posititon;
        mViews = new SparseArray<View>();
        //布局充气筒,实现视图的布局
        mConvertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        //设置视图的 Tag
        mConvertView.setTag(this);
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosititon() {
        return mPosititon;
    }

    /**
     * get 方法得当 viewholder 对象,如果当前视图存在 viewholder 对象则返回该对象
     * 否则 new 一个 viewholder 对象
     * @param context       用于创建 viewholder
     * @param convertView   传入的视图,判断是否存在 viewholder 对象
     * @param parent        用于创建 viewholder
     * @param layoutId      用于创建 viewholder
     * @param posititon     listView 的参数
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int posititon) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, posititon);
        } else {
            //利用 getTag 返回对象
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过控件的 Id 获取对于的控件，如果没有则加入 views
     * @param viewId    控件 id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            //加入 view 到 view 集合中
            mViews.put(viewId, view);
        }
        //强制转换并返回该 view
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     * @param viewId
     * @param url
     * @return
     */
    public ViewHolder setImageByUrl(int viewId, String url) {
        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
                (ImageView) getView(viewId));
        return this;
    }
}
