
package com.oahcfly.chgame.core.assetmanager;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;
import com.badlogic.gdx.utils.ObjectMap;
import com.oahcfly.chgame.core.mvc.CHGame;

/**
 * 
 * <pre>
 * 【资源管理】
 * 
 * 格式：
 * bigstar代表group
 * {
 * "bigstar" : [
 *    {   
 *        "type" : "com.badlogic.gdx.graphics.Texture",
 *        "path" : "screen/big_star.png"
 *     }
 * ] 
 *  
 * }
 * 
 * 代码范例：
 *        CHAssets chAssets = new CHAssets();
 *        chAssets.loadAssetFile("asset.ch");
 *        chAssets.loadGroup("bigstar");
 *        chAssets.finishLoading();
 *        System.out.println("p" + chAssets.getProgress());
 *        Image image = new Image(chAssets.get("screen/big_star.png", Texture.class));
 *        addActor(image);
 * date: 2014-12-20
 * </pre>
 * @author caohao
 */
public class CHAssets implements Disposable, AssetErrorListener {

    private static final String TAG = "Assets";

    private AssetManager manager;

    private ObjectMap<String, Array<Asset>> groups;

    public CHAssets() {
        manager = CHGame.getInstance().getAssetManager();
        manager.setErrorListener(this);
        //loadToGroups(assetFile);
    }

    public AssetManager getAssetManager() {
        return manager;
    }

    public AssetLoader<?, ?> getLoader(Class<?> type) {
        return manager.getLoader(type);
    }

    /**
     * 
     * <pre>
     * 加载所有资源
     * 
     * date: 2014-12-20
     * </pre>
     * @author caohao
     */
    public void loadAllGroup() {
        Iterator<String> iterator = groups.keys().iterator();
        while (iterator.hasNext()) {
            String keyString = iterator.next();
            loadGroup(keyString);
        }
    }

    /**
     * 
     * <pre>
     * 加载group组内的资源
     * 
     * date: 2014-12-20
     * </pre>
     * @author caohao
     * @param groupName
     */
    @SuppressWarnings("unchecked")
    private void loadGroup(String groupName) {
        // Gdx.app.log(TAG, "loading group " + groupName);

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                //Gdx.app.log(TAG, "loading..." + asset.path);
                manager.load(asset.path, asset.type, asset.parameters);
            }
        } else {
            Gdx.app.log(TAG, "error loading group " + groupName + ", not found");
        }
    }

    /**
     * 
     * <pre>
     * 释放资源
     * 
     * date: 2014-12-20
     * </pre>
     * @author caohao
     * @param groupName
     */
    public void unloadGroup(String groupName) {
        Gdx.app.log(TAG, "unloading group " + groupName);

        Array<Asset> assets = groups.get(groupName, null);

        if (assets != null) {
            for (Asset asset : assets) {
                if (manager.isLoaded(asset.path, asset.type)) {
                    manager.unload(asset.path);
                }
            }
        } else {
            Gdx.app.log(TAG, "error unloading group " + groupName + ", not found");
        }
    }

    public synchronized <T> T get(String fileName) {
        return manager.get(fileName);
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    public <T> boolean isLoaded(String fileName, Class<T> type) {
        return manager.isLoaded(fileName, type);
    }

    public boolean update() {
        return manager.update();
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "shutting down");
    }

    @Override
    public void error(@SuppressWarnings("rawtypes")
    AssetDescriptor asset, Throwable throwable) {
        Gdx.app.log(TAG, "error loading " + asset.fileName + " message: " + throwable.getMessage());
    }

    /**
     * 
     * <pre>
     * assetFile 资源文件路径asset.ch
     * [后续调用loadAllGroup方法]
     * date: 2014-12-20
     * </pre>
     * @author caohao
     * @param assetFile
     */
    public void loadAssetFile(String assetFile) {
        groups = new ObjectMap<String, Array<Asset>>();

        Gdx.app.log(TAG, "loading file " + assetFile);
        long stattime = System.currentTimeMillis();

        try {
            Json json = new Json();
            JsonReader reader = new JsonReader();
            JsonValue root = reader.parse(Gdx.files.internal(assetFile));

            JsonIterator groupIt = root.iterator();

            Gdx.app.debug(TAG, "asset time 1 : " + (System.currentTimeMillis() - stattime));
            stattime = System.currentTimeMillis();

            while (groupIt.hasNext()) {
                JsonValue groupValue = groupIt.next();

                if (groups.containsKey(groupValue.name)) {
                    Gdx.app.log(TAG, "group " + groupValue.name + " already exists, skipping");
                    continue;
                }

                // Gdx.app.log(TAG, "registering group " + groupValue.name);

                Array<Asset> assets = new Array<Asset>();

                JsonIterator assetIt = groupValue.iterator();

                while (assetIt.hasNext()) {
                    JsonValue assetValue = assetIt.next();
                    Asset asset = json.fromJson(Asset.class, assetValue.toString());
                    assets.add(asset);
                }

                groups.put(groupValue.name, assets);
            }

            Gdx.app.debug(TAG, "asset time 2: " + (System.currentTimeMillis() - stattime));
        } catch (Exception e) {
            Gdx.app.log(TAG, "error loading file " + assetFile + " " + e.getMessage());
        }

    }

}
