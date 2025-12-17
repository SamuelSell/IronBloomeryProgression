package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RecipeDataGen extends RecipeProvider {
    protected RecipeDataGen(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, CustomBlockDefinitions.BLOOMERY_CONTROLLER)
                .pattern("BBB")
                .pattern("BCB")
                .pattern("SLS")
                .define('B', Items.BRICKS)
                .define('C', Items.CHARCOAL)
                .define('S', Tags.Items.COBBLESTONES)
                .define('L', ItemTags.LOGS_THAT_BURN)
                .unlockedBy("has_raw_iron", this.has(Items.RAW_IRON))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, CustomItemDefinitions.BLOOM_HAMMER)
                .pattern(" CS")
                .pattern(" SC")
                .pattern("S  ")
                .define('S', Items.STICK)
                .define('C', Items.COBBLESTONE)
                .unlockedBy("has_raw_iron", this.has(Items.RAW_IRON))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new RecipeDataGen(provider, output);
        }

        @Override
        public String getName() {
            return "RecipeProviderDataGen";
        }
    }
}
