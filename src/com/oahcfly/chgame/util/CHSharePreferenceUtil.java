
package com.oahcfly.chgame.util;

import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.oahcfly.chgame.util.aes.CHAESEncryptor;

/**
 * 存储配置信息的工具类 <br>
 * 注：可读取的数据类型有-<code>boolean、int、float、long、String.</code>
 */
public class CHSharePreferenceUtil {

    private final Preferences sharedpreferences;

    // 是否进行加密
    private boolean useAes;

    public CHSharePreferenceUtil(String fileName) {
        this(fileName, false);
    }

    public CHSharePreferenceUtil(String fileName, boolean aes) {
        useAes = aes;
        if (useAes) {
            fileName = CHAESEncryptor.encodeData(fileName);
        }
        sharedpreferences = Gdx.app.getPreferences(fileName);

    }

    public void saveSharedPreferences(String key, String value) {
        if (useAes) {
            sharedpreferences.putString(CHAESEncryptor.encodeData(key), CHAESEncryptor.encodeData(value));
        } else {
            sharedpreferences.putString(key, value);
        }
        sharedpreferences.flush();
    }

    public String loadStringSharedPreference(String key, String defaultString) {
        String str = null;
        try {
            if (useAes) {
                key = CHAESEncryptor.encodeData(key);
            }
            str = sharedpreferences.getString(key, defaultString);
            if (str != null && !"".equals(str)) {
                if (useAes)
                str = CHAESEncryptor.decodeData(str);
            }
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), e.getMessage());
        }
        return str;
    }

    public void saveSharedPreferences(String key, int value) {
        if (useAes) {
            sharedpreferences.putInteger(CHAESEncryptor.encodeData(key), value);
        } else {
            sharedpreferences.putInteger(key, value);
        }
        sharedpreferences.flush();
    }

    public int loadIntSharedPreference(String key, int defaultValue) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.getInteger(key, defaultValue);
    }

    public void saveSharedPreferences(String key, long value) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        sharedpreferences.putLong(key, value);
        sharedpreferences.flush();
    }

    public long loadLongSharedPreference(String key, long defaultValue) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.getLong(key, defaultValue);
    }

    public void saveSharedPreferences(String key, float value) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        sharedpreferences.putFloat(key, value);
        sharedpreferences.flush();
    }

    public float loadFloatSharedPreference(String key) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.getFloat(key, 0f);
    }

    public void saveSharedPreferences(String key, Long value) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        sharedpreferences.putLong(key, value);
        sharedpreferences.flush();
    }

    public long loadLongSharedPreference(String key) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.getLong(key, 0l);
    }

    public void saveSharedPreferences(String key, Boolean value) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        sharedpreferences.putBoolean(key, value);
        sharedpreferences.flush();
    }

    public boolean loadBooleanSharedPreference(String key, boolean def) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.getBoolean(key, def);
    }

    public void saveAllSharePreference(String keyName, List<?> list) {
        int size = list.size();
        if (size < 1) {
            return;
        }
        if (useAes) {
            keyName = CHAESEncryptor.encodeData(keyName);
        }
        if (list.get(0) instanceof String) {
            for (int i = 0; i < size; i++) {
                sharedpreferences.putString(keyName + i, (String)list.get(i));
            }
        } else if (list.get(0) instanceof Long) {
            for (int i = 0; i < size; i++) {
                sharedpreferences.putLong(keyName + i, (Long)list.get(i));
            }
        } else if (list.get(0) instanceof Float) {
            for (int i = 0; i < size; i++) {
                sharedpreferences.putFloat(keyName + i, (Float)list.get(i));
            }
        } else if (list.get(0) instanceof Integer) {
            for (int i = 0; i < size; i++) {
                sharedpreferences.putLong(keyName + i, (Integer)list.get(i));
            }
        } else if (list.get(0) instanceof Boolean) {
            for (int i = 0; i < size; i++) {
                sharedpreferences.putBoolean(keyName + i, (Boolean)list.get(i));
            }
        }
        sharedpreferences.flush();
    }

    public Map<String, ?> loadAllSharePreference(String key) {
        if (useAes) {
            key = CHAESEncryptor.encodeData(key);
        }
        return sharedpreferences.get();
    }

    public void removeKey(String key) {
        sharedpreferences.remove(key);
        sharedpreferences.flush();
    }

    public void removeAllKey() {
        sharedpreferences.clear();
        sharedpreferences.flush();
    }
}
