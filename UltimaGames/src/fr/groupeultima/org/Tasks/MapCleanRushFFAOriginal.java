package fr.groupeultima.org.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.groupeultima.org.UltimaGames;
import fr.groupeultima.org.Listener.RushFFA;

public class MapCleanRushFFAOriginal implements Runnable {

	UltimaGames UltimaGames;

	public MapCleanRushFFAOriginal(UltimaGames instance) {
		UltimaGames = instance;
	}
	
	@Override
	public void run() {
		if(UltimaGames.Oi == 1) { //30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 2) { //1m
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 3) { //1m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 4) { //2m
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 5) { //2m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 6) { //3m
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 7) { //3m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 8) { //4m
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 9) { //4m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 10) { //5m
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "5 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 11) { //5m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 12) { //6m
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "4 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 13) { //6m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 14) { //7m
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "3 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 15) { //7m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 16) { //8m
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "2 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 17) { //8m30s
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 18) { //9m
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "1 minute" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 19) { //9m30s
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Nettoyage de la map dans " + ChatColor.BOLD + "30 secondes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi++;
			return;
		}
		if(UltimaGames.Oi == 20) { //10m
			RushFFA.Oblocks.forEach(blocks -> blocks.setType(Material.AIR));
			Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers().forEach(player -> player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + "" + ChatColor.WHITE + "" + ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "La map a été néttoyée! Prochain nettoyage dans " + ChatColor.BOLD + "10 minutes" + ChatColor.RESET + "" + ChatColor.DARK_GREEN + "."));
			UltimaGames.Oi = 1;
			return;
		}
	}
}
