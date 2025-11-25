package com.raishxn.mmr_industrial_capacities.common.tile;

import com.raishxn.mmr_industrial_capacities.common.inventory.DeepStorageInventoryBuilder;
import com.raishxn.mmr_industrial_capacities.common.inventory.DeepStorageSlot;
import es.degrassi.mmreborn.common.block.prop.ItemBusSize;
import es.degrassi.mmreborn.common.entity.base.TileItemBus;
import es.degrassi.mmreborn.common.machine.IOType;
import es.degrassi.mmreborn.common.util.IOInventory;
import es.degrassi.mmreborn.common.util.ItemSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DeepStorageBusTile extends TileItemBus {

    private final int capacity;
    private static final int SLOT_COUNT = 81;

    public DeepStorageBusTile(BlockEntityType<?> type, BlockPos pos, BlockState state, int capacity) {
        super(type, pos, state, ItemBusSize.TINY, IOType.OUTPUT);
        this.capacity = capacity;

        // CORREÇÃO: Atualiza a capacidade dos slots que foram criados com valor errado (64)
        // porque 'capacity' era 0 durante o super()
        updateInventoryCapacity();
    }

    private void updateInventoryCapacity() {
        if (this.getInventory() != null) {
            for (ItemSlot slot : this.getInventory().getInventory()) {
                if (slot instanceof DeepStorageSlot deepSlot) {
                    deepSlot.setMaxCapacity(this.capacity);
                }
            }
        }
    }

    @Override
    public IOInventory buildInventory(int slots) {
        // Durante a chamada do super(), this.capacity ainda é 0.
        // Usamos 64 temporariamente, e depois corrigimos no construtor acima.
        int cap = this.capacity > 0 ? this.capacity : 64;

        int[] outSlots = new int[SLOT_COUNT];
        for (int i = 0; i < SLOT_COUNT; i++) outSlots[i] = i;

        return DeepStorageInventoryBuilder.build(cap, new int[0], outSlots);
    }
}