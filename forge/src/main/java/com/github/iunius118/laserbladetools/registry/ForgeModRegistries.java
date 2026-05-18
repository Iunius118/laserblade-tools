package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeModRegistries {

    public static void registerGameObjects(BusGroup modBusGroup) {
        registerBlocks(modBusGroup);
        registerItems(modBusGroup);
        registerCreativeModeTabs(modBusGroup);
    }

    private static void registerBlocks(BusGroup modBusGroup) {
        var blocks = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

        blocks.register(Constants.Blocks.LB_COLORIZER.getPath(), () -> ModBlocks.LB_COLORIZER);

        blocks.register(modBusGroup);
    }

    private static void registerItems(BusGroup modBusGroup) {
        var items = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

        items.register(Constants.Items.LB_CORE.getPath(), () -> ModItems.LB_CORE);
        items.register(Constants.Items.LB_SWORD.getPath(), () -> ModItems.LB_SWORD);
        items.register(Constants.Items.LB_SHOVEL.getPath(), () -> ModItems.LB_SHOVEL);
        items.register(Constants.Items.LB_PICKAXE.getPath(), () -> ModItems.LB_PICKAXE);
        items.register(Constants.Items.LB_AXE.getPath(), () -> ModItems.LB_AXE);
        items.register(Constants.Items.LB_HOE.getPath(), () -> ModItems.LB_HOE);
        items.register(Constants.Items.LB_SPEAR.getPath(), () -> ModItems.LB_SPEAR);

        items.register(Constants.Blocks.LB_COLORIZER.getPath(), () -> ModItems.LB_COLORIZER);

        items.register(modBusGroup);
    }

    private static void registerCreativeModeTabs(BusGroup modBusGroup) {
        var creativeModeTabs = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        creativeModeTabs.register(Constants.CreativeModeTabs.MAIN.getPath(),
                ForgeModRegistries::getMainCreativeModeTab);

        creativeModeTabs.register(modBusGroup);
    }

    private static CreativeModeTab getMainCreativeModeTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable(Constants.CreativeModeTabs.TITLE_MOD_MAIN))
                .icon(() -> new ItemStack(ModItems.LB_SWORD))
                .displayItems((params, output) -> {
                    for (Item i : ModItems.ITEMS) {
                        if (i != null) output.accept(i);
                    }
                })
                .build();
    }
}
