package com.goatchez.iron_bloomery;

import com.goatchez.iron_bloomery.blocks.CustomBlockDefinitions;
import com.goatchez.iron_bloomery.items.CustomItemDefinitions;
import com.goatchez.iron_bloomery.sounds.CustomSounds;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IronBloomery.MOD_ID)
public class IronBloomery {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "iron_bloomery";
    // Set Language Code
    public static final String LANG = "en_us";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public IronBloomery(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for mod loading
//        modEventBus.addListener(this::commonSetup);

        CustomItemDefinitions.register(modEventBus);
        CustomBlockDefinitions.register(modEventBus);
        CreativeModTabs.register(modEventBus);
        CustomSounds.register(modEventBus);
        MenuTypes.register(modEventBus);
    }

//    private void commonSetup(FMLCommonSetupEvent event) {
//
//    }
}
