package com.spigot.sp;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private static FileConfiguration cf = null;
	private static FileConfiguration data = null;
	
	private static File customCf = null;
	private static File customData = null;
	
	public static void reloadConfig() {
		if (customCf == null && customData == null) {
			customCf = new File(Main.getPlugin().getDataFolder(), "config.yml");
			customData = new File(Main.getPlugin().getDataFolder(), "playerdata.yml");
		}
		cf = YamlConfiguration.loadConfiguration(customCf);
		data = YamlConfiguration.loadConfiguration(customData);
		try {
			Reader defaultConfig = new InputStreamReader(Main.getPlugin().getResource("config.yml"), "UTF8");
			Reader defaultData = new InputStreamReader(Main.getPlugin().getResource("playerdata.yml"), "UTF8");
			if (defaultConfig != null && defaultData != null) {
		        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defaultConfig);
		        YamlConfiguration defData = YamlConfiguration.loadConfiguration(defaultData);
		        cf.setDefaults(defConfig);
		        data.setDefaults(defData);
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static FileConfiguration getConfig() {
		if (cf == null) {
			reloadConfig();
		}
		return cf;
	}
	
	public static FileConfiguration getDataFile() {
		if (data == null) {
			reloadConfig();
		}
		return data;
	}
	
	public static void saveConfig() {
		if (cf == null || customCf == null) {
			return;
		}
		try {
			getConfig().save(customCf);
		} catch (IOException e) {
			System.out.println("[SecretPassword] Could not save config to " + customCf);
		}
	}
	
	public static void saveDataFile() {
		if (data == null || customData == null) {
			return;
		}
		try {
			getDataFile().save(customData);
		} catch (IOException e) {
			System.out.println("[SecretPassword] Could not save player data to " + customData);
		}
	}
	
	public static void saveDefaultConfig() {
		if (customCf == null) {
			customCf = new File(Main.getPlugin().getDataFolder(), "config.yml");
		}
		if (!customCf.exists()) {
			Main.getPlugin().saveResource("config.yml", false);
		}
	}
	
	public static void saveDefaultDataFile() {
		if (customData == null) {
			customData = new File(Main.getPlugin().getDataFolder(), "playerdata.yml");
		}
		if (!customData.exists()) {
			Main.getPlugin().saveResource("playerdata.yml", false);
		}
	}
	
}
