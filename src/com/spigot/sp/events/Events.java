package com.spigot.sp.events;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.spigot.sp.Config;
import com.spigot.sp.Main;
import com.spigot.sp.data.Data;

public class Events implements Listener {

	public static Main plugin;

	@SuppressWarnings("static-access")
	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		if (!(p.isOp())) {
			return;
		} else {
			p.setGameMode(GameMode.ADVENTURE);
			plugin.list.add(p.getUniqueId());
			Data.savePlayerData(p);
			new BukkitRunnable() {
				@Override
				public void run() {
					p.sendMessage(Config.getConfig().getString("msg.join").replace("&", "§"));
				}
			}.runTaskLater(plugin, 40L);
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255));
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		if (!(p.isOp())) {
			return;
		} else {
			if (plugin.list.contains(p.getUniqueId())) {
				if (msg.equals(Config.getConfig().getString("password"))) {
					e.setCancelled(true);
					plugin.list.remove(p.getUniqueId());
					p.sendMessage(Config.getConfig().getString("msg.login").replace("&", "§"));
					for (PotionEffect effect : p.getActivePotionEffects()) {
						p.removePotionEffect(effect.getType());
					}
					p.playSound(p.getLocation(), Sound.valueOf(Config.getConfig().getString("login-sound")), 4F, 1F);
					Config.getDataFile().set("playerdata." + p.getName() + ".isLogin", Boolean.valueOf(true));
					Config.saveDataFile();
				} else {
					e.setCancelled(true);
					p.sendMessage(Config.getConfig().getString("msg.wrong").replace("&", "§"));
				}
			}
		}
	}

	@EventHandler
	public void onUseCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		if (!(p.isOp())) {
			return;
		} else {
			if (plugin.list.contains(p.getUniqueId())) {
				e.setCancelled(true);
				p.sendMessage(Config.getConfig().getString("msg.cant-do").replace("&", "§"));
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		if (!(p.isOp())) {
			return;
		} else {
			if (plugin.list.contains(p.getUniqueId())) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (!(p.isOp())) {
			return;
		} else {
			if (plugin.list.contains(p.getUniqueId())) {
				e.setCancelled(true);
			} else {
				e.setCancelled(false);
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!Config.getConfig().getBoolean("password-enable")) {
			return;
		}
		Player p = e.getPlayer();
		if (!(p.isOp())) {
			return;
		} else {
			if (plugin.list.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

}
