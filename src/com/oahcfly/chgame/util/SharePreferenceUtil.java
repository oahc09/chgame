package com.oahcfly.chgame.util;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


/**
 * 存储配置信息的工具类 <br>
 * 注：可读取的数据类型有-<code>boolean、int、float、long、String.</code>
 */
public class SharePreferenceUtil {
	
	private final Preferences sharedpreferences;
	

	public SharePreferenceUtil(String fileName) {
		sharedpreferences = Gdx.app.getPreferences(fileName);
	}

	public void saveSharedPreferences(String key, String value) {
		sharedpreferences.putString(key, value);
//		try {
//			sharedpreferences.putString(key, AESEncryptor.encrypt(MAK, value));
//		} catch (Exception e) {
//			sharedpreferences.putString(key, value);
//			e.printStackTrace();
//		}
		sharedpreferences.flush();
	}

	public String loadStringSharedPreference(String key, String defaultString) {
		String str = null;
		try {
			str = sharedpreferences.getString(key, defaultString);
			if (str != null && !"".equals(str)){
				//str = AESEncryptor.decrypt(MAK, str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public void saveSharedPreferences(String key, int value) {
		sharedpreferences.putInteger(key, value);
		sharedpreferences.flush();
	}

	public int loadIntSharedPreference(String key, int defaultValue) {
		return sharedpreferences.getInteger(key, defaultValue);
	}
	
	public void saveSharedPreferences(String key, long value) {
		sharedpreferences.putLong(key, value);
		sharedpreferences.flush();
	}

	public long loadIntSharedPreference(String key, long defaultValue) {
		return sharedpreferences.getLong(key, defaultValue);
	}

	public void saveSharedPreferences(String key, float value) {
		sharedpreferences.putFloat(key, value);
		sharedpreferences.flush();
	}

	public float loadFloatSharedPreference(String key) {
		return sharedpreferences.getFloat(key, 0f);
	}

	public void saveSharedPreferences(String key, Long value) {
		sharedpreferences.putLong(key, value);
		sharedpreferences.flush();
	}

	public long loadLongSharedPreference(String key) {
		return sharedpreferences.getLong(key, 0l);
	}

	public void saveSharedPreferences(String key, Boolean value) {
		sharedpreferences.putBoolean(key, value);
		sharedpreferences.flush();
	}

	public boolean loadBooleanSharedPreference(String key, boolean def) {
		return sharedpreferences.getBoolean(key, def);
	}

	public void saveAllSharePreference(String keyName, List<?> list) {
		int size = list.size();
		if (size < 1) {
			//return false;
			
		}
		if (list.get(0) instanceof String) {
			for (int i = 0; i < size; i++) {
				sharedpreferences.putString(keyName + i, (String) list.get(i));
			}
		} else if (list.get(0) instanceof Long) {
			for (int i = 0; i < size; i++) {
				sharedpreferences.putLong(keyName + i, (Long) list.get(i));
			}
		} else if (list.get(0) instanceof Float) {
			for (int i = 0; i < size; i++) {
				sharedpreferences.putFloat(keyName + i, (Float) list.get(i));
			}
		} else if (list.get(0) instanceof Integer) {
			for (int i = 0; i < size; i++) {
				sharedpreferences.putLong(keyName + i, (Integer) list.get(i));
			}
		} else if (list.get(0) instanceof Boolean) {
			for (int i = 0; i < size; i++) {
				sharedpreferences.putBoolean(keyName + i, (Boolean) list.get(i));
			}
		}
		sharedpreferences.flush();
	}

	public Map<String, ?> loadAllSharePreference(String key) {
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
