package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.LaserBladeTools;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public record NeoForgeModObjectRegistry<V, T extends V>(DeferredRegister<V> deferredRegister, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public Holder<V> register(String name, Supplier<T> object) {
        return deferredRegister.register(name, object);
    }

    @Override
    public void register() {
        if (LaserBladeTools.modEventBus != null) {
            deferredRegister.register(LaserBladeTools.modEventBus);
        }
    }
}
