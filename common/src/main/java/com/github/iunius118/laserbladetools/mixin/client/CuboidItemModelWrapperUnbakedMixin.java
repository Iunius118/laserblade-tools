package com.github.iunius118.laserbladetools.mixin.client;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.client.ModRenderTypes;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = CuboidItemModelWrapper.Unbaked.class, remap = false)
public abstract class CuboidItemModelWrapperUnbakedMixin {
	@Shadow
	@Final
	private Identifier model;

	@Unique
	private static final RenderType UNLIT_ITEM_SHEET = ModRenderTypes.unlitItem();

	@Inject(method = "bake",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/item/CuboidItemModelWrapper;" +
							"validateAtlasUsage(Ljava/util/List;)V"),
			locals = LocalCapture.CAPTURE_FAILSOFT)
	private void onBake(ItemModel.BakingContext context, Matrix4fc transformation,
						CallbackInfoReturnable<ItemModel> cir,
						ModelBaker baker, ResolvedModel resolvedModel, TextureSlots textureSlots, QuadCollection quads,
						ModelRenderProperties properties) {
		// Apply unlit render type to this mod's blade item models
		if (model.getNamespace().equals(Constants.MOD_ID) && model.getPath().endsWith("_blade")) {
			List<BakedQuad> quadList = quads.getAll();

			for (BakedQuad bakedQuad: quadList) {
				var materialInfo = (BakedQuadMaterialInfoAccessor)(Object) bakedQuad.materialInfo();
				materialInfo.setItemRenderType(UNLIT_ITEM_SHEET);
				materialInfo.setShade(false);
				materialInfo.setLightEmission(15);
			}
		}
	}
}
