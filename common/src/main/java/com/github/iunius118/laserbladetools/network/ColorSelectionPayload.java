package com.github.iunius118.laserbladetools.network;

import com.github.iunius118.laserbladetools.CommonClass;
import com.github.iunius118.laserbladetools.menu.ColorizerMenu;
import com.github.iunius118.laserbladetools.platform.Services;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Payload for sending color change requests from the client to the server.
 * Sent each time the player presses a color selection button.
 *
 * @param part       Target part index (0-2)
 * @param colorIndex Selected color index (0 = unchanged, 1-16 = DyeColor)
 */
public record ColorSelectionPayload(int part, int colorIndex) implements CustomPacketPayload {
    public static final Type<ColorSelectionPayload> TYPE = new Type<>(CommonClass.modLocation("colorizer"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ColorSelectionPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT, ColorSelectionPayload::part,
                    ByteBufCodecs.INT, ColorSelectionPayload::colorIndex,
                    ColorSelectionPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ColorSelectionPayload payload, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.containerMenu instanceof ColorizerMenu menu) {
                menu.setColorIndex(payload.part(), payload.colorIndex());
            }
        }
    }

    public static void send(int part, int colorIndex) {
        Services.PLATFORM.sendColorSelectionPayloadToServer(new ColorSelectionPayload(part, colorIndex));
    }
}
