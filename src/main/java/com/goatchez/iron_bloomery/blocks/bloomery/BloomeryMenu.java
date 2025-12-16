package com.goatchez.iron_bloomery.blocks.bloomery;

import com.goatchez.iron_bloomery.MenuTypes;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.SlotItemHandler;

public class BloomeryMenu extends AbstractContainerMenu {
    public final BloomeryControllerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public BloomeryMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, (BloomeryControllerBlockEntity) inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public BloomeryMenu(int containerId, Inventory inventory, BloomeryControllerBlockEntity blockEntity, ContainerData data) {
        super(MenuTypes.BLOOMERY_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.addSlot(new BloomeryIronInputSlot(blockEntity.itemStackHandler, 0, 42, 18));
        this.addSlot(new BloomeryFuelSlot(blockEntity.itemStackHandler, 1, 42, 53));
        this.addSlot(new BloomeryOutputSlot(blockEntity.itemStackHandler, 2, 110, 27));
        this.addSlot(new BloomeryOutputSlot(blockEntity.itemStackHandler, 3, 128, 27));
        this.addSlot(new BloomeryOutputSlot(blockEntity.itemStackHandler, 4, 110, 45));
        this.addSlot(new BloomeryOutputSlot(blockEntity.itemStackHandler, 5, 128, 45));

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public boolean isLit() {
        return data.get(2) > 0;
    }

    protected int getProgress() {
        return data.get(0);
    }

    public int getScaledSteamProgress() {
        int progress = getProgress();
        int maxProgress = data.get(1);

        int steamTextureWidth = 22;

        return maxProgress != 0 && progress != 0 ? (progress * steamTextureWidth) / 200 : 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);

        int arrowPixelSize = 33;

        return maxProgress != 0 && progress != 0 ? ((progress - 200) * arrowPixelSize) / 200 : 0;
    }

    public int getScaledLitProgress() {
        int litProgress = data.get(2);
        int maxLitProgress = data.get(3);

        int litTexHeight = 15;

//        return maxLitProgress != 0 && litProgress != 0 ? (litProgress  * litTexWidth) / 200 : 0;
        return maxLitProgress != 0 && litProgress != 0 ? (litProgress * litTexHeight) / maxLitProgress : 0;
//        return litTexHeight / maxLitProgress;
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, CustomBlockDefinitions.BLOOMERY_CONTROLLER.get());
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 6;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}
