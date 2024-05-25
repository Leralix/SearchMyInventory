package org.leralix.searchmyinventory.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.function.Consumer;

public class InvGUI {


    public static void openInventory(Player player, Player target) {

        Gui gui = createChestGui("Inventory of " + target.getName(),6);

        PlayerInventory playerInventory = target.getInventory();

        ItemStack[] itemList = playerInventory.getContents();
        for(int i = 0; i < 36; i++) {
            ItemStack item = itemList[i];
            if(item != null) {
                GuiItem guiItem = ItemBuilder.from(item).asGuiItem(e -> e.setCancelled(true));
                gui.setItem(i, guiItem);
            }
        }

        ItemStack[] armorList = playerInventory.getArmorContents();

        for(int i = 0; i < 4; i++) {
            ItemStack item = armorList[i];
            if(item != null) {
                GuiItem guiItem = ItemBuilder.from(item).asGuiItem(e -> e.setCancelled(true));
                gui.setItem(i + 36, guiItem);
            }
        }

        gui.open(player);
    }

    static Gui createChestGui(String name, int nRow) {
        return Gui.gui()
                .title(Component.text(name))
                .type(GuiType.CHEST)
                .rows(nRow)
                .create();
    }

}
