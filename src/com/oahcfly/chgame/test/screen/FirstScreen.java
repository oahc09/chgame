
package com.oahcfly.chgame.test.screen;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.core.actions.CountDownAction;
import com.oahcfly.chgame.core.actions.LabelTickerAction;
import com.oahcfly.chgame.core.event.CHEvent;
import com.oahcfly.chgame.core.listener.CHClickListener;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.LabelAtlas;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabel;
import com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget.TTFLabelStyle;
import com.oahcfly.chgame.plist.CHPListCenter;
import com.oahcfly.chgame.test.ui.MainUI;
import com.oahcfly.chgame.test.ui.TestUI;
import com.oahcfly.chgame.util.astar.CHAStar;

public class FirstScreen extends CHScreen {

    @Override
    public void initScreen() {

        Gdx.app.log(getTAG(), "init screen : first" + Texture.getManagedStatus());

        //        tableTest();

        //        testAstar();
        //        plistTest();
        //
        //        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("testAtlas.txt"));
        //        Image autumnImage = new Image(textureAtlas.findRegion("title_summer"));
        //        addActor(autumnImage);
        //
        //        sprite = textureAtlas.createSprite("title_autumn");
        //        sprite.setX(57);

        new MainUI(getStage()).show();
        //
        //        CHAssets chAssets = new CHAssets();
        //        chAssets.loadAssetFile("asset.ch");
        //        chAssets.loadGroup("bigstar");
        //        chAssets.finishLoading();
        //        System.out.println("p" + chAssets.getProgress());
        //        Image image = new Image(chAssets.get("screen/big_star.png", Texture.class));
        //        addActor(image);
        //
        //        image.addListener(new CHEventListener() {
        //
        //            @Override
        //            public void handleEvent(CHEvent chEvent) {
        //                System.out.println("refreshUI:" + chEvent.toString());
        //                chEvent.getListenerActor().moveBy(100, 100);
        //
        //            }
        //        });
        //        httpTest(image);

        TTFLabelStyle labelStyle = new TTFLabelStyle(new LabelStyle(CHGame.getInstance().getInternationalGenerator()
                .getBitmapFont(), null), null, 30);
        TTFLabel label = new TTFLabel("abcdxyz123s撒啊啊啊啊啊啊啊啊测试", labelStyle);
        label.setText("测试文本系统显示啊好大的加拉开始");
        label.setPosition(200, 200);
        addActor(label);

        addLabelAtlas(10, 20, 100);
        addLabelAtlas(100, 30, 127);

        LabelTickerAction ticker = new LabelTickerAction(2f, "人类逆天了啊！哈哈哈");
        label.addAction(ticker);

        // 倒计时
        TextureRegion textureRegion = new TextureRegion(CHGame.getInstance().getTexture("screen/score_numlist.png"));
        TextureRegion[][] textureRegions = textureRegion.split(23, 27);
         CountDownAction countDownAction =CountDownAction.obtain(10f, textureRegions[0]);
         getStage().addAction(countDownAction);
         
    }

    private LabelAtlas addLabelAtlas(int num, int x, int y) {
        final LabelAtlas labelAtlas = new LabelAtlas(new TextureRegion(CHGame.getInstance().getTexture(
                "screen/score_numlist.png")), 23, 27, "0123456789");
        labelAtlas.setText("" + num);
        labelAtlas.setX(x);
        labelAtlas.setY(y);
        labelAtlas.setOrigin(Align.center);
        labelAtlas.addListener(new CHClickListener() {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // TODO Auto-generated method stub
                super.touchUp(event, x, y, pointer, button);
                labelAtlas.setText("1000");
            }

        });
        addActor(labelAtlas);
        return labelAtlas;
    }

    private void createLabel(BitmapFont font, String string, FileHandle fontFileHandle, int h, BitmapFont allBitmapFont) {
        TTFLabelStyle labelStyle = new TTFLabelStyle(new LabelStyle(font, null), fontFileHandle, 20);
        TTFLabel label = new TTFLabel(string, labelStyle);
        label.setText(string);
        label.setPosition(100, h);
        addActor(label);
    }

    private void httpTest(final Image image) {
        HttpRequest request = new HttpRequest(HttpMethods.GET);
        request.setUrl("http://baidu.com");
        Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                Gdx.app.log("HttpRequestExample", "response: " + httpResponse.getResultAsString());
                // 事件处理
                HashMap<String, String> dataHashMap = new HashMap<String, String>();
                dataHashMap.put("cesg", "hahah");
                CHEvent chEvent = new CHEvent("sss", dataHashMap);
                image.fire(chEvent);
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.error("HttpRequestExample", "something went wrong", t);
            }

            @Override
            public void cancelled() {
                Gdx.app.log("HttpRequestExample", "cancelled");
            }
        });
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

        //        getStage().getBatch().begin();
        //        sprite.draw(getStage().getBatch());
        //        getStage().getBatch().end();
    }
}
