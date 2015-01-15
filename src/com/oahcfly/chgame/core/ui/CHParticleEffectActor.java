
package com.oahcfly.chgame.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * 
 * <pre>
 * 粒子效果，持续的展示效果【雪花飘落....】
 * 
 * date: 2015-1-15
 * </pre>
 * @author caohao
 */
public class CHParticleEffectActor extends Actor {

    private ParticleEffect particleEffect;

    private boolean pause = false;

    public CHParticleEffectActor(ParticleEffect pemitter, String... name) {
        this.particleEffect = new ParticleEffect();
        for (String s : name) {
            this.particleEffect.getEmitters().add(new ParticleEmitter(pemitter.findEmitter(s)));
        }
    }

    @Override
    public void setColor(Color color) {
        for (ParticleEmitter e : this.particleEffect.getEmitters()) {
            if (e.getTint().getColors().length != 3)
                return;
            float[] colors = new float[e.getTint().getColors().length];
            for (int i = 0; i < colors.length; i += 3) {
                colors[i] = color.r;
                colors[i + 1] = color.g;
                colors[i + 2] = color.b;
            }
            e.getTint().setColors(colors);
        }
        super.setColor(color);
    }

    public ParticleEffect getpParticleEffect() {
        return particleEffect;
    }

    public void setParticleEffect(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (this.isVisible()) {
            if (parentAlpha == 1) {
                if (pause) {
                } else {
                    this.particleEffect.setPosition(this.getX(), this.getY());
                    this.particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
                }
            }
        }
    }

    public boolean isPaused() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
