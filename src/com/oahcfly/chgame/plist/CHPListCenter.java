
package com.oahcfly.chgame.plist;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.oahcfly.chgame.core.mvc.CHGame;

public class CHPListCenter {

    private static CHPListCenter instance;

    public static CHPListCenter getInstance() {
        if (instance == null) {
            instance = new CHPListCenter();
        }
        return instance;
    }

    private HashMap<String, CHPList> chplitMap = new HashMap<String, CHPList>();

    public Image getImageFromPList(String plistPath, String imgName) {
        CHPList chpList = null;
        if (chplitMap.containsKey(plistPath)) {
            chpList = chplitMap.get(plistPath);
        } else {
            chpList = new CHPList(plistPath);
        }
        chpList.parse();

        String imgPath = plistPath.replace(".plist", ".png");

        FrameParam frameParam = chpList.getFrameParam(imgName);

        Texture texture = CHGame.getInstance().getTexture(imgPath);
        int w = frameParam.isRotated() ? frameParam.getHeight() : frameParam.getWidth();
        int h = frameParam.isRotated() ? frameParam.getWidth() : frameParam.getHeight();
        AtlasRegion textureRegion = new TextureAtlas.AtlasRegion(texture, frameParam.getFromX(), frameParam.getFromY(),
                w, h);

        textureRegion.rotate = frameParam.isRotated();
        textureRegion.flip(frameParam.isRotated(), frameParam.isRotated());
        Image image = new Image(new SpriteDrawable(createSprite(textureRegion)));
        image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
        return image;

    }

    public Sprite createSprite(AtlasRegion region) {
        if (region.rotate) {
            Sprite sprite = new Sprite(region);
            sprite.setBounds(0, 0, region.getRegionHeight(), region.getRegionWidth());
            sprite.rotate90(true);
            return sprite;
        }
        return new Sprite(region);
    }

}
