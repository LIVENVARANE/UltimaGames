package fr.groupeultima.org.Listener;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.groupeultima.Library.IdentityCard.Identity;
import fr.groupeultima.Library.IdentityCard.Enum.Games;
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
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
							+ ChatColor.RED
							+ "Vos récompenses pour avoir cassé des blocks du totem ennemi n'ont pas été ajoutées à votre compte car vous avez quitté la partie.");
				}
				UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName(), null);
				int pre_redPlayers = UltimaGames.getConfig().getInt("Games.Totem.redPlayers");
				int redPlayers = pre_redPlayers - 1;
				UltimaGames.getConfig().set("Games.Totem.redPlayers", redPlayers);
				UltimaGames.saveConfig();
				UltimaGames.reloadConfig();
			} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
				if(UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + p.getName() + ".obsidian") > 0) {
					p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
							+ ChatColor.RED
							+ "Vos récompenses pour avoir cassé des blocks du totem ennemi n'ont pas été ajoutées à votre compte car vous avez quitté la partie.");
				}
				UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName(), null);
				int pre_bluePlayers = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
				int bluePlayers = pre_bluePlayers - 1;
				UltimaGames.getConfig().set("Games.Totem.bluePlayers", bluePlayers);
				UltimaGames.saveConfig();
				UltimaGames.reloadConfig();
			} else { // player was waiting
				int waitingPlayers = Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size();
				if(waitingPlayers < UltimaGames.getConfig().getInt("Games.Totem.minimumPlayersToStart")
						&& UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") == 1) {
					UltimaGames.getConfig().set("Games.Totem.isGameStarting", 0);
					Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()
							.forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] "
									+ ChatColor.RESET + "" + ChatColor.RED
									+ "Lancement de la partie annulé car il n'y a plus assez de joueurs."));
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
		} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
			UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName(), null);
			int pre_bluePlayers = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
			int bluePlayers = pre_bluePlayers - 1;
			UltimaGames.getConfig().set("Games.Totem.bluePlayers", bluePlayers);
			UltimaGames.saveConfig();
			UltimaGames.reloadConfig();
		} else { // player was waiting
			int waitingPlayers = Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size();
			if(waitingPlayers < UltimaGames.getConfig().getInt("Games.Totem.minimumPlayersToStart")
					&& UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") == 1) {
				UltimaGames.getConfig().set("Games.Totem.isGameStarting", 0);
				Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()
						.forEach(Player -> Player.sendMessage(
								ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.RED
										+ "Lancement de la partie annulé car il n'y a plus assez de joueurs."));
			}
		}
	}

	public static ArrayList<Block> TotemBlocks = new ArrayList<Block>();
	
	public static ArrayList<Block> TotemTotemObsidianBlocks = new ArrayList<Block>();

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(e.getBlock().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			TotemBlocks.add(e.getBlock());
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if(e.getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)
				|| e.getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			e.setCancelled(true);
		}
	}

	// death message n death screen avoiding
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		Player d = (Player) e.getDamager(); // killer
		Player p = (Player) e.getEntity(); // victim
		if(p.getGameMode() != GameMode.SPECTATOR) {
			if(e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
				if(p.getHealth() - e.getDamage() < 1) {
					Identity player_info = new Identity();
					if(e.getCause() != DamageCause.VOID) {
						for (Player D1p : Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()) {
							D1p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + e.getEntity().getName() + ChatColor.RESET + "" + ChatColor.YELLOW + " a été tué par " + ChatColor.BOLD + "" + d.getName() + ChatColor.RESET + "" + ChatColor.YELLOW + ".");
						}
						player_info.addKill(1, d.getUniqueId(), Games.Totem);
						if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + d.getName())) {
							int player_kills = UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + d.getName() + ".kills"); 
							player_kills++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Red." + d.getName() + ".kills", player_kills);
							int player_killstreak = UltimaGames.getConfig()
									.getInt("Games.Totem.Teams.Red." + d.getName() + ".killstreak");
							player_killstreak++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Red." + d.getName() + ".killstreak",
									player_killstreak);

							int victim_deaths = UltimaGames.getConfig()
									.getInt("Games.Totem.Teams.Blue." + p.getName() + ".deaths");
							victim_deaths++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".deaths",
									victim_deaths);
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".killstreak", 0);
							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();
						} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + d.getName())) {
							int player_kills = UltimaGames.getConfig()
									.getInt("Games.Totem.Teams.Blue." + d.getName() + ".kills");
							player_kills++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + d.getName() + ".kills",
									player_kills);
							int player_killstreak = UltimaGames.getConfig()
									.getInt("Games.Totem.Teams.Blue." + d.getName() + ".killstreak");
							player_killstreak++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + d.getName() + ".killstreak",
									player_killstreak);

							int victim_deaths = UltimaGames.getConfig()
									.getInt("Games.Totem.Teams.Red." + p.getName() + ".deaths");
							victim_deaths++;
							UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".deaths",
									victim_deaths);
							UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".killstreak", 0);
							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();
						}
						player_info.addCredit(10, d.getUniqueId());
						d.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
								"§cVous avez tué §l" + e.getEntity().getName() + "§r§c. §6+10 Crédits."));
					} else {
						for (Player D2p : Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()) {
							D2p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.YELLOW + "" + ChatColor.BOLD + "" + e.getEntity().getName() + ChatColor.RESET + "" + ChatColor.YELLOW + " a été tué par " + ChatColor.BOLD + "le vide" + ChatColor.RESET + "" + ChatColor.YELLOW + ".");
						}
					}
					p.setHealth(20);
					player_info.addDeath(1, e.getEntity().getUniqueId(), Games.Totem);
					e.setCancelled(true);
					p.getInventory().clear();
					p.setGameMode(GameMode.SPECTATOR);
					Location waitLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap), 0, 0, 0);
					String preWaitLoc[] = UltimaGames.getConfig().getString("Games.Totem.waitSpawnLoc").split(",");
					waitLoc.setX(Double.parseDouble(preWaitLoc[0]));
					waitLoc.setY(Double.parseDouble(preWaitLoc[1]));
					waitLoc.setZ(Double.parseDouble(preWaitLoc[2]));
					p.teleport(waitLoc);
					d.playSound(e.getEntity().getLocation(), Sound.BLOCK_METAL_BREAK, 1, 1);
					p.sendTitle(ChatColor.RED + "Vous êtes mort!", ChatColor.GOLD + "Réapparition dans 3 secondes...");
					final BossBar totem_respawnBBar = Bukkit.createBossBar("§6Réapparition dans §l3 secondes§r§6.",
							BarColor.RED, BarStyle.SOLID);
					totem_respawnBBar.addPlayer(p);
					p.playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5, 1);
					new BukkitRunnable() {
						int i = 0;

						@Override
						public void run() {

							if(i == 0) { // 1s
								totem_respawnBBar.setProgress(0.6);
								totem_respawnBBar.setTitle("§6Réapparition dans §l2 secondes§r§6.");
								p.sendTitle(ChatColor.RED + "Vous êtes mort!",
										ChatColor.GOLD + "Réapparition dans 2 secondes...");
								i++;
								return;
							}

							if(i == 1) { // 2s
								totem_respawnBBar.setProgress(0.3);
								totem_respawnBBar.setTitle("§6Réapparition dans §e§l1 seconde§r§6.");
								p.sendTitle(ChatColor.RED + "Vous êtes mort!",
										ChatColor.YELLOW + "Réapparition dans 1 seconde...");
								totem_respawnBBar.setColor(BarColor.YELLOW);
								i++;
								return;
							}

							if(i == 2) { // 3s
								if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
									Location respawnLoc = new Location(
											Bukkit.getServer().getWorld(UltimaGames.totemMap), 0, 0, 0);
									String preRespawnLoc[] = UltimaGames.getConfig()
											.getString("Games.Totem.redSpawnLoc").split(",");
									respawnLoc.setX(Double.parseDouble(preRespawnLoc[0]));
									respawnLoc.setY(Double.parseDouble(preRespawnLoc[1]));
									respawnLoc.setZ(Double.parseDouble(preRespawnLoc[2]));
									p.teleport(respawnLoc);
								} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
									Location respawnLoc = new Location(
											Bukkit.getServer().getWorld(UltimaGames.totemMap), 0, 0, 0);
									String preRespawnLoc[] = UltimaGames.getConfig()
											.getString("Games.Totem.blueSpawnLoc").split(",");
									respawnLoc.setX(Double.parseDouble(preRespawnLoc[0]));
									respawnLoc.setY(Double.parseDouble(preRespawnLoc[1]));
									respawnLoc.setZ(Double.parseDouble(preRespawnLoc[2]));
									p.teleport(respawnLoc);
								}
								p.setGameMode(GameMode.SURVIVAL);

								p.getInventory().setHeldItemSlot(0);
								p.playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
								totem_respawnBBar.removePlayer((Player) e.getEntity());
								this.cancel();
								return;
							}
						}
					}.runTaskTimer(UltimaGames, 1 * 20, 1 * 20);
				}
			}
		} else {
			return;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onObsidianBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(e.getBlock().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
			if(e.getBlock().getType() == Material.OBSIDIAN) {
				Location redObsiLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap), 0, 0, 0);
				String preRedObsiLoc[] = UltimaGames.getConfig().getString("Games.Totem.redObsiLoc").split(",");
				redObsiLoc.setX(Double.parseDouble(preRedObsiLoc[0]));
				redObsiLoc.setZ(Double.parseDouble(preRedObsiLoc[1]));

				Location blueObsiLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap), 0, 0, 0);
				String preBlueObsiLoc[] = UltimaGames.getConfig().getString("Games.Totem.blueObsiLoc").split(",");
				blueObsiLoc.setX(Double.parseDouble(preBlueObsiLoc[0]));
				blueObsiLoc.setZ(Double.parseDouble(preBlueObsiLoc[1]));

				if(e.getBlock().getLocation().getX() == redObsiLoc.getX()
						&& e.getBlock().getLocation().getZ() == redObsiLoc.getZ()) {
					if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
						e.setDropItems(false);
						TotemTotemObsidianBlocks.add(e.getBlock());
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
								+ ChatColor.YELLOW + "Vous avez cassé un bloc d'obsibienne du totem ennemi, bien jou�!"
								+ ChatColor.GOLD
								+ "\nVous receverez 50 Crédits si vous jouez jusqu'à la fin de la partie!");
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
								"§cVous avez cassé un bloc d'obsidienne §r§c. §4+50 Crédits en fin de partie."));
						int player_obsidian = UltimaGames.getConfig()
								.getInt("Games.Totem.Teams.Blue." + p.getName() + ".obsidian");
						player_obsidian++;
						UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".obsidian",
								player_obsidian);
						int blocks_broken = UltimaGames.getConfig().getInt("Games.Totem.redTotemBlocksBroken");
						blocks_broken++;
						UltimaGames.getConfig().set("Games.Totem.redTotemBlocksBroken", blocks_broken);
						UltimaGames.saveConfig();
						UltimaGames.reloadConfig();

						// check if blocks_broken == totem_blocks_number (is game ended?)
						if(UltimaGames.getConfig().getInt("Games.Totem.totemHeight") == blocks_broken) {
							// blue won

							UltimaGames.getConfig().set("Games.Totem.Teams", null);
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem"),
									"Teams");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Red");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Blue");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Red"), "a");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), "a");
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

							UltimaGames.getConfig().set("Games.Totem.isGameFinished", 1);

							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();
							
							Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> {
								Player.setGameMode(GameMode.SPECTATOR);
								Player.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Les Bleu",
										ChatColor.DARK_AQUA + "ont gagnés!");
								Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] "
										+ ChatColor.DARK_GREEN + "Fin de la partie, les bleus gagnent!");

								new BukkitRunnable() {
									int i = 1;

									@Override
									public void run() {
										if(i == 1) {
											i++;
											return;
										}
										if(i == 2) {
											i++;
											return;
										}
										if(i == 3) {
											i++;
											return;
										}
										if(i == 4) {
											i++;
											return;
										}
										if(i == 5) {
											i++;
											if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
												return;
											}
											Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()
													.forEach(Player -> Player.sendMessage(" \n" + ChatColor.GOLD + ""
															+ ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
															+ ChatColor.YELLOW + "Retour au hub...\n "));

											// give money to each team players

											return;
										}
										if(i == 6) {
											i++;
											Player.performCommand("hub");
											TotemBlocks.forEach(blocks -> blocks.setType(Material.AIR));
											TotemTotemObsidianBlocks.forEach(blocks -> blocks.setType(Material.OBSIDIAN));
											UltimaGames.getConfig().set("Games.Totem.isGameFinished", 0);
											UltimaGames.saveConfig();
											UltimaGames.reloadConfig();
											return;
										}
									}
								}.runTaskTimer(UltimaGames, 0, 20);
							});
						}
					} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
								+ ChatColor.RED + "Vous ne pouvez pas casser votre totem!");
					} else {
						e.setCancelled(true);
					}
				} else if(e.getBlock().getLocation().getX() == blueObsiLoc.getX()
						&& e.getBlock().getLocation().getZ() == blueObsiLoc.getZ()) {
					if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + p.getName())) {
						e.setDropItems(false);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
								+ ChatColor.YELLOW + "Vous avez cassé un bloc d'obsibienne du totem ennemi, bien joué!"
								+ ChatColor.GOLD
								+ "\nVous receverez 50 Crédits si vous jouez jusqu'à la fin de la partie!");
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
								"§cVous avez cassé un bloc d'obsidienne §r§c. §4+50 Crédits en fin de partie."));
						int player_obsidian = UltimaGames.getConfig()
								.getInt("Games.Totem.Teams.Red." + p.getName() + ".obsidian");
						player_obsidian++;
						UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".obsidian",
								player_obsidian);
						int blocks_broken = UltimaGames.getConfig().getInt("Games.Totem.blueTotemBlocksBroken");
						blocks_broken++;
						UltimaGames.getConfig().set("Games.Totem.blueTotemBlocksBroken", blocks_broken);
						UltimaGames.saveConfig();
						UltimaGames.reloadConfig();

						// check if blocks_broken == totem_blocks_number (is game ended?)
						if(UltimaGames.getConfig().getInt("Games.Totem.totemHeight") == blocks_broken) {
							// red won

							UltimaGames.getConfig().set("Games.Totem.Teams", null);
							FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem"),
									"Teams");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Red");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams"), "Blue");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Red"), "a");
							FileConfiguration.createPath(
									UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), "a");
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

							UltimaGames.getConfig().set("Games.Totem.isGameFinished", 1);

							UltimaGames.saveConfig();
							UltimaGames.reloadConfig();

							Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> {
								Player.setGameMode(GameMode.SPECTATOR);
								Player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Les Rouges",
										ChatColor.GOLD + "ont gagnés!");
								Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] "
										+ ChatColor.DARK_GREEN + "Fin de la partie, les rouges gagnent!");

								new BukkitRunnable() {
									int i = 1;

									@Override
									public void run() {
										if(i == 1) {
											i++;
											return;
										}
										if(i == 2) {
											i++;
											return;
										}
										if(i == 3) {
											i++;
											return;
										}
										if(i == 4) {
											i++;
											return;
										}
										if(i == 5) {
											i++;
											if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
												return;
											}
											Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers()
													.forEach(Player -> Player.sendMessage(" \n" + ChatColor.GOLD + ""
															+ ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
															+ ChatColor.YELLOW + "Retour au hub...\n "));

											// give money to each team players

											return;
										}
										if(i == 6) {
											i++;
											Player.performCommand("hub");
											TotemBlocks.forEach(blocks -> blocks.setType(Material.AIR));
											TotemTotemObsidianBlocks.forEach(blocks -> blocks.setType(Material.OBSIDIAN));
											UltimaGames.getConfig().set("Games.Totem.isGameFinished", 0);
											UltimaGames.saveConfig();
											UltimaGames.reloadConfig();
											return;
										}
									}
								}.runTaskTimer(UltimaGames, 0, 20);
							});

						}
					} else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + p.getName())) {
						e.setCancelled(true);
						p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + ""
								+ ChatColor.RED + "Vous ne pouvez pas casser votre totem!");
					} else {
						e.setCancelled(true);
					}
				} else {
					return;
				}
			} else {
				return;
			}
		} else {
			return;
		}
	}
}
