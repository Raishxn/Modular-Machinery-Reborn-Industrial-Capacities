package com.raishxn.mmr_industrial_capacities.common.registration;

import com.raishxn.mmr_industrial_capacities.MMRIndustrialCapacities;
import com.raishxn.mmr_industrial_capacities.client.container.DeepStorageContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MMRIndustrialCapacitiesMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MMRIndustrialCapacities.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<DeepStorageContainer>> DEEP_STORAGE_MENU =
            MENUS.register("deep_storage_container", () -> IMenuTypeExtension.create(DeepStorageContainer::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}