package com.github.iunius118.laserbladetools;

import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class LaserBladeTools implements ModInitializer {

    @Override
    public void onInitialize() {
        // Use Fabric to bootstrap the Common mod.
        //Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register listeners
        registerPayloads();
    }

    private void registerPayloads() {
        PayloadTypeRegistry.serverboundPlay().register(ColorSelectionPayload.TYPE, ColorSelectionPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ColorSelectionPayload.TYPE,
                (payload, context) -> ColorSelectionPayload.handle(payload, context.player()));
    }
}
