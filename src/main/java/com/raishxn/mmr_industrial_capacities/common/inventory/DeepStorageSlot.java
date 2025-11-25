package com.raishxn.mmr_industrial_capacities.common.inventory;

import es.degrassi.mmreborn.common.util.IOInventory;
import es.degrassi.mmreborn.common.util.ItemSlot;
import net.minecraft.world.item.ItemStack;
import java.util.function.Predicate;

public class DeepStorageSlot extends ItemSlot {

    private long deepCount = 0;
    private int maxCapacity; // <--- Removido 'final'

    public DeepStorageSlot(int slot, IOInventory manager, int capacity, int maxInput, int maxOutput, Predicate<ItemStack> filter) {
        super(slot, manager, capacity, maxInput, maxOutput, filter);
        this.maxCapacity = capacity;
    }

    // NOVO MÉTODO: Permite corrigir a capacidade após a inicialização
    public void setMaxCapacity(int capacity) {
        this.maxCapacity = capacity;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack visualStack = super.getItemStack();
        if (!visualStack.isEmpty()) {
            int visualCount = (int) Math.min(visualStack.getMaxStackSize(), deepCount);
            visualStack.setCount(visualCount);
        }
        return visualStack;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !isItemValid(0, stack)) {
            return stack;
        }

        ItemStack current = super.getItemStack();
        if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack)) {
            return stack;
        }

        // Usa maxCapacity atualizado
        long space = maxCapacity - deepCount;
        int toInsert = (int) Math.min(stack.getCount(), space);

        if (toInsert <= 0) return stack;

        if (!simulate) {
            if (current.isEmpty()) {
                super.setItemStack(stack.copyWithCount(1));
            }
            deepCount += toInsert;
            this.getManager().setChanged();
        }

        return stack.copyWithCount(stack.getCount() - toInsert);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (deepCount <= 0) {
            super.setItemStack(ItemStack.EMPTY);
            return ItemStack.EMPTY;
        }

        ItemStack current = super.getItemStack();
        int toExtract = (int) Math.min(amount, Math.min(current.getMaxStackSize(), deepCount));

        if (toExtract <= 0) return ItemStack.EMPTY;

        ItemStack extracted = current.copy();
        extracted.setCount(toExtract);

        if (!simulate) {
            deepCount -= toExtract;
            if (deepCount <= 0) {
                super.setItemStack(ItemStack.EMPTY);
            } else {
                this.getManager().setChanged();
            }
        }

        return extracted;
    }
}