package eisenwave.elytra;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.*;

public class SuperElytraListener implements Listener {
    
    // STATIC CONST
    
    private final static String
        PERMISSION_LAUNCH = "superelytra.launch",
        PERMISSION_GLIDE = "superelytra.glide",
        CONFIG_SPEED_MULTIPLIER = "speed_multiplier",
        CONFIG_CHARGE_UP_TIME = "chargup_time";
    
    private final static int DEFAULT_CHARGE_UP_TIME = 60;
    private final static double DEFAULT_SPEED_MULTIPLIER = 1;
    private final static double BASE_SPEED = 0.05;
    
    // INSTANCE
    
    private Map<Player, Integer> playerChargeUpTicksMap = new WeakHashMap<>();
    
    private final double speedMultiplier;
    private final int chargeUpTime;
    
    public SuperElytraListener(SuperElytraPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        this.speedMultiplier = config.getDouble(CONFIG_SPEED_MULTIPLIER, DEFAULT_SPEED_MULTIPLIER) * BASE_SPEED;
        this.chargeUpTime = config.getInt(CONFIG_CHARGE_UP_TIME, DEFAULT_CHARGE_UP_TIME);
    }
    
    @SuppressWarnings("deprecation")
    public void onTick() {
        for (Player player : playerChargeUpTicksMap.keySet()) {
            if (!player.isOnGround()) {
                playerChargeUpTicksMap.remove(player);
                continue;
            }
            int time = playerChargeUpTicksMap.get(player);
            playerChargeUpTicksMap.put(player, time++);
            
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 0.2F, 0.2F, 0.2F, 0.0F, 1, 30);
            if (time % 3 == 0) {
                player.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 0.1F, 0.1F);
                if (time >= 60)
                    player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 0.1F, 0.1F);
            }
        }
    }
    
    // BUKKIT EVENT HANDLERS
    
    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isGliding() && player.hasPermission(PERMISSION_GLIDE)) {
            Vector unitVector = new Vector(0, player.getLocation().getDirection().getY(), 0);
            player.setVelocity(player.getVelocity().add(unitVector.multiply(speedMultiplier)));
        }
    }
    
    @SuppressWarnings("deprecation")
    @EventHandler(ignoreCancelled = true)
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!player.isOnGround() || !player.hasPermission(PERMISSION_LAUNCH)) return;
        
        ItemStack chestPlate = player.getEquipment().getChestplate();
        if (chestPlate == null || chestPlate.getType() != Material.ELYTRA)
            return;
        
        // start charging up
        if (event.isSneaking()) {
            this.playerChargeUpTicksMap.put(player, 1);
        }
        
        // release charge
        else {
            if (playerChargeUpTicksMap.containsKey(player) && playerChargeUpTicksMap.get(player) >= chargeUpTime) {
                Location loc = player.getLocation();
                Vector dir = loc.getDirection().add(new Vector(0, 3, 0));
    
                player.setVelocity(player.getVelocity().add(dir));
                loc.getWorld().spigot().playEffect(loc, Effect.CLOUD, 0, 0, 0.5F, 0.5F, 0.5F, 0.0F, 30, 30);
                player.playSound(loc, Sound.ENTITY_ENDERDRAGON_FLAP, 0.1F, 2.0F);
            }
            this.playerChargeUpTicksMap.remove(player);
        }
    }
    
}
