package com.github.iunius118.laserbladetools.component;

import net.minecraft.core.component.DataComponentType;

public class ModDataComponents {
    public static final DataComponentType<LBCustomModelData> LB_CUSTOM_MODEL_DATA =
            new DataComponentType.Builder<LBCustomModelData>()
                    .persistent(LBCustomModelData.CODEC)
                    .networkSynchronized(LBCustomModelData.STREAM_CODEC)
                    .build();
}
