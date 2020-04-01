package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ResumeCommand extends SubCommand {

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getDescription() {
        return "Resumes the game";
    }

    @Override
    public String getSyntax() {
        return "/fk resume";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (FallenKingdomsPlugin.isGamePaused()) {
            FallenKingdomsPlugin.setGamePaused(false);
            player.sendMessage("The game is resumed.");
        } else {
            player.sendMessage(ChatColor.RED + "ERROR : The game is already started and not on pause.");
        }

    }
}
