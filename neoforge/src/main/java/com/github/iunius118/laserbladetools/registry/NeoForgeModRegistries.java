package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
        registerCreativeModeTabs(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);

        blocks.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        items.register(modEventBus);
    }

    private static void registerCreativeModeTabs(IEventBus modEventBus) {
        var creativeModeTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(),
                NeoForgeModRegistries::getMainCreativeModeTab);

        creativeModeTabs.register(modEventBus);
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                //.icon(() -> new ItemStack(ModItems.ICON_MAIN))
                .displayItems((params, output) -> {
                    for (Item i : ModItems.ITEMS) {
                        if (i != null) output.accept(i);
                    }
                })
                .build();
    }
}
