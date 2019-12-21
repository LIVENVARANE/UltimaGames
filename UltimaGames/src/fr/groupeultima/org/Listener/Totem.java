package fr.groupeultima.org.Listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.groupeultima.org.UltimaGames;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Totem implements Listener {

	UltimaGames UltimaGames;

	public Totem(UltimaGames instance) {
		UltimaGames = instance;
	}
	
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
				if(UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + p.getName() + ".obsidian") > 0) {
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Vos r�compenses pour avoir cass� des blocks du totem ennemi n'ont pas �t� ajout�es � votre compte car vous avez quitt� la partie.");
				}
				UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName(), null);
				int pre_redPlayers = UltimaGames.getConfig().getInt("Games.Totem.redPlayers");
				int redPlayers = pre_redPlayers - 1;
				UltimaGames.getConfig().set("Games.Totem.redPlayers", redPlayers);
				UltimaGames.saveConfig();
				UltimaGames.reloadConfig();
			}
			else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
				if(UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + p.getName() + ".obsidian") > 0) {
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Vos r�compenses pour avoir cass� des blocks du totem ennemi n'ont pas �t� ajout�es � votre compte car vous avez quitt� la partie.");
				}
				UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName(), null);
				int pre_bluePlayers = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
				int bluePlayers = pre_bluePlayers - 1;
				UltimaGames.getConfig().set("Games.Totem.bluePlayers", bluePlayers);
				UltimaGames.saveConfig();
				UltimaGames.reloadConfig();
			}
			else { //player was waiting
				int waitingPlayers = Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size();
				if(waitingPlayers < UltimaGames.getConfig().getInt("Games.Totem.minimumPlayersToStart") && UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") == 1) {
					UltimaGames.getConfig().set("Games.Totem.isGameStarting", 0);
					Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Lancement de la partie annulé car il n'y a plus assez de joueurs."));
				}
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
			UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName(), null);
			int pre_redPlayers = UltimaGames.getConfig().getInt("Games.Totem.redPlayers");
			int redPlayers = pre_redPlayers - 1;
			UltimaGames.getConfig().set("Games.Totem.redPlayers", redPlayers);
			UltimaGames.saveConfig();
			UltimaGames.reloadConfig();
		}
		else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
			UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName(), null);
			int pre_bluePlayers = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
			int bluePlayers = pre_bluePlayers - 1;
			UltimaGames.getConfig().set("Games.Totem.bluePlayers", bluePlayers);
			UltimaGames.saveConfig();
			UltimaGames.reloadConfig();
		}
		else { //player was waiting
			int waitingPlayers = Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size();
			if(waitingPlayers < UltimaGames.getConfig().getInt("Games.Totem.minimumPlayersToStart") && UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") == 1) {
				UltimaGames.getConfig().set("Games.Totem.isGameStarting", 0);
				Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Lancement de la partie annulé car il n'y a plus assez de joueurs."));
			}
		}
	}
	
	public static ArrayList<Block> TotemBlocks = new ArrayList<Block>();
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			TotemBlocks.add(e.getBlock());
		}
	}
	
	@EventHandler
	public void onObsidianBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(e.getBlock().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			if(e.getBlock().getType() == Material.OBSIDIAN) {
				Location redObsiLoc =  new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap),0,0,0);
				String preRedObsiLoc[] = UltimaGames.getConfig().getString("Games.Totem.redObsiLoc").split(",");
				redObsiLoc.setX(Double.parseDouble(preRedObsiLoc[0]));
				redObsiLoc.setZ(Double.parseDouble(preRedObsiLoc[1]));
				
				Location blueObsiLoc =  new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap),0,0,0);
				String preBlueObsiLoc[] = UltimaGames.getConfig().getString("Games.Totem.blueObsiLoc").split(",");
				blueObsiLoc.setX(Double.parseDouble(preBlueObsiLoc[0]));
				blueObsiLoc.setZ(Double.parseDouble(preBlueObsiLoc[1]));
				
				if(e.getBlock().getLocation().getX() == redObsiLoc.getX() && e.getBlock().getLocation().getZ() == redObsiLoc.getZ()) {
					if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
						e.setDropItems(false);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.YELLOW + "Vous avez cass� un bloc d'obsibienne du totem ennemi, bien jou�!" + ChatColor.GOLD + "\nVous receverez 50 Cr�dits si vous jouez jusqu'� la fin de la partie!");
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�cVous avez cass� un bloc d'obsidienne �r�c. �4+50 Cr�dits en fin de partie."));
						int player_obsidian = UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + p.getName() + ".obsidian");
						player_obsidian++;
						UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".obsidian", player_obsidian);
						int blocks_broken = UltimaGames.getConfig().getInt("Games.Totem.redTotemBlocksBroken");
						blocks_broken++;
						UltimaGames.getConfig().set("Games.Totem.redTotemBlocksBroken", blocks_broken);
						UltimaGames.saveConfig();
						UltimaGames.reloadConfig();
						
						//check if blocks_broken == totem_blocks_number (is game ended?)
						if(UltimaGames.getConfig().getInt("Games.Totem.totemHeight") == blocks_broken) {
							//blue won
							
							TotemBlocks.forEach(blocks -> blocks.setType(Material.AIR));
							
							p.sendMessage("you won");
							
							UltimaGames.getConfig().set("Games.Totem.Teams", null);
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem"), "Teams");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Red");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Blue");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Red"), "a");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), "a");
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.obsidian", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.obsidian", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.kills", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.kills", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.deaths", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.deaths", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.killstreak", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.killstreak", 0);
							UltimaGames.getConfig().set("Games.Totem.redTotemBlocksBroken", 0);
							UltimaGames.getConfig().set("Games.Totem.blueTotemBlocksBroken", 0);
							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();
							
						}
					}
					else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Vous ne pouvez pas casser votre totem!");
					}
					else {
						e.setCancelled(true);
					}
				}
				else if(e.getBlock().getLocation().getX() == blueObsiLoc.getX() && e.getBlock().getLocation().getZ() == blueObsiLoc.getZ()) {
					if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
						e.setDropItems(false);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.YELLOW + "Vous avez cass� un bloc d'obsibienne du totem ennemi, bien jou�!" + ChatColor.GOLD + "\nVous receverez 50 Cr�dits si vous jouez jusqu'� la fin de la partie!");
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�cVous avez cass� un bloc d'obsidienne �r�c. �4+50 Cr�dits en fin de partie."));
						int player_obsidian = UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + p.getName() + ".obsidian");
						player_obsidian++;
						UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".obsidian", player_obsidian);
						int blocks_broken = UltimaGames.getConfig().getInt("Games.Totem.blueTotemBlocksBroken");
						blocks_broken++;
						UltimaGames.getConfig().set("Games.Totem.blueTotemBlocksBroken", blocks_broken);
						UltimaGames.saveConfig();
						UltimaGames.reloadConfig();
						
						//check if blocks_broken == totem_blocks_number (is game ended?)
						if(UltimaGames.getConfig().getInt("Games.Totem.totemHeight") == blocks_broken) {
							//red won
							
							TotemBlocks.forEach(blocks -> blocks.setType(Material.AIR));
							
							p.sendMessage("you won");
							
							UltimaGames.getConfig().set("Games.Totem.Teams", null);
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem"), "Teams");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Red");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Blue");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Red"), "a");
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), "a");
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.obsidian", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.obsidian", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.kills", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.kills", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.deaths", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.deaths", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red.a.killstreak", 0);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue.a.killstreak", 0);
							UltimaGames.getConfig().set("Games.Totem.redTotemBlocksBroken", 0);
							UltimaGames.getConfig().set("Games.Totem.blueTotemBlocksBroken", 0);
							UltimaGames.getConfig().set("Games.Totem.redPlayers", 0);
							UltimaGames.getConfig().set("Games.Totem.bluePlayers", 0);
							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();
							
						}
					}
					else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED + "Vous ne pouvez pas casser votre totem!");
					}
					else {
						e.setCancelled(true);
					}
				}
				else {
					return;
				}
			}
			else {
				return;
			}
		}
		else {
			return;
		}
	}
}
