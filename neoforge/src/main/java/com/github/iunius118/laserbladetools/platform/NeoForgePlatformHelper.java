package com.github.iunius118.laserbladetools.platform;

import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import com.github.iunius118.laserbladetools.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

import java.util.Objects;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !Objects.requireNonNull(FMLLoader.getCurrentOrNull()).isProduction();
    }

    @Override
    public void sendColorSelectionPayloadToServer(ColorSelectionPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
}
