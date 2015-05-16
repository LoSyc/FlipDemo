package org.losyc.android.flipcopy.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import org.losyc.android.flipcopy.R;

/**
 * 自定义 View 类,可实现字体图标, 图标文本, 单图标, 单文本的渐变色
 * 字体图标引用于 http://fortawesome.github.io/Font-Awesome/
 * 将 ttf文件(字体图标.ttf) 放入到Android的assets目录
 * 在 xml 中引用此自定义 View 代替 Button 类即可
 * 字符串与图标的对应,请使用 FontLab Stduio 查看其 Unicode 码
 * 在String 里引用其文本即可  ...>&#x(4位Unicode码);<...
 * Created by Losyc on 2015/5/15.
 * Modified by LoSyc on 2015/5/15
 */
public class TopTabView extends View {
    private static final String FONT_ICON_TTF = "frist_font_icon.ttf";
    private static Typeface fontIconTypeface;
    private static final int GREY = 0xFFD8D8D8;
    private static final String INSTANCE_STATUS = "INSTANCE_STATUS";
    private static final String INSTANCE_ALPHA = "INSTANCE_ALPHA";

    private int mColor = GREY;
    private Bitmap mIconBitmap;
    private String mFontIcon;
    private String mText;
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private int mFontIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private float mAlpha = 0.0f;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;

    private Rect mIconRect;
    private Rect mTextBound;
    private Rect mFontIconBound;

    private Paint mTextPaint;
    private Paint mFontIconPaint;

    private boolean mIconBitmapIsEmpty = true;
    private boolean mTextIsEmpty = true;
    private boolean mFontIconIsEmpty = true;

    public TopTabView(Context context) {
        super(context);
    }

    public TopTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopTabView);

        initAttribute(typedArray);
        if (!mTextIsEmpty) {
            mTextPaint = initTextPaint(mTextPaint, mText, mTextSize, mTextBound);
        }
        if (!mFontIconIsEmpty) {
            mFontIconPaint = new Paint();
            setTypeface(getFontIconTypeface(context, FONT_ICON_TTF));
            setBackgroundResource(R.drawable.image_button_background);
            mFontIconPaint.setTextSize(mFontIconSize);
            mFontIconPaint.getTextBounds(mFontIcon, 0, mFontIcon.length(), mFontIconBound);
        }
    }

    public static Typeface getFontIconTypeface(Context context, String typeface) {
        if (fontIconTypeface == null) {
            fontIconTypeface = Typeface.createFromAsset(context.getAssets(), typeface);
        }
        return fontIconTypeface;
    }

    public void setTypeface(Typeface tf) {
        if (mFontIconPaint.getTypeface() != tf) {
            mFontIconPaint.setTypeface(tf);
        }
    }

    /**
     * 从自定义 xml 文件中获取自定义属性的值
     * @param typedArray
     */
    private void initAttribute(TypedArray typedArray) {
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TopTabView_icon:
                    BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    mIconBitmapIsEmpty = false;
                    break;
                case R.styleable.TopTabView_fontIcon:
                    mFontIcon = typedArray.getString(attr);
                    mFontIconBound = new Rect();
                    mFontIconIsEmpty = false;
                    break;
                case R.styleable.TopTabView_text:
                    mText = typedArray.getString(attr);
                    mTextBound = new Rect();
                    mTextIsEmpty = false;
                    break;
                case R.styleable.TopTabView_textSize:
                    mTextSize = (int) typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TopTabView_fontIconSize:
                    mFontIconSize = (int) typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TopTabView_color:
                    mColor = typedArray.getColor(attr, mColor);
                    break;
            }
        }
    }

    /**
     * 初始化 text 类 Paint,并设置 textBound
     *
     * @param textPaint 文本画笔
     * @param text      文本内容
     * @param textSize  文本大小
     * @param textBound 文本绘制范围
     */
    private Paint initTextPaint(Paint textPaint, String text, int textSize, Rect textBound) {
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        return textPaint;
    }

    /**
     * 计算绘制正方形 IconView 的范围
     * @param widthMeasureSpec 控件的宽度
     * @param heightMeasureSpec 控件的高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iconWidth;
        int x, y;
        if (!mIconBitmapIsEmpty || !mFontIconIsEmpty) {
            if (!mIconBitmapIsEmpty) {
                if (!mTextIsEmpty) {
                    iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() -
                            getPaddingBottom() - mTextBound.height());
                    x = getMeasuredWidth() / 2 - iconWidth / 2;
                    y = getMeasuredHeight() / 2 - (iconWidth + mTextBound.height()) / 2;
                } else {
                    iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() -
                            getPaddingBottom());
                    x = getMeasuredWidth() / 2 - iconWidth / 2;
                    y = getMeasuredHeight() / 2 - iconWidth / 2;
                }
                mIconRect = new Rect(x, y, x + iconWidth, y + iconWidth);
            } else {
                if (!mTextIsEmpty) {
                    x = getMeasuredWidth() / 2 - mFontIconBound.width() / 2;
                    y = getMeasuredHeight() / 2 - (mFontIconBound.height() + mTextBound.height()) / 2;
                } else {
                    x = getMeasuredWidth() / 2 - mFontIconBound.width() / 2;
                    y = getMeasuredHeight() / 2 - mFontIconBound.height() / 2;
                }
                mIconRect = new Rect(x, y, x + mFontIconBound.width(), y + mFontIconBound.height());
            }
        }
    }

    /**
     * 绘制 Icon
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        int alpha = (int) Math.ceil(mAlpha * 255);

        if (!mIconBitmapIsEmpty) {
            canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
            setupTargetBitmap(alpha);
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
        if (!mFontIconIsEmpty) {
            setTextColorAlgha(mFontIconPaint, GREY, 255 - alpha);
            drawFontIcon(canvas);
            setTextColorAlgha(mFontIconPaint, mColor, alpha);
            drawFontIcon(canvas);
        }
        if (!mTextIsEmpty) {
            setTextColorAlgha(mTextPaint, GREY, 255 - alpha);
            drawText(canvas);
            setTextColorAlgha(mTextPaint, mColor, alpha);
            drawText(canvas);
        }
    }

    private void setTextColorAlgha(Paint textPaint,int color, int alpha) {
        textPaint.setColor(color);
        textPaint.setAlpha(alpha);
    }

    private void drawFontIcon(Canvas canvas) {

        int x = mIconRect.left;
        int y = mIconRect.bottom;
        canvas.drawText(mFontIcon, x, y, mFontIconPaint);
    }

    private void drawText(Canvas canvas) {
        int x, y;
        if (!mIconBitmapIsEmpty || !mFontIconIsEmpty) {
            x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
            y = mIconRect.bottom + mTextBound.height();
        } else {
            x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
            y = getMeasuredHeight() / 2 + mTextBound.height() / 2;
        }
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制渐变色的 ICON
     */
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);

    }

    public void setCustomAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(INSTANCE_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
