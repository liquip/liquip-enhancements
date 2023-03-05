package io.github.liquip.enhancements;

import io.github.liquip.api.Liquip;
import io.github.liquip.api.LiquipProvider;
import io.github.liquip.enhancements.event.ArmorChangeListener;
import io.github.liquip.enhancements.event.InteractListener;
import io.github.liquip.enhancements.event.ProjectileDamageListener;
import io.github.liquip.enhancements.event.QuitListener;
import io.github.liquip.enhancements.item.StaffOfPower;
import io.github.liquip.enhancements.item.TeleportStaff;
import io.github.liquip.enhancements.item.armor.AbyssalDepthSuit;
import io.github.liquip.enhancements.item.armor.CelestialPlate;
import io.github.liquip.enhancements.item.armor.LiquipArmor;
import io.github.liquip.enhancements.item.armor.TitaniumArmor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LiquipEnhancements extends JavaPlugin {
    private final Map<String, Consumer<Player>> tags = new HashMap<>();
    private Liquip api;

    @Override
    public void onEnable() {
        api = LiquipProvider.get();
        final StaffOfPower staffOfPower = new StaffOfPower(this);
        final TeleportStaff teleportStaff = new TeleportStaff(api);
        final AbyssalDepthSuit abyssalDepthSuit = new AbyssalDepthSuit(this);
        final CelestialPlate celestialPlate = new CelestialPlate(this);
        final LiquipArmor liquipArmor = new LiquipArmor(this);
        final TitaniumArmor titaniumArmor = new TitaniumArmor(this);
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(
            new ArmorChangeListener(api, List.of(abyssalDepthSuit, liquipArmor, titaniumArmor), List.of(celestialPlate)), this);
        pluginManager.registerEvents(new InteractListener(api,
            Map.of(StaffOfPower.KEY, staffOfPower::onInteract, TeleportStaff.KEY, teleportStaff::onInteract)), this);
        pluginManager.registerEvents(new ProjectileDamageListener(api), this);
        pluginManager.registerEvents(new QuitListener(this), this);
    }

    public Map<String, Consumer<Player>> getTags() {
        return tags;
    }

    public Liquip getApi() {
        return api;
    }
}
