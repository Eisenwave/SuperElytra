package eisenwave.elytra;

import eisenwave.elytra.command.ElytraModeCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperElytraPlugin extends JavaPlugin implements Listener {
    
    private SuperElytraListener eventHandler;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
    
        initListeners();
        initCommands();
    }
    
    private void initListeners() {
        this.eventHandler = new SuperElytraListener(this);
    
        getServer().getPluginManager().registerEvents(eventHandler, this);
    
        getServer().getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                eventHandler.onTick();
            }
        }, 0, 1);
    }
    
    private void initCommands() {
        getCommand("elytramode").setExecutor(new ElytraModeCommand(this));
    }
    
    public SuperElytraListener getEventHandler() {
        return eventHandler;
    }
    
}
