
package com.ecommerce.manager;

import com.ecommerce.enums.LogLevel;
import com.ecommerce.log.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigManager {

	/** The config. */
	private static Properties config = new Properties();

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public static String getValue(String key) {

		String value = "";
		if (config.getProperty(key) != null) {
			value = config.getProperty(key);
		} else {
			LogUtil.log("The key " + key + " is not found in property file", LogLevel.HIGH);
		}
		return value;
	}

	/**
	 * Gets the all keys.
	 *
	 * @return the all keys
	 */
	public static List<String> getAllKeys() {

		List<String> keys = new ArrayList<String>();
		for (Object key : config.keySet()) {
			keys.add(key.toString());
		}
		return keys;
	}

	/**
	 * Load MyAiEcho config.
	 *
	 * @param propertyFile
	 */
    public static void loadMyAiEchoConfig(String propertyFile) {

		try {
			config.load(ConfigManager.class.getClassLoader().getResourceAsStream(propertyFile));
			LogUtil.log("Loaded PropertyFile:" +propertyFile, LogLevel.HIGH);
		}
		catch (Exception e) {
			LogUtil.log("No config file named " + propertyFile, LogLevel.HIGH);
		}
	}
}