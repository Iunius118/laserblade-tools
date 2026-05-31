package com.github.iunius118.laserbladetools.mixin.client;

import com.github.iunius118.laserbladetools.client.ModRenderTypes;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.FastColor;
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

// Forge 1.20.6+ uses official mappings at runtime, so Mixin shouldn't reobfuscate
@Mixin(value = ItemRenderer.class, remap = false)
public abstract class ItemRendererMixin {
    @Shadow @Final
    private ItemColors itemColors;

    @Unique
    private MultiBufferSource laserblade_tools$bufferSource;
    @Unique
    private static final RenderType UNLIT_ITEM_SHEET = ModRenderTypes.unlitItem();

    @Inject(method = "render", at = @At(value = "HEAD"))
    private void onRender(
            ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack,
            MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BakedModel p_model, CallbackInfo ci)
    {
        // Store the buffer source for later use in renderQuadList
        this.laserblade_tools$bufferSource = bufferSource;
    }

    @Inject(method = "renderQuadList", at = @At(value = "HEAD"), cancellable = true)
    private void onRenderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads,
                                  ItemStack itemStack, int combinedLight, int combinedOverlay, CallbackInfo ci)
    {
        // Only apply custom rendering for items tagged as LASER_BLADE_TOOLS
        if (!itemStack.is(ModItemTags.LASER_BLADE_TOOLS)) {
            return;
        }

        boolean flag = !itemStack.isEmpty();
        PoseStack.Pose posestack$pose = poseStack.last();
        boolean prevIsBlade = false;

        for (BakedQuad bakedquad : quads) {
            int color = -1;

            if (flag && bakedquad.isTinted()) {
                int tintIndex = bakedquad.getTintIndex();
                color = itemColors.getColor(itemStack, tintIndex);
                boolean nextIsBlade = tintIndex > 0;

                // If the tint index indicates a different part (blade vs non-blade) than previous quad, switch buffer
                if (nextIsBlade != prevIsBlade) {
                    buffer = laserblade_tools$getBuffer(nextIsBlade, itemStack.hasFoil());
                    prevIsBlade = nextIsBlade;
                }
            }

            float a = (float) FastColor.ARGB32.alpha(color) / 255.0F;
            float r = (float) FastColor.ARGB32.red(color) / 255.0F;
            float g = (float) FastColor.ARGB32.green(color) / 255.0F;
            float b = (float) FastColor.ARGB32.blue(color) / 255.0F;
            buffer.putBulkData(posestack$pose, bakedquad, r, g, b, a, combinedLight, combinedOverlay, true);
        }

        ci.cancel();
    }

    @Unique
    private VertexConsumer laserblade_tools$getBuffer(boolean isBlade, boolean hasFoil) {
        // Get the appropriate buffer based on whether it's the blade part and whether it has foil
        VertexConsumer buffer = isBlade
                ? laserblade_tools$bufferSource.getBuffer(UNLIT_ITEM_SHEET)
                : laserblade_tools$bufferSource.getBuffer(Sheets.translucentCullBlockSheet());

        if (hasFoil) {
            return VertexMultiConsumer.create(laserblade_tools$bufferSource.getBuffer(RenderType.glint()), buffer);
        } else {
            return buffer;
        }
    }
}
