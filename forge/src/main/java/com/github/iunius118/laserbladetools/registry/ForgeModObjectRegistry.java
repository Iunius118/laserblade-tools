package com.github.iunius118.laserbladetools.registry;

import com.github.iunius118.laserbladetools.LaserBladeTools;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record ForgeModObjectRegistry<V, T extends V>(DeferredRegister<V> deferredRegister, String namespace)
        implements ModObjectRegistry<V, T> {

    @Override
    public Holder<V> register(String name, Supplier<T> object) {
        return new HolderImpl<>(deferredRegister.register(name, object));
    }

    @Override
    public void register() {
        if (LaserBladeTools.modBusGroup != null) {
            deferredRegister.register(LaserBladeTools.modBusGroup);
        }
    }

    private record HolderImpl<T>(RegistryObject<T> object) implements Holder<T> {

        @Override
        public T value() {
            return object.get();
        }

        @Override
        public boolean isBound() {
            return object.isPresent();
        }

        @Override
        public boolean areComponentsBound() {
            return object.getHolder().map(Holder::areComponentsBound).orElse(false);
        }

        @Override
        public boolean is(Identifier key) {
            return object.getKey().identifier().equals(key);
        }

        @Override
        public boolean is(ResourceKey<T> key) {
            return object.getKey() == key;
        }

        @Override
        public boolean is(Predicate<ResourceKey<T>> predicate) {
            return predicate.test(object.getKey());
        }

        @Override
        public boolean is(TagKey<T> tag) {
            return object.getHolder().map(holder -> holder.is(tag)).orElse(false);
        }

        @Override
        public boolean is(Holder<T> holder) {
            return holder.is(object.getKey());
        }

        @Override
        public Stream<TagKey<T>> tags() {
            return object.getHolder().map(Holder::tags).orElse(Stream.empty());
        }

        @Override
        public DataComponentMap components() {
            return object.getHolder().map(Holder::components).orElse(DataComponentMap.EMPTY);
        }

        @Override
        public Either<ResourceKey<T>, T> unwrap() {
            return Either.left(object.getKey());
        }

        @Override
        public Optional<ResourceKey<T>> unwrapKey() {
            return Optional.of(object.getKey());
        }

        @Override
        public Kind kind() {
            return Holder.Kind.REFERENCE;
        }

        @Override
        public boolean canSerializeIn(HolderOwner<T> registry) {
            return object.getHolder().map(holder -> holder.canSerializeIn(registry)).orElse(false);
        }
    }
}
