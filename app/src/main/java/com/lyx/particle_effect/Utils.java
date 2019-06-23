package com.lyx.particle_effect;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

public class Utils  {

    public static final Random RANDOM = new Random(System.currentTimeMillis());
    public static final Canvas CANVAS = new Canvas();

    public static Bitmap createBitmapFromView(View view){
        view.clearFocus(); // 使View失去焦点
        Bitmap bitmap = createBitmapSafely(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888,3); //防止内存溢出
        if(bitmap != null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }


    private static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config,int retryCount) {
        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError error){
            error.printStackTrace();
            if(retryCount > 0){
                System.gc();
                return createBitmapSafely(width,height,config,retryCount - 1);
            }
        }
        return null;
    }
}
