package com.yangna.lbdsp.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.yangna.lbdsp.R;
import com.yangna.lbdsp.common.utils.Utils;

/**
 * @Author：Administrator 2017/11/23 0023 下午 5:37
 * artifact  带删除按钮的EditText
 */
public class ClearEditText extends AppCompatEditText {

    private static final int DRAWABLE_LEFT = 0;
    private static final int DRAWABLE_TOP = 1;
    private static final int DRAWABLE_RIGHT = 2;
    private static final int DRAWABLE_BOTTOM = 3;
    private Drawable mClearDrawable;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Resources res = this.getResources();
        Bitmap oldBmp = BitmapFactory.decodeResource(res, R.mipmap.ic_edit_text_clear);
        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp, 60, 60, true);//重新设置大小
//        mClearDrawable = new BitmapDrawable(res, newBmp);
        mClearDrawable = new BitmapDrawable(res, newBmp);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() > 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[DRAWABLE_RIGHT];
            if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    private void setClearIconVisible(boolean visible) {
        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWABLE_LEFT], getCompoundDrawables()[DRAWABLE_TOP], visible ? mClearDrawable : null, getCompoundDrawables()[DRAWABLE_BOTTOM]);
        setCompoundDrawablePadding(Utils.dip2px(getContext(), 10));//默认文字靠左边距离
    }

//    @Override
//    public boolean dispatchKeyEventPreIme(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
////            ((Activity) this.getContext()).onKeyDown(KeyEvent.KEYCODE_BACK, event);
////            ((Activity) this.getContext()).finish();
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }

}

