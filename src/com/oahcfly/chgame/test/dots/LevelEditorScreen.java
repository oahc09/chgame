
package com.oahcfly.chgame.test.dots;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.oahcfly.chgame.core.listener.CHClickListener;
import com.oahcfly.chgame.core.mvc.CHGame;
import com.oahcfly.chgame.core.mvc.CHScreen;

public class LevelEditorScreen extends CHScreen implements GestureListener {
    DotsMap dotsMap;

    ArrayList<DotCell> dotCells;

    int level = 1;

    Label curLavelLabel;

    @Override
    public void initScreen() {

        // 8行6列
        dotsMap = new DotsMap(8, 6);
        // 保存按钮，下一关按钮
        addLabels();
        // 待拖动的图片
        addAiXin();

        curLavelLabel = CHGame.getInstance().getInternationalGenerator().createLabel("Level ");
        curLavelLabel.setPosition(320, 1000);
        addActor(curLavelLabel);

        // 监听器
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new GestureDetector(this), getStage());
        Gdx.input.setInputProcessor(inputMultiplexer);

        dotCells = new ArrayList<DotCell>();
    }

    private Image addAiXin(String index, float px, float py) {
        String path = String.format("ui/x%s.png", index);
        Image image = CHGame.getInstance().getImage(path);
        image.setName("float_" + index);
        image.setPosition(px, py);
        DotCell dotCell = new DotCell();
        dotCell.setDotType(Integer.valueOf(index));
        image.setUserObject(dotCell);
        addActor(image);
        return image;
    }

    private void addAiXin() {
        // 12个，分2排显示
        for (int i = 1; i <= 6; i++) {
            String path = String.format("ui/x%d.png", i);
            Image image = CHGame.getInstance().getImage(path);
            image.setName("aixin_" + i);
            image.setPosition(image.getWidth() * (i - 1) + 60, 0);
            addActor(image);
        }

        for (int i = 7; i <= 12; i++) {
            String path = String.format("ui/x%d.png", i);
            Image image = CHGame.getInstance().getImage(path);
            image.setName("aixin_" + i);
            image.setPosition(image.getWidth() * (i - 7) + 60, 100);
            addActor(image);
        }
    }

    public void addLabels() {

        String saveString = CHGame.getInstance().getInternational().get("level_editor_save");
        Label saveLabel = CHGame.getInstance().getInternationalGenerator().createLabel(saveString);
        saveLabel.setName("save");
        saveLabel.setColor(Color.WHITE);
        saveLabel.setPosition(60, 240);
        addActor(saveLabel);

        String nextString = CHGame.getInstance().getInternational().get("level_editor_next_level");
        Label nextLabel = CHGame.getInstance().getInternationalGenerator().createLabel(nextString);
        nextLabel.setName("next");
        nextLabel.setColor(Color.WHITE);
        nextLabel.setPosition(250, 240);
        addActor(nextLabel);

        // 设置关卡
        String changeLevelString = CHGame.getInstance().getInternational().get("level_editor_change_level");
        Label changeLevelLabel = CHGame.getInstance().getInternationalGenerator().createLabel(changeLevelString);
        changeLevelLabel.setName("changelevel");
        changeLevelLabel.setColor(Color.WHITE);
        changeLevelLabel.setPosition(440, 240);
        addActor(changeLevelLabel);
        // 
        CHClickListener chClickListener = new CHClickListener(CHClickListener.CLICKTYPE.FADE) {

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // TODO Auto-generated method stub
                super.touchUp(event, x, y, pointer, button);
                String actorName = event.getListenerActor().getName();
                if (actorName.equals("save")) {
                    // 保存
                    System.out.println("关卡 [" + level + "] 的数据已保存!");
                    SaveHelper.writeToJson(level, dotCells);
                    level++;
                    dotCells.clear();
                } else if (actorName.equals("next")) {
                    // 下一关 移除屏幕中已有的图片
                    Array<Actor> removeArray = new Array<Actor>();
                    Iterator<Actor> iterator = getStage().getActors().iterator();
                    while (iterator.hasNext()) {
                        Actor actor = iterator.next();
                        if (actor != null && actor.getName() != null && actor.getName().contains("float_")) {
                            removeArray.add(actor);
                        }
                    }
                    for (Actor actor : removeArray) {
                        actor.remove();
                    }
                } else if (actorName.equals("changelevel")) {
                    // 修改关卡

                    TextInputListener listen = new TextInputListener() {
                        public void input(String text) {
                            CHGame.getInstance().getInternationalGenerator().appendToFont(text);
                            level = Integer.valueOf(text);
                        }

                        public void canceled() {

                            System.out.println("canceled");
                        }
                    };
                    Gdx.input.getTextInput(listen, "请输入关卡：", "1", "");

                }
                Gdx.app.debug(getTAG(), "--" + actorName);
            }

        };

        saveLabel.addListener(chClickListener);
        changeLevelLabel.addListener(chClickListener);
        nextLabel.addListener(chClickListener);
    }

    @Override
    public void clickBackKey() {
        // TODO Auto-generated method stub

    }

    @Override
    public void endScreen() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        dotsMap.render(getStage().getCamera());
        super.render(delta);
        curLavelLabel.setText("Level " + level);
    }

    Image floatedImage;

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));
        Actor actor = getStage().hit(vector2.x, vector2.y, true);
        if (actor != null && actor.getName() != null && actor.getName().contains("aixin_") && floatedImage == null) {
            floatedImage = addAiXin(actor.getName().split("_")[1], vector2.x, vector2.y);
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));

        if (floatedImage != null) {
            floatedImage.setPosition(vector2.x, vector2.y, Align.center);
        }
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Vector2 vector2 = getStage().screenToStageCoordinates(new Vector2(x, y));
        if (floatedImage != null) {
            floatedImage.setPosition(vector2.x, vector2.y, Align.center);
            // 修正坐标
            DotCell dotCell = (DotCell)floatedImage.getUserObject();
            Vector2 vector3 = dotsMap.getNearestCellPosition(floatedImage.getX(Align.center),
                    floatedImage.getY(Align.center), dotCell);
            floatedImage.setPosition(vector3.x, vector3.y);

            // 记录数据
            dotCells.add(dotCell);
        }
        floatedImage = null;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // TODO Auto-generated method stub
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
    public boolean zoom(float initialDistance, float distance) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }

}
