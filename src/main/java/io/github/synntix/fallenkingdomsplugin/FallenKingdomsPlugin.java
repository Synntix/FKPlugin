package io.github.synntix.fallenkingdomsplugin;

import io.github.synntix.fallenkingdomsplugin.commands.CommandManager;
import io.github.synntix.fallenkingdomsplugin.commands.FkTabCompletion;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKScoreboard;
import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class FallenKingdomsPlugin extends JavaPlugin {

    private static boolean gameStarted;
    private static boolean gamePaused;
    private static ArrayList<FKPlayer> fkPlayerList;
    private static HashMap<Player, FKPlayer> fkPlayers;
    private static HashMap<FKTeam, ArrayList<FKBase>> enemyBases;
    private static HashMap<String, FKPlayer> fkLeavers;
    private static ArrayList<Material> allowedBlocks;
    private static FKScoreboard scoreboard;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Attributes initialisation
        gameStarted = false;
        gamePaused = false;
        fkPlayerList = new ArrayList<>();
        scoreboard = new FKScoreboard();
        fkPlayers = new HashMap<>();
        enemyBases = new HashMap<>();
        fkLeavers = new HashMap<>();
        allowedBlocks = new ArrayList<>();
        // TODO : Initialize scoreboard

        //Commands and Events setup
        getServer().getPluginManager().registerEvents(new Listeners(),this);
        getCommand("fk").setExecutor(new CommandManager());
        getCommand("fk").setTabCompleter(new FkTabCompletion());
        //TODO : Create scoreboard
        System.out.println("Fallen Kingdoms Plugin initialised");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static boolean isGameStarted() {
        return gameStarted;
    }

    public static boolean isGamePaused() {
        return gamePaused;
    }

    public static ArrayList<FKPlayer> getFkPlayerList() {
        return fkPlayerList;
    }

    public static HashMap<Player, FKPlayer> getFkPlayers() {
        return fkPlayers;
    }

    public static HashMap<FKTeam, ArrayList<FKBase>> getEnemyBases() {
        return enemyBases;
    }

    public static HashMap<String, FKPlayer> getFkLeavers() {
        return fkLeavers;
    }

    public static ArrayList<Material> getAllowedBlocks() {
        return allowedBlocks;
    }

    public static FKScoreboard getScoreboard() {
        return scoreboard;
    }

    public static void setGameStarted(boolean gameStarted) {
        FallenKingdomsPlugin.gameStarted = gameStarted;
    }

    public static void setGamePaused(boolean gamePaused) {
        FallenKingdomsPlugin.gamePaused = gamePaused;
    }
}
