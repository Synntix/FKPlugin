package io.github.synntix.fallenkingdomsplugin;

import io.github.synntix.fallenkingdomsplugin.scoreboard.FKTeam;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class FKPlayer {

    private Player player;
    private FKTeam team;
    private boolean inOwnBase;
    private boolean inEnemyBase;
    private boolean inGame;
    private HashMap<FKBase, Boolean> inThatBase;

    public FKPlayer(Player player) {
        this.player = player;
        this.team = FKTeam.NOTEAM;
        this.inOwnBase = false;
        this.inEnemyBase = false;
        this.inGame = true;
        this.inThatBase = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public FKTeam getTeam() {
        return team;
    }

    public boolean isInOwnBase() {
        return inOwnBase;
    }

    public boolean isInEnemyBase() {
        return inEnemyBase;
    }

    public boolean isInGame() {
        return inGame;
    }

    public HashMap<FKBase, Boolean> isInThatBase() {
        return inThatBase;
    }

    public void setInOwnBase(boolean inOwnBase) {
        this.inOwnBase = inOwnBase;
    }

    public void setInEnemyBase(boolean inEnemyBase) {
        this.inEnemyBase = inEnemyBase;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setTeam(FKTeam team) throws IllegalArgumentException {
        if (team.isEnabled()) {
            this.team = team;
        } else {
            throw new IllegalArgumentException("Tried to add player " + player.getName() + " to a disabled team!");
        }
    }
}
