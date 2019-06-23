package com.lyx.particle_effect;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class ExplosionAnimator extends ValueAnimator {

    private static final String TAG = "ExplosionAnimator";
    public static final int DEFAULT_DURATION = 1500;  //动画默认持续时间
    private Particle[][] mParticles;    //粒子们
    private ParticleFactory mParticleFactory;   //粒子工厂
    private View mContainer;   //动画执行区域
    private Paint mPaint;


    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound) {
        mParticleFactory = new FallingParticleFactory();
        mPaint = new Paint();
        setFloatValues(0f,1f);
        setDuration(DEFAULT_DURATION);
        mParticles = mParticleFactory.generateParticles(bitmap,bound);
        mContainer = view;
    }

    public void draw(Canvas canvas){
        if(!isStarted()){
            //动画结束
            return;
        }
        //所有粒子开始运动
        for (Particle[] mParticle : mParticles) {
            for (Particle particle : mParticle) {
                particle.advance(canvas,mPaint,(float) getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
        Log.d(TAG, "start: ");
    }
}
