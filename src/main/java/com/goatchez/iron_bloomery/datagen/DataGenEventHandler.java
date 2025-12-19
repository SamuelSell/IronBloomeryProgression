package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = IronBloomery.MOD_ID)
public class DataGenEventHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        dataGenerator.addProvider(event.includeClient(), new BlockStateDataGen(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new ItemModelDataGen(packOutput, existingFileHelper));

        dataGenerator.addProvider(event.includeClient(), new SoundDefinitionDataGen(packOutput, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(), new TranslationDataGen(packOutput));

        BlockTagsProvider blockTagsProvider = new BlockTagsDataGen(packOutput, lookupProvider, existingFileHelper);
        dataGenerator.addProvider(event.includeServer(), blockTagsProvider);
        dataGenerator.addProvider(event.includeServer(), new ItemTagsDataGen(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        dataGenerator.addProvider(event.includeServer(), new RecipeDataGen(packOutput, lookupProvider));


        dataGenerator.addProvider(
                event.includeServer(),
                new LootTableProvider(packOutput,
                        Set.of(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(
                                        BlockLootTableDataGen::new,
                                        LootContextParamSets.BLOCK
                                )
                        ),
                        lookupProvider
                )
        );
    }
}
