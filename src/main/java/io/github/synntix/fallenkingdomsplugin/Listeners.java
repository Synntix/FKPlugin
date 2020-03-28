package io.github.synntix.fallenkingdomsplugin;

import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class Listeners implements Listener {

    @EventHandler
    // If the game is not started
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!FallenKingdomsPlugin.isGameStarted()) {
            e.setJoinMessage(e.getPlayer().getDisplayName() + " joined the game.");
            e.getPlayer().setGameMode(GameMode.ADVENTURE);
        } else { // If the game has already started
            e.setJoinMessage("");
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.getPlayer().sendMessage("Too late, the game has already started! Now you can spectate.");
        }

        //Add the new player to the fkPlayers HashMap
        FallenKingdomsPlugin.getFkPlayers().put(e.getPlayer(), new FKPlayer(e.getPlayer()));
        //Add the new player to the fkPlayerList
        FallenKingdomsPlugin.getFkPlayerList().add(FallenKingdomsPlugin.getFkPlayers().get(e.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        // If the game is not started
        if (!FallenKingdomsPlugin.isGameStarted()) {
            e.setQuitMessage(e.getPlayer().getDisplayName() + " leaved the game.");
            FallenKingdomsPlugin.getFkPlayers().remove(e.getPlayer());
        } else { // If the game has already started
            e.setQuitMessage(e.getPlayer().getDisplayName() + " leaved the game. Do /fk pause to suspend the game.");
            FallenKingdomsPlugin.getFkPlayerList().remove(FallenKingdomsPlugin.getFkPlayers().get(e.getPlayer()));
        }

    }

    @EventHandler
    public void onPlayerMovement(PlayerMoveEvent e) {
        if (FallenKingdomsPlugin.isGameStarted()) {

            if (FallenKingdomsPlugin.isGamePaused()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot move while the game is paused!");
            }
            FKPlayer player = FallenKingdomsPlugin.getFkPlayers().get(e.getPlayer());

            // If the player enters his base
            if (player.getTeam() != FKTeam.NOTEAM && !player.isInOwnBase() && player.getTeam().getBase().isInBase(e.getPlayer().getLocation())) {
                player.setInOwnBase(true);
                e.getPlayer().sendMessage("You're entering " + player.getTeam().getColor()
                        + "your base" + ChatColor.WHITE + ".");

                // If the player leaves his base
            } else if (player.isInOwnBase() && !player.getTeam().getBase().isInBase(e.getPlayer().getLocation())) {
                player.setInOwnBase(false);
                e.getPlayer().sendMessage("You're leaving " + player.getTeam().getColor()
                        + "your base" + ChatColor.WHITE + ".");
            }

            //For each enemy base check if the player enters or leaves it
            for (FKBase enemyBase : FallenKingdomsPlugin.getEnemyBases().get(player.getTeam())) {
                // If the player enters an enemy base
                if (!player.isInThatBase().get(enemyBase) && enemyBase.isInBase(e.getPlayer().getLocation())) {
                    player.setInEnemyBase(true);
                    player.isInThatBase().put(enemyBase,true);
                    e.getPlayer().sendMessage("You're entering " + enemyBase.getColor()
                            + "an enemy base" + ChatColor.WHITE + ".");
                // If the player enters an enemy base
                } else if (player.isInThatBase().get(enemyBase) && !enemyBase.isInBase(e.getPlayer().getLocation())) {
                    player.setInEnemyBase(false);
                    player.isInThatBase().put(enemyBase,false);
                    e.getPlayer().sendMessage("You're leaving " + enemyBase.getColor()
                            + "an enemy base" + ChatColor.WHITE + ".");
                }
            }
        }



    }

    @EventHandler
    public void onBlockBuild(BlockCanBuildEvent e) {
        FKPlayer player = FallenKingdomsPlugin.getFkPlayers().get(e.getPlayer());

        boolean isAllowedBlock = false;
        for (Material block : FallenKingdomsPlugin.getAllowedBlocks()) {
            if (e.getMaterial() == block) {
                isAllowedBlock = true;
            }
        }

        if (FallenKingdomsPlugin.isGameStarted()) {
                // If the block is not in his base and isn't allowed
            if (!isAllowedBlock && !player.getTeam().getBase().isInBase(e.getBlock().getLocation())) {
                e.setBuildable(false);
                e.getPlayer().sendMessage(ChatColor.RED + "You cannot build outside of your base!");
            } else {
                e.setBuildable(true);
            }
            
            //TODO : only op can build before game starts
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        FKPlayer player = FallenKingdomsPlugin.getFkPlayers().get(e.getPlayer());

        if (FallenKingdomsPlugin.isGameStarted()) {
            ArrayList<FKBase> enemyBases = new ArrayList<>();
            for (FKTeam fkTeam : FKTeam.values()) {
                if (fkTeam.isEnabled() && fkTeam != player.getTeam()) {
                    enemyBases.add(fkTeam.getBase());
                }
            }
            for (FKBase enemyBase : enemyBases) {
                if (enemyBase.isInBase(e.getBlock().getLocation())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("You can't break a block in an enemy base");
                }
            }
            //TODO : only op can break blocks before game starts
        }

    }

}
