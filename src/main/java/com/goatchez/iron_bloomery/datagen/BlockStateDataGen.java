package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateDataGen extends BlockStateProvider {
    public BlockStateDataGen(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, IronBloomery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModelFile bloomerControllerModelFile = models().getExistingFile(modLoc("block/bloomery_controller"));

        horizontalBlock(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get(), bloomerControllerModelFile);

        simpleBlockWithItem(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get(), cubeAll(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get()));
        simpleBlockItem(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get(), new ModelFile.ExistingModelFile(modLoc("block/bloomery_controller"),this.models().existingFileHelper));
    }
}
