
package com.oahcfly.chgame.core.helper;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Pool;
import com.oahcfly.chgame.core.actions.CHScheduleAction;
import com.oahcfly.chgame.core.mvc.CHGame;

/**
 * 
 * <pre>
 *   
 * 可自由扩展的粒子播放器
 * <设置粒子图片路径即可>
 * date: 2015年3月26日
 * </pre>
 * @author caohao
 */
public class CHAutoParticle {
    private Random random = new Random();

    private Pool<Image> starPool = null;

    /**
     * 
     * @param imgPath 图片路径
     */
    public CHAutoParticle(final String imgPath) {
        starPool = new Pool<Image>(200, 400) {
            @Override
            protected Image newObject() {
                Image img = CHGame.getInstance().getImage(imgPath);
                img.setSize(50, 50);
                img.setOrigin(Align.center);
                float r = (float)((Math.random() * 0.4f) + 0.7f);
                float g = (float)((Math.random() * 0.4f) + 0.7f);
                float b = (float)((Math.random() * 0.4f) + 0.7f);
                img.setColor(r, g, b, 1);
                return img;
            }
        };
    }

    /**
     * 
     * <pre>
     *   <由中心向周围扩散并且旋转缩小>
     *    播放粒子效果
     * date: 2015年3月26日
     * </pre>
     * @author caohao
     * @param stage
     * @param centerX 中心点位置X
     * @param centerY 中心点位置Y
     */
    public void playCircleParticle(Stage stage, float centerX, float centerY) {
        // 半径范围
        float initRadius = 100;
        // 星星数量
        float starNum = 50;
        float initStarDuration = 0.3f;
        for (int i = 0; i < starNum; i++) {
            // 随机角度
            int degree = random.nextInt(360) + 1;
            // 随机运行时间
            float starDuration = (float)(initStarDuration + random.nextInt(5) / 10f);
            // 随机半径
            float radius = initRadius + random.nextInt(10);
            final Image starImg = starPool.obtain();
            starImg.setPosition(centerX, centerY, Align.center);
            starImg.setScale(1);
            // 目标点
            float targetX = centerX + (float)(radius * Math.cos(Math.toRadians(degree)));
            float targetY = centerY + (float)(radius * Math.sin(Math.toRadians(degree)));

            // 运动同时进行旋转，缩小
            Action parallelAction = Actions.parallel(Actions.moveTo(targetX, targetY, starDuration),
                    Actions.rotateBy(360, starDuration), Actions.scaleTo(0.1f, 0.1f, starDuration));
            starImg.addAction(Actions.sequence(parallelAction, Actions.run(new Runnable() {

                @Override
                public void run() {
                    // 释放加移除
                    starPool.free(starImg);
                    starImg.remove();
                }
            })));
            stage.addActor(starImg);
        }
    }

    /**
     * 
     * <pre>
     * <烟花效果>
     * 
     * date: 2015年3月26日
     * </pre>
     * @author caohao
     * @param stage
     * @param rangeRectangle 烟花范围
     * @param repeatCount 重复次数
     */
    public void playFireWorks(final Stage stage, final Rectangle rangeRectangle, int repeatCount) {
        Action runnableAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    int x = (int)(random.nextInt((int)rangeRectangle.width) + rangeRectangle.x);
                    int y = (int)(random.nextInt((int)rangeRectangle.height) + rangeRectangle.y);
                    playCircleParticle(stage, x, y);
                }
            }
        });
        CHScheduleAction scheduleAction = new CHScheduleAction(0.5f, repeatCount, runnableAction);
        stage.addAction(scheduleAction);
    }
}
