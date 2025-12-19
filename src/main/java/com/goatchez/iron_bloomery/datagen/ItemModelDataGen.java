package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, IronBloomery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(CustomItemDefinitions.BLOOM_HAMMER.getId().getPath(), ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID,"item/" + CustomItemDefinitions.BLOOM_HAMMER.getId().getPath()));



        // todo add iron bloom data gen

    }
}
