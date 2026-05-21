package com.github.iunius118.laserbladetools.client.gui;

import com.github.iunius118.laserbladetools.CommonClass;
import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.item.LaserBladeColor;
import com.github.iunius118.laserbladetools.menu.ColorizerMenu;
import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class ColorizerScreen extends AbstractContainerScreen<ColorizerMenu> implements ContainerListener {
	// GUI texture
	private static final Identifier TEXTURE = CommonClass.modLocation("textures/gui/colorizer.png");
	// GUI size (match texture)
	private static final int GUI_WIDTH = 176;
	private static final int GUI_HEIGHT = 216;
	// color selection buttons
	private static final int COLOR_BTN_X = 8;
	private static final int COLOR_BTN_Y_START = 54;
	private static final int COLOR_BTN_Y_STEP = 22;
	private static final int COLOR_BTN_WIDTH = GUI_WIDTH - 36;
	private static final int COLOR_BTN_HEIGHT = 18;
	// Color swatch panel (to the right of the button)
	private static final int COLOR_PREVIEW_X = COLOR_BTN_X + COLOR_BTN_WIDTH + 4;
	private static final int COLOR_PREVIEW_SIZE = 16;
	// Armor stand preview area (GUI right side)
	private static final Vector3f ARMOR_STAND_TRANSLATION = new Vector3f(0.0F, 1.0F, 0.0F);
	private static final Quaternionf ARMOR_STAND_ANGLE =
			new Quaternionf().rotationXYZ(0.43633232F, 0.0F, (float) Math.PI);
	private static final int ARMOR_STAND_SCALE = 25;
	private static final int ARMOR_STAND_LEFT = 118;
	private static final int ARMOR_STAND_TOP = 0;
	private static final int ARMOR_STAND_RIGHT = 168;
	private static final int ARMOR_STAND_BOTTOM = 50;

	/**
	 * Color selection button for each part
	 */
	@SuppressWarnings("unchecked")
	private final CycleButton<Integer>[] colorButtons = new CycleButton[ColorizerMenu.NUM_PARTS];
	/**
	 * Armor stand preview entity (client only)
	 */
	private final ArmorStandRenderState armorStandPreview = new ArmorStandRenderState();

	public ColorizerScreen(ColorizerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, GUI_WIDTH, GUI_HEIGHT);

		// Init armor stand preview
		armorStandPreview.entityType = EntityType.ARMOR_STAND;
		armorStandPreview.showBasePlate = false;
		armorStandPreview.showArms = true;
		armorStandPreview.xRot = 25.0F;
		armorStandPreview.bodyRot = 210.0F;
	}

	@Override
	protected void init() {
		super.init();
		this.menu.addSlotListener(this);

		for (int i = 0; i < ColorizerMenu.NUM_PARTS; i++) {
			final int part = i;
			Component name = switch(part) {
				case 0 -> Component.translatable(Constants.Colorizer.PART_HANDLE);
				case 1 -> Component.translatable(Constants.Colorizer.PART_OUTER_BLADE);
				case 2 -> Component.translatable(Constants.Colorizer.PART_INNER_BLADE);
				default -> Component.empty();
			};
			colorButtons[i] = CycleButton.builder(ColorizerScreen::getColorName,
							this.menu.getColorIndex(part))
					.withValues(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)
					.create(
							leftPos + COLOR_BTN_X,
							topPos + COLOR_BTN_Y_START + part* COLOR_BTN_Y_STEP,
							COLOR_BTN_WIDTH,
							COLOR_BTN_HEIGHT,
							name,
							(button, value) -> {
								this.menu.setColorIndex(part, value);
								ColorSelectionPayload.send(part, value);
							});
			addRenderableWidget(colorButtons[part]);
		}

		updateArmorStandPreview(this.menu.getSlot(ColorizerMenu.OUTPUT_SLOT).getItem());
	}

	private static Component getColorName(int colorIndex) {
		if (colorIndex == 0) {
			return Component.translatable(Constants.Colorizer.COLOR_UNCOLORED);
		}

		DyeColor dyeColor = DyeColor.values()[colorIndex - 1];
		return Component.translatable("color.minecraft." + dyeColor.getName());
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float renderTicks) {
		super.extractBackground(graphics, mouseX, mouseY, renderTicks);
		int x = (this.width - this.imageWidth) / 2;
		int y = (this.height - this.imageHeight) / 2;
		// Render background texture
		graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0F, 0F, this.imageWidth, this.imageHeight, 256, 256);
		// Render color previews
		renderColorPreviews(graphics);
		// Render armor stand
		graphics.entity(armorStandPreview, ARMOR_STAND_SCALE, ARMOR_STAND_TRANSLATION, ARMOR_STAND_ANGLE, null,
				this.leftPos + ARMOR_STAND_LEFT, this.topPos + ARMOR_STAND_TOP,
				this.leftPos + ARMOR_STAND_RIGHT, this.topPos + ARMOR_STAND_BOTTOM);
	}

	private void renderColorPreviews(GuiGraphicsExtractor graphics) {
		ItemStack inputItem = menu.getSlot(ColorizerMenu.INPUT_SLOT).getItem();
		CustomModelData existingData = inputItem.isEmpty()
				? null
				: inputItem.get(DataComponents.CUSTOM_MODEL_DATA);
		List<Integer> existingColors = (existingData != null) ? existingData.colors() : List.of();

		// RenderColorPreviews
		for (int i = 0; i < ColorizerMenu.NUM_PARTS; i++) {
			int colorIndex = colorButtons[i].getValue();
			int argb;

			if (colorIndex == 0) {
				// The existing color
				if (i < existingColors.size()) {
					argb = existingColors.get(i) | 0xFF000000;
				} else {
					continue;
				}
			} else {
				argb = LaserBladeColor.get(colorIndex - 1).partColor(i) | 0xFF000000;
			}

			int x = leftPos + COLOR_PREVIEW_X;
			int y = topPos + COLOR_BTN_Y_START + i * COLOR_BTN_Y_STEP + 1;
			graphics.fill(x, y, x + COLOR_PREVIEW_SIZE, y + COLOR_PREVIEW_SIZE, argb);
			graphics.outline(x, y, COLOR_PREVIEW_SIZE, COLOR_PREVIEW_SIZE, 0xFF000000);
		}
	}

	@Override
	public void dataChanged(AbstractContainerMenu container, int id, int value) {
	}

	@Override
	public void slotChanged(AbstractContainerMenu container, int slotIndex, ItemStack itemStack) {
		if (slotIndex == ColorizerMenu.OUTPUT_SLOT) {
			this.updateArmorStandPreview(itemStack);
		}
	}

	private void updateArmorStandPreview(ItemStack itemStack) {
		armorStandPreview.leftHandItemStack = ItemStack.EMPTY;
		armorStandPreview.leftHandItemState.clear();

		if (!itemStack.isEmpty()) {
			var itemModelResolver = this.minecraft.getItemModelResolver();
			armorStandPreview.leftHandItemStack = itemStack.copy();
			itemModelResolver.updateForTopItem(armorStandPreview.leftHandItemState, itemStack,
					ItemDisplayContext.THIRD_PERSON_LEFT_HAND, null, null, 0);
		}
	}
}
