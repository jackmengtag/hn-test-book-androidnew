package com.yangna.lbdsp.common.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.MetricAffectingSpan;

import androidx.annotation.NonNull;

/**
 * artifact  文字管理
 */
public class TextStyleUtils {


    /**
     * 设置文本的字体、颜色
     * @param text 指定的文本
     * @param color 颜色值
     * @param size 字体大小（dp）
     * @return
     */
    private static SpannableStringBuilder spanColorAndSize(CharSequence text, int color, int size) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder();
        }
        final int length = text.length();
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        ssb.setSpan(new ForegroundColorSpan(color), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (size > 0) {
            ssb.setSpan(new AbsoluteSizeSpan(size, true), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ssb;
    }

    /**
     * 设置文本的字体颜色
     * @param text 指定的文本
     * @param color 颜色值
     * @return
     */
    private static SpannableStringBuilder spanColor(CharSequence text, int color) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder();
        }
        final int length = text.length();
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        ssb.setSpan(new ForegroundColorSpan(color), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    /**
     * 设置文本的字体大小
     * @param text 指定的文本
     * @param size 字体大小
     * @return
     */
    private static SpannableStringBuilder spanSize(CharSequence text, int size) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder();
        }
        final int length = text.length();
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        ssb.setSpan(new AbsoluteSizeSpan(size, true), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    /**
     * 设置文本的字体对齐
     * @param text 指定的文本
     * @param ratio 对齐参数
     * @return
     */
    public static SpannableStringBuilder spanAlign(CharSequence text, float ratio) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder();
        }
        final int length = text.length();
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);
        ssb.setSpan(new ShiftVerticalSpan(ratio), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }


    public static class TextStyle {

        private SpannableStringBuilder ssb = new SpannableStringBuilder();

        private int color;

        private int size;

        public TextStyle(int color, int size) {
            this.color = color;
            this.size = size;
        }

        public TextStyle(int color) {
            this.color = color;
            this.size = 0;
        }

        public TextStyle setColor(int color) {
            this.color = color;
            return this;
        }

        public TextStyle setSize(int size) {
            this.size = size;
            return this;
        }

        public SpannableStringBuilder merge(String start, String styleStr, String end) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(start);
            ssb.append(TextStyleUtils.spanColorAndSize(styleStr, color, size));
            ssb.append(end);
            return ssb;
        }

        public SpannableStringBuilder merge(String start, String styleStr) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(start);
            ssb.append(TextStyleUtils.spanColorAndSize(styleStr, color, size));
            return ssb;
        }

        public SpannableStringBuilder mergeEnd(String styleStr, String end) {
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(TextStyleUtils.spanColorAndSize(styleStr, color, size));
            ssb.append(end);
            return ssb;
        }

        public TextStyle spanSize(String text) {
            ssb.append(TextStyleUtils.spanSize(text, size));
            return this;
        }

        public TextStyle spanColor(String text) {
            ssb.append(TextStyleUtils.spanColor(text, color));
            return this;
        }

        public TextStyle spanColorAndSize(String text) {
            ssb.append(TextStyleUtils.spanColorAndSize(text, color, size));
            return this;
        }

        public TextStyle span(String text) {
            ssb.append(text);
            return this;
        }

        public TextStyle clear() {
            ssb.clear();
            return this;
        }

        public SpannableStringBuilder getText() {
            return ssb;
        }
    }

    /**
     * 设置字体的对齐距离
     */
    static class ShiftVerticalSpan extends MetricAffectingSpan {

        private float shiftRatio = 0.5f;

        ShiftVerticalSpan(float shiftRatio) {
            this.shiftRatio = shiftRatio;
        }

        @Override
        public void updateMeasureState(@NonNull TextPaint p) {
            p.baselineShift += shiftRatio * p.ascent();
        }

        @Override
        public void updateDrawState(TextPaint tp) {
            tp.baselineShift += shiftRatio * tp.ascent();
        }
    }


}
