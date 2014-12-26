package com.oahcfly.chgame.test;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.oahcfly.chgame.smartfont.SmartFontGenerator;

public class SmartFontExample  extends ApplicationAdapter {
    private Stage stage;
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        SmartFontGenerator fontGen = new SmartFontGenerator();
        FileHandle exoFile = Gdx.files.local("x.ttf");
        BitmapFont fontSmall = fontGen.createFont(exoFile,"小字体是多少是多少奥特曼", "exo-small", 24);
        BitmapFont fontMedium = fontGen.createFont(exoFile,"中字体的是多少",  "exo-medium", 48);
        BitmapFont fontLarge = fontGen.createFont(exoFile, "大字体大师大师", "exo-large", 64);

        stage = new Stage();

        Label.LabelStyle smallStyle = new Label.LabelStyle();
        smallStyle.font = fontSmall;
        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = fontMedium;
        Label.LabelStyle largeStyle = new Label.LabelStyle();
        largeStyle.font = fontLarge;

        Label small = new Label("Small Font 小字体多少奥特曼", smallStyle);
        Label medium = new Label("Medium Font 中字体多少", mediumStyle);
        Label large = new Label("Large Font 大字体大师", largeStyle);

        Table table = new Table();
        table.setFillParent(true);
        table.align(Align.center);
        stage.addActor(table);

        table.defaults().size(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 6);

        table.add(small).row();
        table.add(medium).row();
        table.add(large).row();
    }

    @Override
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}