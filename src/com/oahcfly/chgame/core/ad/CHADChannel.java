
package com.oahcfly.chgame.core.ad;

import java.util.HashMap;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;

/**
 * 
 * <pre>
 * 广告根据渠道配置读取开关
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 */
public class CHADChannel {
    public static final String CHANNEL_GP = "googleplay";

    public static final String CHANNEL_360 = "qh360";

    public static final String CHANNEL_BAIDU = "baidu";

    public static final String CHANNEL_TENGXUN_STRING = "tengxu";

    public static final String CHANNEL_MEIZU = "meizu";

    public static final String CHANNEL_ANZHI = "anzhi";

    public static final String CHANNEL_MUMAYI = "mumayi";

    private HashMap<String, Boolean> channelMap = new HashMap<String, Boolean>();

    /**
     *  {"360":false,"baidu":true,"tengxu":false,"meizu":false,"anzhi":false,"googleplay":true}
     * @param json
     */
    public CHADChannel(String json) {
        if (json != null) {
            try {
                JsonReader jsonReader = new JsonReader();
                JsonValue jsonValue = jsonReader.parse(json);
                JsonIterator jsonIterator = jsonValue.iterator();
                while (jsonIterator.hasNext()) {
                    JsonValue jv = jsonIterator.next();
                    //System.out.println("-"+jv.name+"-"+jv.asBoolean());
                    setChannelAD(jv.name, jv.asBoolean());
                }
            } catch (Exception e) {
                // do nothing
                System.err.println(getClass().getSimpleName() + ":parse ad json error!!!!" + e.getMessage());
            }

        }
    }

    private void setChannelAD(String channelName, boolean open) {
        channelMap.put(channelName, open);
    }

    /**
     * 
     * <pre>
     * 是否开启广告
     * 
     * date: 2014-12-3
     * </pre>
     * @author caohao
     * @param channelName
     * @return true开启 false关闭
     */
    public boolean openAD(String channelName) {
        return channelMap.containsKey(channelName) ? channelMap.get(channelName) : false;
    }
}
