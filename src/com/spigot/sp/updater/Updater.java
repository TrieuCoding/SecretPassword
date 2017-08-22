package com.spigot.sp.updater;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.spigot.sp.Main;

import net.md_5.bungee.api.ChatColor;

public class Updater {

	public static Main plugin;

	@SuppressWarnings("static-access")
	public Updater(Main plugin) {
		this.plugin = plugin;
	}

	private static final String KEY = "98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4";
	private static final int ID = 45789;

	private static String latest() {
		try {
			URL url = new URL("http://www.spigotmc.org/api/general.php");
			URLConnection urlc = url.openConnection();
			HttpURLConnection hurlc = (HttpURLConnection) urlc;
			hurlc.setDoOutput(true);
			hurlc.setRequestMethod("POST");

			OutputStream os = hurlc.getOutputStream();
			String req = "key=" + KEY + "&resource=" + ID;
			byte[] write = req.getBytes("UTF-8");
			os.write(write);

			InputStream is = hurlc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String version = br.readLine();

			is.close();
			os.close();
			return version;
		} catch (Exception ex) {
			String error = "Failed to check for update:\n" + ex.getCause();
			plugin.getLogger().log(Level.WARNING, error);
		}
		return current();
	}

	private static String current() {
		Main CL = Main.instance;
		PluginDescriptionFile pdf = CL.getDescription();
		String version = pdf.getVersion();
		return version;
	}

	private static void print(String... msg) {
		String[] arrayOfString = msg;
		int j = msg.length;
		for (int i = 0; i < j; i++) {
			String s = arrayOfString[i];
			String c = ChatColor.translateAlternateColorCodes('&', s);
			ConsoleCommandSender ccs = Bukkit.getConsoleSender();
			ccs.sendMessage(c);
		}
	}

	public static String check() {
		String latest = latest();
		String current = current();
		if (latest.equals(current)) {
			return "updated";
		}
		return latest;
	}

	public static void print() {
		String v = check();
		Main CL = Main.instance;
		if (v.equals("updated")) {
			String[] msg = { CL.cslprefix + "No update was found!",
							CL.cslprefix + "Current version: " + current()};

			print(msg);
		} else {
			String[] msg = {CL.cslprefix + "A new update was found: " + v,
					CL.cslprefix + "Current version: " + current(),
					CL.cslprefix + "Download it: ", 
					"https://www.spigotmc.org/resources/1-7-1-12-1-secret-password-security-an-account-has-op.45789/"};

			print(msg);
		}
	}

}
