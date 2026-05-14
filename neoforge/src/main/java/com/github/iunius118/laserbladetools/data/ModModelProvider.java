package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModModelProvider extends ModelProvider {

	public ModModelProvider(PackOutput output) {
		super(output, Constants.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		generateBlockModels(blockModels);
		generateItemModels(itemModels);
	}

	private void generateBlockModels(BlockModelGenerators blockModels) {
		var output = blockModels.blockStateOutput;

	}

	private void generateItemModels(ItemModelGenerators itemModels) {
		var output = itemModels.itemModelOutput;

	}

	private void addBlockItemModel(ItemModelOutput output, Item item, String suffix) {
		if (item instanceof BlockItem blockItem) {
			var model = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(blockItem.getBlock(), suffix));
			output.accept(item, model);
		}
	}
}
