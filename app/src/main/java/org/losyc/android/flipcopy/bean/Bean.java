package org.losyc.android.flipcopy.bean;

/**
 * Created by Administrator on 2015/5/6.
 */
public class Bean {
    private int mPhone;
    private String mDate;
    private String mTitle;
    private String mDescribe;

    public Bean(String title, String describe, int phone, String date) {
        this.mPhone = phone;
        this.mDate = date;
        this.mTitle = title;
        this.mDescribe = describe;
    }

    public int getPhone() {
        return mPhone;
    }

    public void setPhone(int phone) {
        this.mPhone = phone;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        this.mDescribe = describe;
    }
}
