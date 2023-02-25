package io.github.liquip.enhancements;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.LiquipProvider;
import io.github.liquip.enhancements.event.EventListeners;
import io.github.liquip.enhancements.item.StaffOfPower;
import io.github.liquip.enhancements.item.TeleportStaff;
import io.github.liquip.enhancements.item.armor.Armor;
import io.github.liquip.enhancements.item.armor.LiquipArmor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class LiquipEnhancements extends JavaPlugin {
    private Liquip api;
    private List<Armor> armors;

    @Override
    public void onLoad() {
        api = LiquipProvider.get();
        new StaffOfPower().register(api);
        new TeleportStaff().register(api);
        armors = List.of(new LiquipArmor(api));
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager()
            .registerEvents(new EventListeners(api, armors), this);
    }
}
