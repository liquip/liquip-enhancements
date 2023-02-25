package io.github.liquip.enhancements.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.github.liquip.api.Liquip;
import io.github.liquip.enhancements.LiquipEnhancements;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import static io.github.liquip.enhancements.item.armor.LiquipArmor.BOOTS_KEY;
import static io.github.liquip.enhancements.item.armor.LiquipArmor.CHESTPLATE_KEY;
import static io.github.liquip.enhancements.item.armor.LiquipArmor.HELMET_KEY;
import static io.github.liquip.enhancements.item.armor.LiquipArmor.LEGGINGS_KEY;

public record EventListeners(@NotNull Liquip api) implements Listener {
    private static final AttributeModifier modifier =
        new AttributeModifier("liquip:liquip_armor", 20, AttributeModifier.Operation.ADD_NUMBER);

    @EventHandler
    public void onArmorChange(@NotNull PlayerArmorChangeEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        Bukkit.getScheduler()
            .runTask(LiquipEnhancements.getPlugin(LiquipEnhancements.class), task -> {
                final ItemStack helmet = inventory.getHelmet();
                player.sendMessage("C");
                if (checkItemStack(helmet, HELMET_KEY)) {
                    remove(player);
                    player.sendMessage("4");
                    return;
                }
                final ItemStack chestplate = inventory.getChestplate();
                if (checkItemStack(chestplate, CHESTPLATE_KEY)) {
                    remove(player);
                    player.sendMessage("3");
                    return;
                }
                final ItemStack leggings = inventory.getLeggings();
                if (checkItemStack(leggings, LEGGINGS_KEY)) {
                    remove(player);
                    player.sendMessage("2");
                    return;
                }
                final ItemStack boots = inventory.getBoots();
                if (checkItemStack(boots, BOOTS_KEY)) {
                    remove(player);
                    player.sendMessage("1");
                    return;
                }
                if (player.getScoreboardTags()
                    .contains("liquip:liquip_armor")) {
                    player.sendMessage("B");
                    return;
                }
                player.sendMessage("A");
                player.addScoreboardTag("liquip:liquip_armor");
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                    .addModifier(modifier);
            });
    }

    private boolean checkItemStack(ItemStack item, NamespacedKey key) {
        if (item == null || !api.isCustomItemStack(item)) {
            return true;
        }
        return !key.equals(api.getKeyFromItemStack(item));
    }

    private void remove(Player player) {
        if (!player.getScoreboardTags()
            .contains("liquip:liquip_armor")) {
            player.sendMessage("E");
            return;
        }
        player.sendMessage("D");
        player.removeScoreboardTag("liquip:liquip_armor");
        player.getAttribute(Attribute.GENERIC_ARMOR)
            .removeModifier(modifier);
    }
}
