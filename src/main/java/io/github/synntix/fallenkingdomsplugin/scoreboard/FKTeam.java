package io.github.synntix.fallenkingdomsplugin.scoreboard;

import io.github.synntix.fallenkingdomsplugin.FKBase;
import io.github.synntix.fallenkingdomsplugin.FKPlayer;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public enum FKTeam {

    NOTEAM("No Team", ChatColor.WHITE, 0,true),
    WHITE("White", ChatColor.WHITE, 0,false),
    ORANGE("Orange", ChatColor.RED, 1,false),
    PURPLE("Purple", ChatColor.DARK_PURPLE, 2,false),
    BLUE("Blue", ChatColor.BLUE, 3,false),
    YELLOW("Yellow", ChatColor.YELLOW, 4,false),
    GREEN("Green", ChatColor.GREEN, 5,false),
    PINK("Pink", ChatColor.LIGHT_PURPLE, 6,false),
    DARK_GRAY("Dark-Gray", ChatColor.DARK_GRAY, 7,false),
    GRAY("Gray", ChatColor.GRAY, 8,false),
    AQUA("Aqua", ChatColor.AQUA, 9,false),
    GOLD("Gold", ChatColor.GOLD, 10,false),
    DARK_BLUE("Dark-Blue", ChatColor.DARK_BLUE, 11,false),
    DARK_AQUA("Dark-aqua", ChatColor.DARK_AQUA, 12,false),
    DARK_GREEN("Dark-Green", ChatColor.DARK_GREEN, 13,false),
    RED("Red", ChatColor.DARK_RED, 14,false),
    BLACK("Black", ChatColor.BLACK, 15,false);

    private String name;
    private ChatColor color;
    private int woolID;
    private boolean enabled;
    private FKBase base;
    private ArrayList<FKPlayer> players;


    FKTeam(String name, ChatColor color, int woolID, boolean enabled) {
        this.name = name;
        this.color = color;
        this.woolID = woolID;
        this.enabled = enabled;
        this.base = null;
        this.players = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setBase(FKBase base) {
        this.base = base;
    }

    public void addPlayer(FKPlayer fkPlayer) {
        this.players.add(fkPlayer);
    }

    public void removePlayer(FKPlayer fkPlayer) {
        this.players.remove(fkPlayer);
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getWoolID() {
        return woolID;
    }

    public ArrayList<FKPlayer> getPlayers() {
        return players;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public FKBase getBase() {
        return base;
    }

    public static FKTeam getById(int id) throws IllegalArgumentException{
        switch(id){
            case 0 : return FKTeam.WHITE;
            case 1 : return FKTeam.ORANGE;
            case 2 : return FKTeam.PURPLE;
            case 3 : return FKTeam.BLUE;
            case 4 : return FKTeam.YELLOW;
            case 5 : return FKTeam.GREEN;
            case 6 : return FKTeam.PINK;
            case 7 : return FKTeam.DARK_GRAY;
            case 8 : return FKTeam.GRAY;
            case 9 : return FKTeam.AQUA;
            case 10 : return FKTeam.GOLD;
            case 11 : return FKTeam.DARK_BLUE;
            case 12 : return FKTeam.DARK_AQUA;
            case 13 : return FKTeam.DARK_GREEN;
            case 14 : return FKTeam.RED;
            case 15 : return FKTeam.BLACK;
        }
        throw new IllegalArgumentException();
    }

    public static FKTeam getByColorName(String colorName) throws IllegalArgumentException{
        switch(colorName.toLowerCase()){
            case "white" : return FKTeam.WHITE;
            case "orange" : return FKTeam.ORANGE;
            case "purple" : return FKTeam.PURPLE;
            case "blue" : return FKTeam.BLUE;
            case "yellow" : return FKTeam.YELLOW;
            case "green" : return FKTeam.GREEN;
            case "pink" : return FKTeam.PINK;
            case "dark-gray" : return FKTeam.DARK_GRAY;
            case "gray" : return FKTeam.GRAY;
            case "aqua" : return FKTeam.AQUA;
            case "gold" : return FKTeam.GOLD;
            case "dark-blue" : return FKTeam.DARK_BLUE;
            case "dark-aqua" : return FKTeam.DARK_AQUA;
            case "dark-green" : return FKTeam.DARK_GREEN;
            case "red" : return FKTeam.RED;
            case "black" : return FKTeam.BLACK;
        }
        throw new IllegalArgumentException();
    }

    public static ArrayList<FKTeam> getTeamsEnabled() {
        ArrayList<FKTeam> teamsEnabled = new ArrayList<>();
        for (FKTeam fkTeam : FKTeam.values()) {
            if (fkTeam != FKTeam.NOTEAM && fkTeam.isEnabled()) {
                teamsEnabled.add(fkTeam);
            }
        }
        return teamsEnabled;
    }

    public static boolean aTeamHaveNoBase() {
        for (FKTeam fkTeam : FKTeam.getTeamsEnabled()) {
            if (fkTeam.getBase() == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isATeamEmpty() {
        for (FKTeam fkTeam : FKTeam.getTeamsEnabled()) {
            if (fkTeam.getPlayers().size() == 0) {
                return true;
            }
        }
        return false;
    }

}
