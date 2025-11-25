package com.raishxn.mmr_industrial_capacities.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class LongFluidTank implements IFluidHandler {

    private long storedAmount = 0;
    private final long capacity;
    @NotNull
    private FluidStack fluidType = FluidStack.EMPTY;
    private final Runnable changeListener;

    public LongFluidTank(long capacity, Runnable changeListener) {
        this.capacity = capacity;
        this.changeListener = changeListener;
    }

    public long getLongAmount() {
        return storedAmount;
    }

    public void setLongAmount(long amount) {
        this.storedAmount = amount;
        onContentsChanged();
    }

    public void setFluid(FluidStack stack) {
        this.fluidType = stack.copy();
        if (stack.isEmpty()) this.storedAmount = 0;
        onContentsChanged();
    }

    @Override
    public int getTanks() { return 1; }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        int displayAmount = (int) Math.min(storedAmount, Integer.MAX_VALUE);
        if (fluidType.isEmpty()) return FluidStack.EMPTY;
        return fluidType.copyWithAmount(displayAmount);
    }

    @Override
    public int getTankCapacity(int tank) {
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        // CORREÇÃO: Chamada estática FluidStack.matches(a, b)
        return fluidType.isEmpty() || FluidStack.matches(fluidType, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        // CORREÇÃO: Chamada estática FluidStack.matches(a, b)
        if (resource.isEmpty() || (!fluidType.isEmpty() && !FluidStack.matches(fluidType, resource))) {
            return 0;
        }
        long spaceRemaining = capacity - storedAmount;
        int toFill = (int) Math.min(resource.getAmount(), Math.min(spaceRemaining, Integer.MAX_VALUE));

        if (action.execute()) {
            if (fluidType.isEmpty()) fluidType = resource.copyWithAmount(1);
            storedAmount += toFill;
            onContentsChanged();
        }
        return toFill;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
        // CORREÇÃO: Chamada estática FluidStack.matches(a, b)
        if (resource.isEmpty() || fluidType.isEmpty() || !FluidStack.matches(fluidType, resource)) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        if (storedAmount <= 0 || fluidType.isEmpty()) return FluidStack.EMPTY;
        int toDrain = (int) Math.min(maxDrain, Math.min(storedAmount, Integer.MAX_VALUE));

        if (action.execute()) {
            storedAmount -= toDrain;
            if (storedAmount <= 0) fluidType = FluidStack.EMPTY;
            onContentsChanged();
        }
        return fluidType.copyWithAmount(toDrain);
    }

    public CompoundTag writeToNBT(CompoundTag nbt, HolderLookup.Provider registry) {
        nbt.putLong("DeepAmount", storedAmount);
        if (!fluidType.isEmpty()) fluidType.save(registry, nbt);
        return nbt;
    }

    public void readFromNBT(CompoundTag nbt, HolderLookup.Provider registry) {
        this.storedAmount = nbt.getLong("DeepAmount");
        this.fluidType = FluidStack.parseOptional(registry, nbt);
    }

    private void onContentsChanged() {
        if(changeListener != null) changeListener.run();
    }
}