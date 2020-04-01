package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FKBase;
import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BaseCommand extends SubCommand {

    @Override
    public String getName() {
        return "base";
    }

    @Override
    public String getDescription() {
        return "Manage team's bases";
    }

    @Override
    public String getSyntax() {
        return "/fk base <set/unset> <color> [radius]";
    }

    @Override
    public void perform(Player player, String[] args) {

        // ----------------- /fk base ----------------
        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "ERROR : Wrong syntax.");
            player.sendMessage("Please, try like this : " + this.getSyntax());
            player.sendMessage("For more information do " + ChatColor.UNDERLINE + "/fk "+ this.getName() +" help");

        } else if (FallenKingdomsPlugin.isGameStarted()) {
            player.sendMessage(ChatColor.RED + "Error : you cannot edit bases while the game is started.");


        } else {

            switch (args[1].toLowerCase()) {

                case "help":
                    player.sendMessage("Setting a base : /fk base set <COLOR> <RADIUS>");
                    player.sendMessage("Delete existing base : /fk base delete <COLOR>");
                    break;


                case "set":
                    if (args.length != 4) {
                        player.sendMessage(ChatColor.RED + "ERROR : Wrong syntax.");
                        player.sendMessage("To set a base please do " + ChatColor.UNDERLINE + "/fk base set <COLOR> <RADIUS>");

                    } else {

                        try {
                            FKTeam targetedTeam = FKTeam.getByColorName(args[2]);

                            if (!targetedTeam.isEnabled()) {
                                player.sendMessage(ChatColor.RED + "ERROR : The "
                                        + targetedTeam.getName()
                                        + " team was not created, please create it.");
                            } else {
                                targetedTeam.setBase(new FKBase(FKTeam.getByColorName(args[2]),
                                        player.getLocation(),
                                        Double.parseDouble(args[3])));

                                player.sendMessage("The " + targetedTeam.getColor()
                                        + targetedTeam.getName() + ChatColor.WHITE + " base have been created.");
                            }
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatColor.RED + "ERROR : team not found.");
                        }

                    }
                    break;


                case "unset":
                    if (args.length != 3) {
                        player.sendMessage(ChatColor.RED + "ERROR : Wrong syntax.");
                        player.sendMessage("To set a base please do " + ChatColor.UNDERLINE + "/fk base unset <COLOR>");

                    } else {
                        try {
                            FKTeam targetedTeam = FKTeam.getByColorName(args[2]);

                            if (targetedTeam.isEnabled()) {
                                if (targetedTeam.getBase() == null) {
                                    player.sendMessage(ChatColor.RED + "ERROR : The " + targetedTeam.getName()
                                            + " base is not set.");
                                } else {
                                    targetedTeam.setBase(null);
                                    player.sendMessage("The " + targetedTeam.getColor()
                                            + targetedTeam.getName() + ChatColor.WHITE + " base have been unset.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "ERROR : The " + targetedTeam.getName()
                                        + " team was not created.");
                            }
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatColor.RED + "ERROR : team not found.");
                        }
                    }
                    break;
            }
        }

    }
}
