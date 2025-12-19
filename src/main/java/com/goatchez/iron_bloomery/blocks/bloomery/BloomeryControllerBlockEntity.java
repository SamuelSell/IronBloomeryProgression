package com.goatchez.iron_bloomery.blocks.bloomery;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BloomeryControllerBlockEntity extends BlockEntity implements MenuProvider {

    private static final Component DEFAULT_NAME = Component.translatable("container.iron_bloomery.bloomery");

    private static final int ORE_INPUT = 0;
    private static final int CHARCOAL_INPUT = 1;
    private static final int BLOOM_OUTPUT = 2;

    private static final int[] BLOOM_OUTPUT_SLOTS = {2, 3, 4, 5};

//    private static List<Integer> AvailableOutputSlots =

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 400;
    private int litProgress = 0;
    private int maxLitProgress = 200;

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(6) {
//        @Override
//        protected int getStackLimit(int slot, ItemStack stack) {
//            return 1;
//        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inv.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemStackHandler.serializeNBT(registries));
        tag.putInt("bloomery_entity.progress", progress);
        tag.putInt("bloomery_entity.maxProgress", maxProgress);
        tag.putInt("bloomery_entity.litProgress", litProgress);
        tag.putInt("bloomery_entity.maxLitProgress", maxLitProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemStackHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("bloomery_entity.progress");
        maxProgress = tag.getInt("bloomery_entity.maxProgress");
        litProgress = tag.getInt("bloomery_entity.litProgress");
        maxLitProgress = tag.getInt("bloomery_entity.maxLitProgress");
    }

    public BloomeryControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(CustomBlockDefinitions.BLOOMERY_CONTROLLER_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BloomeryControllerBlockEntity.this.progress;
                    case 1 -> BloomeryControllerBlockEntity.this.maxProgress;
                    case 2 -> BloomeryControllerBlockEntity.this.litProgress;
                    case 3 -> BloomeryControllerBlockEntity.this.maxLitProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        BloomeryControllerBlockEntity.this.progress = value;
                    case 1:
                        BloomeryControllerBlockEntity.this.maxProgress = value;
                    case 2:
                        BloomeryControllerBlockEntity.this.litProgress = value;
                    case 3:
                        BloomeryControllerBlockEntity.this.maxLitProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public Direction getFacing() {
        return getBlockState().getValue(BloomeryControllerBlock.FACING);
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BloomeryMenu(containerId, playerInventory, this, this.data);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    private List<Integer> getEmptyOutputSlots() {
        List<Integer> empty = new ArrayList<>();

        for (int slot : BLOOM_OUTPUT_SLOTS) {
            if (itemStackHandler.getStackInSlot(slot).isEmpty()) {
                empty.add(slot);
            }
        }
        return empty;
    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (hasRecipe()) {

            ((ServerLevel) level).sendParticles(
                    ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    blockPos.getX() + 0.5,
                    blockPos.getY() + 1.75,
                    blockPos.getZ() + 0.5,
                    1,          // count
                    0.00, 0.1, 0.0,  // spread
                    0.025        // speed
            );

//            ((ServerLevel) level).sendParticles(
//                    ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
//                    blockPos.getX() + 0.5f,
//                    blockPos.getY() + 1.5f,
//                    blockPos.getZ() + 0.5f,
//                    5,
//                    0,level.getRandom().nextInt(0, 5),0, 0.05f
//            );

            if (level.getRandom().nextFloat() < 0.10f) {
                level.playSound(null,
                        blockPos.getX(),
                        blockPos.getY() + 0.5,
                        blockPos.getZ(),
                        SoundEvents.FIRE_AMBIENT,
                        SoundSource.BLOCKS,
                        .8f,
                        0.8f + level.getRandom().nextFloat() * 0.2f
                );
            }

            if (level.getRandom().nextFloat() < 0.01f) {
                level.playSound(null,
                        blockPos.getX(),
                        blockPos.getY() + 0.5,
                        blockPos.getZ(),
                        SoundEvents.LAVA_POP,
                        SoundSource.BLOCKS,
                        .8f,
                        0.8f + level.getRandom().nextFloat() * 0.2f
                );
            }

            // intake charcoal and change lit progress
            if (litProgress <= 0) {
                removeACharcoal();
                level.playSound(null, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS);
                litProgress = maxLitProgress;
            }

            increaseProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()) {
                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS);
                craftItem((ServerLevel) level);
                resetProgress();
            }
        } else {
            undoProgress();
        }

    }

    private void undoProgress() {
        if (this.progress > 0) {
            --this.progress;
            --this.progress;
        }
        if (this.litProgress > 0) {
            --this.litProgress;
            --this.litProgress;
        }
    }


    private void craftItem(ServerLevel level) {
        itemStackHandler.extractItem(ORE_INPUT, 1, false);

        ItemStack output = new ItemStack(CustomItemDefinitions.IRON_BLOOM.get(), 1);
        int bloomCount = level.getRandom().nextBoolean() ? 1 : 2;

        List<Integer> emptySlots = getEmptyOutputSlots();
        bloomCount = Math.min(bloomCount, emptySlots.size());

        for (int i = 0; i < bloomCount; i++) {
            int slot = emptySlots.get(i);
            itemStackHandler.setStackInSlot(slot, new ItemStack(output.getItem(), 1));
        }


//        itemStackHandler.extractItem(CHARCOAL_INPUT, 2, false);
//        itemStackHandler.setStackInSlot(BLOOM_OUTPUT, new ItemStack(output.getItem(), itemStackHandler.getStackInSlot(BLOOM_OUTPUT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 400;
    }


    private void increaseProgress() {
        ++this.progress;
        --this.litProgress;
    }

    private void removeACharcoal() {
        itemStackHandler.extractItem(CHARCOAL_INPUT, 1, false);
    }

    private boolean hasRecipe() {
        ItemStack output = new ItemStack(CustomItemDefinitions.IRON_BLOOM.get());

        return itemStackHandler.getStackInSlot(ORE_INPUT).is(Items.RAW_IRON)
//                && canInsertAmountIntoOutput(output.getCount())
                && canInsertItemTypeIntoOutput(output)
                && ((itemStackHandler.getStackInSlot(CHARCOAL_INPUT).is(Items.CHARCOAL)
                && itemStackHandler.getStackInSlot(CHARCOAL_INPUT).getCount() > 0) || litProgress > 0);
    }

    private boolean canInsertAmountIntoOutput(int count) {
        int maxCount = itemStackHandler.getStackInSlot(BLOOM_OUTPUT).isEmpty() ? 64 : itemStackHandler.getStackInSlot(BLOOM_OUTPUT).getMaxStackSize();
        int currentCount = itemStackHandler.getStackInSlot(BLOOM_OUTPUT).getCount();

        return maxCount >= currentCount + count;
    }

    private boolean canInsertItemTypeIntoOutput(ItemStack output) {
        return !getEmptyOutputSlots().isEmpty();
    }
}
