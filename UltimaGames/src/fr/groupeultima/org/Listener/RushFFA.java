package fr.groupeultima.org.Listener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.groupeultima.library.identitycard.Identity;
import fr.groupeultima.library.identitycard.Enum.Games;
import fr.groupeultima.org.UltimaGames;
import io.netty.util.internal.ThreadLocalRandom;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class RushFFA implements Listener {

	UltimaGames UltimaGames;

	public RushFFA(UltimaGames instance) {
		UltimaGames = instance;
	}

	// elytra powerup only in deluxe rushffa
	@EventHandler
	public void onItemPickup(EntityPickupItemEvent e) {
		if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if (e.getItem().getItemStack().equals(new ItemStack(Material.ELYTRA))) {
				if (e.getEntity() instanceof Player) {
					e.setCancelled(true);
					e.getItem().remove();
					ItemStack elytra = new ItemStack(Material.ELYTRA);
					ItemMeta elytraM = elytra.getItemMeta();
					elytraM.setDisplayName(ChatColor.RED + "Powerup" + ChatColor.GRAY + " → " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Élytra");
					elytra.setItemMeta(elytraM);
					Bukkit.getPlayer(e.getEntity().getName()).getInventory().addItem(elytra);
				}
			}
		} else {
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		if(item == null) {
			return;
		}
		else if(item.getType() == Material.ELYTRA) {
			ItemStack actualChest = e.getPlayer().getInventory().getChestplate();
			e.getItem().setAmount(0);
			e.getPlayer().getInventory().addItem(actualChest);
			ItemStack elytra = new ItemStack(Material.ELYTRA);
			ItemMeta elytraM = elytra.getItemMeta();
			elytraM.setDisplayName(ChatColor.RED + "Powerup" + ChatColor.GRAY + " → " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Élytra");
			elytra.setDurability((short) 35);
			elytra.setItemMeta(elytraM);
			Bukkit.getPlayer(e.getPlayer().getName()).getInventory().setChestplate(elytra);
		}
		else if(item.getType() == Material.LEATHER_CHESTPLATE) {
			ItemStack actualChest = e.getPlayer().getInventory().getChestplate();
			Bukkit.getPlayer(e.getPlayer().getName()).getInventory().setChestplate(e.getItem());
			e.getItem().setAmount(0);
			e.getPlayer().getInventory().addItem(actualChest);
		}
		else {
			return;
		}
	}

	// kill when in void
	@SuppressWarnings("deprecation")
	@EventHandler
	public void voidkill(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Identity player_info = new Identity();
		if (p.getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
			if (p.getLocation().getY() < 0) {
				if (p.getGameMode() != GameMode.SPECTATOR) {
					String spawnLocNumber = UltimaGames.getConfig().getString("Games.RushFFA.Original.specLoc");
					String preSpawnLoc[] = spawnLocNumber.split(",");
					Location specSpawnLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap), 0, 0, 0);
					specSpawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
					specSpawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
					specSpawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
					player_info.addDeath(1, p.getUniqueId(), Games.RushOriginal);
					p.teleport(specSpawnLoc);
					p.setHealth(20);
					p.getInventory().clear();
					p.setGameMode(GameMode.SPECTATOR);
					
					for (Player D2p : Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers()) {
						D2p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET
								+ "" + ChatColor.WHITE + "" + "Original" + ChatColor.RESET + ""
								+ ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + ""
								+ ChatColor.AQUA + "" + ChatColor.BOLD + "" + p.getName()
								+ ChatColor.RESET + "" + ChatColor.AQUA + " a été tué par le " + ChatColor.BOLD
								+ "vide" + ChatColor.RESET + "" + ChatColor.AQUA + ".");
					}
					p.sendTitle(ChatColor.RED + "Vous êtes mort!",
							ChatColor.GOLD + "Réapparition dans 3 secondes...");
					final BossBar respawnBBar = Bukkit.createBossBar("§6Réapparition dans §l3 secondes§r§6.",
							BarColor.RED, BarStyle.SOLID);
					respawnBBar.addPlayer(p);
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5, 1);
					new BukkitRunnable() {
						int i = 0;

						@Override
						public void run() {

							if (i == 0) { // 1s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									respawnBBar.setProgress(0.6);
									respawnBBar.setTitle("§6Réapparition dans §l2 secondes§r§6.");
									p.sendTitle(ChatColor.RED + "Vous êtes mort!",
											ChatColor.GOLD + "Réapparition dans 2 secondes...");
									i++;
									return;
								}
							}

							if (i == 1) { // 2s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									respawnBBar.setProgress(0.3);
									respawnBBar.setTitle("§6Réapparition dans §e§l1 seconde§r§6.");
									p.sendTitle(ChatColor.RED + "Vous êtes mort!",
											ChatColor.YELLOW + "Réapparition dans 1 seconde...");
									respawnBBar.setColor(BarColor.YELLOW);
									i++;
									return;
								}
							}

							if (i == 2) { // 3s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									int spawnNumber = ThreadLocalRandom.current().nextInt(1, 38);
									String spawnLocNumber = UltimaGames.getConfig()
											.getString("Games.RushFFA.Original.spawnLoc" + spawnNumber);
									String preSpawnLoc[] = spawnLocNumber.split(",");
									Location spawnLoc = new Location(
											Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap), 0, 0, 0);
									spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
									spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
									spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
									int jokeNumber = ThreadLocalRandom.current().nextInt(1, 11);
	
									p.teleport(spawnLoc);
									p.setGameMode(GameMode.SURVIVAL);
									ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
									ItemMeta helmM = helm.getItemMeta();
									helmM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									helmM.setDisplayName(ChatColor.DARK_AQUA + "Casque");
									helmM.setUnbreakable(true);
									helm.setItemMeta(helmM);
	
									ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
									ItemMeta chestM = chest.getItemMeta();
									chestM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
									chestM.setDisplayName(ChatColor.DARK_AQUA + "Plastron");
									chestM.setUnbreakable(true);
									chest.setItemMeta(chestM);
	
									ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
									ItemMeta pantsM = pants.getItemMeta();
									pantsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									pantsM.setDisplayName(ChatColor.DARK_AQUA + "Jambières");
									pantsM.setUnbreakable(true);
									pants.setItemMeta(pantsM);
	
									ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
									ItemMeta bootsM = boots.getItemMeta();
									bootsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									bootsM.setDisplayName(ChatColor.DARK_AQUA + "Chaussures");
									bootsM.setUnbreakable(true);
									boots.setItemMeta(bootsM);
	
									ItemStack sword = new ItemStack(Material.IRON_SWORD);
									ItemMeta swordM = sword.getItemMeta();
									swordM.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
									swordM.addEnchant(Enchantment.KNOCKBACK, 1, true);
									swordM.setDisplayName(ChatColor.DARK_AQUA + "Épée");
									swordM.setUnbreakable(true);
									sword.setItemMeta(swordM);
	
									ItemStack pick = new ItemStack(Material.IRON_PICKAXE);
									ItemMeta pickM = pick.getItemMeta();
									pickM.addEnchant(Enchantment.DIG_SPEED, 3, true);
									pickM.setDisplayName(ChatColor.DARK_AQUA + "Pioche");
									pickM.setUnbreakable(true);
									pick.setItemMeta(pickM);
	
									ItemStack tnt = new ItemStack(Material.TNT, 64);
									ItemMeta tntM = tnt.getItemMeta();
									tntM.setDisplayName(ChatColor.RED + "TNT");
									tnt.setItemMeta(tntM);
	
									ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 64);
									ItemMeta gappleM = gapple.getItemMeta();
									gappleM.setDisplayName(ChatColor.YELLOW + "Gapple");
									gapple.setItemMeta(gappleM);
	
									ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
									ItemMeta fireM = fire.getItemMeta();
									fireM.setDisplayName(ChatColor.DARK_AQUA + "Briquet");
									fireM.setUnbreakable(true);
									fire.setItemMeta(fireM);
	
									ItemStack sandstone = new ItemStack(Material.CUT_SANDSTONE, 64);
									ItemMeta sandstoneM = sandstone.getItemMeta();
									sandstoneM.setDisplayName(ChatColor.DARK_AQUA + "Sandstone");
									sandstone.setItemMeta(sandstoneM);
	
									p.addPotionEffect(
											new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1000));
	
									p.getInventory().setItemInOffHand(new ItemStack(sandstone));
									p.getInventory().setItem(0, sword);
									p.getInventory().setItem(1, pick);
									p.getInventory().setItem(2, gapple);
									p.getInventory().setItem(3, tnt);
									p.getInventory().setItem(4, fire);
									for (int i = 5; i < 8; i++) {
										p.getInventory().setItem(i, sandstone);
									}
									p.getInventory().setHelmet(helm);
									p.getInventory().setChestplate(chest);
									p.getInventory().setLeggings(pants);
									p.getInventory().setBoots(boots);
									p.getInventory().setHeldItemSlot(0);
									p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,
											10, 1);
									String selectedJoke = UltimaGames.getConfig()
											.getString("Games.RushFFA.Original.joke" + jokeNumber);
									p.sendTitle(ChatColor.AQUA + "RushFFA" + ChatColor.WHITE + ""
											+ " Original", ChatColor.DARK_AQUA + selectedJoke);
									respawnBBar.removePlayer((Player) p);
									this.cancel();
									return;
								}
							}
						}
					}.runTaskTimer(UltimaGames, 1 * 20, 1 * 20);
				}
			}
		}
		else if(p.getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if(p.getLocation().getY() < 0) {
				if (p.getGameMode() != GameMode.SPECTATOR) {
					String spawnLocNumber = UltimaGames.getConfig().getString("Games.RushFFA.Deluxe.specLoc");
					String preSpawnLoc[] = spawnLocNumber.split(",");
					Location specSpawnLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap), 0, 0, 0);
					specSpawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
					specSpawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
					specSpawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
					player_info.addDeath(1, p.getUniqueId(), Games.RushffaDeluxe);
					p.teleport(specSpawnLoc);
					p.setHealth(20);
					p.getInventory().clear();
					p.setGameMode(GameMode.SPECTATOR);
					
					for (Player D2p : Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers()) {
						D2p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET
								+ "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + ""
								+ ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + ""
								+ ChatColor.AQUA + "" + ChatColor.BOLD + "" + p.getName()
								+ ChatColor.RESET + "" + ChatColor.AQUA + " a été tué par le " + ChatColor.BOLD
								+ "vide" + ChatColor.RESET + "" + ChatColor.AQUA + ".");
					}
					p.sendTitle(ChatColor.RED + "Vous êtes mort!",
							ChatColor.GOLD + "Réapparition dans 3 secondes...");
					final BossBar respawnBBar = Bukkit.createBossBar("§6Réapparition dans §l3 secondes§r§6.",
							BarColor.RED, BarStyle.SOLID);
					respawnBBar.addPlayer(p);
					p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5, 1);
					new BukkitRunnable() {
						int i = 0;

						@Override
						public void run() {

							if (i == 0) { // 1s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									respawnBBar.setProgress(0.6);
									respawnBBar.setTitle("§6Réapparition dans §l2 secondes§r§6.");
									p.sendTitle(ChatColor.RED + "Vous êtes mort!",
											ChatColor.GOLD + "Réapparition dans 2 secondes...");
									i++;
									return;
								}
							}

							if (i == 1) { // 2s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									respawnBBar.setProgress(0.3);
									respawnBBar.setTitle("§6Réapparition dans §e§l1 seconde§r§6.");
									p.sendTitle(ChatColor.RED + "Vous êtes mort!",
											ChatColor.YELLOW + "Réapparition dans 1 seconde...");
									respawnBBar.setColor(BarColor.YELLOW);
									i++;
									return;
								}
							}

							if (i == 2) { // 3s
								if(p.getWorld() != Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
									respawnBBar.removePlayer(p);
									return;
								}
								else {
									int spawnNumber = ThreadLocalRandom.current().nextInt(1, 38);
									String spawnLocNumber = UltimaGames.getConfig()
											.getString("Games.RushFFA.Deluxe.spawnLoc" + spawnNumber);
									String preSpawnLoc[] = spawnLocNumber.split(",");
									Location spawnLoc = new Location(
											Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap), 0, 0, 0);
									spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
									spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
									spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
									int jokeNumber = ThreadLocalRandom.current().nextInt(1, 11);
	
									p.teleport(spawnLoc);
									p.setGameMode(GameMode.SURVIVAL);
									ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
									ItemMeta helmM = helm.getItemMeta();
									helmM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									helmM.setDisplayName(ChatColor.DARK_AQUA + "Casque");
									helmM.setUnbreakable(true);
									helm.setItemMeta(helmM);
	
									ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
									ItemMeta chestM = chest.getItemMeta();
									chestM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
									chestM.setDisplayName(ChatColor.DARK_AQUA + "Plastron");
									chestM.setUnbreakable(true);
									chest.setItemMeta(chestM);
	
									ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
									ItemMeta pantsM = pants.getItemMeta();
									pantsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									pantsM.setDisplayName(ChatColor.DARK_AQUA + "Jambières");
									pantsM.setUnbreakable(true);
									pants.setItemMeta(pantsM);
	
									ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
									ItemMeta bootsM = boots.getItemMeta();
									bootsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
									bootsM.setDisplayName(ChatColor.DARK_AQUA + "Chaussures");
									bootsM.setUnbreakable(true);
									boots.setItemMeta(bootsM);
	
									ItemStack sword = new ItemStack(Material.IRON_SWORD);
									ItemMeta swordM = sword.getItemMeta();
									swordM.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
									swordM.addEnchant(Enchantment.KNOCKBACK, 1, true);
									swordM.setDisplayName(ChatColor.DARK_AQUA + "Épée");
									swordM.setUnbreakable(true);
									sword.setItemMeta(swordM);
	
									ItemStack pick = new ItemStack(Material.IRON_PICKAXE);
									ItemMeta pickM = pick.getItemMeta();
									pickM.addEnchant(Enchantment.DIG_SPEED, 3, true);
									pickM.setDisplayName(ChatColor.DARK_AQUA + "Pioche");
									pickM.setUnbreakable(true);
									pick.setItemMeta(pickM);
	
									ItemStack tnt = new ItemStack(Material.TNT, 64);
									ItemMeta tntM = tnt.getItemMeta();
									tntM.setDisplayName(ChatColor.RED + "TNT");
									tnt.setItemMeta(tntM);
	
									ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 64);
									ItemMeta gappleM = gapple.getItemMeta();
									gappleM.setDisplayName(ChatColor.YELLOW + "Gapple");
									gapple.setItemMeta(gappleM);
	
									ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
									ItemMeta fireM = fire.getItemMeta();
									fireM.setDisplayName(ChatColor.DARK_AQUA + "Briquet");
									fireM.setUnbreakable(true);
									fire.setItemMeta(fireM);
	
									ItemStack sandstone = new ItemStack(Material.CUT_SANDSTONE, 64);
									ItemMeta sandstoneM = sandstone.getItemMeta();
									sandstoneM.setDisplayName(ChatColor.DARK_AQUA + "Sandstone");
									sandstone.setItemMeta(sandstoneM);
	
									p.addPotionEffect(
											new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1000));
	
									p.getInventory().setItemInOffHand(new ItemStack(sandstone));
									p.getInventory().setItem(0, sword);
									p.getInventory().setItem(1, pick);
									p.getInventory().setItem(2, gapple);
									p.getInventory().setItem(3, tnt);
									p.getInventory().setItem(4, fire);
									for (int i = 5; i < 8; i++) {
										p.getInventory().setItem(i, sandstone);
									}
									p.getInventory().setHelmet(helm);
									p.getInventory().setChestplate(chest);
									p.getInventory().setLeggings(pants);
									p.getInventory().setBoots(boots);
									p.getInventory().setHeldItemSlot(0);
									p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,
											10, 1);
									String selectedJoke = UltimaGames.getConfig()
											.getString("Games.RushFFA.Deluxe.joke" + jokeNumber);
									p.sendTitle(ChatColor.AQUA + "RushFFA" + ChatColor.GOLD + ""
											+ ChatColor.ITALIC + " Deluxe", ChatColor.DARK_AQUA + selectedJoke);
									respawnBBar.removePlayer((Player) p);
									this.cancel();
									return;
								}
							}
						}
					}.runTaskTimer(UltimaGames, 1 * 20, 1 * 20);
				}
			}
		}
	}

	

	// fall damages
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if (e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}
			if (e.getEntityType() == EntityType.ENDER_CRYSTAL) {
				if (e.getEntity().isOp() == false) {
					e.setCancelled(true);
				}
			}
			EntityDamageEvent.DamageCause damageCause = e.getCause();
			double finalDamage = e.getFinalDamage();
			if (damageCause == EntityDamageEvent.DamageCause.FALL) {
				e.setDamage(finalDamage / 5.0D);
			}
		}
	}

	// death drops
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			e.getDrops().clear();
		}
	}

	// weather
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if (e.getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			e.setCancelled(true);
		}
	}

	// death message n death screen avoiding
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(EntityDamageByEntityEvent e) {
		Player d = (Player) e.getDamager();
		Player p = (Player) e.getEntity();
		if (p.getGameMode() != GameMode.SPECTATOR) {
			if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
				for (Player O1p : Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap).getPlayers()) {
					O1p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET + ""
							+ ChatColor.ITALIC + "Original" + ChatColor.RESET + "" + ChatColor.DARK_AQUA + ""
							+ ChatColor.BOLD + "] " + ChatColor.RESET + "" + ChatColor.AQUA + "" + ChatColor.BOLD + ""
							+ e.getEntity().getName() + ChatColor.RESET + "" + ChatColor.AQUA + " a été tué par "
							+ ChatColor.BOLD + "" + d.getName() + ChatColor.RESET + "" + ChatColor.AQUA + ".");
				}
				d.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent
						.fromLegacyText("§cVous avez tué &l" + e.getEntity().getName() + "&r&c. &4+10 CRédits."));
			} else if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
				if (p.getHealth() - e.getDamage() < 1) {
					Identity player_info = new Identity();
					if (e.getCause() != DamageCause.VOID) {
						player_info.addKill(1, d.getUniqueId(), Games.RushffaDeluxe);
						for (Player D1p : Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers()) {
							D1p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET
									+ "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + ""
									+ ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + ""
									+ ChatColor.AQUA + "" + ChatColor.BOLD + "" + e.getEntity().getName()
									+ ChatColor.RESET + "" + ChatColor.AQUA + " a été tué par " + ChatColor.BOLD + ""
									+ d.getName() + ChatColor.RESET + "" + ChatColor.AQUA + ".");
						}
						d.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
								"§cVous avez tué §l" + e.getEntity().getName() + "§r§c. §6+10 CRédits."));
					} else {
						for (Player D2p : Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap).getPlayers()) {
							D2p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "[RushFFA " + ChatColor.RESET
									+ "" + ChatColor.GOLD + "" + ChatColor.ITALIC + "Deluxe" + ChatColor.RESET + ""
									+ ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "] " + ChatColor.RESET + ""
									+ ChatColor.AQUA + "" + ChatColor.BOLD + "" + e.getEntity().getName()
									+ ChatColor.RESET + "" + ChatColor.AQUA + " a été tué par le " + ChatColor.BOLD
									+ "vide" + ChatColor.RESET + "" + ChatColor.AQUA + ".");
						}
					}
					p.setHealth(20);
					player_info.addDeath(1, e.getEntity().getUniqueId(), Games.RushffaDeluxe);
					e.setCancelled(true);
					String spawnLocNumber = UltimaGames.getConfig().getString("Games.RushFFA.Deluxe.specLoc");
					String preSpawnLoc[] = spawnLocNumber.split(",");
					Location specSpawnLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap), 0,
							0, 0);
					specSpawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
					specSpawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
					specSpawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));

					p.getInventory().clear();
					p.setGameMode(GameMode.SPECTATOR);
					p.teleport(specSpawnLoc);
					d.playSound(e.getEntity().getLocation(), Sound.BLOCK_METAL_BREAK, 1, 1);
					p.sendTitle(ChatColor.RED + "Vous êtes mort!", ChatColor.GOLD + "Réapparition dans 3 secondes...");
					final BossBar respawnBBar = Bukkit.createBossBar("§6Réapparition dans §l3 secondes§r§6.",
							BarColor.RED, BarStyle.SOLID);
					respawnBBar.addPlayer(p);
					p.playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5, 1);
					new BukkitRunnable() {
						int i = 0;

						@Override
						public void run() {

							if (i == 0) { // 1s
								respawnBBar.setProgress(0.6);
								respawnBBar.setTitle("§6Réapparition dans §l2 secondes§r§6.");
								p.sendTitle(ChatColor.RED + "Vous êtes mort!",
										ChatColor.GOLD + "Réapparition dans 2 secondes...");
								i++;
								return;
							}

							if (i == 1) { // 2s
								respawnBBar.setProgress(0.3);
								respawnBBar.setTitle("§6Réapparition dans §e§l1 seconde§r§6.");
								p.sendTitle(ChatColor.RED + "Vous êtes mort!",
										ChatColor.YELLOW + "Réapparition dans 1 seconde...");
								respawnBBar.setColor(BarColor.YELLOW);
								i++;
								return;
							}

							if (i == 2) { // 3s
								int spawnNumber = ThreadLocalRandom.current().nextInt(1, 38);
								String spawnLocNumber = UltimaGames.getConfig()
										.getString("Games.RushFFA.Deluxe.spawnLoc" + spawnNumber);
								String preSpawnLoc[] = spawnLocNumber.split(",");
								Location spawnLoc = new Location(
										Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap), 0, 0, 0);
								spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
								spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
								spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
								int jokeNumber = ThreadLocalRandom.current().nextInt(1, 11);

								e.getEntity().teleport(spawnLoc);
								p.setGameMode(GameMode.SURVIVAL);
								ItemStack helm = new ItemStack(Material.LEATHER_HELMET);
								ItemMeta helmM = helm.getItemMeta();
								helmM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
								helmM.setDisplayName(ChatColor.DARK_AQUA + "Casque");
								helmM.setUnbreakable(true);
								helm.setItemMeta(helmM);

								ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
								ItemMeta chestM = chest.getItemMeta();
								chestM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
								chestM.setDisplayName(ChatColor.DARK_AQUA + "Plastron");
								chestM.setUnbreakable(true);
								chest.setItemMeta(chestM);

								ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
								ItemMeta pantsM = pants.getItemMeta();
								pantsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
								pantsM.setDisplayName(ChatColor.DARK_AQUA + "Jambières");
								pantsM.setUnbreakable(true);
								pants.setItemMeta(pantsM);

								ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
								ItemMeta bootsM = boots.getItemMeta();
								bootsM.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
								bootsM.setDisplayName(ChatColor.DARK_AQUA + "Chaussures");
								bootsM.setUnbreakable(true);
								boots.setItemMeta(bootsM);

								ItemStack sword = new ItemStack(Material.IRON_SWORD);
								ItemMeta swordM = sword.getItemMeta();
								swordM.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
								swordM.addEnchant(Enchantment.KNOCKBACK, 1, true);
								swordM.setDisplayName(ChatColor.DARK_AQUA + "Épée");
								swordM.setUnbreakable(true);
								sword.setItemMeta(swordM);

								ItemStack pick = new ItemStack(Material.IRON_PICKAXE);
								ItemMeta pickM = pick.getItemMeta();
								pickM.addEnchant(Enchantment.DIG_SPEED, 3, true);
								pickM.setDisplayName(ChatColor.DARK_AQUA + "Pioche");
								pickM.setUnbreakable(true);
								pick.setItemMeta(pickM);

								ItemStack tnt = new ItemStack(Material.TNT, 64);
								ItemMeta tntM = tnt.getItemMeta();
								tntM.setDisplayName(ChatColor.RED + "TNT");
								tnt.setItemMeta(tntM);

								ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 64);
								ItemMeta gappleM = gapple.getItemMeta();
								gappleM.setDisplayName(ChatColor.YELLOW + "Gapple");
								gapple.setItemMeta(gappleM);

								ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
								ItemMeta fireM = fire.getItemMeta();
								fireM.setDisplayName(ChatColor.DARK_AQUA + "Briquet");
								fireM.setUnbreakable(true);
								fire.setItemMeta(fireM);

								ItemStack sandstone = new ItemStack(Material.CUT_SANDSTONE, 64);
								ItemMeta sandstoneM = sandstone.getItemMeta();
								sandstoneM.setDisplayName(ChatColor.DARK_AQUA + "Sandstone");
								sandstone.setItemMeta(sandstoneM);

								p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1000));

								p.getInventory().setItemInOffHand(new ItemStack(sandstone));
								p.getInventory().setItem(0, sword);
								p.getInventory().setItem(1, pick);
								p.getInventory().setItem(2, gapple);
								p.getInventory().setItem(3, tnt);
								p.getInventory().setItem(4, fire);
								for (int i = 5; i < 8; i++) {
									p.getInventory().setItem(i, sandstone);
								}
								p.getInventory().setHelmet(helm);
								p.getInventory().setChestplate(chest);
								p.getInventory().setLeggings(pants);
								p.getInventory().setBoots(boots);
								p.getInventory().setHeldItemSlot(0);
								p.playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
								String selectedJoke = UltimaGames.getConfig()
										.getString("Games.RushFFA.Deluxe.joke" + jokeNumber);
								p.sendTitle(ChatColor.AQUA + "RushFFA" + ChatColor.GOLD + "" + ChatColor.ITALIC + " Deluxe", ChatColor.DARK_AQUA + selectedJoke);
								respawnBBar.removePlayer((Player) e.getEntity());
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

	// explosions
	@EventHandler
	public void onExplosion(EntityExplodeEvent e) {
		if (e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getEntity().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if (e.getEntity().getType() == EntityType.PRIMED_TNT) {
				Iterator<Block> iter = e.blockList().iterator();
				while (iter.hasNext()) {
					Block b = iter.next();
					if (b.getType() != Material.CUT_SANDSTONE) {
						iter.remove();
					}
				}
			}
		}
	}

	// block destroy
	@EventHandler
	public void onBlockDestroy(BlockBreakEvent e) {
		if (e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if (e.getBlock().getType() == Material.TNT) {
				ItemStack tnt = new ItemStack(Material.TNT);
				ItemMeta tntM = tnt.getItemMeta();
				tntM.setDisplayName(ChatColor.RED + "TNT");
				tnt.setItemMeta(tntM);
				e.getPlayer().getInventory().addItem(tnt);
			}
			if (e.getBlock().getType() == Material.CUT_SANDSTONE) {
				ItemStack sandstone = new ItemStack(Material.CUT_SANDSTONE);
				ItemMeta sandstoneM = sandstone.getItemMeta();
				sandstoneM.setDisplayName(ChatColor.DARK_AQUA + "Sandstone");
				sandstone.setItemMeta(sandstoneM);
				e.getPlayer().getInventory().addItem(sandstone);
			} else {
				if (e.getPlayer().isOp() == false && e.getBlock().getType() != Material.CUT_SANDSTONE
						&& e.getBlock().getType() != Material.TNT) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		} else if (e.getWhoClicked().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)
				|| e.getWhoClicked().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
			if (e.getClickedInventory().contains(Material.LEATHER_HELMET)
					|| e.getClickedInventory().contains(Material.LEATHER_CHESTPLATE)
					|| e.getClickedInventory().contains(Material.LEATHER_LEGGINGS)
					|| e.getClickedInventory().contains(Material.LEATHER_BOOTS)) {
				e.setCancelled(true);
			}
		}
	}

	// block place and arraylist define \!/ cleaning method is in
	// fr.groupeultima.org.Tasks.MapCleanRushFFA \!/
	public static ArrayList<Block> Dblocks = new ArrayList<Block>();
	public static ArrayList<Block> Oblocks = new ArrayList<Block>();

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.CUT_SANDSTONE) {
			if (e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
				Dblocks.add(e.getBlock());
			}
			if (e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
				Oblocks.add(e.getBlock());
			}
		}
	}

	// item drop
	@EventHandler
	public void onItemDropOnGround(PlayerDropItemEvent e) {
		if (e.getItemDrop().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)
				|| e.getItemDrop().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
			if (e.getPlayer().isOp() == false) {
				e.setCancelled(true);
			}
		}
	}

	
	
	// tntfly

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		ItemStack item = event.getItem();
		Action action = event.getAction();
		Block block = event.getClickedBlock();
		if (item == null) {
			return;
		}
		if (item.getType() != Material.FLINT_AND_STEEL) {
			return;
		}
		if (block == null) {
			return;
		}
		if (block.getType() != Material.TNT) {
			event.setCancelled(true);
			return;
		}
		if (action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		block.setType(Material.AIR);
		event.setCancelled(true);
		Location location = block.getLocation().add(0.5D, 0.25D, 0.5D);
		TNTPrimed primedTNT = (TNTPrimed) block.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
		primedTNT.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
		primedTNT.setCustomName("§4CHARGEMENT...");
		primedTNT.setCustomNameVisible(true);
	}

	@EventHandler
	public void onExplodeTNT(EntityExplodeEvent event) {
		List<Block> blocks = event.blockList();
		Iterator<Block> blockI = blocks.iterator();
		while (blockI.hasNext()) {
			Block block = (Block) blockI.next();
			if (block.getType() == Material.TNT) {
				block.setType(Material.AIR);
				Location location = block.getLocation().add(0.5D, 0.25D, 0.5D);
				TNTPrimed primedTNT = (TNTPrimed) block.getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
				primedTNT.setVelocity(new Vector(0.0D, 0.25D, 0.0D));
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof TNTPrimed)) {
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		TNTPrimed primedTNT = (TNTPrimed) event.getDamager();
		Player player = (Player) event.getEntity();
		double damage = event.getFinalDamage() / 15.0D;
		for (Entity entitys : primedTNT.getNearbyEntities(1.5D, 1.5D, 1.5D)) {
			if (!(entitys instanceof Player)) {
				return;
			}
			Player players = (Player) entitys;
			if (!players.equals(player)) {
				return;
			}
			player.setVelocity(player.getLocation().getDirection().multiply(damage).setY(0.15));
		}
		event.setDamage(damage);
	}
}
