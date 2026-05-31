package com.github.iunius118.laserbladetools.client.gui;

import com.github.iunius118.laserbladetools.CommonClass;
import com.github.iunius118.laserbladetools.Constants;
import com.github.iunius118.laserbladetools.component.LBCustomModelData;
import com.github.iunius118.laserbladetools.component.ModDataComponents;
import com.github.iunius118.laserbladetools.item.LaserBladeColor;
import com.github.iunius118.laserbladetools.menu.ColorizerMenu;
import com.github.iunius118.laserbladetools.network.ColorSelectionPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ColorizerScreen extends AbstractContainerScreen<ColorizerMenu> implements ContainerListener {
	// GUI texture
	private static final ResourceLocation TEXTURE = CommonClass.modLocation("textures/gui/colorizer.png");
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
	private ArmorStand armorStandPreview;

	public ColorizerScreen(ColorizerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
        this.inventoryLabelY = this.imageHeight - 94;
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
			colorButtons[i] = CycleButton.builder(ColorizerScreen::getColorName)
                    .withInitialValue(this.menu.getColorIndex(part))
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

        // Init armor stand preview
        armorStandPreview = new ArmorStand(this.minecraft.level, 0.0, 0.0, 0.0);
        armorStandPreview.setNoBasePlate(true);
        armorStandPreview.setShowArms(true);
        armorStandPreview.yBodyRot = 210.0F;
        armorStandPreview.setXRot(25.0F);
        armorStandPreview.yHeadRot = this.armorStandPreview.getYRot();
        armorStandPreview.yHeadRotO = this.armorStandPreview.getYRot();
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float renderTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, renderTicks);
        super.render(guiGraphics, mouseX, mouseY, renderTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float renderTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        // Render background texture
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        // Render color previews
        renderColorPreviews(guiGraphics);
        // Render armor stand
        InventoryScreen.renderEntityInInventory(guiGraphics,
                this.leftPos + (ARMOR_STAND_LEFT + ARMOR_STAND_RIGHT) / 2F,
                this.topPos + (ARMOR_STAND_TOP + ARMOR_STAND_BOTTOM) / 2F,
                ARMOR_STAND_SCALE, ARMOR_STAND_TRANSLATION, ARMOR_STAND_ANGLE, null, armorStandPreview);
    }

	private void renderColorPreviews(GuiGraphics graphics) {
		ItemStack inputItem = menu.getSlot(ColorizerMenu.INPUT_SLOT).getItem();
        LBCustomModelData existingData = inputItem.isEmpty() ?
                null : inputItem.getOrDefault(ModDataComponents.LB_CUSTOM_MODEL_DATA, LBCustomModelData.EMPTY);
        List<Boolean> existingFlags = (existingData != null) ? existingData.flags() : List.of();
        List<Integer> existingColors = (existingData != null) ? existingData.colors() : List.of();

        if (existingFlags.isEmpty() && !existingColors.isEmpty()) {
            // Fix the data if it has colors but no flags (for backward compatibility)
            existingFlags = new ArrayList<>();

            for (Integer color : existingColors) {
                existingFlags.add(true);
            }
        }

		// RenderColorPreviews
		for (int i = 0; i < ColorizerMenu.NUM_PARTS; i++) {
			int colorIndex = colorButtons[i].getValue();
			int argb;

			if (colorIndex == 0) {
				// The existing color
				if (existingFlags.size() > i && existingFlags.get(i) && existingColors.size() > i) {
					argb = existingColors.get(i) | 0xFF000000;
				} else {
					continue;
				}
			} else {
				argb = LaserBladeColor.get(colorIndex - 1).partColor(i) | 0xFF000000;
			}

			int x = leftPos + COLOR_PREVIEW_X;
			int y = topPos + COLOR_BTN_Y_START + i * COLOR_BTN_Y_STEP + 1;
            graphics.fill(x - 1, y - 1, x + COLOR_PREVIEW_SIZE + 1, y + COLOR_PREVIEW_SIZE + 1,  0xFF000000);
			graphics.fill(x, y, x + COLOR_PREVIEW_SIZE, y + COLOR_PREVIEW_SIZE, argb);
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
        if (this.armorStandPreview == null) {
            return;
        }

		if (!itemStack.isEmpty()) {
            this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, itemStack.copy());
		} else {
            this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
	}
}
