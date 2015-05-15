package org.losyc.android.flipcopy.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import org.losyc.android.flipcopy.R;

/**
 * Created by Losyc on 2015/5/15.
 * Modified by LoSyc on 12:33
 */
public class TopBarView extends View {
    private static final String FONT_ICON_TTF = "frist_font_icon.ttf";
    private static Typeface fontIconTypeface;

    private int mColor = 0xFFF5F5F5;
    private Bitmap mIconBitmap;
    private String mFontIcon;
    private String mText;
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private int mFontIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());

    private int mAlpha;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Canvas mCanvas;

    private Rect mIconRect;
    private Rect mTextBound;
    private Rect mFontIconBound;

    private Paint mTextPaint;
    private Paint mFontIconPaint;

    public TopBarView(Context context) {
        super(context);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);

        mTextBound = new Rect();
        mFontIconBound = new Rect();
        mText = new String();
        mFontIcon = new String();

        initAttribute(typedArray);



        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        mFontIconPaint = new Paint();
        mFontIconPaint.setTextSize(mFontIconSize);
        mFontIconPaint.getTextBounds(mFontIcon, 0, mFontIcon.length(), mFontIconBound);
    }

    /**
     * 获取自定义属性的值
     * @param typedArray
     */
    private void initAttribute(TypedArray typedArray) {
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TopBarView_icon:
                    BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.TopBarView_fontIcon:
                    mFontIcon = typedArray.getString(attr);
                    break;
                case R.styleable.TopBarView_text:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.TopBarView_textSize:
                    mTextSize = (int) typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TopBarView_fontIconSize:
                    mFontIconSize = (int) typedArray.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                            getResources().getDisplayMetrics()));
                    break;
                case R.styleable.TopBarView_color:
                    mColor = typedArray.getColor(attr, mColor);
                    break;
            }
        }
    }

    /**
     * 绘制正方形View
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth;
        Point start;
        iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() -
                getPaddingBottom() - mTextBound.height());
        start = new Point(getMeasuredWidth() / 2 - iconWidth / 2, (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2);
        mIconRect = new Rect(start.x, start.y, start.x + iconWidth, start.y + iconWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        super.onDraw(canvas);
    }
}
