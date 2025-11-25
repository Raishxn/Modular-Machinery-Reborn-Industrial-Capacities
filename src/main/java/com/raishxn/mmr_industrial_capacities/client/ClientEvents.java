package com.raishxn.mmr_industrial_capacities.client;

import com.raishxn.mmr_industrial_capacities.MMRIndustrialCapacities;
import com.raishxn.mmr_industrial_capacities.client.screen.DeepStorageScreen;
import com.raishxn.mmr_industrial_capacities.common.registration.MMRIndustrialCapacitiesMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = MMRIndustrialCapacities.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        // Conecta o Container lógico à Tela visual
        event.register(MMRIndustrialCapacitiesMenus.DEEP_STORAGE_MENU.get(), DeepStorageScreen::new);
    }
}