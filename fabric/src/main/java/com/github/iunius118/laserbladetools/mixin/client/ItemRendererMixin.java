package com.github.iunius118.laserbladetools.mixin.client;

import com.github.iunius118.laserbladetools.client.LaserBladeToolRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    @Final
    private ItemColors itemColors;

    @Unique
    private MultiBufferSource laserBladeTools$bufferSource;
    @Unique
    private final LaserBladeToolRenderer laserBladeTools$renderer = new LaserBladeToolRenderer();

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void onRender(
            ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack,
            MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci)
    {
        // Store the buffer source for later use in renderQuadList
        laserBladeTools$bufferSource = bufferSource;
    }

    @Inject(method = "renderQuadList", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads,
                                  ItemStack itemStack, int combinedLight, int combinedOverlay, CallbackInfo ci)
    {
        // Delegate the rendering to LaserBladeToolRenderer, If the itemStack is a laser blade tool
        if (laserBladeTools$renderer.renderQuadList(poseStack, laserBladeTools$bufferSource, quads,
                itemStack, itemColors, combinedLight, combinedOverlay)) {
            // If it returns true, cancel the subsequent processing
            ci.cancel();
        }
    }
}
