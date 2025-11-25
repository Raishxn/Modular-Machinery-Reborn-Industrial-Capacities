package com.raishxn.mmr_industrial_capacities.data;

import com.raishxn.mmr_industrial_capacities.MMRIndustrialCapacities;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, MMRIndustrialCapacities.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.mmr_industrial_capacities", "MMR Industrial Capacities");

        // Itens
        addBlock(MMRIndustrialCapacities.ITEM_1M.block(), "Deep Item Output (1M)");
        addBlock(MMRIndustrialCapacities.ITEM_4M.block(), "Deep Item Output (4M)");
        addBlock(MMRIndustrialCapacities.ITEM_16M.block(), "Deep Item Output (16M)");
        addBlock(MMRIndustrialCapacities.ITEM_64M.block(), "Deep Item Output (64M)");
        addBlock(MMRIndustrialCapacities.ITEM_128M.block(), "Deep Item Output (128M)");
        addBlock(MMRIndustrialCapacities.ITEM_256M.block(), "Deep Item Output (256M)");

        // Fluidos
        addBlock(MMRIndustrialCapacities.FLUID_1M.block(), "Deep Fluid Output (1M B)");
        addBlock(MMRIndustrialCapacities.FLUID_4M.block(), "Deep Fluid Output (4M B)");
        addBlock(MMRIndustrialCapacities.FLUID_16M.block(), "Deep Fluid Output (16M B)");
        addBlock(MMRIndustrialCapacities.FLUID_64M.block(), "Deep Fluid Output (64M B)");
        addBlock(MMRIndustrialCapacities.FLUID_128M.block(), "Deep Fluid Output (128M B)");
        addBlock(MMRIndustrialCapacities.FLUID_256M.block(), "Deep Fluid Output (256M B)");
    }
}