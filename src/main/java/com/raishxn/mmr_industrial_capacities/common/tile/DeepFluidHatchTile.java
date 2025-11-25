package com.raishxn.mmr_industrial_capacities.common.tile; // Ajuste o pacote

import com.raishxn.mmr_industrial_capacities.common.util.DeepStorageTank;
import es.degrassi.mmreborn.common.block.prop.FluidHatchSize;
import es.degrassi.mmreborn.common.entity.base.FluidTankEntity;
import es.degrassi.mmreborn.common.machine.IOType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DeepFluidHatchTile extends FluidTankEntity {

    private final long maxCapacity;

    public DeepFluidHatchTile(BlockEntityType<?> type, BlockPos pos, BlockState state, long capacityMB) {
        // Passamos TINY apenas para satisfazer o construtor e texturas
        super(type, pos, state, FluidHatchSize.TINY, IOType.values()[0]); // IOType será sobrescrito pelo NBT ou lógica de bloco

        this.maxCapacity = capacityMB;

        // AQUI ESTÁ O PULO DO GATO:
        // Substituímos o tanque criado pelo super() pelo nosso tanque gigante
        this.setTank(new DeepStorageTank(this.maxCapacity));

        // Recolocamos o listener para que o Multiblock saiba quando o fluido muda
        this.getTank().setListener(() -> {
            if (getController() != null)
                getController().getProcessor().setMachineInventoryChanged();
        });
    }

    // Precisamos salvar/carregar a quantidade DEEP, senão ao fechar o mundo volta para o limite de int
    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        if (this.getTank() instanceof DeepStorageTank deepTank) {
            compound.putLong("DeepAmount", deepTank.getDeepFluidAmount());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        // O super.loadAdditional recria o tanque baseado no Size salvo no NBT.
        // Precisamos forçar nosso tanque de volta.

        DeepStorageTank customTank = new DeepStorageTank(this.maxCapacity);

        // Lê o tanque padrão (tipo de fluido)
        if (compound.contains("tank")) {
            customTank.readFromNBT(provider, compound.getCompound("tank"));
        }

        // Lê a quantidade gigante e injeta
        if (compound.contains("DeepAmount")) {
            long amount = compound.getLong("DeepAmount");
            // Precisamos usar reflection ou lógica interna para setar o deepAmount
            // Ou simplesmente encher o tanque "falsamente"
            if (!customTank.isEmpty()) {
                // Esvazia e enche com a quantidade certa simulada via fill interno se possível
                // Mas como deepAmount é privado na classe acima, adicione um setter lá:
                // public void setDeepAmount(long amt) { this.deepAmount = amt; updateParentFluid(); }
                customTank.setDeepAmount(amount);
            }
        }

        this.setTank(customTank);

        // Listener novamente
        this.getTank().setListener(() -> {
            if (getController() != null)
                getController().getProcessor().setMachineInventoryChanged();
        });
    }
}