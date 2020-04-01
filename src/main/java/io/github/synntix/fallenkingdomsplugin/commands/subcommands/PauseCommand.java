package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PauseCommand extends SubCommand {

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getDescription() {
        return "Pauses the game";
    }

    @Override
    public String getSyntax() {
        return "/fk pause";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (FallenKingdomsPlugin.isGameStarted()) {
            if (!FallenKingdomsPlugin.isGamePaused()) {
                FallenKingdomsPlugin.setGamePaused(true);
                player.sendMessage("The game is now paused.");
            } else {
                player.sendMessage(ChatColor.RED + "ERROR : The game is already on pause.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "ERROR : The game must be started to be paused.");
        }

    }
}
