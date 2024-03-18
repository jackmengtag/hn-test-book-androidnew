package com.shuiying.videomanage.filters;

import android.content.res.Resources;
import android.opengl.GLES20;

import com.shuiying.videomanage.filters.BaseFilter;

/**
 * description:显示画面的filter
 * Created by aserbao on 2018/5/15.
 */


public class NoneFilter extends BaseFilter {
    public NoneFilter(Resources res) {
        super(res);
    }

    @Override
    protected void onCreate() {
        createProgramByAssetsFile("shader/base_vertex.sh",
                "shader/base_fragment.sh");
    }

    /**
     * 背景默认为黑色
     */
    @Override
    protected void onClear() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    protected void onSizeChanged(int width, int height) {

    }
}
