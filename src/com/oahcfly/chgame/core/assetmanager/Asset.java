
package com.oahcfly.chgame.core.assetmanager;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * 
 * <pre>
 * 资源加载属性
 * 
 * date: 2014-12-3
 * </pre>
 * @author caohao
 * @param <T>
 * @param <T>
 */
public class Asset implements Json.Serializable {
    // 类型
    public Class<?> type;

    // 路径
    public String path;

    // 参数
    @SuppressWarnings("rawtypes")
    public AssetLoaderParameters parameters;

    @Override
    public void write(Json json) {
        json.writeValue("assetType", type.getName());
        json.writeValue("path", path);
        json.writeValue("parameters", parameters);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        try {
            type = Class.forName(jsonData.get("type").asString());
        } catch (Exception e) {
            type = null;
        }

        path = jsonData.get("path").asString();

        JsonValue parametersValue = jsonData.get("parameters");
        parameters = parametersValue != null ? json.fromJson(AssetLoaderParameters.class, parametersValue.toString())
                : null;
    }

}
