package com.spigot.sp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.sp.commands.Commands;
import com.spigot.sp.data.Data;
import com.spigot.sp.events.Events;
import com.spigot.sp.updater.Updater;

public class Main extends JavaPlugin {

	private static Plugin plugin;

	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static Main instance;

	public String cslprefix = "[SecretPassword] ";

	public List<UUID> list = new ArrayList<>();

	@SuppressWarnings("unused")
	private Boolean isoutdated;

	String i = getServer().getClass().getPackage().getName();
	String version = i.substring(i.indexOf(".") + 1);
	
	@Override
	public void onEnable() {
		ConsoleCommandSender console = getServer().getConsoleSender();
		plugin = this;
		instance = this;
		Commands.plugin = this;
		Events.plugin = this;
		Data.plugin = this;
		Updater.plugin = this;
		Config.reloadConfig();
		Config.getConfig().options().copyDefaults(true);
		Config.getDataFile().options().copyDefaults(true);
		Config.saveDataFile();
		registerListeners();
		registerCommands();
		console.sendMessage(cslprefix + "Plugin has been enabled!");
		if (Config.getConfig().getBoolean("check-update")) {
			Updater.print();
		}
		try {
			if ((!version.contains("1_8_R1")) || (!version.contains("1_7_R1"))) {
				Config.getConfig().set("login-sound", "ENTITY_EXPERIENCE_ORB_PICKUP");
				Config.saveConfig();
				console.sendMessage(cslprefix + "Using version for 1.9+");
			} else {
				Config.getConfig().set("login-sound", "ORB_PICKUP");
				Config.saveConfig();
				console.sendMessage(cslprefix + "Using version for 1.8");
			}
		} catch (Exception e) {
			console.sendMessage("Failed to check server version! " + e.getCause());
		}
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new Events(this), this);
	}

	private void registerCommands() {
		getCommand("secretpassword").setExecutor(new Commands(this));
	}

}
