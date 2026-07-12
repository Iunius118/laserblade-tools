package com.github.iunius118.laserbladetools.menu;

import com.github.iunius118.laserbladetools.block.ModBlocks;
import com.github.iunius118.laserbladetools.component.LBCustomModelData;
import com.github.iunius118.laserbladetools.component.ModDataComponents;
import com.github.iunius118.laserbladetools.item.LaserBladeColor;
import com.github.iunius118.laserbladetools.tags.ModItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColorizerMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int PLAYER_INV_START = 2;
    public static final int PLAYER_INV_END = 38;
    public static final int NUM_PARTS = 3;
    // Player inventory area
    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = 134;

    // Coordinates of each UI element (offset from top left of GUI)
    private static final int INPUT_SLOT_X = 33;
    private static final int INPUT_SLOT_Y = 26;
    private static final int OUTPUT_SLOT_X = 87;
    private static final int OUTPUT_SLOT_Y = 26;

    private final ContainerLevelAccess access;
    private final SimpleContainer inputContainer;
    private final ResultContainer resultContainer;
    private final DataSlot[] colorIndexSlots = new DataSlot[NUM_PARTS];

    // Client-side constructor
    public ColorizerMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    // Server-side constructor
    public ColorizerMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.COLORIZER, containerId);
        this.access = access;
        this.inputContainer = new SimpleContainer(1) {
            @Override
            public void setChanged() {
                super.setChanged();
                slotsChanged(this);
            }
        };
        this.resultContainer = new ResultContainer();

        // Input slot
        this.addSlot(new Slot(inputContainer, 0, INPUT_SLOT_X, INPUT_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack itemStack) {
                // Accept only laser blade tools
                return itemStack.is(ModItemTags.COLORIZER_CAN_CHANGE_COLOR);
            }
        });

        // Output slot
        this.addSlot(new Slot(resultContainer, 0, OUTPUT_SLOT_X, OUTPUT_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                // Consume one input item
                inputContainer.removeItem(0, 1);
                // Update result slot
                updateResult();
                super.onTake(player, stack);
            }
        });

        // Player inventory extended slots
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(playerInventory, x + (y + 1) * 9, PLAYER_INV_X + x * 18, PLAYER_INV_Y + y * 18));
            }
        }

        // Player inventory hotbar slots
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, PLAYER_INV_X + x * 18, PLAYER_INV_Y + 58));
        }

        // Data slots
        for (int i = 0; i < NUM_PARTS; i++) {
            colorIndexSlots[i] = DataSlot.standalone();
            addDataSlot(colorIndexSlots[i]);
        }
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        updateResult();
    }

    private void updateResult() {
        ItemStack input = inputContainer.getItem(0);
        ItemStack result = resultContainer.getItem(0);

        if (input.isEmpty()) {
            resultContainer.setItem(0, ItemStack.EMPTY);
        } else {
            resultContainer.setItem(0, applyColors(input));
        }

        // Compare the instances, as the result slot will contain a different instance if it has been modified
        if (result != resultContainer.getItem(0)) {
            // If the result slot has been modified, notify the client to update it
            this.broadcastChanges();
        }
    }

    public ItemStack applyColors(ItemStack input) {
        boolean hasChanged = false;
        LBCustomModelData existing =
                input.getOrDefault(ModDataComponents.LB_CUSTOM_MODEL_DATA, LBCustomModelData.EMPTY);

        if (existing.flags().isEmpty() && !existing.colors().isEmpty()) {
            // Fix the data if it has colors but no flags (for backward compatibility)
            List<Boolean> fixedFlags = new ArrayList<>();

            for (Integer color : existing.colors()) {
                fixedFlags.add(true);
            }

            existing =  new LBCustomModelData(existing.floats(), fixedFlags, existing.strings(), existing.colors());
            hasChanged = true;
        }

        List<Integer> newColors = new ArrayList<>();
        List<Boolean> newFlags = new ArrayList<>();

        for (int i = 0; i < NUM_PARTS; i++) {
            int colorIndex = colorIndexSlots[i].get();

            if (colorIndex == 0) {
                // If "Uncolored" is selected,
                Integer existingColor = existing.getColor(i);

                if (Objects.requireNonNullElse(existing.getBoolean(i), false) && existingColor != null) {
                    // Preserve the existing color if present
                    newColors.add(existingColor);
                    newFlags.add(true);
                } else {
                    // There is no color to apply
                    newColors.add(0);
                    newFlags.add(false);
                }
            } else {
                // If any color is selected,
                // Force colors to be opaque
                int newColor = LaserBladeColor.get(colorIndex - 1).partColor(i) | 0xFF000000;
                Integer oldColor = existing.getColor(i);
                newColors.add(newColor);
                newFlags.add(true);

                if (Objects.requireNonNullElse(existing.getBoolean(i), false) && oldColor != null) {
                    if (oldColor != newColor) {
                        // If the selected color is different from the existing color,
                        // Update the part color with the selected color
                        hasChanged = true;
                    }
                } else {
                    // If no existing color is present,
                    // Apply a new color
                    hasChanged = true;
                }
            }
        }

        if (hasChanged) {
            ItemStack output = input.copy();
            output.set(ModDataComponents.LB_CUSTOM_MODEL_DATA,
                    new LBCustomModelData(existing.floats(), newFlags, existing.strings(), newColors));
            return output;
        }

        // There is no color to apply
        return ItemStack.EMPTY;
    }

    public void setColorIndex(int part, int colorIndex) {
        if (part < 0 || part >= NUM_PARTS) return;
        colorIndexSlots[part].set(colorIndex);
        updateResult();
    }

    public int getColorIndex(int part) {
        if (part < 0 || part >= NUM_PARTS) return 0;
        return colorIndexSlots[part].get();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stackCopy = slot.getItem().copy();
        ItemStack stack = slot.getItem();

        if (slotIndex == OUTPUT_SLOT) {
            // Output slot -> player inventory
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true)) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        } else if (slotIndex == INPUT_SLOT) {
            // Input slot -> player inventory
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false)) {
                return ItemStack.EMPTY;
            }
        } else if (slotIndex >= PLAYER_INV_START && slotIndex < PLAYER_INV_END) {
            // Player inventory -> input slot (only when the tags match)
            if (slots.get(INPUT_SLOT).mayPlace(stack)) {
                if (!moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == stackCopy.getCount()) {
            return ItemStack.EMPTY;
        }

        return stackCopy;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.COLORIZER);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        // Return the item in the input slot to the player when the GUI is closed
        this.access.execute((level, pos) -> this.clearContainer(player, this.inputContainer));
    }
}
