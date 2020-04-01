package io.github.synntix.fallenkingdomsplugin.commands;

import io.github.synntix.fallenkingdomsplugin.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    //Attributes
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    // Constructor
    public CommandManager() {
        subCommands.add(new HelpCommand());
        subCommands.add(new TeamCommand());
        subCommands.add(new PlayerCommand());
        subCommands.add(new BaseCommand());
        subCommands.add(new StartCommand());
        subCommands.add(new StopCommand());
        subCommands.add(new PauseCommand());
        subCommands.add(new ResumeCommand());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            SubCommand help = new HelpCommand();
            help.perform(player,args);
        } else {
            for (SubCommand subCommand : this.getSubCommands()) {
                // If the first argument matches the name of a subcommand
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.perform(player, args);

                    return true;
                }
            }
        }

        return true;

    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

}
