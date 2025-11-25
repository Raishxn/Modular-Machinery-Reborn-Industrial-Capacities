package com.raishxn.mmr_industrial_capacities;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.raishxn.mmr_industrial_capacities.Config;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(MMRIndustrialCapacities.MODID)
public class MMRIndustrialCapacities {
    public static final String MODID = "mmr_industrial_capacities";
    public static final Logger LOGGER = LogUtils.getLogger();
    public MMRIndustrialCapacities(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON,  com.raishxn.mmr_industrial_capacities.Config.SPEC);
    }
    private void commonSetup(FMLCommonSetupEvent event) {
        if ( com.raishxn.mmr_industrial_capacities.Config.LOG_DIRT_BLOCK.getAsBoolean()) {
        }
        LOGGER.info("{}{}",  com.raishxn.mmr_industrial_capacities.Config.MAGIC_NUMBER_INTRODUCTION.get(),  com.raishxn.mmr_industrial_capacities.Config.MAGIC_NUMBER.getAsInt());
        com.raishxn.mmr_industrial_capacities.Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}
