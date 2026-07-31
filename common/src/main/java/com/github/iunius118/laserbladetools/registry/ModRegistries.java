package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import com.github.iunius118.laserbladetools.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModRegistries {
    private static Holder<Item> LB_SWORD;

    public static void registerGameObjects() {
        // Blocks
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.BLOCK, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Blocks.COLORIZER, () -> ModBlocks.COLORIZER);
                });
        // Items
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.ITEM, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.Items.LB_CORE, () -> ModItems.LB_CORE);
                    LB_SWORD = r.register(Constants.Items.LB_SWORD, () -> ModItems.LB_SWORD);
                    r.register(Constants.Items.LB_SHOVEL, () -> ModItems.LB_SHOVEL);
                    r.register(Constants.Items.LB_PICKAXE, () -> ModItems.LB_PICKAXE);
                    r.register(Constants.Items.LB_AXE, () -> ModItems.LB_AXE);
                    r.register(Constants.Items.LB_HOE, () -> ModItems.LB_HOE);
                    r.register(Constants.Items.LB_SPEAR, () -> ModItems.LB_SPEAR);
                    // Block items
                    r.register(Constants.Items.COLORIZER, () -> ModItems.COLORIZER);
                });
        // Menus
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.MENU, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.MenuTypes.COLORIZER, () -> ModMenuTypes.COLORIZER);
                });
        // Creative mode tabs
        Services.PLATFORM.createModObjectRegistry(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID)
                .registerObjects(r -> {
                    r.register(Constants.CreativeModeTabs.MAIN, ModRegistries::getMainCreativeModeTab);
                });
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return Services.PLATFORM.createCreativeModeTabBuilder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                // Check whether the mod items exist
                .icon(() -> LB_SWORD.isBound() ?  new ItemStack(ModItems.LB_SWORD) : ItemStack.EMPTY)
                .displayItems((params, output) -> {
                    // Check whether the mod items exist
                    if (!LB_SWORD.isBound()) return;

                    for (Item i : ModItems.ITEMS) {
                        output.accept(i);
                    }
                })
                .build();
    }

    private ModRegistries() {}
}
