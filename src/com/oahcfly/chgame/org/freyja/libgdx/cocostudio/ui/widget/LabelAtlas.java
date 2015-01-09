
package com.oahcfly.chgame.org.freyja.libgdx.cocostudio.ui.widget;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 数字标签控件,暂时不支持.首字符设置.也就是说数字图片必须是0-9
 *      setTransform(true);缩放才生效
 * @author i see
 * 
 */
public class LabelAtlas extends Table {
    char[] chars;

    TextureRegion[] trs;

    int tileWidth;

    int tileHeight;

    /**
     * 
     * @param tr
     *            数字材质
     * @param tileWidth
     *            数字宽度
     * @param tileHeight
     *            数字高度
     * @param startCharMap
     *            起始字符,例如:0123456789*+-x * 这里必须填写数字的全部字符,并且顺序一致.否则会显示错乱
     * @param stringValue
     *            显示内容,例如:89/50
     */
    public LabelAtlas(TextureRegion tr, int tileWidth, int tileHeight, String startCharMap, String stringValue) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        TextureRegion[][] arr = tr.split(tileWidth, tileHeight);
        trs = arr[0];

        if (startCharMap == null) {
            startCharMap = "0";// 默认值
        }
        this.chars = startCharMap.toCharArray();
        setText(stringValue);

        // 设置为true，缩放才生效
        // setTransform(true);

    }

    public LabelAtlas(TextureRegion tr, int tileWidth, int tileHeight, String startCharMap) {
        this(tr, tileWidth, tileHeight, startCharMap, null);
    }

    String text;

    public String getText() {
        return text;
    }

    /**
     * 设置显示文本
     * 
     * @param text
     *            例如:895+
     */
    public void setText(String text) {
        this.text = text;

        clearChildren();

        if (text == null) {
            return;
        }
        char[] arr = text.toCharArray();
        for (char c : arr) {
            int index = index(c, chars);
            //Image img = null;
            TextureRegion tr = null;
            if (index != -1) {
                tr = trs[index];
            }

            NumberActor numberActor = NumberActor.obtain(NumberActor.class);
            if (tr == null) {
                //img = new Image();
                //img.setSize(tileWidth, tileHeight);// 没有的字符显示空格
                numberActor.setSize(tileWidth, tileHeight);// 没有的字符显示空格
            } else {
                //                img = new Image(tr);
                numberActor.setRegion(tr);
            }
            //            img.setColor(getColor());
            //                        add(img);
            numberActor.setColor(getColor());
            add(numberActor);
        }

        float oldX = getX(Align.center);

        setSize(tileWidth * arr.length, tileHeight);

        // 修改坐标
        setX(oldX - getWidth() / 2);
    }

    int index(char c, char[] chars) {
        int index = 0;
        for (char cc : chars) {
            if (cc == c) {
                return index;
            }
            index++;
        }
        return -1;

    }

}
