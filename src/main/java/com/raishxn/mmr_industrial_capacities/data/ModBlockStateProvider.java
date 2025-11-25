package com.raishxn.mmr_industrial_capacities.data;

import com.raishxn.mmr_industrial_capacities.MMRIndustrialCapacities;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MMRIndustrialCapacities.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // --- ITENS (Deep Storage) ---
        // O segundo parâmetro "deep_storage_bus_1m" será usado para o nome do arquivo JSON
        // E, com a mudança abaixo, também para buscar a textura "overlay_deep_storage_bus_1m.png"
        registerTieredBlock(MMRIndustrialCapacities.ITEM_1M.block(), "deep_storage_bus_1m");
        registerTieredBlock(MMRIndustrialCapacities.ITEM_4M.block(), "deep_storage_bus_4m");
        registerTieredBlock(MMRIndustrialCapacities.ITEM_16M.block(), "deep_storage_bus_16m");
        registerTieredBlock(MMRIndustrialCapacities.ITEM_64M.block(), "deep_storage_bus_64m");
        registerTieredBlock(MMRIndustrialCapacities.ITEM_128M.block(), "deep_storage_bus_128m");
        registerTieredBlock(MMRIndustrialCapacities.ITEM_256M.block(), "deep_storage_bus_256m");

        // --- FLUIDOS (Deep Fluid) ---
        registerTieredBlock(MMRIndustrialCapacities.FLUID_1M.block(), "deep_fluid_hatch_1m");
        registerTieredBlock(MMRIndustrialCapacities.FLUID_4M.block(), "deep_fluid_hatch_4m");
        registerTieredBlock(MMRIndustrialCapacities.FLUID_16M.block(), "deep_fluid_hatch_16m");
        registerTieredBlock(MMRIndustrialCapacities.FLUID_64M.block(), "deep_fluid_hatch_64m");
        registerTieredBlock(MMRIndustrialCapacities.FLUID_128M.block(), "deep_fluid_hatch_128m");
        registerTieredBlock(MMRIndustrialCapacities.FLUID_256M.block(), "deep_fluid_hatch_256m");
    }

    // Método auxiliar simplificado
    private void registerTieredBlock(DeferredHolder<Block, ? extends Block> blockRegistry, String name) {
        // Define o caminho da textura (overlay)
        String overlayTexturePath = MMRIndustrialCapacities.MODID + ":block/overlay_" + name;

        // Define o local do modelo pai (do mod original)
        ResourceLocation parentLocation = ResourceLocation.parse("modular_machinery_reborn:block/blockmodel_overlay");

        // Define as texturas base
        ResourceLocation baseTexture = ResourceLocation.parse("modular_machinery_reborn:block/casing_plain");
        ResourceLocation overlayTexture = ResourceLocation.parse(overlayTexturePath);

        // CORREÇÃO AQUI:
        // Usamos 'getBuilder' + 'UncheckedModelFile' para evitar o erro de "model does not exist"
        ModelFile model = models().getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile(parentLocation))
                .texture("base", baseTexture)
                .texture("overlay", overlayTexture);

        // Gera o Blockstate simples
        simpleBlock(blockRegistry.get(), model);
    }
}