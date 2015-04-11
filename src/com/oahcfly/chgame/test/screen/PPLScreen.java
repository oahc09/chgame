
package com.oahcfly.chgame.test.screen;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.FreeTypeFontGeneratorExt.FreeTypeFontParameter;
import com.oahcfly.chgame.core.actions.CHActions;
import com.oahcfly.chgame.core.actions.CHCountDownAction;
import com.oahcfly.chgame.core.actions.CHLabelTickerAction;
import com.oahcfly.chgame.core.actions.CHScheduleAction;
import com.oahcfly.chgame.core.helper.CHAutoParticle;
import com.oahcfly.chgame.core.mvc.CHActor;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;
import com.oahcfly.chgame.core.ui.CHAniamtionPlayer;
import com.oahcfly.chgame.core.ui.CHGifDecoder;
import com.oahcfly.chgame.core.ui.CHParticle;
import com.oahcfly.chgame.core.ui.CHParticle.ParticleType;
import com.oahcfly.chgame.core.ui.CHParticleEffectActor;
import com.oahcfly.chgame.test.TestGame;

public class PPLScreen extends CHScreen implements GestureListener {

    CHParticle chParticle;

    public void fun() {

        System.out.println("fun调用:" + (System.currentTimeMillis() / 1000));
    }

    CHAutoParticle chAutoParticle;

    @Override
    public void initScreen() {

        chAutoParticle = new CHAutoParticle("xinxin/x1.png");
        //System.out.println("fun调用开始:" + (System.currentTimeMillis() / 1000));
        // addSyncSchedule("fun", 1f);

        chParticle = new CHParticle(ParticleType.STAR);

        addActor(CHGame.getInstance().getImage("first_bg.png"));
        //        for (int i = 0; i < 5; i++) {
        //            Image image = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", i + 1));
        //            image.setName("xin_" + (i + 1));
        //            image.setPosition(300 + image.getWidth() * i, 100);
        //            image.addListener(new CHClickListener() {
        //
        //                @Override
        //                public void clicked(InputEvent event, float x, float y) {
        //                    // TODO Auto-generated method stub
        //                    System.out.println("clicked");
        //                }
        //
        //            });
        //            addActor(image);
        //        }

        // testSplitPane();

        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.shadowColor = Color.BLUE;
        parameter.shadowOffsetX = -2;
        parameter.shadowOffsetY = -2;
        final Label saveLabel = CHGame.getInstance().getInternationalGenerator()
                .createLabel("测试\r\n文字描边ABCDabcd1234!！");
        saveLabel.setColor(Color.WHITE);
        saveLabel.setPosition(300, 100);
        addActor(saveLabel);

        //        final CHTextInputListener chTextInputListener = new CHTextInputListener(saveLabel) {
        //
        //            @Override
        //            public void getInput(String input) {
        //
        //            }
        //        };
        //        saveLabel.addListener(new CHClickListener() {
        //
        //            @Override
        //            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        //                // TODO Auto-generated method stub
        //                super.touchUp(event, x, y, pointer, button);
        //
        //                chTextInputListener.show("测试");
        //            }
        //
        //        });

        //        testGif();

        // addActor(CHGame.getInstance().getImage("add3.png"));

        // 监听器
        InputMultiplexer inputMultiplexer = new InputMultiplexer(getStage(), new GestureDetector(this));
        Gdx.input.setInputProcessor(inputMultiplexer);

        createStars();

        testLabelTickerAction();
    }

    private void testLabelTickerAction() {
        CHLabelTickerAction chLabelTickerAction = new CHLabelTickerAction(2f, "你好啊。欢迎进入CHGAME游戏框架！！！");
        chLabelTickerAction.setCallbackRunnable(new Runnable() {

            @Override
            public void run() {
                System.out.println("显示结束");
            }
        });
        Label label = CHGame.getInstance().getInternationalGenerator().createLabel("你好啊。欢迎进入CHGAME游戏框架！！！");
        label.setColor(Color.RED);
        label.setPosition(100, 100);
        label.addAction(chLabelTickerAction);
        addActor(label);
    }

    private void testScheduleAction() {
        CHScheduleAction scheduleAction = new CHScheduleAction("test", 1f, 5, Actions.run(new Runnable() {

            @Override
            public void run() {
                System.out.println("testScheduleAction-运行时间（秒）：" + (System.currentTimeMillis() / 1000));
            }
        }));
        scheduleAction.setCallbackRunnable(new Runnable() {

            @Override
            public void run() {
                System.out.println("testScheduleAction-运行结束");
            }
        });
        getStage().addAction(scheduleAction);
        System.out.println("testScheduleAction-开始时间（秒）：" + (System.currentTimeMillis() / 1000));
    }

    private void createStars() {

        Array<StarActor> actorArray = new Array<StarActor>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int id = j + i * 6;
                System.out.println("id=" + id);
                StarActor starActor = CHActor.obtain(StarActor.class);
                starActor.setBgTexture(CHGame.getInstance().getTexture("xinxin/x2.png"));
                starActor.setPosition(j * starActor.getWidth(), i * starActor.getHeight());
                getStage().addActor(starActor);
                actorArray.add(starActor);
            }
        }

        SequenceAction sequenceAction = new SequenceAction();
        for (int i = 1, size = actorArray.size; i < size; i++) {
            StarActor starActor = actorArray.get(i);

            //Actions.moveTo(starActor.getX(), starActor.getY(), 0.2f)
            float amountX = starActor.getX() - 200;
            float amountY = starActor.getY() - 300;
            float duration = 0.1f;
            AddAction addAction = Actions.addAction(Actions.moveBy(amountX, amountY, duration), actorArray.get(i));

            sequenceAction.addAction(Actions.delay(0.1f * i));
            sequenceAction.addAction(addAction);
            starActor.setPosition(200, 300);
        }
        actorArray.get(0).addAction(sequenceAction);

        // 1达到目标，2开始运动，
    }

    private void snowParticleEffect() {
        // 粒子
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.classpath("com/oahcfly/chgame/particle/snow.p"),
                Gdx.files.classpath("com/oahcfly/chgame/particle/"));
        CHParticleEffectActor actor = new CHParticleEffectActor(particleEffect, "snow");
        actor.setPosition(100, 400);
        addActor(actor);
    }

    private void testGif() {
        Animation anim = CHGifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("test.gif").read());

        CHAniamtionPlayer chAniamtionPlayer = new CHAniamtionPlayer(300, 300, anim);
        chAniamtionPlayer.play();
        addActor(chAniamtionPlayer);

    }

    private void testCDAction() {
        Image image = CHGame.getInstance().getImage("screen/big_star.png");
        image.setPosition(200, 200);
        addActor(image);
        // 倒计时
        //        TextureRegion textureRegion = new TextureRegion(CHGame.getInstance().getTexture("screen/score_numlist.png"));
        //        TextureRegion[][] textureRegions = textureRegion.split(23, 27);

        Array<Texture> teArray = new Array<Texture>();
        teArray.add(CHGame.getInstance().getTexture("ready.png"));
        teArray.add(CHGame.getInstance().getTexture("go.png"));
        CHCountDownAction countDownAction = CHCountDownAction.obtain(2f, teArray);
        countDownAction.setCallBackRunnable(new Runnable() {

            @Override
            public void run() {
                System.out.println("倒计时结束:" + System.currentTimeMillis());
            }
        });
        image.addAction(countDownAction);
        System.out.println("倒计时开始:" + System.currentTimeMillis());
    }

    private void testParabolaAction() {
        Image image1 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 1));
        image1.setDebug(true);
        image1.addAction(CHActions.createLeftAndRightAction(10, 0.2f, -1));//CHActions.createAutoTwinkleAction(1f, -1));
        //CHActions.createAutoScaleToCenter(1.2f, 0.5f, 4));
        //CHActions.createRotateAndMoveAction(1.8f, 2,  200, -200));
        image1.setOrigin(Align.center);
        image1.setPosition(94, 253);

        addActor(image1);
    }

    private void testSplitPane() {
        Image image1 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 1));
        Image image2 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 2));
        SplitPane splitPane = new SplitPane(image1, image2, true, new SplitPaneStyle(new TextureRegionDrawable(
                new TextureRegion(CHGame.getInstance().getTexture("screen/en_panel_piece.png")))));
        splitPane.pack();

        addActor(splitPane);
    }

    private void testStack() {
        // 没什么特别的
        Stack stack = new Stack();
        for (int i = 0; i < 5; i++) {
            Image image = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", i + 1));
            image.setName("xin_" + (i + 1));
            stack.add(image);
        }
        stack.pack();
        addActor(stack);
    }

    private void testNewActions() {
        // 图片6移动到400,400,闪动3下，接着图片7移动到600,200
        Image image6 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 6));
        Image image7 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 7));
        Action action71 = Actions.moveTo(600, 200, 2f);

        Action action61 = Actions.moveTo(400, 400, 2f);
        Action action62 = Actions.repeat(3, Actions.sequence(Actions.alpha(0.2f, 0.3f), Actions.alpha(1f, 0.3f)));
        SequenceAction sequenceAction = new SequenceAction(action61, action62, Actions.addAction(action71, image7));
        image6.addAction(sequenceAction);

        addActor(image6);
        addActor(image7);
    }

    private void testOldActions() {
        // 图片6移动到400,400,闪动3下，接着图片7移动到600,200
        Image image6 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 6));
        final Image image7 = CHGame.getInstance().getImage(String.format("xinxin/x%d.png", 7));
        final Action action71 = Actions.moveTo(600, 200, 2f);

        Action action61 = Actions.moveTo(400, 400, 2f);
        Action action62 = Actions.repeat(3, Actions.sequence(Actions.alpha(0.2f, 0.3f), Actions.alpha(1f, 0.3f)));
        SequenceAction sequenceAction = new SequenceAction(action61, action62, Actions.run(new Runnable() {

            @Override
            public void run() {
                image7.addAction(action71);
            }
        }));
        image6.addAction(sequenceAction);

        addActor(image6);
        addActor(image7);
    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        super.render(delta);
        chParticle.render(getStage().getBatch());

    }

    HashSet<String> actorNameList;

    Label testLabel;

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("touchDown");
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));
        System.out.println("touchDown : x=" + vector2.x + ",y=" + vector2.y);
        actorNameList = new HashSet<String>();

        chAutoParticle.playCircleParticle(getStage(), vector2.x, vector2.y);
        chParticle.createEffect(vector2.x, vector2.y);
        unSyncSchedule("fun");

        addSyncSchedule("fun", 2);
        // 创建字体
        if (testLabel == null) {
            testLabel = CHGame.getInstance().getInternationalGenerator().createLabel("测试" + TestGame.getRandomHan());
            testLabel.setColor(Color.WHITE);
            testLabel.setPosition(300, 100);
            testLabel.setFontScale(0.4f);
            addActor(testLabel);
        } else {
            String newString = TestGame.getRandomHan() + TestGame.getRandomHan() + TestGame.getRandomHan();
            CHGame.getInstance().getInternationalGenerator().appendToFont("测试" + newString);
            testLabel.setText("测试" + newString);
        }

        if (mainUI == null) {
            mainUI = new MainUI(this);
        }

        if (mainUI.isShowing()) {
            mainUI.dismiss();
        } else {
            mainUI.show();
            mainUI.getGroup().setTouchable(Touchable.disabled);
        }
        return false;
    }

    MainUI mainUI;

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));
        //System.out.println("pan : x=" + vector2.x + ",y=" + vector2.y);
        Actor actor = getStage().hit(vector2.x, vector2.y, true);
        if (actor != null) {
            System.out.println("actor:" + actor.getName());
            actorNameList.add(actor.getName());
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));
        System.out.println("panStop : actorNameList=" + actorNameList.toString());

        chAutoParticle.playFireWorks(getStage(), new Rectangle(0, 0, 400, 400), 3, new Runnable() {

            @Override
            public void run() {
                System.out.println("烟花结束");
            }
        });
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        System.out.println("pinch");
        return false;
    }

}
