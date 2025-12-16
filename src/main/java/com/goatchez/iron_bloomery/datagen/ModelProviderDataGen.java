package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Damage;
import net.minecraft.data.PackOutput;

public class ModelProviderDataGen extends ModelProvider {
    public ModelProviderDataGen(PackOutput output) {
        super(output, IronBloomery.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModels.createNonTemplateHorizontalBlock(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get());
        blockModels.createTrivialCube(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get());

        itemModels.generateFlatItem(CustomItemDefinitions.BLOOM_HAMMER.get(), ModelTemplates.FLAT_HANDHELD_ITEM);


//        itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_0", ModelTemplates.FLAT_ITEM)

        ItemModel.Unbaked ironBloom0 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_0", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked ironBloom1 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_1", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked ironBloom2 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_2", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked ironBloom3 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_3", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked ironBloom4 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_4", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked ironBloom5 = ItemModelUtils.plainModel(itemModels.createFlatItemModel(CustomItemDefinitions.IRON_BLOOM.get(), "_5", ModelTemplates.FLAT_ITEM));

        itemModels.itemModelOutput.accept(
                CustomItemDefinitions.IRON_BLOOM.get(),
                ItemModelUtils.rangeSelect(
                        new Damage(true),
                        1,
                        ironBloom0,
                        ItemModelUtils.override(ironBloom1, 0.16f),
                        ItemModelUtils.override(ironBloom2, 0.33f),
                        ItemModelUtils.override(ironBloom3, 0.50f),
                        ItemModelUtils.override(ironBloom4, 0.66f),
                        ItemModelUtils.override(ironBloom5, 0.83f)
                )
        );

//        itemModels.itemModelOutput.accept(
//                CustomItemDefinitions.IRON_BLOOM.get(),
//                new RangeSelectItemModel.Unbaked(
//                        new Damage(true),
//
//                        1,
//
//                        List.of(
//                                new RangeSelectItemModel.Entry(
//                                        1.0f,
//                                        new BlockModelWrapper.Unbaked(
//                                                ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "item/iron_bloom_0"),
//                                                Collections.emptyList()
//                                        )
//                                ),
//                                new RangeSelectItemModel.Entry(
//                                        0.6f,
//                                        new BlockModelWrapper.Unbaked(
//                                                ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "item/iron_bloom_1"),
//                                                Collections.emptyList()
//                                        )
//                                ),
//                                new RangeSelectItemModel.Entry(
//                                        0.3f,
//                                        new BlockModelWrapper.Unbaked(
//                                                ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "item/iron_bloom_2"),
//                                                Collections.emptyList()
//                                        )
//                                )
//                        ),
//                        Optional.of(
//                                new BlockModelWrapper.Unbaked(
//                                        ModelLocationUtils.getModelLocation(CustomItemDefinitions.IRON_BLOOM.get()),
////                                        ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, "item/iron_bloom/iron_bloom_0"),
//                                        Collections.emptyList()
//                                )
//                        )
//
//                )
//        );
    }
}
