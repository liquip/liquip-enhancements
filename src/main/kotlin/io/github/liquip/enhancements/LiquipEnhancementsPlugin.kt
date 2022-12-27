package io.github.liquip.enhancements

import com.github.sqyyy.cougar.Cougar
import com.github.sqyyy.cougar.Slot
import com.github.sqyyy.cougar.Ui
import com.github.sqyyy.cougar.impl.panel.ClickPanel
import com.github.sqyyy.cougar.impl.panel.SingleSlotClickPanel
import com.github.sqyyy.cougar.impl.paperUi
import io.github.liquip.api.LiquipProvider
import io.github.liquip.api.item.Item
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.stream.Collectors
import java.util.stream.StreamSupport
import kotlin.math.max
import kotlin.math.min

class LiquipEnhancementsPlugin : JavaPlugin() {
    private val itemsPerPage = 7 * 4
    private val recipeBookPages = Object2IntOpenHashMap<UUID>()
    private var pageCount = 3
    private var catalogue = listOf<Pair<Item, ItemStack>>()
    private var openCatalogue = mutableMapOf<UUID, Pair<Item, ItemStack>>()

    override fun onEnable() {
        Cougar.initializeSystem(this)
        val api = LiquipProvider.get()

        catalogue = StreamSupport.stream(api.itemRegistry.spliterator(), false)
            .map { it to it.newItemStack() }
            .collect(Collectors.toUnmodifiableList())
        val size = catalogue.size
        pageCount = if (size % itemsPerPage == 0) size / itemsPerPage else size / itemsPerPage + 1

        var recipeBook: Ui? = null
        var recipeBookShowcase: Ui? = null
        val craftingTable = paperUi {
            title = Component.text("Crafting Table")
            type = InventoryType.CHEST
            rows = 5
            fill(0, Slot.RowOneSlotOne, Slot.RowFiveSlotNine, namelessItem(Material.BLACK_STAINED_GLASS_PANE))
            fill(1, Slot.RowTwoSlotTwo, Slot.RowFourSlotFour, namelessItem(Material.AIR))
            frame(1, Slot.RowTwoSlotSix, Slot.RowFourSlotEight, namelessItem(Material.LIME_STAINED_GLASS_PANE))
            put(1, Slot.RowThreeSlotNine, namedItem(Material.KNOWLEDGE_BOOK, Component.text("Recipe Book")))
            +listOf(1 to SingleSlotClickPanel(Slot.RowThreeSlotNine.chestSlot) { player, _, _ ->
                recipeBookPages[player.uniqueId] = 0
                recipeBook?.open(player)
            })
        }
        recipeBook = paperUi {
            title = Component.text("Recipe Book")
            type = InventoryType.CHEST
            rows = 6
            frame(0, Slot.RowOneSlotOne, Slot.RowSixSlotNine, namelessItem(Material.BLACK_STAINED_GLASS_PANE))
            put(1, Slot.RowThreeSlotNine, namedItem(Material.CRAFTING_TABLE, Component.text("Crafting Table")))
            put(1, Slot.RowSixSlotFour, namedItem(Material.SPECTRAL_ARROW, Component.text("Previous Page")))
            put(1, Slot.RowSixSlotFive, namedItem(Material.PAPER, Component.text("Page ???/???")))
            put(1, Slot.RowSixSlotSix, namedItem(Material.SPECTRAL_ARROW, Component.text("Next Page")))
            +listOf(1 to SingleSlotClickPanel(Slot.RowThreeSlotNine.chestSlot) { player, _, _ ->
                recipeBookPages.removeInt(player.uniqueId)
                craftingTable.open(player)
            }, 1 to SingleSlotClickPanel(Slot.RowSixSlotFour.chestSlot) { player, view, _ ->
                val previousPage = recipeBookPages.getInt(player.uniqueId)
                val newPage = max(previousPage - 1, 0)
                if (previousPage != newPage) {
                    recipeBookPages[player.uniqueId] = newPage
                    updateRecipeBook(player, view.topInventory)
                }
            }, 1 to SingleSlotClickPanel(Slot.RowSixSlotSix.chestSlot) { player, view, _ ->
                val previousPage = recipeBookPages.getInt(player.uniqueId)
                val newPage = min(previousPage + 1, pageCount - 1)
                if (previousPage != newPage) {
                    recipeBookPages[player.uniqueId] = newPage
                    updateRecipeBook(player, view.topInventory)
                }
            }, 1 to ClickPanel(Slot.RowTwoSlotTwo.chestSlot, Slot.RowFourSlotEight.chestSlot, 9) { player, _, slot ->
                val row = Slot.getRow(9, slot) - 1
                val column = Slot.getColumn(9, slot) - 1
                val i = row * 7 + column
                val entry = catalogue.getOrNull(i) ?: return@ClickPanel
                openCatalogue[player.uniqueId] = entry
                recipeBookShowcase?.open(player)
            })
            onOpen(2) { player, inventory -> updateRecipeBook(player, inventory) }
            onClose { player, _, reason ->
                if (reason != InventoryCloseEvent.Reason.OPEN_NEW) {
                    recipeBookPages.removeInt(player.uniqueId)
                }
            }
        }
        recipeBookShowcase = paperUi {
            title = Component.text("Recipe Showcase")
            type = InventoryType.CHEST
            rows = 5
            fill(0, Slot.RowOneSlotOne, Slot.RowFiveSlotNine, namelessItem(Material.BLACK_STAINED_GLASS_PANE))
            fill(1, Slot.RowTwoSlotTwo, Slot.RowFourSlotFour, namelessItem(Material.AIR))
            frame(1, Slot.RowTwoSlotSix, Slot.RowFourSlotEight, namelessItem(Material.BARRIER))
            put(1, Slot.RowThreeSlotNine, namedItem(Material.CRAFTING_TABLE, Component.text("Crafting Table")))
            put(1, Slot.RowFiveSlotNine, namedItem(Material.SPECTRAL_ARROW, Component.text("Back")))
            +listOf(1 to SingleSlotClickPanel(Slot.RowThreeSlotNine.chestSlot) { player, _, _ ->
                recipeBookPages.removeInt(player.uniqueId)
                openCatalogue.remove(player.uniqueId)
                craftingTable.open(player)
            }, 1 to SingleSlotClickPanel(Slot.RowFiveSlotNine.chestSlot) { player, _, _ ->
                openCatalogue.remove(player.uniqueId)
                recipeBook.open(player)
            })
            onOpen(2) { player, inventory -> loadRecipeShowcase(player, inventory) }
            onClose { player, _, reason ->
                if (reason != InventoryCloseEvent.Reason.OPEN_NEW) {
                    openCatalogue.remove(player.uniqueId)
                    recipeBookPages.removeInt(player.uniqueId)
                }
            }
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

    private fun namedItem(material: Material, name: Component) = ItemStack(material).apply {
        editMeta { it.displayName(name.decoration(TextDecoration.ITALIC, false)) }
    }

    private fun updateRecipeBook(player: Player, inventory: Inventory) {
        for (row in 1..4) {
            for (column in 1..7) {
                inventory.setItem(row * 9 + column, null)
            }
        }
        val page = recipeBookPages.getInt(player.uniqueId)
        inventory.getItem(Slot.RowSixSlotFive.chestSlot)
            ?.editMeta { it.displayName(Component.text("Page ${page + 1}/$pageCount")) }
        val startIndex = page * itemsPerPage
        val endIndex = min(startIndex + itemsPerPage, catalogue.size)
        for (i in startIndex until endIndex) {
            val column = Slot.getColumn(7, i) + 1
            val row = Slot.getRow(7, i) + 1
            inventory.setItem(row * 9 + column, catalogue[i].second)
        }
    }

    private fun loadRecipeShowcase(player: Player, inventory: Inventory) {
        val pair = openCatalogue[player.uniqueId] ?: return
        inventory.setItem(Slot.RowThreeSlotSeven.chestSlot, pair.second)
    }
}