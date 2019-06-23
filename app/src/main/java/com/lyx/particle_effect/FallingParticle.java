package com.lyx.particle_effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class FallingParticle extends Particle {

    private static final String TAG = "FallingParticle";
    private float radius = FallingParticleFactory.PART_WH;   //粒子半径
    private float alpha = 1.0f;  //透明度
    private Rect mBound;

    public FallingParticle(float x, float y, int color,Rect bound) {
        super(x, y, color);
        mBound = bound;
    }

    @Override
    protected void compute(float factor) {
        x = x + factor * Utils.RANDOM.nextInt(mBound.width()) * (Utils.RANDOM.nextFloat() - 0.5f); //[0-1↑] * [-0.5w,0.5w]
        y = y + factor * Utils.RANDOM.nextInt(mBound.height() / 2); //[0-1↑] * [0,0.5h]

        radius = radius - factor * Utils.RANDOM.nextInt(2);  //radius - [0-1↑] * [0-2]
        alpha = (1 - factor) * (1 + Utils.RANDOM.nextFloat());   // [0-1↓] * (1 + [0,1])
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(x,y,radius,paint);
    }
}
