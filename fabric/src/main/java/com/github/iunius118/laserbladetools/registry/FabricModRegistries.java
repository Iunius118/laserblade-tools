package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.component.ModDataComponents;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FabricModRegistries {

    public static void registerGameObjects() {
        registerBlocks();
        registerItems();
        registerDataComponentTypes();
        registerMenuTypes();
        registerCreativeModeTabs();
    }

    private static void registerBlocks() {
        var blocks = ModObjectRegistry.create(BuiltInRegistries.BLOCK, Constants.MOD_ID);

        blocks.register(Constants.Blocks.COLORIZER.getPath(), ModBlocks.COLORIZER);
    }

    private static void registerItems() {
        var items = ModObjectRegistry.create(BuiltInRegistries.ITEM, Constants.MOD_ID);

        items.register(Constants.Items.LB_CORE.getPath(), ModItems.LB_CORE);
        items.register(Constants.Items.LB_SWORD.getPath(), ModItems.LB_SWORD);
        items.register(Constants.Items.LB_SHOVEL.getPath(), ModItems.LB_SHOVEL);
        items.register(Constants.Items.LB_PICKAXE.getPath(), ModItems.LB_PICKAXE);
        items.register(Constants.Items.LB_AXE.getPath(), ModItems.LB_AXE);
        items.register(Constants.Items.LB_HOE.getPath(), ModItems.LB_HOE);

        items.register(Constants.Blocks.COLORIZER.getPath(), ModItems.COLORIZER);
    }

    private static void registerDataComponentTypes() {
        var dataComponentTypes = ModObjectRegistry.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponentTypes.LB_CUSTOM_MODEL_DATA.getPath(),
                ModDataComponents.LB_CUSTOM_MODEL_DATA);
    }

    private static void registerMenuTypes() {
        var menuTypes = ModObjectRegistry.create(BuiltInRegistries.MENU, Constants.MOD_ID);

        menuTypes.register(Constants.MenuTypes.COLORIZER.getPath(), ModMenuTypes.COLORIZER);
    }

    private static void registerCreativeModeTabs() {
        var creativeModeTabs = ModObjectRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(), getMainCreativeModeTab());
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return FabricItemGroup.builder()
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
            Registry.register(registry, ResourceLocation.fromNamespaceAndPath(modId, id), object);
        }
    }
}
