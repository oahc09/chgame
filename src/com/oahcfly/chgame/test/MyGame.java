
package com.oahcfly.chgame.test;

import java.util.Locale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.oahcfly.chgame.core.FreeTypeFontGeneratorExt;
import com.oahcfly.chgame.core.International;
import com.oahcfly.chgame.core.listener.LocaleListener;

public class MyGame extends ApplicationAdapter {
    private FreeTypeFontGeneratorExt Generator;

    International international;

    private Stage stage;

    private LocaleListener listener;

    public MyGame(LocaleListener listener) {
        this.listener = listener;
    }

    @Override
    public void create() {
        // 初始化
        Generator = new FreeTypeFontGeneratorExt(30, false);
        FileHandle baseFileHandle = Gdx.files.internal("values/strings");// 读取国际化资源
        Locale locale = listener.getLocale();
        international = International.createBundle(baseFileHandle, locale);//
 
        Generator.appendToFont(international.getVaulesString());// 创建国际化方案里的当前语言的所有字符纹理
        // 初始化舞台
        stage = new Stage(new StretchViewport(480, 800));
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        // 创建文本
        Label label = Generator.createLabel(international.get("wrong"));
        label.setColor(Color.GREEN);
        label.setPosition(240 - label.getWidth() / 2, 700);
        stage.addActor(label);
        Label label0 = Generator.createLabel(international.get("toolong"));
        label0.setColor(Color.BLUE);
        label0.setPosition(240 - label0.getWidth() / 2, 600);
        stage.addActor(label0);
        Label label00 = Generator.createLabel(international.get("please"));
        label00.setColor(Color.LIGHT_GRAY);
        label00.setPosition(240 - label00.getWidth() / 2, 550);
        stage.addActor(label00);
        Label label1 = Generator.createLabel(international.get("user"));
        label1.setColor(Color.ORANGE);
        label1.setPosition(240 - label1.getWidth() / 2, 500);
        stage.addActor(label1);
        Label label2 = Generator.createLabel(international.get("newgame"));
        label2.setFontScale(0.9f);
        label2.setColor(Color.YELLOW);
        label2.setPosition(240 - label2.getPrefWidth() / 2, 350);
        stage.addActor(label2);
        Label label3 = Generator.createLabel(international.get("continue"));
        label3.setFontScale(0.8f);
        label3.setColor(Color.RED);
        label3.setPosition(240 - label3.getPrefWidth() / 2, 250);
        stage.addActor(label3);
        final Label name = Generator.createLabel(international.get("list"));
        name.setPosition(240 - name.getPrefWidth() / 2, 200);
        stage.addActor(name);
        // 输入框
        name.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                TextInputListener listen = new TextInputListener() {
                    public void input(String text) {
                        Generator.appendToFont(text);
                        name.setText(text);
                    }

                    public void canceled() {

                    }
                };
                Gdx.input.getTextInput(listen, international.get("please"), name.getText().toString());
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
}
