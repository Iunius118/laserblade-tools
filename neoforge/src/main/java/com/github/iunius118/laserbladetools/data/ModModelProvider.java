package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.LaserBladeColor;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.minecraft.client.color.item.CustomModelDataSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
		Consumer<BlockModelDefinitionGenerator> blockStateOutput = blockModels.blockStateOutput;

		blockModels.createHorizontallyRotatedBlock(ModBlocks.COLORIZER, TexturedModel.CUBE_TOP_BOTTOM);
	}

	private void generateItemModels(ItemModelGenerators itemModels) {
		var itemModelOutput = itemModels.itemModelOutput;
		BiConsumer<Identifier, ModelInstance> modelOutput = itemModels.modelOutput;

		itemModels.generateFlatItem(ModItems.LB_CORE, ModelTemplates.FLAT_ITEM);
		generateToolModel(ModItems.LB_SWORD, itemModels);
		generateToolModel(ModItems.LB_SHOVEL, itemModels);
		generateToolModel(ModItems.LB_PICKAXE,itemModels);
		generateToolModel(ModItems.LB_AXE, itemModels);
		generateToolModel(ModItems.LB_HOE, itemModels);
		generateSpearModel(ModItems.LB_SPEAR, itemModels);
	}

	private void generateToolModel(Item item, ItemModelGenerators itemModels) {
		itemModels.itemModelOutput.accept(item,
				generateToolModel(getItemId(item).getPath(), this.mcLocation("item/handheld"), itemModels.modelOutput));
	}

	private void generateSpearModel(Item item, ItemModelGenerators itemModels) {
		String name = getItemId(item).getPath();
		BiConsumer<Identifier, ModelInstance> modelOutput = itemModels.modelOutput;
		var spearModel = generateToolModel(name, this.mcLocation("item/generated"), modelOutput);
		var spearInHandModel = generateToolModel(name + "_in_hand", this.mcLocation("item/spear_in_hand"), modelOutput);
		itemModels.itemModelOutput.accept(item,
				ItemModelGenerators.createFlatModelDispatch(spearModel, spearInHandModel),
				new ClientItem.Properties(true, false, 1.95F));
	}

	private ItemModel.Unbaked generateToolModel(String name, Identifier parent,
												BiConsumer<Identifier, ModelInstance> modelOutput) {
		// Generate handle and blade models of the tool
		var handleModel = ItemModelUtils.tintedModel(
				ExtendedModelTemplateBuilder.builder()
						.parent(parent)
						.requiredTextureSlot(TextureSlot.LAYER0)
						.build().create(getItemModelId(name + "_handle"), TextureMapping.layer0(
								getMaterial(name)), modelOutput),
				new CustomModelDataSource(0, LaserBladeColor.WHITE.handleColor()));
		var bladeModel = ItemModelUtils.tintedModel(
				ExtendedModelTemplateBuilder.builder()
						.parent(parent)
						.requiredTextureSlot(TextureSlot.LAYER0)
						.requiredTextureSlot(TextureSlot.LAYER1)
						.build().create(getItemModelId(name + "_blade"), TextureMapping.layered(
								getMaterial(name + "_blade_0"),
								getMaterial(name + "_blade_1")), modelOutput),
				new CustomModelDataSource(1, LaserBladeColor.RED.bladeColor()),
				new CustomModelDataSource(2, LaserBladeColor.WHITE.bladeColor()));
		return ItemModelUtils.composite(handleModel, bladeModel);
	}

	private Identifier getItemModelId(String name) {
		return this.modLocation("item/" + name);
	}

	private Material getMaterial(String name) {
		return new Material(getItemModelId(name));
	}

	private Identifier getItemId(Item item) {
		return BuiltInRegistries.ITEM.getKey(item);
	}

	private void addBlockItemModel(ItemModelOutput output, Item item, String suffix) {
		if (item instanceof BlockItem blockItem) {
			var model = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(blockItem.getBlock(), suffix));
			output.accept(item, model);
		}
	}
}
