package com.github.iunius118.laserbladetools.data;

import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Constants.MOD_ID, existingFileHelper);
	}

    @Override
    protected void registerModels() {
		this.basicItem(ModItems.LB_CORE);
		generateToolModel(ModItems.LB_SWORD);
		generateToolModel(ModItems.LB_SHOVEL);
		generateToolModel(ModItems.LB_PICKAXE);
		generateToolModel(ModItems.LB_AXE);
		generateToolModel(ModItems.LB_HOE);
        this.simpleBlockItem(ModBlocks.COLORIZER);
	}

	private void generateToolModel(Item item) {
		this.getBuilder(getItemId(item).getPath())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", "item/" + getItemId(item).getPath())
                .texture("layer1", "item/" + getItemId(item).getPath() + "_blade_0")
                .texture("layer2", "item/" + getItemId(item).getPath() + "_blade_1");
	}

	private ResourceLocation getItemId(Item item) {
		return BuiltInRegistries.ITEM.getKey(item);
	}
}
