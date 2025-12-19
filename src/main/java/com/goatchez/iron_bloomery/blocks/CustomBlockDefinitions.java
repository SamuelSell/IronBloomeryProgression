package com.goatchez.iron_bloomery.blocks;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.bloomery.BloomeryControllerBlock;
import com.goatchez.iron_bloomery.blocks.bloomery.BloomeryControllerBlockEntity;
import com.goatchez.iron_bloomery.blocks.bloomery.BloomeryBlockTopHalf;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class CustomBlockDefinitions {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IronBloomery.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronBloomery.MOD_ID);

    // BLOCKS ************************************************************

    public static final DeferredBlock<BloomeryControllerBlock> BLOOMERY_CONTROLLER = registerBlock(
            "bloomery_controller",
            properties -> new BloomeryControllerBlock(properties.strength(3.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).noOcclusion())
    );

    public static final DeferredBlock<Block> BLOOMERY_BLOCK_TOP_HALF = registerBlock(
            "bloomery_block_top_half",
            properties -> new BloomeryBlockTopHalf(properties.strength(3.0f).requiresCorrectToolForDrops().sound(SoundType.STONE).noLootTable())
    );

    // BLOCK ENTITIES *****************************************************

    public static final Supplier<BlockEntityType<BloomeryControllerBlockEntity>> BLOOMERY_CONTROLLER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "bloomery_controller_block_entity",
            () -> BlockEntityType.Builder.of(
                    BloomeryControllerBlockEntity::new, BLOOMERY_CONTROLLER.get()).build(null)
    );


    // HELPER FUNCTIONS ***************************************************

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> props) {
        DeferredBlock<T> registeredBlock = BLOCKS.registerBlock(name, props);
        registerBlockItem(name, registeredBlock);
        return registeredBlock;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        CustomItemDefinitions.ITEMS.registerItem(name, properties -> (new BlockItem(block.get(), new Item.Properties())));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }
}
