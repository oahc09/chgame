
package com.oahcfly.chgame.test.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.core.CHGame;
import com.oahcfly.chgame.core.CHScreen;
import com.oahcfly.chgame.plist.CHPListCenter;
import com.oahcfly.chgame.test.ui.TestUI;
import com.oahcfly.chgame.util.astar.CHAStar;

public class FirstScreen extends CHScreen {

    @Override
    public void initScreen() {

        Gdx.app.log(getTAG(), "init screen : first" + Texture.getManagedStatus());

        //        tableTest();

        //        testAstar();
        plistTest();

        TextureAtlas     textureAtlas = new TextureAtlas(Gdx.files.internal("testAtlas.txt"));
        Image autumnImage = new Image(textureAtlas.findRegion("title_summer"));
        addActor(autumnImage);
        
        sprite=textureAtlas.createSprite("title_autumn");
        sprite.setX(57);
      
    }

    Sprite sprite;

    private void testAstar() {
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
        CHAStar chaStar = new CHAStar(new Vector2(0, 0), new Vector2(6, 6), map);
        chaStar.doSearch();
    }

    private void plistTest() {
        final Image achieveImage = CHPListCenter.getInstance().getImageFromPList("plist/newPic1.plist",
                "makeConfirm.png");
        addActor(achieveImage);
        achieveImage.setX(CHGame.getInstance().gameWidth / 2 - achieveImage.getWidth() / 2);
        achieveImage.setY(20);
        //        achieveImage.rotateBy(90);
        achieveImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                achieveImage.setScale(0.8f);
                new TestUI(getStage()).show();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                achieveImage.setScale(1f);
                super.touchUp(event, x, y, pointer, button);
            }

        });

        Image awardForAndriod = CHPListCenter.getInstance().getImageFromPList("plist/objecta.plist", "1002.png");
        awardForAndriod.setX(CHGame.getInstance().gameWidth / 2 - awardForAndriod.getWidth() / 2);
        awardForAndriod.setY(200);
        addActor(awardForAndriod);
    }

    private void tableTest() {
        Image starImage = CHGame.getInstance().getImage("screen/big_star.png");
        Image careerImage = CHGame.getInstance().getImage("screen/button_career_up.png");

        Group group = new Group();
        careerImage.setX(100);
        starImage.setX(0);
        group.addActor(careerImage);

        group.addActor(starImage);

        Table mytable = new Table();
        mytable.align(Align.bottom | Align.left);
        mytable.add(group);
        // 第一行添加图片
        //        mytable.add(starImage).left();
        //        mytable.row();
        //        mytable.add(careerImage);

        mytable.setX(20);
        mytable.setY(20);
        mytable.setWidth(400);
        mytable.setHeight(400);
        mytable.debug();

        getStage().addActor(mytable);

    }

    private void scrollPaneTest() {
        Table mytable = new Table();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 5; i++) {
                Image starImage = CHGame.getInstance().getImage("screen/big_star.png");
                mytable.add(starImage);
            }
            mytable.row();
        }

        ScrollPane scrollPane = new ScrollPane(mytable);

        scrollPane.pack();
        scrollPane.setWidth(100);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setX(200);
        scrollPane.setY(240);
        getStage().addActor(scrollPane);
    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

        Gdx.app.log(getTAG(), "end screen : first");
    }

    @Override
    public void draw() {
        // TODO Auto-generated method stub
        super.draw();
        
        getStage().getBatch().begin();
        sprite.draw(getStage().getBatch());
        getStage().getBatch().end();
    }
}
