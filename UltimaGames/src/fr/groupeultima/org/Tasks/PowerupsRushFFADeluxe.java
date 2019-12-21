package fr.groupeultima.org.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.groupeultima.org.UltimaGames;
import io.netty.util.internal.ThreadLocalRandom;

public class PowerupsRushFFADeluxe implements Runnable {

	UltimaGames UltimaGames;

	public PowerupsRushFFADeluxe(UltimaGames instance) {
		UltimaGames = instance;
	}

	@Override
	public void run() {
		int spawnNumber = ThreadLocalRandom.current().nextInt(1, 4);
		String spawnLocNumber = UltimaGames.getConfig().getString("Games.RushFFA.Deluxe.elytraLoc" + spawnNumber);
		String preSpawnLoc[] = spawnLocNumber.split(",");
		Location spawnLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap), 0, 0, 0);
		spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
		spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
		spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
		Bukkit.getWorld(UltimaGames.rushffaDeluxeMap).dropItem(spawnLoc, new ItemStack(Material.ELYTRA));
		for (Player p : Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers()) {
			p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + ""
					+ ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + "" + ChatColor.DARK_AQUA
					+ "" + ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.DARK_GREEN + " Une "
					+ ChatColor.BOLD + "élytra" + ChatColor.RESET + "" + ChatColor.DARK_AQUA
					+ " est apparue quelque part dans le faiseau central!");
		}
	}

}
