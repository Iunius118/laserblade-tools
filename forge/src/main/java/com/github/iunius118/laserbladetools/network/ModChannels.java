package com.github.iunius118.laserbladetools.network;

import com.github.iunius118.laserbladetools.CommonClass;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;

public class ModChannels {
    public static final Channel<CustomPacketPayload> PAYLOAD_CHANNEL = ChannelBuilder
            .named(CommonClass.modLocation("payload"))
            .networkProtocolVersion(0)
            .payloadChannel()
            .play()
            .serverbound()
            .addMain(ColorSelectionPayload.TYPE, ColorSelectionPayload.STREAM_CODEC,
                    (payload, context) -> ColorSelectionPayload.handle(payload, context.getSender())
            )
            .build();

    public static void init() {}
}
