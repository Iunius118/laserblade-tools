package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataGenModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);

        blocks.register(Constants.Blocks.COLORIZER.getPath(), () -> ModBlocks.COLORIZER);

        blocks.register(modEventBus);
    }

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        items.register(Constants.Items.LB_CORE.getPath(), () -> ModItems.LB_CORE);
        items.register(Constants.Items.LB_SWORD.getPath(), () -> ModItems.LB_SWORD);
        items.register(Constants.Items.LB_SHOVEL.getPath(), () -> ModItems.LB_SHOVEL);
        items.register(Constants.Items.LB_PICKAXE.getPath(), () -> ModItems.LB_PICKAXE);
        items.register(Constants.Items.LB_AXE.getPath(), () -> ModItems.LB_AXE);
        items.register(Constants.Items.LB_HOE.getPath(), () -> ModItems.LB_HOE);
        items.register(Constants.Items.LB_SPEAR.getPath(), () -> ModItems.LB_SPEAR);

        items.register(Constants.Blocks.COLORIZER.getPath(), () -> ModItems.COLORIZER);

        items.register(modEventBus);
    }
}
