package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = IronBloomery.MOD_ID)
public class DataGenEventHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(TranslationDataGen::new);
        event.createProvider(ModelProviderDataGen::new);
        event.createProvider(SoundDefinitionDataGen::new);
        event.createProvider(ItemTagsDataGen::new);
        event.createProvider(BlockTagsDataGen::new);
        event.createProvider(RecipeDataGen.Runner::new);
        event.createProvider(((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(
                                BlockLootTableDataGen::new,
                                LootContextParamSets.BLOCK
                        )
                ),
                lookupProvider
        )));
    }
}
