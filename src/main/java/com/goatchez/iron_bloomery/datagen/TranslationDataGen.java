package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class TranslationDataGen extends LanguageProvider {
    public TranslationDataGen(PackOutput output) {
        super(output, IronBloomery.MOD_ID, IronBloomery.LANG);
    }

    @Override
    protected void addTranslations() {
        this.add(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get(), "Bloomery");
        this.add(CustomBlockDefinitions.BLOOMERY_BLOCK_TOP_HALF.get(), "Bloomery Sub Block");
        this.add("creative_tab.iron_bloomery_mod", "Iron Bloomery Progression");
        this.add(CustomItemDefinitions.BLOOM_HAMMER.get(), "Bloom Hammer");
        this.add(CustomItemDefinitions.IRON_BLOOM.get(), "Iron Bloom");
        this.add("item.iron_bloomery.iron_bloom.tooltip", "Use with §oBloom Hammer§r");
        this.add("item.iron_bloomery.bloom_hammer.tooltip", "Converts Blooms into Iron Nuggets");
        this.add("sound.iron_bloomery.bloom_hammer_use", "Forging Iron Bloom");
        this.add("container.iron_bloomery.bloomery", "Bloomery");
//        this.add("sound.iron_bloomery.bloom_hammer_use_strong", "Forging Iron Bloom");
//        this.add("sound.iron_bloomery.bloom_hammer_use_finished", "Forging Iron Bloom");
    }
}
