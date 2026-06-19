package com.github.iunius118.laserbladetools.item;

import com.github.iunius118.laserbladetools.tags.ModBlockTags;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.world.item.ToolMaterial;

public class ModToolMaterials {
	public static final ToolMaterial LASER_BLADE = new ToolMaterial(
			ModBlockTags.INCORRECT_FOR_LASER_BLADE_TOOL,
			ToolMaterial.DIAMOND.durability() + ToolMaterial.IRON.durability() * 3,
			ToolMaterial.DIAMOND.speed(),
			ToolMaterial.DIAMOND.attackDamageBonus(),
			ToolMaterial.IRON.enchantmentValue(),
			ModItemTags.LASER_BLADE_TOOL_MATERIALS);
}
