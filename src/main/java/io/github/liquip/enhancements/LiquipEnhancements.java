package io.github.liquip.enhancements;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.LiquipProvider;
import io.github.liquip.enhancements.event.EventListeners;
import io.github.liquip.enhancements.item.StaffOfPower;
import io.github.liquip.enhancements.item.TeleportStaff;
import io.github.liquip.enhancements.item.armor.LiquipArmor;
import io.github.liquip.enhancements.item.armor.TitaniumArmor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class LiquipEnhancements extends JavaPlugin {
    @Override
    public void onEnable() {
        final Liquip api = LiquipProvider.get();
        new StaffOfPower().register(api);
        new TeleportStaff().register(api);
        Bukkit.getPluginManager()
            .registerEvents(new EventListeners(api, List.of(new LiquipArmor(api), new TitaniumArmor(api))), this);
    }
}
