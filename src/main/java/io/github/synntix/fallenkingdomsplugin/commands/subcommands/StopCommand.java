package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StopCommand extends SubCommand {

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stops the game";
    }

    @Override
    public String getSyntax() {
        return "/fk stop";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (FallenKingdomsPlugin.isGameStarted()) {
            FallenKingdomsPlugin.setGameStarted(false);
            player.sendMessage("The game is now stopped.");
        } else {
            player.sendMessage(ChatColor.RED + "ERROR : The game is already stopped.");
        }

    }
}
