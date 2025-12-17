package com.goatchez.iron_bloomery.blocks.bloomery;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BloomeryBlockTopHalf extends Block {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 11, 16);


    public BloomeryBlockTopHalf(Properties properties) {
        super(properties);

        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, Half.TOP));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(HALF) == Half.TOP) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);

            if (belowState.is(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get())) {
                boolean drop = !player.isCreative() && player.getMainHandItem().isCorrectToolForDrops(belowState);
                level.destroyBlock(belowPos, drop);
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }



    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(HALF) == Half.TOP) {
            pos = pos.below();
//            state = level.getBlockState(pos);
        }

        if (level.getBlockEntity(pos) instanceof BloomeryControllerBlockEntity controllerBlockEntity) {
            if (!level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(controllerBlockEntity, Component.translatable("container.iron_bloomery.bloomery")), pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }
}
