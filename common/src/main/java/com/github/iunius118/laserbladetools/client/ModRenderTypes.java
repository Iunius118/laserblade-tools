package com.github.iunius118.laserbladetools.client;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.mixin.client.RenderTypeInvoker;
import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class ModRenderTypes {
	// Unlit render pipeline using entity shader
	public static final RenderPipeline UNLIT_TRANSLUCENT_PIPELINE = RenderPipeline.builder()
			.withLocation("pipeline/lb_unlit_translucent")
			.withBindGroupLayout(BindGroupLayouts.GLOBALS)
			.withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
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
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.build();

	private static final BiFunction<String, Identifier, RenderType> UNLIT_TRANSLUCENT = Util.memoize(
			(name, texture) -> {
				RenderSetup renderSetup = RenderSetup.builder(UNLIT_TRANSLUCENT_PIPELINE)
						.withTexture("Sampler0", texture)
						.setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
						.sortOnUpload()
						.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
						.createRenderSetup();
				return RenderTypeInvoker.invokeCreate(name, renderSetup);
			}
	);

	public static RenderType unlit(Identifier texture) {
		return UNLIT_TRANSLUCENT.apply(Constants.MOD_ID + ":lb_unlit", texture);
	}

	public static RenderType unlitItem() {
		return unlit(TextureAtlas.LOCATION_ITEMS);
	}
}
