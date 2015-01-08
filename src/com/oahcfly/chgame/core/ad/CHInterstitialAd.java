
package com.oahcfly.chgame.core.ad;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.oahcfly.chgame.util.CHRandomHelper;

/**
 * 
 * <pre>
 * 插屏广告比例配置
 * {"waps":20,"dyd":60,"changsi":20}
 * date: 2015-1-8
 * </pre>
 * @author caohao
 */
public class CHInterstitialAd {

    /**
     * 
     * <pre>
     * 插屏广告类型
     * 
     * date: 2015-1-8
     * </pre>
     * @author caohao
     */
    public enum InterstitialAdType {
        WAPS("waps"), DYD("dyd"), CHANGSI("changsi");
        private String value;

        private InterstitialAdType(String value) {
            this.value = value;
        }

        public String getTypeValue() {
            return this.value;
        }
    }

    private CHRandomHelper randomHelper;

    private ArrayList<String> typeName = new ArrayList<String>();

    private ArrayList<Integer> probData = new ArrayList<Integer>();

    private ArrayList<InterstitialAdType> adTypes = new ArrayList<InterstitialAdType>();

    /**
     * 
     * @param cfgJson  {"waps":10,"dyd":20,"changsi":70}
     */
    public CHInterstitialAd(String cfgJson) {
        adTypes.add(InterstitialAdType.WAPS);
        adTypes.add(InterstitialAdType.DYD);
        adTypes.add(InterstitialAdType.CHANGSI);

        if (cfgJson != null) {
            try {  
                JsonReader jsonReader = new JsonReader();
                JsonValue jsonValue = jsonReader.parse(cfgJson);
                JsonIterator jsonIterator = jsonValue.iterator();

                while (jsonIterator.hasNext()) {
                    JsonValue jv = jsonIterator.next();
                    System.out.println("-" + jv.name + "-" + jv.asInt());
                    typeName.add(jv.name);
                    probData.add(jv.asInt());
                }
                randomHelper = new CHRandomHelper(probData);
            } catch (Exception e) {
                // do nothing
                System.err.println(getClass().getSimpleName() + ":parse ad json error!!!!" + e.getMessage());
            }

        }
    }

    /**
     * 
     * <pre>
     * 根据配置的比例，生成此次显示的插屏广告类型
     * 
     * date: 2015-1-8
     * </pre>
     * @author caohao
     * @return
     */
    public InterstitialAdType getInterstitalAdType() {
        InterstitialAdType adType;
        try {
            int index = randomHelper.getSingleResultIndex();
            String name = typeName.get(index);
            adType = getAdTypeByName(name);
            if (adType == null) {
                adType = InterstitialAdType.DYD;
            }
        } catch (Exception e) {
            Gdx.app.error("CHInterstitialAd", e.getMessage());
            return InterstitialAdType.DYD;
        }
        return adType;
    }

    private InterstitialAdType getAdTypeByName(String name) {
        for (InterstitialAdType adType : adTypes) {
            if (adType.getTypeValue().equals(name)) {
                return adType;
            }
        }
        return null;
    }
}
