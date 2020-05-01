package fr.groupeultima.org.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.groupeultima.library.identitycard.Identity;
import fr.groupeultima.library.identitycard.Enum.Games;
import fr.groupeultima.org.UltimaGames;

public class GamesScoreboards implements Listener {
	
	UltimaGames UltimaGames;
	
	public GamesScoreboards(UltimaGames instance)
	{
		UltimaGames = instance;
	}
	
	private Identity player_info;
	
	public Identity getPlayerInfos() {
		player_info = new Identity();
		return player_info;
	}
	
	@EventHandler
	public void onWorldChanger(PlayerChangedWorldEvent e) {
		if(e.getPlayer().getWorld() == null) {
			return;
		}
		else {
			if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
				new BukkitRunnable() {
		    		@Override
		            public void run() {
		    			if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaOriginalMap)) {
		    				Identity player_info = new Identity();
			    			ScoreboardManager amanager = Bukkit.getScoreboardManager();
			    	        final Scoreboard rfaO = amanager.getNewScoreboard();
			    	        final Objective aobjective = rfaO.registerNewObjective("rfaO", "rfaO", "rfaO");
			    	        aobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
			    	        aobjective.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "RushFFA" + ChatColor.RESET + "" + ChatColor.ITALIC + " Original");
			    	        Score ascore = aobjective.getScore(" ");
			    	        ascore.setScore(8);
			    	        Score ascore1 = aobjective.getScore(ChatColor.AQUA + "→ Kills: " + String.valueOf(player_info.getKill(e.getPlayer().getUniqueId(), Games.RushOriginal)));
			    	        ascore1.setScore(7);
			    	        Score ascore2 = aobjective.getScore(ChatColor.AQUA + "→ Killstreak: " + "0"); // INSERT KILLSTREAK METHOD INSERT KILLSTREAK METHOD
			    	        ascore2.setScore(6);
			    	        Score ascore3 = aobjective.getScore(ChatColor.AQUA + "→ Morts: " + String.valueOf(player_info.getDeath(e.getPlayer().getUniqueId(), Games.RushOriginal)));
			    	        ascore3.setScore(5);
			    	        Double ratio = (double) player_info.getKill(e.getPlayer().getUniqueId(), Games.RushOriginal) / (double) player_info.getDeath(e.getPlayer().getUniqueId(), Games.RushOriginal);
			    	        Score ascore4 = aobjective.getScore(ChatColor.AQUA + "→ Ratio: " + ratio + " K/M");
			    	        ascore4.setScore(4);
			    	        Score ascore5 = aobjective.getScore("  ");
			    	        ascore5.setScore(3);
			    	        int ping = ((CraftPlayer) e.getPlayer()).getHandle().ping;
			    	        Score ascore6 = aobjective.getScore(ChatColor.GOLD + "→ Ping: " + ping + " ms");
			    	        ascore6.setScore(2);
			    	        Score ascore7 = aobjective.getScore("   ");
			    	        ascore7.setScore(1);
			    	        Score ascore8 = aobjective.getScore(ChatColor.BLUE + "" + "     " + ChatColor.UNDERLINE + "ultima.net");
			    	        ascore8.setScore(0);
			    	        e.getPlayer().setScoreboard(rfaO);
		    			}
		    			else { return; }
		            }
		    	}.runTaskTimer(UltimaGames, 0, 40);
			}
			else if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
				new BukkitRunnable() {
		    		@Override
		            public void run() {
		    			if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.rushffaDeluxeMap)) {
		    				Identity player_info = new Identity();
			    			ScoreboardManager bmanager = Bukkit.getScoreboardManager();
			    	        final Scoreboard rfaD = bmanager.getNewScoreboard();
			    	        final Objective bobjective = rfaD.registerNewObjective("rfaD", "rfaD", "rfaD");
			    	        bobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
			    	        bobjective.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "RushFFA" + ChatColor.RESET + "" + ChatColor.GOLD+ "" + ChatColor.ITALIC + " Deluxe");
			    	        Score bscore = bobjective.getScore(" ");
			    	        bscore.setScore(8);
			    	        Score bscore1 = bobjective.getScore(ChatColor.AQUA + "→ Kills: " + player_info.getKill(e.getPlayer().getUniqueId(), Games.RushffaDeluxe));
			    	        bscore1.setScore(7);
			    	        Score bscore2 = bobjective.getScore(ChatColor.AQUA + "→ Killstreak: " + "0"); // INSERT KILLSTREAK METHOD INSERT KILLSTREAK METHOD
			    	        bscore2.setScore(6);
			    	        Score bscore3 = bobjective.getScore(ChatColor.AQUA + "→ Morts: " + player_info.getDeath(e.getPlayer().getUniqueId(), Games.RushffaDeluxe));
			    	        bscore3.setScore(5);
			    	        double ratio = 0;
			    	        if(player_info.getKill(e.getPlayer().getUniqueId(), Games.RushffaDeluxe) == 0 && player_info.getDeath(e.getPlayer().getUniqueId(), Games.RushffaDeluxe) == 0) {
			    	        	ratio = 0;
			    	        }
			    	        else {
				    	        ratio = (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".kills") / (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".deaths");
			    	        }
			    	        Score bscore4 = bobjective.getScore(ChatColor.AQUA + "→ Ratio: " + ratio + " K/M");
			    	        bscore4.setScore(4);
			    	        Score bscore5 = bobjective.getScore("  ");
			    	        bscore5.setScore(3);
			    	        int ping = ((CraftPlayer) e.getPlayer()).getHandle().ping;
			    	        Score bscore6 = bobjective.getScore(ChatColor.GOLD + "→ Ping: " + ping + " ms");
			    	        bscore6.setScore(2);
			    	        Score bscore7 = bobjective.getScore("   ");
			    	        bscore7.setScore(1);
			    	        Score bscore8 = bobjective.getScore(ChatColor.BLUE + "" + "     " + ChatColor.UNDERLINE + "ultima.net");
			    	        bscore8.setScore(0);
			    	        e.getPlayer().setScoreboard(rfaD);
		    			}
		    			else { return; }
		            }
		    	}.runTaskTimer(UltimaGames, 0, 40);
			}
			else if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
				new BukkitRunnable() {
		    		@Override
		            public void run() {
		    			if(e.getPlayer().getWorld() == Bukkit.getServer().getWorld(UltimaGames.totemMap)) {
		    				if(UltimaGames.getConfig().contains("Games.Totem.Teams.Red." + e.getPlayer().getName())) { // player is ig, in red team
				    			ScoreboardManager cmanager = Bukkit.getScoreboardManager();
				    	        final Scoreboard totemR = cmanager.getNewScoreboard();
				    	        final Objective cobjective = totemR.registerNewObjective("totemR", "totemR", "totemR");
				    	        cobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
				    	        cobjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem");
				    	        Score cscore = cobjective.getScore(" ");
				    	        cscore.setScore(8);
				    	        Score cscore1 = cobjective.getScore(ChatColor.DARK_RED + "Vous êtes rouge");
				    	        cscore1.setScore(7);
				    	        Score cscore2 = cobjective.getScore("  ");
				    	        cscore2.setScore(6);
				    	        Score cscore3 = cobjective.getScore(ChatColor.RED + "→ Kills: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".kills"));
				    	        cscore3.setScore(5);
				    	        Score cscore4 = cobjective.getScore(ChatColor.RED + "→ Killstreak: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".killstreak"));
				    	        cscore4.setScore(4);
				    	        Score cscore5 = cobjective.getScore(ChatColor.RED + "→ Morts: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".deaths"));
				    	        cscore5.setScore(3);
				    	        double ratio = 0;
				    	        if(UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".kills") == 0 && UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".deaths") == 0) {
				    	        	ratio = 0;
				    	        }
				    	        else {
					    	        ratio = (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".kills") / (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Red." + e.getPlayer().getName() + ".deaths");
				    	        }
				    	        Score cscore6 = cobjective.getScore(ChatColor.RED + "→ Ratio: " + ratio + " K/M");
				    	        cscore6.setScore(2);
				    	        Score cscore7 = cobjective.getScore("   ");
				    	        cscore7.setScore(1);
				    	        Score cscore8 = cobjective.getScore(ChatColor.BLUE + "" + "     " + ChatColor.UNDERLINE + "ultima.net");
				    	        cscore8.setScore(0);
				    	        e.getPlayer().setScoreboard(totemR);
		    				}
		    				else if(UltimaGames.getConfig().contains("Games.Totem.Teams.Blue." + e.getPlayer().getName())) { // player is ig, in blue team
				    			ScoreboardManager cmanager = Bukkit.getScoreboardManager();
				    	        final Scoreboard totemB = cmanager.getNewScoreboard();
				    	        final Objective cobjective = totemB.registerNewObjective("totemB", "totemB", "totemB");
				    	        cobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
				    	        cobjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem");
				    	        Score cscore = cobjective.getScore(" ");
				    	        cscore.setScore(8);
				    	        Score cscore1 = cobjective.getScore(ChatColor.BLUE + "Vous êtes bleu");
				    	        cscore1.setScore(7);
				    	        Score cscore2 = cobjective.getScore("  ");
				    	        cscore2.setScore(6);
				    	        Score cscore3 = cobjective.getScore(ChatColor.AQUA + "→ Kills: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".kills"));
				    	        cscore3.setScore(5);
				    	        Score cscore4 = cobjective.getScore(ChatColor.AQUA + "→ Killstreak: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".killstreak"));
				    	        cscore4.setScore(4);
				    	        Score cscore5 = cobjective.getScore(ChatColor.AQUA + "→ Morts: " + UltimaGames.getConfig().getString("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".deaths"));
				    	        cscore5.setScore(3);
				    	        double ratio = 0;
				    	        if(UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".kills") == 0 && UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".deaths") == 0) {
				    	        	ratio = 0;
				    	        }
				    	        else {
					    	        ratio = (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".kills") / (double) UltimaGames.getConfig().getInt("Games.Totem.Teams.Blue." + e.getPlayer().getName() + ".deaths");
				    	        }
				    	        Score cscore6 = cobjective.getScore(ChatColor.AQUA + "→ Ratio: " + ratio + " K/M");
				    	        cscore6.setScore(2);
				    	        Score cscore7 = cobjective.getScore("   ");
				    	        cscore7.setScore(1);
				    	        Score cscore8 = cobjective.getScore(ChatColor.BLUE + "" + "     " + ChatColor.UNDERLINE + "ultima.net");
				    	        cscore8.setScore(0);
				    	        e.getPlayer().setScoreboard(totemB);
		    				}
		    				else { // game not started
		    					Identity player_info = new Identity();
				    			ScoreboardManager cmanager = Bukkit.getScoreboardManager();
				    	        final Scoreboard totemW = cmanager.getNewScoreboard();
				    	        final Objective cobjective = totemW.registerNewObjective("totemW", "totemW", "totemW");
				    	        cobjective.setDisplaySlot(DisplaySlot.SIDEBAR);
				    	        cobjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Totem");
				    	        Score cscore = cobjective.getScore(" ");
				    	        cscore.setScore(9);
				    	        if(UltimaGames.getConfig().getInt("Games.Totem.isGameStarting") == 1) {
				    	        	Score cscore1 = cobjective.getScore(ChatColor.GREEN + "Démarrage...");
					    	        cscore1.setScore(8);
					    	        Score cscore2 = cobjective.getScore("  ");
					    	        cscore2.setScore(7);
					    	        Score cscore3 = cobjective.getScore(ChatColor.GREEN + "→ Joueurs: " + Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size());
					    	        cscore3.setScore(6);
				    	        }
				    	        else {
				    	        	Score cscore1 = cobjective.getScore(ChatColor.YELLOW + "En attente de joueurs...");
					    	        cscore1.setScore(8);
					    	        Score cscore2 = cobjective.getScore("  ");
					    	        cscore2.setScore(7);
					    	        Score cscore3 = cobjective.getScore(ChatColor.YELLOW + "→ Joueurs: " + Bukkit.getServer().getWorld(UltimaGames.totemMap).getPlayers().size());
					    	        cscore3.setScore(6);
				    	        }   	        
				    	        Score cscore4 = cobjective.getScore("   ");
				    	        cscore4.setScore(5);
				    	        Score cscore5 = cobjective.getScore(ChatColor.AQUA + "→ Kills totaux: " + player_info.getKill(e.getPlayer().getUniqueId(), Games.Totem));
				    	        cscore5.setScore(4);
				    	        Score cscore6 = cobjective.getScore(ChatColor.AQUA + "→ Morts totales: " + player_info.getDeath(e.getPlayer().getUniqueId(), Games.Totem));
				    	        cscore6.setScore(3);
				    	        double ratio = (double) player_info.getKill(e.getPlayer().getUniqueId(), Games.Totem) / (double) player_info.getDeath(e.getPlayer().getUniqueId(), Games.Totem);
				    	        Score cscore7 = cobjective.getScore(ChatColor.AQUA + "→ Ratio: " + ratio + " K/M");
				    	        cscore7.setScore(2);
				    	        Score cscore8 = cobjective.getScore("    ");
				    	        cscore8.setScore(1);
				    	        Score cscore9 = cobjective.getScore(ChatColor.BLUE + "" + "     " + ChatColor.UNDERLINE + "ultima.net");
				    	        cscore9.setScore(0);
				    	        e.getPlayer().setScoreboard(totemW);
		    				}
		    			}
		    			else { return; }
		            }
		    	}.runTaskTimer(UltimaGames, 0, 40);
			}
			else if(e.getPlayer().getWorld() != Bukkit.getServer().getWorld("hub")) {
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				final Scoreboard noSidebar = manager.getNewScoreboard();
		        final Objective objectiveN = noSidebar.registerNewObjective("noSidebar", "noSidebar", "noSidebar");
		        objectiveN.setDisplaySlot(DisplaySlot.SIDEBAR);
				e.getPlayer().setScoreboard(noSidebar);
			}
		}
	}
}
