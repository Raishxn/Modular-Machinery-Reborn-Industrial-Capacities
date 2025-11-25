package com.raishxn.mmr_industrial_capacities.common.util;

import es.degrassi.mmreborn.common.util.HybridTank;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class DeepStorageTank extends HybridTank {

    private long deepAmount = 0;
    private final long deepCapacity;

    public DeepStorageTank(long capacityMB) {
        super((int) Math.min(capacityMB, Integer.MAX_VALUE));
        this.deepCapacity = capacityMB;
    }
    public void setDeepAmount(long amount) {
        this.deepAmount = amount;
        updateParentFluid();
    }
    public long getDeepFluidAmount() {
        return deepAmount;
    }
    @Override
    public int getFluidAmount() {
        return (int) Math.min(deepAmount, Integer.MAX_VALUE);
    }
    @Override
    public int getCapacity() {
        return (int) Math.min(deepCapacity, Integer.MAX_VALUE);
    }
    @Override
    public boolean isFluidValid(FluidStack stack) {
        return true;
    }
    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) return 0;

        if (deepAmount == 0) {
            if (action.execute()) {
                this.fluid = resource.copy();
                this.fluid.setAmount(1);
            }
        } else if (!resource.isFluidEqual(this.fluid)) {
            return 0;
        }
        long space = deepCapacity - deepAmount;
        int toFill = (int) Math.min(resource.getAmount(), Math.min(space, Integer.MAX_VALUE));

        if (action.execute()) {
            deepAmount += toFill;
            updateParentFluid();
            onContentsChanged();
        }

        return toFill;
    }

    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if (deepAmount <= 0) return FluidStack.EMPTY;

        int toDrain = (int) Math.min(maxDrain, Math.min(deepAmount, Integer.MAX_VALUE));

        FluidStack drained = this.fluid.copy();
        drained.setAmount(toDrain);

        if (action.execute()) {
            deepAmount -= toDrain;
            if (deepAmount <= 0) {
                setFluid(FluidStack.EMPTY);
                deepAmount = 0;
            } else {
                updateParentFluid();
            }
            onContentsChanged();
        }

        return drained;
    }

    private void updateParentFluid() {
        if (!this.fluid.isEmpty()) {
            this.fluid.setAmount((int) Math.min(deepAmount, Integer.MAX_VALUE));
        }
    }
}