package com.github.iunius118.laserbladetools.mixin.client;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BakedQuad.MaterialInfo.class, remap = false)
public interface BakedQuadMaterialInfoAccessor {

	@Accessor
	@Mutable
	void setItemRenderType(RenderType renderType);

	@Accessor
	@Mutable
	void setShade(boolean shade);

	@Accessor
	@Mutable
	void setLightEmission(int lightEmission);
}
