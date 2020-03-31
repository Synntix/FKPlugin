package io.github.synntix.fallenkingdomsplugin.commands;

import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FkTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            commands.add("help");
            commands.add("teams");
            commands.add("base");
            commands.add("start");
            commands.add("stop");
            commands.add("pause");
            commands.add("resume");

            return commands;

        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("teams")) {
                commands.add("help");
                commands.add("create");
                commands.add("delete");
                commands.add("add");
                commands.add("remove");
                commands.add("list");
            } else if (args[0].equalsIgnoreCase("base")) {
                commands.add("help");
                commands.add("set");
                commands.add("unset");
            }

            return commands;
        } else if (args.length == 3) {

            if (args[0].equalsIgnoreCase("teams")
                    && (args[1].equalsIgnoreCase("create")
                    || args[1].equalsIgnoreCase("delete"))) {

                //Add Every team name except "no team" and those already enabled
                for (FKTeam fkTeam : FKTeam.values()) {
                    if (fkTeam != FKTeam.NOTEAM && !fkTeam.isEnabled()) {
                        commands.add(fkTeam.getName());
                    }
                }

            } else if (args[0].equalsIgnoreCase("teams")

                    && (args[1].equalsIgnoreCase("add")
                    || args[1].equalsIgnoreCase("remove"))) {
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    commands.add(player.getName());
                }

            } else if (args[0].equalsIgnoreCase("base")
                    && (args[1].equalsIgnoreCase("set")
                    || args[1].equalsIgnoreCase("unset"))) {

                //Add Every team name except "no team" and those already enabled
                for (FKTeam fkTeam : FKTeam.getTeamsEnabled()) {
                    commands.add(fkTeam.getName());
                }

            }

            return commands;

        } else if (args.length == 4) {

            if (args[0].equalsIgnoreCase("teams") && args[1].equalsIgnoreCase("add")) {
                //Add Every enabled team
                for (FKTeam fkTeam : FKTeam.getTeamsEnabled()) {
                    commands.add(fkTeam.getName());
                }
            }

            return commands;
        }

        return null;
    }

}
