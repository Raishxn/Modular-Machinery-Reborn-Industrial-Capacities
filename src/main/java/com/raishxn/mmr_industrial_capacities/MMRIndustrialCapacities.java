package com.raishxn.mmr_industrial_capacities;

import com.raishxn.mmr_industrial_capacities.client.screen.DeepStorageScreen;
import com.raishxn.mmr_industrial_capacities.common.block.DeepFluidHatchBlock;
import com.raishxn.mmr_industrial_capacities.common.block.DeepStorageBlock;
import com.raishxn.mmr_industrial_capacities.common.registration.MMRIndustrialCapacitiesMenus;
import com.raishxn.mmr_industrial_capacities.common.tile.DeepFluidHatchTile;
import com.raishxn.mmr_industrial_capacities.common.tile.DeepStorageBusTile;
import es.degrassi.mmreborn.common.block.prop.ItemBusSize;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(MMRIndustrialCapacities.MODID)
public class MMRIndustrialCapacities {
    public static final String MODID = "mmr_industrial_capacities";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MODID);

    // --- REGISTRO DE ITENS (1M a 256M) ---
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_1M =
            registerDeepStorage("deep_storage_bus_1m", 1_000_000);
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_4M =
            registerDeepStorage("deep_storage_bus_4m", 4_000_000);
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_16M =
            registerDeepStorage("deep_storage_bus_16m", 16_000_000);
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_64M =
            registerDeepStorage("deep_storage_bus_64m", 64_000_000);
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_128M =
            registerDeepStorage("deep_storage_bus_128m", 128_000_000);
    public static final RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> ITEM_256M =
            registerDeepStorage("deep_storage_bus_256m", 256_000_000);

    // --- REGISTRO DE FLUIDOS (1M a 256M) ---
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_1M =
            registerDeepFluid("deep_fluid_hatch_1m", 1_000_000L * 1000);
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_4M =
            registerDeepFluid("deep_fluid_hatch_4m", 4_000_000L * 1000);
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_16M =
            registerDeepFluid("deep_fluid_hatch_16m", 16_000_000L * 1000);
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_64M =
            registerDeepFluid("deep_fluid_hatch_64m", 64_000_000L * 1000);
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_128M =
            registerDeepFluid("deep_fluid_hatch_128m", 128_000_000L * 1000);
    public static final RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> FLUID_256M =
            registerDeepFluid("deep_fluid_hatch_256m", 256_000_000L * 1000);

    // --- ABA CRIATIVA ---
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("mmr_industrial_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(ITEM_256M.block().get()))
            .displayItems((parameters, output) -> {
                output.accept(ITEM_1M.block().get());
                output.accept(ITEM_4M.block().get());
                output.accept(ITEM_16M.block().get());
                output.accept(ITEM_64M.block().get());
                output.accept(ITEM_128M.block().get());
                output.accept(ITEM_256M.block().get());

                output.accept(FLUID_1M.block().get());
                output.accept(FLUID_4M.block().get());
                output.accept(FLUID_16M.block().get());
                output.accept(FLUID_64M.block().get());
                output.accept(FLUID_128M.block().get());
                output.accept(FLUID_256M.block().get());
            }).build());

    public MMRIndustrialCapacities(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        MMRIndustrialCapacitiesMenus.register(modEventBus);
    }


    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MMRIndustrialCapacitiesMenus.DEEP_STORAGE_MENU.get(), DeepStorageScreen::new);
    }

    // Helpers
    private static RegistryObjectGroup<DeepStorageBlock, BlockEntityType<DeepStorageBusTile>> registerDeepStorage(String name, int capacity) {
        // Hack para referência circular (Lazy loading)
        final DeferredHolder<BlockEntityType<?>, BlockEntityType<DeepStorageBusTile>>[] tileRef = new DeferredHolder[1];

        DeferredHolder<Block, DeepStorageBlock> block = BLOCKS.register(name,
                () -> new DeepStorageBlock(ItemBusSize.TINY, capacity, () -> tileRef[0].get()));

        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        tileRef[0] = BLOCK_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(
                        (pos, state) -> new DeepStorageBusTile(tileRef[0].get(), pos, state, capacity),
                        block.get()
                ).build(null));

        return new RegistryObjectGroup<>(block, tileRef[0]);
    }

    private static RegistryObjectGroup<DeepFluidHatchBlock, BlockEntityType<DeepFluidHatchTile>> registerDeepFluid(String name, long capacity) {
        final DeferredHolder<BlockEntityType<?>, BlockEntityType<DeepFluidHatchTile>>[] tileRef = new DeferredHolder[1];

        DeferredHolder<Block, DeepFluidHatchBlock> block = BLOCKS.register(name,
                () -> new DeepFluidHatchBlock(capacity, () -> tileRef[0].get()));

        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

        tileRef[0] = BLOCK_ENTITIES.register(name,
                () -> BlockEntityType.Builder.of(
                        (pos, state) -> new DeepFluidHatchTile(tileRef[0].get(), pos, state, capacity),
                        block.get()
                ).build(null));

        return new RegistryObjectGroup<>(block, tileRef[0]);
    }

    // CORREÇÃO IMPORTANTE AQUI:
    // Alteramos 'Supplier' para 'DeferredHolder' para preservar a informação de registro (ID)
    // necessária para o DataGen.
    public record RegistryObjectGroup<B extends Block, T extends BlockEntityType<?>>(
            DeferredHolder<Block, B> block,
            DeferredHolder<BlockEntityType<?>, T> tile
    ) {}
}