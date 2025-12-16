package com.goatchez.iron_bloomery;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronBloomery.MOD_ID);

    public static final Supplier<CreativeModeTab> IRON_BLOOMERY_MOD = CREATIVE_MODE_TAB.register(
            "iron_bloomery_mod",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(CustomBlockDefinitions.BLOOMERY_CONTROLLER.get()))
                    .title(Component.translatable("creative_tab.iron_bloomery_mod"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        output.accept(CustomBlockDefinitions.BLOOMERY_CONTROLLER);
                        output.accept(CustomItemDefinitions.BLOOM_HAMMER);
                        output.accept(CustomItemDefinitions.IRON_BLOOM);
                    }))
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
