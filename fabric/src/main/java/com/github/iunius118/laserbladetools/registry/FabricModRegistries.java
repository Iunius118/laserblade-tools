package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricModRegistries {

    public static void registerGameObjects() {
        registerBlocks();
        registerItems();
        registerCreativeModeTabs();
    }

    private static void registerBlocks() {
        var blocks = ModObjectRegistry.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);
    }

    private static void registerItems() {
        var items = ModObjectRegistry.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

        items.register(Constants.Items.LB_CORE.getPath(), ModItems.LB_CORE);
        items.register(Constants.Items.LB_SWORD.getPath(), ModItems.LB_SWORD);
        items.register(Constants.Items.LB_SHOVEL.getPath(), ModItems.LB_SHOVEL);
        items.register(Constants.Items.LB_PICKAXE.getPath(), ModItems.LB_PICKAXE);
        items.register(Constants.Items.LB_AXE.getPath(), ModItems.LB_AXE);
        items.register(Constants.Items.LB_HOE.getPath(), ModItems.LB_HOE);
        items.register(Constants.Items.LB_SPEAR.getPath(), ModItems.LB_SPEAR);
    }

    private static void registerCreativeModeTabs() {
        var creativeModeTabs = ModObjectRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(), getMainCreativeModeTab());
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return FabricCreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.LB_SWORD))
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                .displayItems((params, output) -> {
                    for (Item i : ModItems.ITEMS) {
                        if (i != null) output.accept(i);
                    }
                })
                .build();
    }

    private record ModObjectRegistry<V, T extends V>(Registry<V> registry, String modId) {

        public static <V, T extends V> ModObjectRegistry<V, T> create(Registry<V> registry, String modId) {
            return new ModObjectRegistry<>(registry, modId);
        }

        public void register(String id, T object) {
            Registry.register(registry, Identifier.fromNamespaceAndPath(modId, id), object);
        }
    }
}
