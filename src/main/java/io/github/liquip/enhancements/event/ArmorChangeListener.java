package io.github.liquip.enhancements.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.github.liquip.api.Liquip;
import io.github.liquip.enhancements.LiquipEnhancements;
import io.github.liquip.enhancements.item.armor.Armor;
import io.github.liquip.enhancements.item.armor.ArmorPiece;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ArmorChangeListener(@NotNull Liquip api, @NotNull List<Armor> armorSets, @NotNull List<ArmorPiece> armorPieces)
    implements Listener {
    @EventHandler
    public void onArmorChange(@NotNull PlayerArmorChangeEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        Bukkit.getScheduler()
            .runTask(LiquipEnhancements.getPlugin(LiquipEnhancements.class), task -> {
                final ItemStack helmet = inventory.getHelmet();
                final ItemStack chestplate = inventory.getChestplate();
                final ItemStack leggings = inventory.getLeggings();
                final ItemStack boots = inventory.getBoots();
                for (final Armor armor : armorSets) {
                    if (checkItemStack(helmet, armor.helmet())) {
                        if (player.removeScoreboardTag(armor.tag())) {
                            armor.removeCallback(player);
                        }
                        continue;
                    }
                    if (checkItemStack(chestplate, armor.chestplate())) {
                        if (player.removeScoreboardTag(armor.tag())) {
                            armor.removeCallback(player);
                        }
                        continue;
                    }
                    if (checkItemStack(leggings, armor.leggings())) {
                        if (player.removeScoreboardTag(armor.tag())) {
                            armor.removeCallback(player);
                        }
                        continue;
                    }
                    if (checkItemStack(boots, armor.boots())) {
                        if (player.removeScoreboardTag(armor.tag())) {
                            armor.removeCallback(player);
                        }
                        continue;
                    }
                    if (player.getScoreboardTags()
                        .contains(armor.tag())) {
                        break;
                    }
                    player.addScoreboardTag(armor.tag());
                    armor.attachCallback(player);
                    break;
                }
                final ItemStack item = switch (event.getSlotType()) {
                    case HEAD -> inventory.getHelmet();
                    case CHEST -> inventory.getChestplate();
                    case LEGS -> inventory.getLeggings();
                    case FEET -> inventory.getBoots();
                };
                for (final ArmorPiece piece : armorPieces) {
                    if (event.getSlotType() != piece.slot()) {
                        continue;
                    }
                    if (checkItemStack(item, piece.key())) {
                        if (player.removeScoreboardTag(piece.tag())) {
                            piece.removeCallback(player);
                        }
                        continue;
                    }
                    if (player.getScoreboardTags()
                        .contains(piece.tag())) {
                        break;
                    }
                    player.addScoreboardTag(piece.tag());
                    piece.attachCallback(player);
                    break;
                }
            });
    }

    private boolean checkItemStack(ItemStack item, NamespacedKey key) {
        if (item == null || !api.isCustomItemStack(item)) {
            return true;
        }
        return !key.equals(api.getKeyFromItemStack(item));
    }
}
