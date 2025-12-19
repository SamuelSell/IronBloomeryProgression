package com.goatchez.iron_bloomery.datagen;

import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.sounds.CustomSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitionDataGen extends SoundDefinitionsProvider {
    public SoundDefinitionDataGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IronBloomery.MOD_ID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        add(CustomSounds.BLOOM_HAMMER_USE_STRONG.get(), SoundDefinition.definition()
                .with(
                        sound("iron_bloomery:bing1", SoundDefinition.SoundType.SOUND)
                                .volume(0.6f)
                                .pitch(1.0f)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false)
                )
                .subtitle("sound.iron_bloomery.bloom_hammer_use")
                .replace(false)
        );
        add(CustomSounds.BLOOM_HAMMER_USE_WEAK.get(), SoundDefinition.definition()
                .with(
                        sound("iron_bloomery:hit1", SoundDefinition.SoundType.SOUND)
                                .volume(0.4f)
                                .pitch(1.0f)
                                .weight(4)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("iron_bloomery:hit2", SoundDefinition.SoundType.SOUND)
                                .volume(0.4f)
                                .pitch(1.0f)
                                .weight(6)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false)
                )
                .subtitle("sound.iron_bloomery.bloom_hammer_use")
                .replace(false)
        );
        add(CustomSounds.BLOOM_HAMMER_USE_FINISHED.get(), SoundDefinition.definition()
                .with(
                        sound("iron_bloomery:finished", SoundDefinition.SoundType.SOUND)
                                .volume(1.0f)
                                .pitch(1.0f)
                                .attenuationDistance(8)
                )
                .subtitle("sound.iron_bloomery.bloom_hammer_use")
                .replace(false)
        );
    }
}
