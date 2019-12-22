package fr.groupeultima.org.Commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.groupeultima.org.UltimaGames;
import io.netty.util.internal.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;

public class PlayCommand implements CommandExecutor {
	
	UltimaGames UltimaGames;
	
	public PlayCommand(UltimaGames instance)
	{
		UltimaGames = instance;
	}
	
	public int i2 = 0;
	
	@SuppressWarnings("deprecation")
	public void joinAsRed(Player p) {
		FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Red"), p.getName());
		UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".obsidian", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".kills", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".deaths", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Red." + p.getName() + ".killstreak", 0);
		int redPlayers = UltimaGames.getConfig().getInt("Games.Totem.redPlayers");
		redPlayers++;
		UltimaGames.getConfig().set("Games.Totem.redPlayers", redPlayers);
		UltimaGames.saveConfig();
		UltimaGames.reloadConfig();
		Location spawnLoc =  new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap),0,0,0);
		String preSpawnLoc[] = UltimaGames.getConfig().getString("Games.Totem.redSpawnLoc").split(",");
		spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
		spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
		spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
		p.teleport(spawnLoc);
		p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem", ChatColor.RED + "Vous êtes rouge!");
		p.getInventory().setHeldItemSlot(0);
		p.playSound(p.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
	}
	
	@SuppressWarnings("deprecation")
	public void joinAsBlue(Player p) {
		FileConfiguration.createPath(UltimaGames.getConfig().getConfigurationSection("Games.Totem.Teams.Blue"), p.getName());
		UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".obsidian", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".kills", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".deaths", 0);
		UltimaGames.getConfig().set("Games.Totem.Teams.Blue." + p.getName() + ".killstreak", 0);
		int bluePlayers = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
		bluePlayers++;
		UltimaGames.getConfig().set("Games.Totem.bluePlayers", bluePlayers);
		UltimaGames.saveConfig();
		UltimaGames.reloadConfig();
		Location spawnLoc =  new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap),0,0,0);
		String preSpawnLoc[] = UltimaGames.getConfig().getString("Games.Totem.blueSpawnLoc").split(",");
		spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
		spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
		spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
		p.teleport(spawnLoc);
		p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem", ChatColor.BLUE + "Vous êtes bleu!");
		p.getInventory().setHeldItemSlot(0);
		p.playSound(p.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);

	}
	
	@SuppressWarnings("deprecation")
	public void joinAsWaiting(Player p) {
		Location spawnLoc =  new Location(Bukkit.getServer().getWorld(UltimaGames.totemMap),0,0,0);
		String preSpawnLoc[] = UltimaGames.getConfig().getString("Games.Totem.waitSpawnLoc").split(",");
		spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
		spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
		spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
		p.teleport(spawnLoc);
		int waitingPlayers = Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size();
		if(waitingPlayers >= UltimaGames.getConfig().getInt("Games.Totem.minimumPlayersToStart") && UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
			UltimaGames.getConfig().set("Games.Totem.isGameStarting", 1); //tell to players game is starting
			p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem", ChatColor.GREEN + "Lancement en cours");
			p.getInventory().setHeldItemSlot(0);
			p.playSound(p.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
			
			new BukkitRunnable() {
				int i = 1;
				@Override
				public void run() {
					if(i == 1) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie dans 5 secondes.."));
						return;
					}
					if(i == 2) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie dans 4 secondes.."));
						return;
					}
					if(i == 3) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie dans 3 secondes.."));
						return;
					}
					if(i == 4) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie dans 2 secondes.."));
						return;
					}
					if(i == 5) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie dans 1 seconde.."));
						return;
					}
					if(i == 6) {
						i++;
						if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") != 1) {
							return;
						}
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> Player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "[Totem] " + ChatColor.RESET + "" + ChatColor.GREEN + "Lancement de la partie!"));
						
						//equally dispatch players in each teams
						ArrayList<Player> player_list = new ArrayList<Player>();
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(Player -> player_list.add(Player));
						Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().forEach(
								Player -> {
									if(i2 == 0) { //red team
										joinAsRed(Player);
										player_list.remove(Player);
										i2++;
										
									}
									else if(i2 == 1) { //blue team
										joinAsBlue(Player);
										player_list.remove(Player);
										i2 = i2 - 1;
										
									}
									
									else {
										return;
									}
								}
								);
	
						
						
						UltimaGames.getConfig().set("Games.Totem.isGameStarting", 0);
						return;
					}
				}
			}.runTaskTimer(UltimaGames, 0, 20);
		}
		else {
			p.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem", ChatColor.YELLOW + "En attente de joueurs...");
			p.getInventory().setHeldItemSlot(0);
		}
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = ((Player) sender).getPlayer();
			if(((Player) sender).getPlayer().getWorld() == Bukkit.getServer().getWorld("hub")) {
				if(args.length == 0) {
					sender.sendMessage(ChatColor.YELLOW + "Veuillez spécifier quel à quel mode de jeu vous voulez jouer: /play [rushffa/ffa/sumo/totem]");
				}
				else {
					if(args.length == 1) {
						if(args[0].equalsIgnoreCase("rushffa")) {
							sender.sendMessage(ChatColor.YELLOW + "Veuillez spécifier quel à quel mode de RushFFA vous voulez jouer: /play rushffa [original/deluxe]");
						}
						else if(args[0].equalsIgnoreCase("totem")) {
							if(UltimaGames.getConfig().getInt("Games.Totem.isGameFinished") == 1) { //game just finished
								p.sendMessage(ChatColor.RED + "Une partie de Totem vient de se finir, veuillez attendre quelques secondes avant de rejoindre.");
							}
							else {
								int blue_players = UltimaGames.getConfig().getInt("Games.Totem.bluePlayers");
								int red_players = UltimaGames.getConfig().getInt("Games.Totem.redPlayers");
								
								if(red_players == 0 && blue_players == 0) {
									//waiting time
									joinAsWaiting(p);
								}
								else { //game is started
								
									if(red_players > blue_players) {
										joinAsBlue(p);
									}
									else if(red_players < blue_players) {
										joinAsRed(p);
									}
									else if(red_players == blue_players) {
										int redorblue = ThreadLocalRandom.current().nextInt(0, 2);
										if(redorblue == 0) {
											joinAsRed(p);
										}
										else {
											joinAsBlue(p);
										}
									}
									else {
										p.sendMessage(ChatColor.RED + "Une erreur est survenue, veuillez ré-essayer.");
									}
								}
							}
						}
						else {
							sender.sendMessage(ChatColor.YELLOW + "Veuillez spécifier quel à quel mode de jeu vous voulez jouer: /play [rushffa/ffa/sumo/totem]");
						}
					}
					else if(args.length == 2) {
						if(args[0].equalsIgnoreCase("rushffa")) {
							if(args[1].equalsIgnoreCase("original")) {
								sender.sendMessage("orignal");
								sender.sendMessage(UltimaGames.getConfig().getString("Games.RushFFA.Original.mapCleanDelay"));
							}
							else if(args[1].equalsIgnoreCase("deluxe")) {
								int spawnNumber = ThreadLocalRandom.current().nextInt(1, 38);
								String spawnLocNumber = UltimaGames.getConfig().getString("Games.RushFFA.Deluxe.spawnLoc" + spawnNumber);
								String preSpawnLoc[] = spawnLocNumber.split(",");
								Location spawnLoc = new Location(Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap),0,0,0);
								spawnLoc.setX(Double.parseDouble(preSpawnLoc[0]));
								spawnLoc.setY(Double.parseDouble(preSpawnLoc[1]));
								spawnLoc.setZ(Double.parseDouble(preSpawnLoc[2]));
								int jokeNumber = ThreadLocalRandom.current().nextInt(1, 11);
								
								((Player) sender).teleport(spawnLoc);
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
								pantsM.setDisplayName(ChatColor.DARK_AQUA + "Jambi�res");
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
								swordM.setDisplayName(ChatColor.DARK_AQUA + "�p�e");
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
								
								((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1000));
								
								((Player) sender).getInventory().setItemInOffHand(new ItemStack(sandstone));
								((Player) sender).getInventory().setItem(0, sword);
								((Player) sender).getInventory().setItem(1, pick);
								((Player) sender).getInventory().setItem(2, gapple);
								((Player) sender).getInventory().setItem(3, tnt);
								((Player) sender).getInventory().setItem(4, fire);
								for(int i = 5; i < 8; i++) {
									((Player) sender).getInventory().setItem(i, sandstone);
								}
								((Player) sender).getInventory().setHelmet(helm);
								((Player) sender).getInventory().setChestplate(chest);
								((Player) sender).getInventory().setLeggings(pants);
								((Player) sender).getInventory().setBoots(boots);
								((Player) sender).getPlayer().getInventory().setHeldItemSlot(0);
								String selectedJoke = UltimaGames.getConfig().getString("Games.RushFFA.Deluxe.joke" + jokeNumber);
								((Player) sender).getPlayer().playSound(((Player) sender).getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
								((Player) sender).getPlayer().sendTitle(ChatColor.AQUA + "RushFFA" + ChatColor.GOLD + "" + ChatColor.ITALIC + " Deluxe", ChatColor.DARK_AQUA + selectedJoke);
							}
							else {
								sender.sendMessage(ChatColor.YELLOW + "Veuillez spécifier quel à quel mode de RushFFA vous voulez jouer: /play rushffa [original/deluxe]");
							}
						}
						else {
							sender.sendMessage(ChatColor.YELLOW + "Veuillez spécifier quel à quel mode de jeu vous voulez jouer: /play [rushffa/ffa/sumo]");
						}
					}
				}
			}
		}
		else { sender.sendMessage(ChatColor.RED + "[UltimaGames] Console cannot execute \"/" + label + "\" command."); }
	return false;
	}
}
