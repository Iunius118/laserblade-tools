package com.github.iunius118.laserbladetools.platform;

import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import com.github.iunius118.laserbladetools.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendColorSelectionPayloadToServer(ColorSelectionPayload payload) {
        ClientPlayNetworking.send(payload);
    }
}
