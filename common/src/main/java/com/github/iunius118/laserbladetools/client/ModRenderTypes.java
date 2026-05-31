package com.github.iunius118.laserbladetools.client;

import com.github.iunius118.laserbladetools.Constants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;

public class ModRenderTypes extends RenderType {
    private static final RenderStateShard.ShaderStateShard LASER_BLADE_UNLIT_SHADER_STATE = getUnlitShader();

    private static final BiFunction<String, ResourceLocation, RenderType> UNLIT_TRANSLUCENT =
            (name, texture) -> {
                RenderType.CompositeState state = RenderType.CompositeState.builder()
                        .setShaderState(LASER_BLADE_UNLIT_SHADER_STATE)
                        .setTextureState(new TextureStateShard(texture, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(true);
                return create(name, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true, state);
    };

    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize,
                          boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    private static RenderStateShard.ShaderStateShard getUnlitShader() {
        // Temporarily set beacon beam shader from vanilla shaders
        return RENDERTYPE_BEACON_BEAM_SHADER;
    }

    public static RenderType unlit(ResourceLocation texture) {
		return UNLIT_TRANSLUCENT.apply(Constants.RenderTypes.LASER_BLADE_UNLIT, texture);
	}

	public static RenderType unlitItem() {
		return unlit(TextureAtlas.LOCATION_BLOCKS);
	}
}
