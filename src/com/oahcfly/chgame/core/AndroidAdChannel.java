
package com.oahcfly.chgame.core;

import java.util.HashMap;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;

public class AndroidAdChannel {
    public static final String CHANNEL_360 = "360";

    public static final String CHANNEL_BAIDU = "baidu";

    public static final String CHANNEL_TENGXUN_STRING = "tengxun";

    public static final String CHANNEL_MEIZU = "meizu";

    public static final String CHANNEL_ANZHI = "anzhi";

    private HashMap<String, Boolean> channelMap = new HashMap<String, Boolean>();

    
    /**
     *  {"360":false,"baidu":true,"tengxu":false,"meizu":false,"anzhi":false}
     * @param json
     */
    public AndroidAdChannel(String json){
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(json); 
        JsonIterator jsonIterator= jsonValue.iterator();
        while (jsonIterator.hasNext()) {
            JsonValue jv = jsonIterator.next();
            //System.out.println("-"+jv.name+"-"+jv.asBoolean());
            setChannelAD(jv.name, jv.asBoolean());
        }
    }
    
    private void setChannelAD(String channelName, boolean open) {
        channelMap.put(channelName, open);
    }

    public boolean openAD(String channelName) {
        return channelMap.containsKey(channelName) ? channelMap.get(channelName) : false;
    }
}
