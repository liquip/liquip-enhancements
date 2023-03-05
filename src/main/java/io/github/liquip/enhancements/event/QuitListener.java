package io.github.liquip.enhancements.event;

import io.github.liquip.enhancements.LiquipEnhancements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public record QuitListener(@NotNull LiquipEnhancements plugin) implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Set<String> scoreboardTags = player.getScoreboardTags();
        plugin.getTags()
            .forEach((k, v) -> {
                if (scoreboardTags.contains(k)) {
                    v.accept(player);
                }
            });
    }
}
