package com.github.iunius118.laserbladetools.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record LBCustomModelData(List<Float> floats, List<Boolean> flags, List<String> strings, List<Integer> colors) {
    public static final LBCustomModelData EMPTY = new LBCustomModelData(List.of(), List.of(), List.of(), List.of());
    private static final Codec<Integer> RGB_COLOR_CODEC = Codec.withAlternative(
            Codec.INT, ExtraCodecs.VECTOR3F, v3f -> FastColor.ARGB32.colorFromFloat(1F, v3f.x(), v3f.y(), v3f.z())
    );
    public static final Codec<LBCustomModelData> CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            Codec.FLOAT.listOf().optionalFieldOf("floats", List.of())
                                    .forGetter(LBCustomModelData::floats),
                            Codec.BOOL.listOf().optionalFieldOf("flags", List.of())
                                    .forGetter(LBCustomModelData::flags),
                            Codec.STRING.listOf().optionalFieldOf("strings", List.of())
                                    .forGetter(LBCustomModelData::strings),
                            RGB_COLOR_CODEC.listOf().optionalFieldOf("colors", List.of())
                                    .forGetter(LBCustomModelData::colors)
                    )
                    .apply(i, LBCustomModelData::new)
    );
    public static final StreamCodec<ByteBuf, LBCustomModelData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT.apply(ByteBufCodecs.list()),
            LBCustomModelData::floats,
            ByteBufCodecs.BOOL.apply(ByteBufCodecs.list()),
            LBCustomModelData::flags,
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()),
            LBCustomModelData::strings,
            ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
            LBCustomModelData::colors,
            LBCustomModelData::new
    );

    private static <T> @Nullable T getSafe(final List<T> values, final int index) {
        return (T)(index >= 0 && index < values.size() ? values.get(index) : null);
    }

    public @Nullable Float getFloat(final int index) {
        return getSafe(this.floats, index);
    }

    public @Nullable Boolean getBoolean(final int index) {
        return getSafe(this.flags, index);
    }

    public @Nullable String getString(final int index) {
        return getSafe(this.strings, index);
    }

    public @Nullable Integer getColor(final int index) {
        return getSafe(this.colors, index);
    }
}
