package com.lyx.particle_effect;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

public class FallingParticleFactory extends ParticleFactory {

    private static final String TAG = "FallingParticleFactory";
    public static final int PART_WH = 8;  //粒子默认宽高

    @Override
    protected Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();
        Log.d(TAG, "generateParticles: " + bound.left + "," + bound.top);

        int partW_count = w / PART_WH;  //横向个数
        int partH_count = h / PART_WH;  //竖向个数

        //判断个数是否小于1，这种情况是控件的大小 < 8
        partW_count = partW_count > 1 ? partW_count : 1;
        partH_count = partH_count > 1 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count;
        int bitmap_part_h = bitmap.getHeight() / partH_count;

        Particle[][] particles = new Particle[partH_count][partW_count];
        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                //取得当前粒子所在位置颜色
                int color = bitmap.getPixel(column * bitmap_part_w,row * bitmap_part_h);
                float x = bound.left + column * PART_WH;
                float y = bound.top + row * PART_WH;
                particles[row][column] = new FallingParticle(x,y,color,bound);
            }
        }
        return particles;
    }
}
