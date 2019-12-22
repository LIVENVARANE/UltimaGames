package fr.groupeultima.org;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.groupeultima.org.Commands.PlayCommand;
import fr.groupeultima.org.Commands.UltimaGamesCommand;
import fr.groupeultima.org.Listener.RushFFA;
import fr.groupeultima.org.Listener.Totem;
import fr.groupeultima.org.Tasks.GamesScoreboards;
import fr.groupeultima.org.Tasks.MapCleanRushFFADeluxe;
import fr.groupeultima.org.Tasks.MapCleanRushFFAOriginal;
import fr.groupeultima.org.Tasks.PowerupsRushFFADeluxe;

public class UltimaGames extends JavaPlugin {
	
	public static UltimaGames plugin;
	
	public int Oi = 1; // rushffaoriginalcleaner
	public int Di = 1; // rushffadeluxecleaner
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		getCommand("play").setExecutor(new PlayCommand(this));
		getCommand("ultimagames").setExecutor(new UltimaGamesCommand(this));
		getServer().getPluginManager().registerEvents(new GamesScoreboards(this), this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[UltimaGames] " + ChatColor.GOLD + "Getting game worlds...");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[UltimaGames] " + ChatColor.GOLD + "Loading [Games:RushFFA:Original:mapName=" + getConfig().getString("Games.RushFFA.Original.mapName") + "][Games:RushFFA:Deluxe:mapName=" + getConfig().getString("Games.RushFFA.Deluxe.mapName") + "]");
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[UltimaGames] " + ChatColor.GOLD + "Plugin has started successfully.");
		//rushffa
		getServer().getPluginManager().registerEvents(new RushFFA(this), this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new MapCleanRushFFAOriginal(this), 30 * 20L, 30 * 20L);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[UltimaGames] RushFFA Original map cleaner loaded.");
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new MapCleanRushFFADeluxe(this), 30 * 20L, 30 * 20L);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[UltimaGames] RushFFA Deluxe map cleaner loaded.");
		
		try {
			int elytraSpawn = Integer.parseInt(getConfig().getString("Games.RushFFA.Deluxe.elytraSpawn"));
			switch(elytraSpawn) {
				case -1:
					Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[UltimaGames] Elytra powerup disabled.");
					break;
				case 0:
					Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[UltimaGames] Elytra powerup enabled.");
					try {
						int ElytraCleanRate = Integer.parseInt(getConfig().getString("Games.RushFFA.Deluxe.elytraSpawnDelay"));
						getServer().getScheduler().scheduleSyncRepeatingTask(this, new PowerupsRushFFADeluxe(this), ElytraCleanRate * 20L, ElytraCleanRate * 20L);
						Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[UltimaGames] RushFFA Deluxe elytra powerup loaded.");
					} catch(NumberFormatException e) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[UltimaGames] Error in config.yml (Elytra spawn delay is not a number)");
					}
					break;
				default:
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[UltimaGames] Error in config.yml (Elytra spawn is not -1 or 0)");
					break;
			}
		} catch(NumberFormatException ex) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[UltimaGames] Error in config.yml (Elytra spawn is not -1 or 0)");
		}
		
		//totem
		getServer().getPluginManager().registerEvents(new Totem(this), this);
		
		getConfig().set("Games.Totem.Teams", null);
		FileConfiguration.createPath(getConfig().getConfigurationSection("Games.Totem"), "Teams");
		FileConfiguration.createPath(getConfig().getConfigurationSection("Games.Totem.Teams"), "Red");
		FileConfiguration.createPath(getConfig().getConfigurationSection("Games.Totem.Teams"), "Blue");
		FileConfiguration.createPath(getConfig().getConfigurationSection("Games.Totem.Teams.Red"), "a");
		FileConfiguration.createPath(getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), "a");
		getConfig().set("Games.Totem.Teams.Red.a.obsidian", 0);
		getConfig().set("Games.Totem.Teams.Blue.a.obsidian", 0);
		getConfig().set("Games.Totem.Teams.Red.a.kills", 0);
		getConfig().set("Games.Totem.Teams.Blue.a.kills", 0);
		getConfig().set("Games.Totem.Teams.Red.a.deaths", 0);
		getConfig().set("Games.Totem.Teams.Blue.a.deaths", 0);
		getConfig().set("Games.Totem.Teams.Red.a.killstreak", 0);
		getConfig().set("Games.Totem.Teams.Blue.a.killstreak", 0);
		getConfig().set("Games.Totem.redTotemBlocksBroken", 0);
		getConfig().set("Games.Totem.blueTotemBlocksBroken", 0);
		getConfig().set("Games.Totem.redPlayers", 0);
		getConfig().set("Games.Totem.bluePlayers", 0);
		saveConfig();
		reloadConfig();
	}
	
	//config.yml
	
	@Override
	public void onDisable() {
		
		plugin = null;
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[UltimaGames] " + ChatColor.GOLD + "Disabling UltimaGames");
	}
	
	public String rushffaOriginalMap = getConfig().getString("Games.RushFFA.Original.mapName");
	public String rushffaDeluxeMap = getConfig().getString("Games.RushFFA.Deluxe.mapName");
	public String totemMap = getConfig().getString("Games.Totem.mapName");
	
	
	public boolean loadConfig() {
		if(!new File(getDataFolder() + File.separator + "config.yml").exists()) {
			saveDefaultConfig();
		}
		try {
			new YamlConfiguration().load(new File(getDataFolder() + File.separator + "config.yml"));
		} catch(Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[UltimaGames] " + ChatColor.RED + "Error while loading config.yml");
			Bukkit.getConsoleSender().sendMessage("Please check error dump below.");
			Bukkit.getConsoleSender().sendMessage(" ");
		    e.printStackTrace();
		    Bukkit.getPluginManager().disablePlugin(this);
		      
		    return false;
		}
		reloadConfig();
		return true;
	}
	
	public File getFile() {
		return super.getFile();
	}
}
