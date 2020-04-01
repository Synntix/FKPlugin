package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displaying help";
    }

    @Override
    public String getSyntax() {
        return "/fk help";
    }

    @Override
    public void perform(Player player, String[] args) {

        player.sendMessage("Manage teams : /fk team [...]");
        player.sendMessage("Manage bases : /fk base [...]");
        player.sendMessage("Manage players : /fk player [...]");
        player.sendMessage("Manage timer : /fk timer [...]");
        player.sendMessage("Get more info : /fk <MODE> help");

    }
}
