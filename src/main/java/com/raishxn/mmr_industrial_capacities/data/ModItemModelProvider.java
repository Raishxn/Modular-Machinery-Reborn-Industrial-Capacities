package com.raishxn.mmr_industrial_capacities.data;

import com.raishxn.mmr_industrial_capacities.MMRIndustrialCapacities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MMRIndustrialCapacities.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Gera item models para todos os blocos registrados
        registerBlockItem(MMRIndustrialCapacities.ITEM_1M.block());
        registerBlockItem(MMRIndustrialCapacities.ITEM_4M.block());
        registerBlockItem(MMRIndustrialCapacities.ITEM_16M.block());
        registerBlockItem(MMRIndustrialCapacities.ITEM_64M.block());
        registerBlockItem(MMRIndustrialCapacities.ITEM_128M.block());
        registerBlockItem(MMRIndustrialCapacities.ITEM_256M.block());

        registerBlockItem(MMRIndustrialCapacities.FLUID_1M.block());
        registerBlockItem(MMRIndustrialCapacities.FLUID_4M.block());
        registerBlockItem(MMRIndustrialCapacities.FLUID_16M.block());
        registerBlockItem(MMRIndustrialCapacities.FLUID_64M.block());
        registerBlockItem(MMRIndustrialCapacities.FLUID_128M.block());
        registerBlockItem(MMRIndustrialCapacities.FLUID_256M.block());
    }

    private void registerBlockItem(DeferredHolder<Block, ? extends Block> block) {
        String path = block.getId().getPath();
        // O Item Model é apenas "parent" do Block Model que criamos antes
        withExistingParent(path, modLoc("block/" + path));
    }
}