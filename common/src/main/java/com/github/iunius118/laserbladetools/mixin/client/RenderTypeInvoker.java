package com.github.iunius118.laserbladetools.mixin.client;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = RenderType.class, remap = false)
public interface RenderTypeInvoker {

	@Invoker
	static RenderType invokeCreate(String name, RenderSetup state) {
		throw new AssertionError();
	}
}
