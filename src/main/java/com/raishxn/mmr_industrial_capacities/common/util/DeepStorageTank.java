package com.raishxn.mmr_industrial_capacities.common.util;

import es.degrassi.mmreborn.common.util.HybridTank;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class DeepStorageTank extends HybridTank {

    private long deepAmount = 0;
    private final long deepCapacity;

    public DeepStorageTank(long capacityMB) {
        super(Integer.MAX_VALUE); // Capacidade visual "falsa" segura para evitar crash
        this.deepCapacity = capacityMB;
    }

    /**
     * Define a quantidade real de fluido (usado ao carregar NBT).
     * @param amount Quantidade em mB (pode ser maior que Integer.MAX_VALUE)
     */
    public void setDeepAmount(long amount) {
        this.deepAmount = amount;
        updateParentFluid(); // Atualiza o visual imediatamente
    }

    // Retorna a quantidade real para seus displays/waila/jade/salvar NBT
    public long getDeepFluidAmount() {
        return deepAmount;
    }

    @Override
    public int getFluidAmount() {
        // Retorna o visual (clampado em MAX_INT para interfaces normais não bugarem)
        return (int) Math.min(deepAmount, Integer.MAX_VALUE);
    }

    @Override
    public int getCapacity() {
        // Diz ao jogo que cabe muito, mas dentro do limite do Java Integer
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) return 0;

        // Se o tanque está vazio logicamente, definimos o tipo do fluido
        if (deepAmount == 0) {
            if (action.execute()) {
                this.fluid = resource.copy(); // Define o tipo do fluido na classe pai
                this.fluid.setAmount(1); // Quantidade dummy para não ficar empty/null
            }
        } else if (!resource.isFluidEqual(this.fluid)) {
            return 0; // Fluido diferente, rejeita
        }

        long space = deepCapacity - deepAmount;
        // O quanto podemos encher, limitado pelo que o chamador enviou (int) e espaço restante
        int toFill = (int) Math.min(resource.getAmount(), Math.min(space, Integer.MAX_VALUE));

        if (action.execute()) {
            deepAmount += toFill;
            updateParentFluid(); // Sincroniza visual
            onContentsChanged();
        }

        return toFill;
    }

    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if (deepAmount <= 0) return FluidStack.EMPTY;

        // Drena o minimo entre: o que pediram, o que tem, e o limite do Integer
        int toDrain = (int) Math.min(maxDrain, Math.min(deepAmount, Integer.MAX_VALUE));

        FluidStack drained = this.fluid.copy();
        drained.setAmount(toDrain);

        if (action.execute()) {
            deepAmount -= toDrain;
            if (deepAmount <= 0) {
                setFluid(FluidStack.EMPTY); // Esvazia totalmente a referência visual
                deepAmount = 0;
            } else {
                updateParentFluid();
            }
            onContentsChanged();
        }

        return drained;
    }

    // Sincroniza a variável 'fluid' da classe pai (HybridTank) com o deepAmount
    // Isso garante que o renderizador do MMR veja que tem fluido, mas sem estourar o limite de int
    private void updateParentFluid() {
        if (!this.fluid.isEmpty()) {
            this.fluid.setAmount((int) Math.min(deepAmount, Integer.MAX_VALUE));
        }
    }
}