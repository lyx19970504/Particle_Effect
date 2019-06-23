package com.lyx.particle_effect;

import android.graphics.Bitmap;
import android.graphics.Rect;

//粒子工厂
public abstract class ParticleFactory {

    protected abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
