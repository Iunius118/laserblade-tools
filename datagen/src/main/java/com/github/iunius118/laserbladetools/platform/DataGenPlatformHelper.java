package com.github.iunius118.laserbladetools.platform;

import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import com.github.iunius118.laserbladetools.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.util.Objects;

public class DataGenPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "DataGen";
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
        // Do nothing
    }
}
