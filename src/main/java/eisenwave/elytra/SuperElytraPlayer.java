package eisenwave.elytra;

import org.bukkit.entity.Player;

/**
 * The SuperElytra wrapper for a player.
 */
public class SuperElytraPlayer {
    
    private final Player player;
    
    private int chargeUpTicks = -1;
    private boolean enabled;
    
    public SuperElytraPlayer(Player player, boolean enabled) {
        this.player = player;
        this.enabled = enabled;
    }
    
    // GETTERS
    
    /**
     * Returns the wrapped player.
     *
     * @return the wrapped player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Returns the amount of charge-up ticks. If the number is negative, the player is not charging up a launch.
     *
     * @return the amount of charge-up ticks
     */
    public int getChargeUpTicks() {
        return chargeUpTicks;
    }
    
    // PREDICATES
    
    /**
     * Returns whether enhanced flight is enabled for this player.
     *
     * @return enhanced flight
     */
    public boolean isEnabled() {
        return enabled;
    }
    
    /**
     * Returns whether the player is charging up a launch.
     *
     * @return whether the player is charging up a launch
     */
    public boolean isChargingLaunch() {
        return chargeUpTicks >= 0;
    }
    
    // SETTERS
    
    /**
     * Sets the enhanced flight mode for this player.
     *
     * @param enabled the enhanced flight mode
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    /**
     * Sets the amount of launch charge-up ticks of the player. If the given amount is negative, this indicates no
     * charge-up at all.
     *
     * @param ticks the amount of ticks
     */
    public void setChargeUpTicks(int ticks) {
        this.chargeUpTicks = ticks;
    }
    
}
