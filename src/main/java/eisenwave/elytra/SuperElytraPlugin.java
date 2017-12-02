package eisenwave.elytra;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperElytraPlugin extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        final SuperElytraListener listener = new SuperElytraListener(this);
        
        getServer().getPluginManager().registerEvents(listener, this);
        
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                listener.onTick();
            }
        }, 0, 1);
    }
    
}
