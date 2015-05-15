package org.losyc.android.flipcopy.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import org.losyc.android.flipcopy.R;

/**
 * 字体图标类,引用于 http://fortawesome.github.io/Font-Awesome/
 * 将 ttf文件(字体图标.ttf) 放入到Android的assets目录
 * 在 xml 中引用此自定义 View 代替 Button 类即可
 * 字符串与图标的对应,请使用 FontLab Stduio 查看其 Unicode 码
 * 在String 里引用其文本即可  ...>&#x(4位Unicode码);<...
 * Created by LoSyc on 2015/5/12.
 * Modified by LoSyc on 2015/5/15
 */


public class FontIconButton extends Button {

    private static final String FONT_ICON_TTF = "frist_font_icon.ttf";

    private static Typeface fontIconTypeface;

    public FontIconButton(Context context) {
        super(context);
        setTypeface(getFontIconTypeface(context, FONT_ICON_TTF));
        // 使用自定义的背景，默认的背景在这里不适合
        setBackgroundResource(R.drawable.image_button_background);
    }

    public FontIconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(getFontIconTypeface(context, FONT_ICON_TTF));
        setBackgroundResource(R.drawable.image_button_background);
    }

    public FontIconButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(getFontIconTypeface(context, FONT_ICON_TTF));
        setBackgroundResource(R.drawable.image_button_background);
    }

    // 从assets目录中读取字体文件
    public static Typeface getFontIconTypeface(Context context, String typeface) {
        if (fontIconTypeface == null) {
            fontIconTypeface = Typeface.createFromAsset(context.getAssets(), typeface);
        }
        return fontIconTypeface;
    }
}