package com.goatchez.iron_bloomery.items.custom;

import com.goatchez.iron_bloomery.sounds.CustomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.function.Consumer;

public class BloomHammer extends Item {
    public BloomHammer(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if (player == null) {
            return InteractionResult.FAIL;
        }

        boolean hammerInOffhand = player.getOffhandItem().getItem() instanceof BloomHammer;
        boolean ironBloomInMainHand = player.getMainHandItem().getItem() instanceof IronBloom;
        boolean logBlock = clickedBlock.defaultBlockState().is(BlockTags.LOGS);

        if (player.getCooldowns().isOnCooldown(player.getOffhandItem())) {
            return InteractionResult.FAIL;
        }

        if (hammerInOffhand && ironBloomInMainHand && logBlock) {
            // Get the log's axis (X, Y, Z)
            Direction.Axis axis = level.getBlockState(context.getClickedPos()).getValue(RotatedPillarBlock.AXIS);
            // Check if face matches vertical end-cap
            boolean isLogEnd = (axis == Direction.Axis.Y && (context.getClickedFace() == Direction.UP));
            if (isLogEnd) {
                if (!level.isClientSide()) {
                    player.getCooldowns().addCooldown(player.getOffhandItem(), 10);
                    player.getOffhandItem().hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) player, item -> player.onEquippedItemBroken(item, EquipmentSlot.OFFHAND));

                    // chance to forge iron bloom
                    if (level.getRandom().nextFloat() < 0.65f) {
                        level.playSound(null, context.getClickedPos(), CustomSounds.BLOOM_HAMMER_USE_STRONG.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                        player.getMainHandItem().hurtAndBreak(1, (ServerLevel) level, (ServerPlayer) player, item -> {
                            level.playSound(null, context.getClickedPos(), CustomSounds.BLOOM_HAMMER_USE_FINISHED.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                        });
                    } else {
                        level.playSound(null, context.getClickedPos(), CustomSounds.BLOOM_HAMMER_USE_WEAK.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                    }

                    if (player.getMainHandItem().isEmpty()) {
                        ItemStack ironNugget = new ItemStack(Items.IRON_NUGGET, level.getRandom().nextInt(1, 6));
                        player.setItemInHand(InteractionHand.MAIN_HAND, ironNugget);
                        ((ServerLevel) level).sendParticles(ParticleTypes.LAVA,
                                context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.0f, context.getClickedPos().getZ() + 0.5f, 10, 0, 0, 0, 2);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable("item.iron_bloomery.bloom_hammer.tooltip"));
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }

}
