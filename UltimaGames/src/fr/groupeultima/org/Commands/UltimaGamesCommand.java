package fr.groupeultima.org.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.groupeultima.org.UltimaGames;
import net.md_5.bungee.api.ChatColor;

public class UltimaGamesCommand implements CommandExecutor {

	UltimaGames UltimaGames;
	
	public UltimaGamesCommand(UltimaGames instance)
	{
		UltimaGames = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("ultimagames")) {
			if(args.length == 0) {
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_GRAY + "\\__________________/" + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "UltimaGames" + ChatColor.RESET + "" + ChatColor.DARK_GRAY + "\\__________________/");
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_AQUA + "      → Commands:");
				sender.sendMessage(ChatColor.AQUA + "               - /ultimagames reload → Reload .yml files");
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_GRAY + "\\___________________________________________________/");
				sender.sendMessage(" ");
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					sender.sendMessage(ChatColor.YELLOW + "[UltimaGames] Reload .yml files...");
					try {
						UltimaGames.reloadConfig();
						sender.sendMessage(ChatColor.GREEN + "[UltimaGames] Succefully reloaded .yml files.");
					} catch(Exception e) {
						sender.sendMessage(ChatColor.DARK_RED + "[UltimaGames] An error occured while reloading .yml files, please check them.");
						sender.sendMessage(ChatColor.RED + "[UltimaGames] A detailled error has been parsed in console.");
						e.printStackTrace();
					}
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "[UltimaGames] Too many arguments. Please execute /ultimagames for help.");
			}
		}
	return false;
	}
}
