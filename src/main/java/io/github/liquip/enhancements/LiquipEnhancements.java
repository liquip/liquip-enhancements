package io.github.liquip.enhancements;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.LiquipProvider;
import io.github.liquip.enhancements.event.ArmorChangeListener;
import io.github.liquip.enhancements.event.InteractListener;
import io.github.liquip.enhancements.item.StaffOfPower;
import io.github.liquip.enhancements.item.TeleportStaff;
import io.github.liquip.enhancements.item.armor.CelestialPlate;
import io.github.liquip.enhancements.item.armor.LiquipArmor;
import io.github.liquip.enhancements.item.armor.TitaniumArmor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class LiquipEnhancements extends JavaPlugin {
    @Override
    public void onEnable() {
        final Liquip api = LiquipProvider.get();
        final StaffOfPower staffOfPower = new StaffOfPower(this, api);
        final TeleportStaff teleportStaff = new TeleportStaff(api);
        final CelestialPlate celestialPlate = new CelestialPlate(api);
        final LiquipArmor liquipArmor = new LiquipArmor(api);
        final TitaniumArmor titaniumArmor = new TitaniumArmor(api);
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArmorChangeListener(api, List.of(liquipArmor, titaniumArmor), List.of(celestialPlate)),
            this);
        pluginManager.registerEvents(new InteractListener(api,
            Map.of(StaffOfPower.KEY, staffOfPower::onInteract, TeleportStaff.KEY, teleportStaff::onInteract)), this);
    }
}
