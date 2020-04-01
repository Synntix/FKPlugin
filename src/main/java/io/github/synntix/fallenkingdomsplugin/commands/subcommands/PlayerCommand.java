package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FKPlayer;
import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerCommand extends SubCommand {

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public String getDescription() {
        return "Adding or removing players from teams";
    }

    @Override
    public String getSyntax() {
        return "/fk player <add/remove> <player> <color>";
    }

    @Override
    public void perform(Player player, String[] args) {

        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
            player.sendMessage("To add a player to a team please do " + ChatColor.RED
                    + "/fk teams add <PSEUDO> <COLOR>");

        } else if (FallenKingdomsPlugin.isGameStarted()) {
        player.sendMessage(ChatColor.RED + "Error : you cannot edit bases while the game is started.");

        } else {

            switch (args[1].toLowerCase()) {

                case "help":
                    player.sendMessage("Add player to team : /fk teams add <PSEUDO> <COLOR>");
                    player.sendMessage("Remove player from team : /fk teams remove <PSEUDO> <COLOR>");
                    break;


                case "add":
                    try {
                        FKPlayer targetedPlayer = FallenKingdomsPlugin.getFkPlayers().get(Bukkit.getPlayerExact(args[2]));
                        FKTeam targetedTeam = FKTeam.getByColorName(args[3]);

                        if (FKTeam.getByColorName(args[3]).isEnabled()) { // If the team has been created before
                            // If the pseudo is correct

                            //Set the team to the fkPlayer
                            targetedPlayer.setTeam(targetedTeam);
                            //Add the player to the FKTeam
                            targetedTeam.addPlayer(targetedPlayer);

                            player.sendMessage(args[2] + " has been added to the "
                                    + targetedTeam.getColor()
                                    + targetedTeam.getName()
                                    + ChatColor.WHITE + " team.");
                        } else {
                            player.sendMessage(ChatColor.RED + "ERROR : The "
                                    + targetedTeam.getName()
                                    + " team was not created, please create it.");
                        }
                    } catch (java.lang.NullPointerException e) {
                        // If the pseudo is not correct
                        player.sendMessage(ChatColor.RED + "ERROR : player not found.");
                        // If the color name is not correct
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "ERROR : team not found.");
                    }
                    break;


                case "remove":
                    try {
                        FKPlayer targetedPlayer = FallenKingdomsPlugin.getFkPlayers().get(Bukkit.getPlayerExact(args[2]));
                        FKTeam targetedTeam = FKTeam.getByColorName(args[3]);

                        if (targetedPlayer.getTeam() == targetedTeam) { //If the player is in the team

                            targetedPlayer.setTeam(FKTeam.NOTEAM);
                            targetedTeam.removePlayer(targetedPlayer);
                            player.sendMessage(args[2] + " successfully removed from the "
                                    + targetedTeam.getColor() + targetedTeam.getName()
                                    + ChatColor.WHITE + " team.");

                        } else {
                            player.sendMessage(ChatColor.RED + "ERROR : The player isn't in the "
                                    + targetedTeam.getName() + " team" + ChatColor.WHITE + ".");
                        }

                    } catch (java.lang.NullPointerException e) {
                        // If the pseudo is not correct
                        player.sendMessage(ChatColor.RED + "ERROR : player not found.");
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "ERROR : team not found.");
                    }
                    break;
            }
        }
    }
}
