
package com.oahcfly.chgame.test.dots;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;

public class SaveHelper {
    public static void writeToJson(int level, ArrayList<DotCell> cells) {
        //        for (int i = 0; i < 10; i++) {
        //            DotCell dotCell = new DotCell();
        //            dotCell.setCol(i + 1);
        //            dotCell.setRow(i + 2);
        //            dotCell.setDotType(i + 10);
        //            cells.add(dotCell);
        //        }
        Json json = new Json();

        FileWriter fileWritter;
        try {
            fileWritter = new FileWriter(Gdx.files.internal("level_data_" + level).file(), false);
            json.setWriter(fileWritter);
            json.writeObjectStart();
            json.writeValue("data", cells, DotCell.class);
            json.writeObjectEnd();
            fileWritter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Gdx.app.error("SaveHelper", e.getMessage());
        }

    }

    /**
     * 
     * <pre>
     * 读取关卡数据
     * 
     * date: 2015-1-1
     * </pre>
     * @author caohao
     * @param level
     * @return
     */
    public static ArrayList<DotCell> readLevelData(int level) {
        ArrayList<DotCell> cells = new ArrayList<DotCell>();
        Json json = new Json();

        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(Gdx.files.internal("level_data_" + level));
        JsonIterator jsonIterator = jsonValue.child.iterator();
        while (jsonIterator.hasNext()) {
            JsonValue jValue = jsonIterator.next();
            DotCell dotCell = json.fromJson(DotCell.class, jValue.toString());
            cells.add(dotCell);
        }
        return cells;
    }
}
