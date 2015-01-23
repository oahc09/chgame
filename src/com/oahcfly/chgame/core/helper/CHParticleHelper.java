
package com.oahcfly.chgame.core.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.oahcfly.chgame.core.ui.CHParticleEffectActor;

/**
 * 
 * <pre>
 * 粒子助手
 * 
 * date: 2015-1-15
 * </pre>
 * @author caohao
 */
public class CHParticleHelper {

    /**
     * 
     * <pre>
     * 飘雪粒子【持续释放】
     * 
     * date: 2015-1-15
     * </pre>
     * @author caohao
     * @return
     */
    public static CHParticleEffectActor createSonwParticleEffectActor() {
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.classpath("com/oahcfly/chgame/particle/snow.p"),
                Gdx.files.classpath("com/oahcfly/chgame/particle/"));
        CHParticleEffectActor actor = new CHParticleEffectActor(particleEffect, "snow");
        return actor;
    }

    /**
     * 
     * <pre>
     * 圆点粒子【持续释放】
     * 
     * date: 2015-1-15
     * </pre>
     * @author caohao
     * @return
     */
    public static CHParticleEffectActor createDotParticleEffectActor() {
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.classpath("com/oahcfly/chgame/particle/level.p"),
                Gdx.files.classpath("com/oahcfly/chgame/particle/"));
        CHParticleEffectActor actor = new CHParticleEffectActor(particleEffect, "level");
        return actor;
    }
}
