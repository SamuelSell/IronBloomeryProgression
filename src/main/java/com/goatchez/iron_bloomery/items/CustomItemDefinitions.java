package com.goatchez.iron_bloomery.items;
import com.goatchez.iron_bloomery.IronBloomery;
import com.goatchez.iron_bloomery.items.custom.BloomHammer;
import com.goatchez.iron_bloomery.items.custom.IronBloom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CustomItemDefinitions {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronBloomery.MOD_ID);

    public static final DeferredItem<BloomHammer> BLOOM_HAMMER = ITEMS.registerItem(
            "bloom_hammer",
            properties -> new BloomHammer(properties.durability(50))
    );

    public static final DeferredItem<IronBloom> IRON_BLOOM = ITEMS.registerItem(
            "iron_bloom",
            properties -> new IronBloom(properties.durability(6))
    );

    // Helper Function to generate a DeferredItem<Item> for any ItemModel
    private static DeferredItem<Item> registerItem(String name) {
        return ITEMS.registerSimpleItem(name);
    }


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
