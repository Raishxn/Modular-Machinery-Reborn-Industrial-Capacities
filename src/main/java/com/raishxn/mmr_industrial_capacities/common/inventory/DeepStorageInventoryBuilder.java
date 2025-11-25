package com.raishxn.mmr_industrial_capacities.common.inventory;

import es.degrassi.mmreborn.common.util.IOInventory;
import es.degrassi.mmreborn.common.util.ItemSlot;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DeepStorageInventoryBuilder {

    public static IOInventory build(int capacity, int[] inSlots, int[] outSlots) {
        // Cria um inventário "vazio" base
        IOInventory inventory = new IOInventory(new int[0], new int[0]);

        // Acessa as listas internas (elas têm getters públicos na API do MMR, mas adicionar é tricky)
        // Se as listas 'inputs' e 'outputs' e 'inventory' forem finais ou protegidas,
        // precisamos usar Reflection ou subclasses.

        // Assumindo que podemos estender e manipular:
        return new CustomIOInventory(capacity, inSlots, outSlots);
    }

    // Classe interna estendida para manipular a geração
    private static class CustomIOInventory extends IOInventory {
        public CustomIOInventory(int capacity, int[] inSlots, int[] outSlots) {
            super(new int[0], new int[0]); // Inicia vazio

            // Adiciona manualmente nossos slots Deep Storage
            Predicate<ItemStack> filter = stack -> true;

            for (int slot : inSlots) {
                // Input Slot Deep Storage
                DeepStorageSlot itemSlot = new DeepStorageSlot(slot, this, capacity, 64, 0, filter);
                this.getInputs().add(itemSlot);
                this.getInventory().add(itemSlot);
            }

            for (int slot : outSlots) {
                // Output Slot Deep Storage
                DeepStorageSlot itemSlot = new DeepStorageSlot(slot, this, capacity, 0, 64, filter);
                this.getOutputs().add(itemSlot);
                this.getInventory().add(itemSlot);
            }
        }
    }
}