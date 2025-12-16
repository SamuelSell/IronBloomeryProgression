package com.goatchez.iron_bloomery;

import com.goatchez.iron_bloomery.blocks.bloomery.BloomeryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = IronBloomery.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuTypes.BLOOMERY_MENU.get(), BloomeryScreen::new);
    }
}
