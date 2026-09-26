package com.github.iunius118.laserbladetools.client;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.mixin.client.RenderTypeInvoker;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.oit.OitPipelineSet;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Optional;
import java.util.function.BiFunction;

public class ModRenderTypes {
    // Unlit render pipeline using entity shader
    public static final RenderPipeline.Snippet UNLIT_TRANSLUCENT_SNIPPET = RenderPipeline.builder()
            .withBindGroupLayout(BindGroupLayouts.GLOBALS)
            .withBindGroupLayout(BindGroupLayouts.PROJECTION)
            .withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
            .withBindGroupLayout(BindGroupLayouts.FOG)
            .withBindGroupLayout(BindGroupLayouts.LIGHTING)
            .withVertexShader("core/entity")
            .withFragmentShader("core/entity")
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
            .withVertexBinding(0, DefaultVertexFormat.ENTITY)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(DepthStencilState.DEFAULT)
            .withShaderDefine("EMISSIVE")
            .withShaderDefine("NO_OVERLAY")
            .withShaderDefine("NO_CARDINAL_LIGHTING")
            .withShaderDefine("ALPHA_CUTOUT", 0.1F)
            .buildSnippet();
    public static final RenderPipeline UNLIT_TRANSLUCENT_PIPELINE = RenderPipeline.builder(UNLIT_TRANSLUCENT_SNIPPET)
            .withLocation("pipeline/lb_unlit_translucent")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .build();
    public static final OitPipelineSet OIT_UNLIT_TRANSLUCENT_PIPELINE = new OitPipelineSet.Builder(
            UNLIT_TRANSLUCENT_SNIPPET, "lb_unlit_translucent")
            .withAccumulateModifier(accumulate -> accumulate
                    .withColorTargetState(new ColorTargetState(Optional.of(BlendFunction.TRANSLUCENT),
                            GpuFormat.RGBA16_FLOAT, 15)))
            .build();

    private static final BiFunction<String, Identifier, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
            (name, texture) -> {
                RenderSetup renderSetup = RenderSetup.builder(UNLIT_TRANSLUCENT_PIPELINE)
                        .setOitPipelines(OIT_UNLIT_TRANSLUCENT_PIPELINE)
                        .withTexture("Sampler0", texture)
                        .sortOnUpload()
                        .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                        .createRenderSetup();
                return RenderTypeInvoker.invokeCreate(name, renderSetup);
            }
    );

    public static RenderType unlit(Identifier texture) {
        return UNLIT_TRANSLUCENT.apply(Constants.MOD_ID + ":lb_unlit", texture);
    }

    @SuppressWarnings("deprecation")
    public static RenderType unlitItem() {
        return unlit(TextureAtlas.LOCATION_ITEMS);
    }
}
