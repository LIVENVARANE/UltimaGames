package fr.groupeultima.org.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.groupeultima.org.UltimaGames;
import fr.groupeultima.org.Listener.RushFFA;


public class MapCleanRushFFADeluxe implements Runnable {

	UltimaGames UltimaGames;

	public MapCleanRushFFADeluxe(UltimaGames instance) {
		UltimaGames = instance;
	}
	
	@Override
	public void run() {
		if(UltimaGames.Di == 1) { //30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 2) { //1m
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 3) { //1m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 4) { //2m
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 5) { //2m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 6) { //3m
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 7) { //3m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 8) { //4m
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 9) { //4m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 10) { //5m
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "5 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 11) { //5m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 12) { //6m
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "4 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 13) { //6m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 14) { //7m
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "3 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 15) { //7m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 16) { //8m
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "2 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 17) { //8m30s
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 18) { //9m
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "1 minute" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 19) { //9m30s
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "30 secondes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di++;
			return;
		}
		if(UltimaGames.Di == 20) { //10m
			RushFFA.Dblocks.forEach(blocks -> blocks.setType(Material.AIR));
			Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "La map a été néttoyée! Prochain nettoyage dans " + ChatColor.BOLD + "10 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Di = 1;
			return;
		}
	}
}
