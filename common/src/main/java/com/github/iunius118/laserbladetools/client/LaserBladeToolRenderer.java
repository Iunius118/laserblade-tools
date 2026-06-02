package com.github.iunius118.laserbladetools.client;

import com.github.iunius118.laserbladetools.tags.ModItemTags;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Renderer for rendering laser blade tool item models.
 * Rendering is delegated from Minecraft's item renderer via mixin.
 */
public class LaserBladeToolRenderer {
    private final RenderType UNLIT_ITEM_SHEET = ModRenderTypes.unlitItem();

    /**
     * Renders the given quads using the Laser Blade Tool render types.
     * When this method returns true, subsequent processing must be canceled.
     *
     * @param poseStack PoseStack for the current render context
     * @param bufferSource MultiBufferSource for getting vertex consumers
     * @param quads List of quads to render
     * @param itemStack The ItemStack being rendered (used for color tinting and foil effect)
     * @param itemColors ItemColors for getting tint colors based on the item stack and tint index
     * @param combinedLight Lightmap coordinates
     * @param combinedOverlay Overlay coordinates
     * @return If true, the subsequent processing should be canceled
     */
    public boolean renderQuadList(PoseStack poseStack, MultiBufferSource bufferSource, List<BakedQuad> quads,
                                  ItemStack itemStack, ItemColors itemColors, int combinedLight, int combinedOverlay) {
        if ((bufferSource == null) || !itemStack.is(ModItemTags.USES_LASER_BLADE_RENDER_TYPE)) {
            return false;
        }

        boolean prevIsBlade = false;
        VertexConsumer buffer = getBuffer(bufferSource, false, itemStack.hasFoil());
        var pose = poseStack.last();

        for (BakedQuad bakedquad : quads) {
            int color = -1;

            if (bakedquad.isTinted()) {
                int tintIndex = bakedquad.getTintIndex();
                color = itemColors.getColor(itemStack, tintIndex);
                // Render with the appropriate render type based on tintIndex (0 -> handle, >0 -> laser blade)
                boolean nextIsBlade = tintIndex > 0;

                // If the tint index indicates a different part (blade vs non-blade) than previous quad, switch buffer
                if (nextIsBlade != prevIsBlade) {
                    buffer = getBuffer(bufferSource, nextIsBlade, itemStack.hasFoil());
                    prevIsBlade = nextIsBlade;
                }
            }

            float a = (float) FastColor.ARGB32.alpha(color) / 255.0F;
            float r = (float) FastColor.ARGB32.red(color) / 255.0F;
            float g = (float) FastColor.ARGB32.green(color) / 255.0F;
            float b = (float) FastColor.ARGB32.blue(color) / 255.0F;
            buffer.putBulkData(pose, bakedquad, new float[]{1.0F, 1.0F, 1.0F, 1.0F}, r, g, b, a,
                    new int[]{combinedLight, combinedLight, combinedLight, combinedLight}, combinedOverlay, true);
        }

        return true;
    }

    private VertexConsumer getBuffer(MultiBufferSource bufferSource, boolean isBlade, boolean hasFoil) {
        // Get the appropriate buffer based on whether it's the blade part and whether it has foil
        VertexConsumer buffer = isBlade
                ? bufferSource.getBuffer(UNLIT_ITEM_SHEET)
                : bufferSource.getBuffer(Sheets.translucentCullBlockSheet());
        return hasFoil ? VertexMultiConsumer.create(bufferSource.getBuffer(RenderType.glint()), buffer) : buffer;
    }
}
