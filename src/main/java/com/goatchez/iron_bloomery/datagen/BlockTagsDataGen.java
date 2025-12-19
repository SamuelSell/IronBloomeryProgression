package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagsDataGen extends BlockTagsProvider {
    public static final TagKey<Block> MINEABLE_PICKAXE = addToMinecraftTag("mineable/pickaxe");
    public static final TagKey<Block> MINING_LEVEL_STONE = addToMinecraftTag("needs_stone_tool");
    public static final TagKey<Block> DIRECTIONAL_LOGS_OVERWORLD = createCustomTag("directional_logs_overworld");

    public BlockTagsDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IronBloomery.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(MINEABLE_PICKAXE)
                .add(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get())
                .add(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get())
                .replace(false);

        this.tag(MINING_LEVEL_STONE)
                .add(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get())
                .add(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get())
                .replace(false);

        this.tag(DIRECTIONAL_LOGS_OVERWORLD)
                .replace(false)
                .add(Blocks.OAK_LOG)
                .add(Blocks.STRIPPED_OAK_LOG)
                .add(Blocks.SPRUCE_LOG)
                .add(Blocks.STRIPPED_SPRUCE_LOG)
                .add(Blocks.BIRCH_LOG)
                .add(Blocks.STRIPPED_BIRCH_LOG)
                .add(Blocks.JUNGLE_LOG)
                .add(Blocks.STRIPPED_JUNGLE_LOG)
                .add(Blocks.ACACIA_LOG)
                .add(Blocks.STRIPPED_ACACIA_LOG)
                .add(Blocks.DARK_OAK_LOG)
                .add(Blocks.STRIPPED_DARK_OAK_LOG)
                .add(Blocks.MANGROVE_LOG)
                .add(Blocks.STRIPPED_MANGROVE_LOG)
                .add(Blocks.CHERRY_LOG)
                .add(Blocks.STRIPPED_CHERRY_LOG);
    }

    private static TagKey<Block> createCustomTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, name));
    }

    private static TagKey<Block> addToMinecraftTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

}
