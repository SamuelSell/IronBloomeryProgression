package com.goatchez.iron_bloomery.sounds;

import com.goatchez.iron_bloomery.IronBloomery;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CustomSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IronBloomery.MOD_ID);


    public static final Supplier<SoundEvent> BLOOM_HAMMER_USE_STRONG= registerSoundEvent("bloom_hammer_use_strong");
    public static final Supplier<SoundEvent> BLOOM_HAMMER_USE_WEAK = registerSoundEvent("bloom_hammer_use_weak");
    public static final Supplier<SoundEvent> BLOOM_HAMMER_USE_FINISHED = registerSoundEvent("bloom_hammer_use_finished");


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(IronBloomery.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
    }


    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}


