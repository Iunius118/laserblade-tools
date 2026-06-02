package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.component.ModDataComponents;
import com.github.iunius118.laserbladetools.item.ModItems;
import com.github.iunius118.laserbladetools.menu.ModMenuTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeModRegistries {

    public static void registerGameObjects(IEventBus modEventBus) {
        registerBlocks(modEventBus);
        registerItems(modEventBus);
        registerDataComponentTypes(modEventBus);
        registerMenuTypes(modEventBus);
        registerCreativeModeTabs(modEventBus);
    }

    private static void registerBlocks(IEventBus modEventBus) {
        var blocks = DeferredRegister.createBlocks(Constants.MOD_ID);

        blocks.register(Constants.Blocks.COLORIZER.getPath(), () -> ModBlocks.COLORIZER);

        blocks.register(modEventBus);
    }

    private static DeferredItem<Item> lb_sword;

    private static void registerItems(IEventBus modEventBus) {
        var items = DeferredRegister.createItems(Constants.MOD_ID);

        items.register(Constants.Items.LB_CORE.getPath(), () -> ModItems.LB_CORE);
        lb_sword = items.register(Constants.Items.LB_SWORD.getPath(), () -> ModItems.LB_SWORD);
        items.register(Constants.Items.LB_SHOVEL.getPath(), () -> ModItems.LB_SHOVEL);
        items.register(Constants.Items.LB_PICKAXE.getPath(), () -> ModItems.LB_PICKAXE);
        items.register(Constants.Items.LB_AXE.getPath(), () -> ModItems.LB_AXE);
        items.register(Constants.Items.LB_HOE.getPath(), () -> ModItems.LB_HOE);

        items.register(Constants.Blocks.COLORIZER.getPath(), () -> ModItems.COLORIZER);

        items.register(modEventBus);
    }

    private static void registerDataComponentTypes(IEventBus modEventBus) {
        var dataComponentTypes = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

        dataComponentTypes.register(Constants.DataComponentTypes.LB_CUSTOM_MODEL_DATA.getPath(),
                () -> ModDataComponents.LB_CUSTOM_MODEL_DATA);

        dataComponentTypes.register(modEventBus);
    }

    private static void registerMenuTypes(IEventBus modEventBus) {
        var menuTypes = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

        menuTypes.register(Constants.MenuTypes.COLORIZER.getPath(), () -> ModMenuTypes.COLORIZER);

        menuTypes.register(modEventBus);
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
                // Check whether the mod items exist
                .icon(() -> lb_sword.isBound() ?  new ItemStack(ModItems.LB_SWORD) : ItemStack.EMPTY)
                .displayItems((params, output) -> {
                    // Check whether the mod items exist
                    if (!lb_sword.isBound()) return;

                    for (Item i : ModItems.ITEMS) {
                        output.accept(i);
                    }
                })
                .build();
    }
}
