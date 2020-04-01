package io.github.synntix.fallenkingdomsplugin.commands.subcommands;

import io.github.synntix.fallenkingdomsplugin.FKBase;
import io.github.synntix.fallenkingdomsplugin.FKPlayer;
import io.github.synntix.fallenkingdomsplugin.FallenKingdomsPlugin;
import io.github.synntix.fallenkingdomsplugin.commands.SubCommand;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StartCommand extends SubCommand {

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts the game";
    }

    @Override
    public String getSyntax() {
        return "/fk start";
    }

    @Override
    public void perform(Player player, String[] args) {

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

                //Here are the allowed blocks
                FallenKingdomsPlugin.getAllowedBlocks().add(Material.TNT);

                //Set the player outside of all enemy bases (initialising isInThatBase)
                for (FKPlayer fkPlayer : FallenKingdomsPlugin.getFkPlayerList()) {
                    for (FKBase fkEnemyBase : FallenKingdomsPlugin.getEnemyBases().get(fkPlayer.getTeam())) {
                        fkPlayer.isInThatBase().put(fkEnemyBase,false);
                    }
                }

                //Starting the game
                FallenKingdomsPlugin.setGameStarted(true);
                player.sendMessage("The game is now started.");
            }

        } else {
            player.sendMessage(ChatColor.RED + "ERROR : The game is already started.");
        }
    }
}
