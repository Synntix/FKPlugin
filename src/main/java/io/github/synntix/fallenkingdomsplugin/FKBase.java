package io.github.synntix.fallenkingdomsplugin;

import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class FKBase {

    private FKTeam fkTeam;
    private Location center;
    private double radius;
    private ChatColor color;

    public FKBase(FKTeam fkTeam, Location location, double radius) {
        this.fkTeam = fkTeam;
        this.center = location;
        this.radius = radius;
        this.color = fkTeam.getColor();
    }

    public FKTeam getFkTeam() {
        return fkTeam;
    }

    public Location getLocation() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public ChatColor getColor() {
        return color;
    }

    public boolean isInBase (Location l) {
        return (l.getX() > center.getX()-radius-1 && l.getX() < center.getX()+radius
                && l.getZ() > center.getZ()-radius-1 && l.getZ() < center.getZ()+radius);
    }
}
