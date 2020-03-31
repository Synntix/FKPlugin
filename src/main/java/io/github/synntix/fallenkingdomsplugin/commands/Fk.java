package io.github.synntix.fallenkingdomsplugin.commands;

import io.github.synntix.fallenkingdomsplugin.FKBase;
import io.github.synntix.fallenkingdomsplugin.FKPlayer;
import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fk implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (args.length > 0) {

            // ===================== /FK HELP =====================
            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage("Manage teams : /fk teams [...]");
                player.sendMessage("Manage bases : /fk bases [...]");
                player.sendMessage("Manage timer : /fk timer [...]");
                player.sendMessage("Get more info : /fk <MODE> help");

                // ===================== /FK START =====================
            } else if (args[0].equalsIgnoreCase("start")) {
                if (!FallenKingdomsPlugin.isGameStarted()) {

                    if (FKTeam.isATeamEmpty()) {
                        player.sendMessage(ChatColor.RED + "ERROR : A created team is empty.");
                        player.sendMessage(ChatColor.RED + "Please add a player to this team or delete it.");

                    } else if (FKTeam.aTeamHaveNoBase()) {
                        player.sendMessage(ChatColor.RED + "ERROR : A created team have no base set.");
                        player.sendMessage(ChatColor.RED + "Please set a base for this team or delete it.");
                    } else {

                        // Creating an array for enemy bases
                        for (FKTeam fkTeamKey : FKTeam.values()) {
                            ArrayList<FKBase> enemyBasesPerKey = new ArrayList<>();
                            for (FKTeam fkTeam : FKTeam.values()) {
                                if (fkTeam.isEnabled() && fkTeam != fkTeamKey && fkTeam != FKTeam.NOTEAM) {
                                    enemyBasesPerKey.add(fkTeam.getBase());
                                }
                            }
                            FallenKingdomsPlugin.getEnemyBases().put(fkTeamKey,enemyBasesPerKey);
                        }

                        FallenKingdomsPlugin.getAllowedBlocks().add(Material.TNT);

                        //TODO : prevent from launching if a created team doesn't have a base
                        // (otherwise isInThatBase check excepts)

                        for (FKPlayer fkPlayer : FallenKingdomsPlugin.getFkPlayerList()) {
                            for (FKBase fkEnemyBase : FallenKingdomsPlugin.getEnemyBases().get(fkPlayer.getTeam())) {
                                fkPlayer.isInThatBase().put(fkEnemyBase,false);
                            }
                        }

                        FallenKingdomsPlugin.setGameStarted(true);
                        player.sendMessage("The game is now started.");
                    }

                } else {
                    player.sendMessage(ChatColor.RED + "ERROR : The game is already started.");
                }

                // ===================== /FK STOP =====================
            } else if (args[0].equalsIgnoreCase("stop")) {
                if (FallenKingdomsPlugin.isGameStarted()) {
                    FallenKingdomsPlugin.setGameStarted(false);
                    player.sendMessage("The game is now stopped.");
                } else {
                    player.sendMessage(ChatColor.RED + "ERROR : The game is already stopped.");
                }
                // ===================== /FK PAUSE =====================
            } else if (args[0].equalsIgnoreCase("pause")) {
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


            } else if (args[0].equalsIgnoreCase("resume")) {
                if (FallenKingdomsPlugin.isGamePaused()) {
                    FallenKingdomsPlugin.setGamePaused(false);
                    player.sendMessage("The game is resumed.");
                } else {
                    player.sendMessage(ChatColor.RED + "ERROR : The game is already started and not on pause.");
                }

                // ===================== /FK TEAMS =====================
            } else if (args[0].equalsIgnoreCase("teams")) {
                // ----------------- /fk teams ----------------
                if (args.length == 1) {
                    player.sendMessage("For more information do " + ChatColor.RED + "/fk teams help");
                } else {
                    // ----------------- /fk teams help ----------------
                    if (args[1].equalsIgnoreCase("help")) {
                        player.sendMessage("Create new team : /fk teams create <COLOR>");
                        player.sendMessage("Delete existing team : /fk teams delete <COLOR>");
                        player.sendMessage("Add player to team : /fk teams add <PSEUDO> <COLOR>");
                        player.sendMessage("Remove player from team : /fk teams remove <PSEUDO> <COLOR>");

                        // ----------------- /fk teams create ----------------
                    } else if (args[1].equalsIgnoreCase("create")) {
                        //If the number of args doesn't fit
                        if (args.length != 3) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To create a team please do " + ChatColor.RED + "/fk teams create <COLOR>");
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
                        // ----------------- /fk teams delete ----------------
                    } else if (args[1].equalsIgnoreCase("delete")) {
                        if (args.length != 3) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To delete a team please do " + ChatColor.RED + "/fk teams delete <COLOR>");
                        } else {
                            //
                            boolean teamBusy = false;

                            try {
                                FKTeam selectedTeam = FKTeam.getByColorName(args[2]);

                                if (selectedTeam.isEnabled()) { //If the team was created

                                    // teamBusy = true if a player is in this team
                                    for (FKPlayer fkPlayer : FallenKingdomsPlugin.getFkPlayerList()) {
                                        if (fkPlayer.getTeam() == selectedTeam) {
                                            teamBusy = true;
                                        }
                                    }
                                    if (teamBusy) {
                                        player.sendMessage(ChatColor.RED + "ERROR : You cannot delete a team if a player is in it.");
                                    } else {
                                        FKTeam.getByColorName(args[2]).setEnabled(false);
                                        player.sendMessage("The " + selectedTeam.getColor() + selectedTeam.getName()
                                                + ChatColor.WHITE + " team successfully deleted.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "ERROR : The team was not created.");
                                }

                            } catch (IllegalArgumentException e) {
                                player.sendMessage(ChatColor.RED + "ERROR : Color not found.");
                            }

                        }

                        // ----------------- /fk teams list ----------------
                    } else if (args[1].equalsIgnoreCase("list")) {
                        if (args.length != 2) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To list created teams please do " + ChatColor.RED + "/fk teams list");
                        } else {
                            player.sendMessage("These are the created teams :");
                            for (FKTeam fkTeam : FKTeam.values()) {
                                if (fkTeam.isEnabled() && fkTeam != FKTeam.NOTEAM) {
                                    player.sendMessage("Team "
                                            + fkTeam.getColor()
                                            + fkTeam.name());
                                }
                            }
                        }
                    // ----------------- /fk teams add ----------------
                    } else if (args[1].equalsIgnoreCase("add")) {
                        //If the number of args doesn't fit
                        if (args.length != 4) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To add a player to a team please do " + ChatColor.RED
                                    + "/fk teams add <PSEUDO> <COLOR>");

                        } else { //If the number of args does fit

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

                        }
                        // ----------------- /fk teams remove ----------------
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        //If the number of args doesn't fit
                        if (args.length != 4) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To remove a player from a team please do " + ChatColor.RED
                                    + "/fk teams remove <PSEUDO> <COLOR>");
                        } else { //If the number of args does fit

                            try {
                                FKPlayer targetedPlayer = FallenKingdomsPlugin.getFkPlayers().get(Bukkit.getPlayerExact(args[2]));
                                FKTeam targetedTeam = FKTeam.getByColorName(args[3]);

                                if (targetedPlayer.getTeam() == targetedTeam) { //If the player is in the team

                                    targetedPlayer.setTeam(FKTeam.NOTEAM);
                                    targetedTeam.removePlayer(targetedPlayer);
                                    player.sendMessage(args[2] + " successfully removed from the "
                                            + targetedTeam.getColor() + targetedTeam.getName()
                                            + ChatColor.WHITE + " team." );

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
                        }
                    }
                }
                // ===================== /FK BASE =====================
            } else if (args[0].equalsIgnoreCase("base")) {
                // ----------------- /fk base ----------------
                if (args.length == 1) {
                    player.sendMessage("For more information do " + ChatColor.RED + "/fk base help");
                } else {
                    // ----------------- /fk base help ----------------
                    if (args[1].equalsIgnoreCase("help")) {
                        player.sendMessage("Setting a base : /fk base set <COLOR> <RADIUS>");
                        player.sendMessage("Delete existing base : /fk base delete <COLOR>");

                        // ----------------- /fk base set ----------------
                    } else if (args[1].equalsIgnoreCase("set")) {
                        if (args.length != 4) {
                            player.sendMessage(ChatColor.RED + "ERROR : Wrong input.");
                            player.sendMessage("To set a base please do " + ChatColor.RED + "/fk base set <COLOR> <RADIUS>");
                        } else {
                            try {
                                if (!FKTeam.getByColorName(args[2]).isEnabled()) {
                                    player.sendMessage(ChatColor.RED + "ERROR : The "
                                            + FKTeam.getByColorName(args[3]).getName()
                                            + " team was not created, please create it.");
                                } else {
                                    FKTeam.getByColorName(args[2]).setBase(new FKBase(FKTeam.getByColorName(args[2]),
                                            player.getLocation(),
                                            Double.parseDouble(args[3])));
                                    player.sendMessage("The " + FKTeam.getByColorName(args[2]).getColor()
                                            + FKTeam.getByColorName(args[2]).getName() + ChatColor.WHITE + " base have been created.");
                                }
                            } catch (IllegalArgumentException e) {
                                player.sendMessage(ChatColor.RED + "ERROR : team not found.");
                            }

                        }
                    } // TODO : /fk base delete
                }

            } else {
                player.sendMessage("Please provide arguments, do " + ChatColor.RED
                        + "/fk help" + ChatColor.WHITE + " for more information");
            }

        }
        return false;
    }
}
