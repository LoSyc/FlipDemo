package org.losyc.android.flipcopy.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
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
 * Created by Losyc on 2015/5/15.
 * Modified by LoSyc on 12:33
 */
public class TopTabView extends View {
    private static final String FONT_ICON_TTF = "frist_font_icon.ttf";
    private static Typeface fontIconTypeface;
    private static final int GREY = 0xFFF5F5F5;
    private static final String INSTANCE_STATUS = "INSTANCE_STATUS";
    private static final String INSTANCE_ALPHA = "INSTANCE_ALPHA";

    private int mColor = GREY;
    private Bitmap mIconBitmap;
    private String mFontIcon = new String();
    private String mText = new String();
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private int mFontIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private float mAlpha = 0.0f;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;

    private Rect mIconRect;
    private Rect mTextBound = new Rect();
    private Rect mFontIconBound = new Rect();

    private Paint mTextPaint;
    private Paint mFontIconPaint;

    public TopTabView(Context context) {
        super(context);
    }

    public TopTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopTabView);

        initAttribute(typedArray);
        mTextPaint = initTextPaint(mTextPaint, mText, mTextSize, mTextBound);
        mFontIconPaint = initTextPaint(mFontIconPaint, mFontIcon, mFontIconSize, mFontIconBound);
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
                    break;
                case R.styleable.TopTabView_fontIcon:
                    mFontIcon = typedArray.getString(attr);
                    break;
                case R.styleable.TopTabView_text:
                    mText = typedArray.getString(attr);
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
        textPaint.getTextBounds(text, 0, text.length(), textBound);
        textPaint.setTextSize(textSize);
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

        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() -
                getPaddingBottom() - mTextBound.height());
        Point start = new Point(getMeasuredWidth() / 2 - iconWidth / 2, (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2);
        mIconRect = new Rect(start.x, start.y, start.x + iconWidth, start.y + iconWidth);
    }

    /**
     * 绘制 Icon
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

            canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

            int alpha = (int) Math.ceil(mAlpha * 255);
            setupTargetBitmap(alpha);

            drawSouceText(canvas, alpha);
            drawTargetText(canvas, alpha);

            drawSouceFontIcon(canvas, alpha);
            drawTargetFontIcon(canvas, alpha);

            canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    private void drawTargetFontIcon(Canvas canvas, int alpha) {
        mFontIconPaint.setColor(mColor);
        mFontIconPaint.setAlpha(alpha);
        Point start = new Point((getMeasuredWidth() - mFontIconBound.width() / 2), mIconRect.bottom + mFontIconBound.height());
        canvas.drawText(mFontIcon, start.x, start.y, mFontIconPaint);
    }

    private void drawSouceFontIcon(Canvas canvas, int alpha) {
        mFontIconPaint.setColor(GREY);
        mFontIconPaint.setAlpha(255 - alpha);
        Point start = new Point((getMeasuredWidth() - mFontIconBound.width() / 2), mIconRect.bottom + mFontIconBound.height());
        canvas.drawText(mFontIcon, start.x, start.y, mFontIconPaint);

    }

    /**
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        Point start = new Point((getMeasuredWidth() - mTextBound.width() / 2), mIconRect.bottom + mTextBound.height());
        canvas.drawText(mText, start.x, start.y, mTextPaint);
    }

    /**
     * @param canvas
     * @param alpha
     */
    private void drawSouceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(GREY);
        mTextPaint.setAlpha(255 - alpha);
        Point start = new Point((getMeasuredWidth() - mTextBound.width() / 2), mIconRect.bottom + mTextBound.height());
        canvas.drawText(mText, start.x, start.y, mTextPaint);
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
