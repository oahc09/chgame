
package com.oahcfly.chgame.test.screen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.oahcfly.chgame.core.ColorLabel;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.plist.CHPListCenter;
import com.oahcfly.chgame.util.aes.AESEncryptor;
import com.oahcfly.chgame.util.astar.CHAStar;
import com.oahcfly.chgame.util.astar.SearchNode;

public class AStarScreen extends CHScreen {

    @Override
    public void initScreen() {
        // TODO Auto-generated method stub
        int[][] map = new int[][] {
                {
                        1, 1, 0, 1, 1, 1, 1,
                }, {
                        1, 0, 0, 1, 0, 1, 1,
                }, {
                        1, 1, 1, 1, 0, 1, 1,
                }, {
                        1, 1, 1, 1, 0, 1, 1,
                }, {
                        1, 1, 1, 1, 0, 1, 1,
                }, {
                        1, 1, 1, 1, 0, 1, 1,
                }, {
                        1, 1, 1, 1, 1, 1, 1,
                }
        };

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (map[i][j] == 0) {
                    Image img = CHPListCenter.getInstance().getImageFromPList("plist/objecta.plist", "1001.png");
                    img.setX(i * img.getWidth());
                    img.setY(j * img.getHeight());
                    img.setName(i + "." + j);
                    addActor(img);
                } else {
                    Image img = CHPListCenter.getInstance().getImageFromPList("plist/objecta.plist", "1006.png");
                    img.setX(i * img.getWidth());
                    img.setY(j * img.getHeight());
                    img.setName(i + "." + j);
                    addActor(img);
                }
            }
        }

        CHAStar chaStar = new CHAStar(new Vector2(0, 0), new Vector2(6, 6), map);
        ArrayList<SearchNode> pathList = chaStar.doSearch();
        for (SearchNode node : pathList) {
            int x = (int)node.getPointVector2().x;
            int y = (int)node.getPointVector2().y;
            Actor actor = getStage().getRoot().findActor(x + "." + y);
            if (actor != null) {
                actor.setColor(Color.RED);
            }
        }

        String text = "#FF0000Complete goals in\n#FFFFFF 100 \n#FF00FF moves";
        ColorLabel label = new ColorLabel(text, new LabelStyle(new BitmapFont(), Color.BLACK));
        label.setY(300);
        addActor(label);

        try {
            String enString = AESEncryptor.encrypt(AESEncryptor.MAK, "abc");
            System.out.println("加密后：" + enString);
            System.out.println("解密后：" + AESEncryptor.decrypt(AESEncryptor.MAK, enString));
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

}
