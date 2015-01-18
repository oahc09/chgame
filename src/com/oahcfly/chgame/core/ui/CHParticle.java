
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * <pre>
 * 粒子封装：【适用于点击操作触发粒子特效】
 * 
 * date: 2014-11-25
 * </pre>
 * @author caohao
 */
public class CHParticle {
    public enum ParticleType {
        DEFAULT, STAR
    }

    //当前粒子列表
    private Array<PooledEffect> _effects;

    // 粒子池
    private ParticleEffectPool _effectPool;

    public CHParticle() {
        // Particle effects
        //        ParticleEffect _effect = new ParticleEffect();//particleStars
        //        _effect.load(Gdx.files.classpath("com/oahcfly/chgame/particle/star.p"),
        //                Gdx.files.classpath("com/oahcfly/chgame/particle"));
        //        _effectPool = new ParticleEffectPool(_effect, 20, 100);
        //        _effects = new Array<PooledEffect>();
        this(ParticleType.DEFAULT);
    }

    public CHParticle(ParticleType particleType) {
        String fileName = "";
        switch (particleType) {
            case DEFAULT:
                fileName = "particleStars";
                break;
            case STAR:
                fileName = "star.p";
                break;
        }

        ParticleEffect _effect = new ParticleEffect();
        _effect.load(Gdx.files.classpath("com/oahcfly/chgame/particle/" + fileName),
                Gdx.files.classpath("com/oahcfly/chgame/particle"));
        _effectPool = new ParticleEffectPool(_effect, 20, 200);
        _effects = new Array<PooledEffect>();

    }

    public CHParticle(FileHandle effectFile, FileHandle imagesDir) {
        // Particle effects
        ParticleEffect _effect = new ParticleEffect();
        _effect.load(effectFile, imagesDir);
        _effectPool = new ParticleEffectPool(_effect, 20, 100);
        _effects = new Array<PooledEffect>();
    }

    /**
     * 
     * <pre>
     * 在(px,py)处添加粒子效果
     * 
     * date: 2014-11-21
     * </pre>
     * @author caohao
     * @param px
     * @param py
     */
    public void createEffect(float px, float py) {
        // 生成粒子特效
        PooledEffect newEffect = _effectPool.obtain();
        newEffect.setPosition(px, py);
        newEffect.start();
        _effects.add(newEffect);
    }

    /**
     * 
     * <pre>
     * 移除已经结束的粒子
     * 
     * date: 2014-6-29
     * </pre>
     * @author caohao
     */
    private void removeEndedParticles() {
        int numParticles = _effects.size;

        for (int i = 0; i < numParticles; ++i) {
            PooledEffect effect = _effects.get(i);

            if (effect.isComplete()) {
                _effectPool.free(effect);
                _effects.removeIndex(i);
                --i;
                --numParticles;
            }
        }
    }

    /**
     * 
     * <pre>
     * 粒子绘制
     * 
     * date: 2014-11-25
     * </pre>
     * @author caohao
     * @param batch
     */
    public void render(Batch batch) {
        // Particle effects
        int numParticles = _effects.size;
        for (int i = 0; i < numParticles; ++i) {
            _effects.get(i).update(Gdx.graphics.getDeltaTime());
        }

        batch.begin();
        // Draw particle systems
        for (int i = 0; i < numParticles; ++i) {
            _effects.get(i).draw(batch);
        }

        batch.end();
        
        // Remove the ended particle systems
        removeEndedParticles();
    }

}
