package com.lyx.particle_effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ExplosionField extends View {

    private static final String TAG = "ExplosionField";
    private List<ExplosionAnimator> explosionAnimators;
    private OnClickListener onClickListener;

    public ExplosionField(Context context) {
        super(context);
        explosionAnimators = new ArrayList<>();
        //将动画区域添加到界面上
        attachToActivity();
    }

    private void attachToActivity() {
        //content是一个帧布局
        ViewGroup rootView = ((Activity) getContext()).getWindow().getDecorView().findViewById(android.R.id.content);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this,params);
    }

    /**
     * 添加需要有粒子特效的View
     * @param view
     */
    public void addListener(View view){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            int viewCount = viewGroup.getChildCount();
            for (int i = 0; i < viewCount; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        }else{
            view.setOnClickListener(getOnClickListener());
        }
    }

    public OnClickListener getOnClickListener(){
        if(onClickListener == null){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    explode(v);
                }
            };
        }
        return onClickListener;
    }

    //执行爆炸特效
    public void explode(final View view){
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect); //获取View相对整个屏幕的位置
        //标题栏高度
        int titleHeight = ((ViewGroup) getParent()).getTop();
        //状态栏高度
        Rect frame = new Rect();
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        //相对整个屏幕的位置-标题栏（actionBar）的高度-状态栏的高度
        rect.offset(0,-titleHeight-statusBarHeight);

        if(rect.width() == 0 || rect.height() == 0){
            return;
        }

        //震动动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view,rect);
            }
        });
        animator.start();
    }

    private void explode(final View view,Rect bound){
       final  ExplosionAnimator explosionAnimator = new ExplosionAnimator(this,Utils.createBitmapFromView(view),bound);
        explosionAnimators.add(explosionAnimator);
        explosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                //缩小透明
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setClickable(true);
                //放大
                view.animate().setDuration(150).scaleX(1f).scaleY(1f).alpha(1f).start();
                explosionAnimators.remove(explosionAnimator);
            }
        });
        explosionAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }
}
