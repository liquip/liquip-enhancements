package io.github.liquip.enhancements

import com.github.sqyyy.cougar.Cougar
import com.github.sqyyy.cougar.Slot
import com.github.sqyyy.cougar.Ui
import com.github.sqyyy.cougar.impl.panel.SingleSlotClickPanel
import com.github.sqyyy.cougar.impl.paperUi
import io.github.liquip.api.LiquipProvider
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class LiquipEnhancementsPlugin : JavaPlugin() {
    override fun onEnable() {
        Cougar.initializeSystem(this)
        val api = LiquipProvider.get()
        var recipeBook: Ui? = null
        val craftingTable = paperUi {
            title = Component.text("Crafting Table")
            type = InventoryType.CHEST
            rows = 5
            fill(0, Slot.RowOneSlotOne, Slot.RowFiveSlotNine, namelessItem(Material.BLACK_STAINED_GLASS_PANE))
            fill(1, Slot.RowTwoSlotTwo, Slot.RowFourSlotFour, namelessItem(Material.AIR))
            fill(1, Slot.RowTwoSlotSix, Slot.RowFourSlotEight, namelessItem(Material.LIME_STAINED_GLASS_PANE))
            put(1, Slot.RowThreeSlotNine, namedItem(Material.KNOWLEDGE_BOOK, Component.text("Recipe Book")))
            +listOf(1 to SingleSlotClickPanel(Slot.RowThreeSlotNine.chestSlot) { player, _, _ ->
                player.closeInventory()
                recipeBook?.open(player)
            })
        }
        recipeBook = paperUi {
            title = Component.text("Recipe Book")
            type = InventoryType.CHEST
            rows = 6
            fill(0, Slot.RowOneSlotOne, Slot.RowSixSlotNine, namelessItem(Material.BLACK_STAINED_GLASS_PANE))
            fill(1, Slot.RowTwoSlotTwo, Slot.RowFiveSlotEight, namelessItem(Material.AIR))
            put(1, Slot.RowThreeSlotNine, namedItem(Material.CRAFTING_TABLE, Component.text("Crafting Table")))
            put(1, Slot.RowSixSlotFour, namedItem(Material.SPECTRAL_ARROW, Component.text("Previous Page")))
            put(1, Slot.RowSixSlotFive, namedItem(Material.PAPER, Component.text("Page 0/0")))
            put(1, Slot.RowSixSlotSix, namedItem(Material.SPECTRAL_ARROW, Component.text("Next Page")))
            +listOf(1 to SingleSlotClickPanel(Slot.RowThreeSlotNine.chestSlot) { player, _, _ ->
                player.closeInventory()
                craftingTable.open(player)
            }, 1 to SingleSlotClickPanel(Slot.RowSixSlotFour.chestSlot) { player, view, _ ->
                player.sendMessage(Component.text("Previous"))
            }, 1 to SingleSlotClickPanel(Slot.RowSixSlotSix.chestSlot) { player, view, _ ->
                player.sendMessage(Component.text("Next"))
            })
        }
        getCommand("test")?.setExecutor { sender, _, _, _ ->
            if (sender !is Player) {
                return@setExecutor true
            }
            craftingTable.open(sender)
            true
        }
    }

    private fun namelessItem(material: Material) = ItemStack(material).apply { editMeta { it.displayName(Component.empty()) } }

    private fun namedItem(material: Material, name: Component) = ItemStack(material).apply { editMeta { it.displayName(name) } }
}