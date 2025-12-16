package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class BlockTagsDataGen extends BlockTagsProvider {
    public BlockTagsDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, IronBloomery.MOD_ID);
    }

    public static final TagKey<Block> MINEABLE_PICKAXE = addToMinecraftTag("mineable/pickaxe");
    public static final TagKey<Block> MINING_LEVEL_STONE = addToMinecraftTag("needs_stone_tool");
    public static final TagKey<Block> MINING_LEVEL_IRON = addToMinecraftTag("needs_iron_tool");

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(MINEABLE_PICKAXE)
                .add(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get())
                .replace(false);

        this.tag(MINING_LEVEL_STONE)
                .add(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get())
                .replace(false);
    }

    private static TagKey<Block> createCustomTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, name));
    }

    private static TagKey<Block> addToMinecraftTag(String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }

}
