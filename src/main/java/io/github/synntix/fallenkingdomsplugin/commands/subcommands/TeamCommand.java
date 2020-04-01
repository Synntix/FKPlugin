package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FKPlayer;
import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamCommand extends SubCommand {

    @Override
    public String getName() {
        return "team";
    }

    @Override
    public String getDescription() {
        return "Manage teams";
    }

    @Override
    public String getSyntax() {
        return "/fk team <create/delete> <color>";
    }

    @Override
    public void perform(Player player, String[] args) {
        // ----------------- /fk teams ----------------
        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "ERROR : Wrong syntax.");
            player.sendMessage("Please, try like this : " + this.getSyntax());
            player.sendMessage("For more information do " + ChatColor.UNDERLINE + "/fk "+ this.getName() +" help");

        } else if (FallenKingdomsPlugin.isGameStarted()) {
            player.sendMessage(ChatColor.RED + "Error you cannot edit teams while the game is started.");

        } else {
            switch (args[1].toLowerCase()) {
                // ----------------- /fk teams help ----------------
                case "help":
                    player.sendMessage("Create new team : /fk teams create <COLOR>");
                    player.sendMessage("Delete existing team : /fk teams delete <COLOR>");
                    break;

                // ----------------- /fk teams create ----------------
                case "create":
                    //If the number of args doesn't fit
                    if (args.length != 3) {
                        player.sendMessage(ChatColor.RED + "ERROR : Wrong Syntax.");
                        player.sendMessage("To create a team please do " + ChatColor.UNDERLINE + "/fk teams create <COLOR>");
                    } else {
                        try {
                            FKTeam.getByColorName(args[2]).setEnabled(true);
                            player.sendMessage("Team "
                                    + FKTeam.getByColorName(args[2]).getColor()
                                    + FKTeam.getByColorName(args[2]).name()
                                    + ChatColor.WHITE
                                    + " has successfully been added.");
                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatColor.RED + "ERROR : Color not found.");
                        }
                    }
                    break;

                // ----------------- /fk teams delete ----------------
                case "delete":
                    if (args.length != 3) {
                        player.sendMessage(ChatColor.RED + "ERROR : Wrong Syntax.");
                        player.sendMessage("To delete a team please do " + ChatColor.UNDERLINE + "/fk teams delete <COLOR>");
                    } else {

                        try {
                            FKTeam targetedTeam = FKTeam.getByColorName(args[2]);

                            if (targetedTeam.isEnabled()) { //If the team was created

                                if (!targetedTeam.isEmpty()) {
                                    player.sendMessage(ChatColor.RED + "ERROR : You cannot delete a team if a player is in it.");
                                } else {
                                    targetedTeam.setEnabled(false);
                                    player.sendMessage("The " + targetedTeam.getColor() + targetedTeam.getName()
                                            + ChatColor.WHITE + " team successfully deleted.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "ERROR : The team was not created.");
                            }

                        } catch (IllegalArgumentException e) {
                            player.sendMessage(ChatColor.RED + "ERROR : Color not found.");
                        }

                    }
                    break;

                // ----------------- /fk teams list ----------------
                case "list":
                    if (args.length != 2) {
                        player.sendMessage(ChatColor.RED + "ERROR : Wrong Syntax.");
                        player.sendMessage("To list created teams please do " + ChatColor.UNDERLINE + "/fk teams list");
                    } else {
                        player.sendMessage("These are the created teams :");
                        for (FKTeam fkTeam : FKTeam.getTeamsEnabled()) {
                                player.sendMessage("Team " + fkTeam.getColor() + fkTeam.name());
                        }
                    }
                    break;
            }
        }
    }
}
