package com.raishxn.mmr_industrial_capacities.client.container;

import com.raishxn.mmr_industrial_capacities.common.registration.MMRIndustrialCapacitiesMenus;
import com.raishxn.mmr_industrial_capacities.common.tile.DeepStorageBusTile;
import es.degrassi.mmreborn.client.container.ContainerBase;
import es.degrassi.mmreborn.client.container.SlotItemComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.concurrent.atomic.AtomicInteger;

public class DeepStorageContainer extends ContainerBase<DeepStorageBusTile> {

    public DeepStorageContainer(int id, Inventory playerInv, DeepStorageBusTile entity) {
        super(entity, playerInv.player, MMRIndustrialCapacitiesMenus.DEEP_STORAGE_MENU.get(), id);
    }

    public DeepStorageContainer(int id, Inventory playerInv, FriendlyByteBuf buffer) {
        this(id, playerInv, (DeepStorageBusTile) playerInv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    @Override
    public void init() {
        AtomicInteger slotIdx = new AtomicInteger(this.getFirstComponentSlotIndex());
        addDeepStorageSlots(slotIdx);
        addPlayerSlots(slotIdx);
    }

    private void addDeepStorageSlots(AtomicInteger atomicInteger) {
        int xOffset = 8;
        int yOffset = 18;
        int cols = 9;

        // CORREÇÃO FATAL: Reduzido para 6 linhas visíveis (54 slots)
        // Isso permite que o inventário do jogador caiba na textura de 256px
        int rows = 6;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Pega o slot real. Nota: O inventário interno tem 81+, mas mostramos 54.
                int index = row * cols + col;

                if (index < getEntity().getInventory().getInventory().size()) {
                    addSyncedSlot(new SlotItemComponent(
                            getEntity().getInventory().getInventory().get(index),
                            atomicInteger.getAndIncrement(),
                            xOffset + col * 18,
                            yOffset + row * 18
                    ));
                }
            }
        }
    }

    @Override
    protected void addPlayerSlots(AtomicInteger slotIndex) {
        // CORREÇÃO: Posição recalculada para alinhar com 6 linhas de slots
        // 18 (topo) + (6 * 18 slots) + 14 (margem) = 140
        int inventoryYOffset = 140;

        // Inventário principal
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSyncedSlot(new Slot(player.getInventory(), slotIndex.getAndIncrement(), 8 + col * 18, inventoryYOffset + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            addSyncedSlot(new Slot(player.getInventory(), slotIndex.getAndIncrement(), 8 + col * 18, inventoryYOffset + 58));
        }
    }
}