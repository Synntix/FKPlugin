package io.github.synntix.fallenkingdomsplugin.scoreboard;

import io.github.synntix.fallenkingdomsplugin.FKBase;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public enum FKTeam {

    NOTEAM("No Team", ChatColor.WHITE, 0),
    WHITE("White", ChatColor.WHITE, 0),
    ORANGE("Orange", ChatColor.RED, 1),
    PURPLE("Purple", ChatColor.DARK_PURPLE, 2),
    BLUE("Blue", ChatColor.BLUE, 3),
    YELLOW("Yellow", ChatColor.YELLOW, 4),
    GREEN("Green", ChatColor.GREEN, 5),
    PINK("Pink", ChatColor.LIGHT_PURPLE, 6),
    DARK_GRAY("Dark Gray", ChatColor.DARK_GRAY, 7),
    GRAY("Gray", ChatColor.GRAY, 8),
    AQUA("Aqua", ChatColor.AQUA, 9),
    GOLD("Gold", ChatColor.GOLD, 10),
    DARK_BLUE("Dark Blue", ChatColor.DARK_BLUE, 11),
    DARK_AQUA("Dark aqua", ChatColor.DARK_AQUA, 12),
    DARK_GREEN("Dark Green", ChatColor.DARK_GREEN, 13),
    RED("Red", ChatColor.DARK_RED, 14),
    BLACK("Black", ChatColor.BLACK, 15);

    private String name;
    private ChatColor color;
    private int woolID;
    private boolean enabled;
    private FKBase base;


    FKTeam(String name, ChatColor color, int woolID) {
        this.name = name;
        this.color = color;
        this.woolID = woolID;
        this.enabled = false;
        this.base = null;
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

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public int getWoolID() {
        return woolID;
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
}
