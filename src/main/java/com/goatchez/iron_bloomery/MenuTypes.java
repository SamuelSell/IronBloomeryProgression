package com.goatchez.iron_bloomery;

import com.goatchez.iron_bloomery.blocks.bloomery.BloomeryMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, IronBloomery.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<BloomeryMenu>> BLOOMERY_MENU = MENUS.register(
            "bloomery_menu",
            () -> IMenuTypeExtension.create(BloomeryMenu::new)
    );

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
