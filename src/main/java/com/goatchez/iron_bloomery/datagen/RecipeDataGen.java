package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.ConditionalRecipeOutput;

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
                .define('S', Items.COBBLESTONE)
                // todo make this a tag to accept all overworld logs
                .define('L', Items.OAK_LOG)
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
