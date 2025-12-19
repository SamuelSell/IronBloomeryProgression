package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RecipeDataGen extends RecipeProvider {
    protected RecipeDataGen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CustomBlockDefinitions.BLOOMERY_CONTROLLER)
                .pattern("BBB")
                .pattern("BCB")
                .pattern("SLS")
                .define('B', Items.BRICKS)
                .define('C', Items.CHARCOAL)
                .define('S', Tags.Items.COBBLESTONES)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CustomItemDefinitions.BLOOM_HAMMER)
                .pattern(" CS")
                .pattern(" SC")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', Tags.Items.COBBLESTONES)
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeOutput);
    }
}
