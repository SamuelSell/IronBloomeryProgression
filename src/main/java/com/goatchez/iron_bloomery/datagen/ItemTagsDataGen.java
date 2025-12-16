package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ItemTagsDataGen extends ItemTagsProvider {
    public ItemTagsDataGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, IronBloomery.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }

    private static TagKey<Item> createCustomTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, name));
    }
}
