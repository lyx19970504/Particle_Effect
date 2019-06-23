package com.lyx.particle_effect;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Particle {

    float x;
    float y;
    int color;

    public Particle(float x, float y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //计算
    protected abstract void compute(float factor);

    //绘制
    protected abstract void draw(Canvas canvas,Paint paint);

    //逐步绘制
    protected void advance(Canvas canvas,Paint paint,float factor){
        compute(factor);
        draw(canvas,paint);
    }
}
