package com.spigot.sp.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.spigot.sp.Config;
import com.spigot.sp.Main;

public class Data {

	public static Main plugin;
	
	@SuppressWarnings("static-access")
	public Data(Main plugin) { this.plugin = plugin; }
	
	public static void savePlayerData(Player p) {
		
		Location loc = p.getLocation();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		World w = loc.getWorld();
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		String time = "playerdata." + p.getName() + ".time";
		Config.getDataFile().set(time, format.format(date));
		String uuid = "playerdata." + p.getName() + ".uuid";
		Config.getDataFile().set(uuid, p.getUniqueId().toString());
		String ipaddress = "playerdata." + p.getName() + ".ip-address";
		Config.getDataFile().set(ipaddress, p.getAddress().getAddress().getHostAddress().toString().trim());
		String xConfig = "playerdata." + p.getName() + ".x";
		Config.getDataFile().set(xConfig, x);
		String yConfig = "playerdata." + p.getName() + ".y";
		Config.getDataFile().set(yConfig, y);
		String zConfig = "playerdata." + p.getName() + ".z";
		Config.getDataFile().set(zConfig, z);
		String wConfig = "playerdata." + p.getName() + ".world";
		Config.getDataFile().set(wConfig, w.getName());
		String isLogin = "playerdata." + p.getName() + ".isLogin";
		Config.getDataFile().set(isLogin, Boolean.valueOf(false));
		
		Config.saveDataFile();
		
	}
	
}
