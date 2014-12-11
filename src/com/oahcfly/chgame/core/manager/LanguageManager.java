
package com.oahcfly.chgame.core.manager;

import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LanguageManager {
    ObjectMap<String, String> map = new ObjectMap<String, String>();

    public final static class LANGUAGE_CODE {
        public final static String ENGLISH = "en";

        public final static String CHINA = "cn";
    }

    private String lang = "cn";

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void load(String name) {
        try {
            parse(new XmlReader().parse(new InputStreamReader(Gdx.files.internal("lang/" + name + "-" + lang + ".xml")
                    .read(), "UTF8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return map.get(key);
    }

    private void parse(Element ele) {
        map.clear();
        for (int i = 0; i < ele.getChildCount(); i++) {
            Element e = ele.getChild(i);
            String text = e.getText();
            text = text.replace("\\n", "\n").replace("\\t", "\t");
            map.put(e.getAttribute("name"), text);
        }
    }
}
