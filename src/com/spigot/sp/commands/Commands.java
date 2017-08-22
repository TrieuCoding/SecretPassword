package com.spigot.sp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.spigot.sp.Config;
import com.spigot.sp.Main;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	public static Main plugin;

	@SuppressWarnings("static-access")
	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	private String cslprefix = "[SecretPassword] ";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (sender.hasPermission("scp.cmd")) {
			if (args.length < 1) {
				if (sender instanceof Player) {
					sender.sendMessage("/secretpassword getip [player]");
					sender.sendMessage("/secretpassword reload");
					sender.sendMessage("Aliases: /scp");
				} else {
					sender.sendMessage(cslprefix + ChatColor.RED + "/secretpassword getip [player]");
					sender.sendMessage(cslprefix + ChatColor.RED + "/secretpassword reload");
					sender.sendMessage(cslprefix + ChatColor.RED + "Aliases: /scp");
				}
				return true;
			} else if (args.length > 2) {
				if (sender instanceof Player) {
					sender.sendMessage(ChatColor.RED + "Unknow args! Please use /secretpassword for help!");
				} else {
					sender.sendMessage(cslprefix + ChatColor.RED + "Unknow args! Please use /secretpassword for help!");
				}
				return true;
			} else if (args.length == 2 && args[0].equalsIgnoreCase("getip")) {
				Player p = Bukkit.getServer().getPlayer(args[1]);

				if (p == null || (!(p.isOnline())) || args[1].equals("[player]")) {
					if (sender instanceof Player) {
						sender.sendMessage(
								ChatColor.RED + "Cannot found " + ChatColor.WHITE + args[1] + ChatColor.RED + "!");
					} else {
						sender.sendMessage(cslprefix + ChatColor.RED + "Cannot found " + ChatColor.WHITE + args[1]
								+ ChatColor.RED + "!");
					}
					return true;
				}

				if (sender instanceof Player) {
					sender.sendMessage(Config.getConfig().getString("prefix").replace("&", "§") + Config.getConfig()
							.getString("msg.getip").replace("&", "§").replace("%player%", String.valueOf(args[1]))
							.replace("%ipaddress%", p.getAddress().getAddress().getHostAddress().toString().trim()));
				} else {
					sender.sendMessage(cslprefix + Config.getConfig().getString("msg.getip").replace("&", "§")
							.replace("%player%", String.valueOf(args[1]))
							.replace("%ipaddress%", p.getAddress().getAddress().getHostAddress().toString().trim()));
				}

			} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				if (sender instanceof Player) {
					Config.reloadConfig();
					sender.sendMessage(Config.getConfig().getString("prefix").replace("&", "§")
							+ Config.getConfig().getString("msg.reload").replace("&", "§"));
				} else {
					Config.reloadConfig();
					sender.sendMessage(cslprefix + ChatColor.GREEN + "Reload config!");
				}
			} else {
				if (sender instanceof Player) {
					sender.sendMessage(ChatColor.RED + "Unknow args! Please use /secretpassword for help!");
				} else {
					sender.sendMessage(cslprefix + ChatColor.RED + "Unknow args! Please use /secretpassword for help!");
				}
				return true;
			}
		} else {
			sender.sendMessage(Config.getConfig().getString("prefix").replace("&", "§")
					+ Config.getConfig().getString("msg.no-permissions").replace("&", "§"));
			return true;
		}
		return true;
	}

}
