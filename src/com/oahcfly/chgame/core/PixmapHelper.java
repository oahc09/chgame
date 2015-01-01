
package com.oahcfly.chgame.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

/**
 * 
 * <pre>
 * 使用Pixmap绘制一些常见的图形
 * 
 * date: 2014-12-31
 * </pre>
 * @author caohao
 */
public class PixmapHelper {

    /**
     * 
     * <pre>
     * 绘制方块
     * 
     * date: 2014-12-31
     * </pre>
     * @author caohao
     * @param color
     * @param width
     * @param height
     * @return
     */
    public static Texture createRectangleTexture(Color color, int width, int height) {
        // 绘制方块
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        // 背景
        //        pixmap.setColor(Color.WHITE);
        //        pixmap.fill();

        // 圆
        pixmap.setColor(color);
        // 以左上角为原点
        pixmap.fillRectangle(0, 0, width, height);
        // Create a texture to contain the pixmap
        Texture texture = new Texture(width, height, Pixmap.Format.RGBA8888);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        // Blit the composited overlay to a texture
        // 以左上角为原点
        texture.draw(pixmap, 0, 0);
        pixmap.dispose();
        return texture;
    }

    /**
     * 
     * <pre>
     * 绘制圆
     * 
     * date: 2014-12-31
     * </pre>
     * @author caohao
     * @param color
     * @param radius
     * @return
     */
    public static Texture createCircleTexture(Color color, int radius) {
        // 绘制圆
        Pixmap pixmap = new Pixmap(radius * 2, radius * 2, Format.RGBA8888);
        // 背景
        //        pixmap.setColor(Color.WHITE);
        //        pixmap.fill();

        // 圆
        pixmap.setColor(color);
        // 以左上角为原点
        pixmap.fillCircle(radius, radius, radius);
        // Create a texture to contain the pixmap
        Texture texture = new Texture(radius * 2, radius * 2, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        // Blit the composited overlay to a texture
        // 以左上角为原点
        texture.draw(pixmap, 0, 0);
        pixmap.dispose();
        return texture;
    }
}
