package com.raishxn.mmr_industrial_capacities.client.container;

import com.raishxn.mmr_industrial_capacities.common.registration.MMRIndustrialCapacitiesMenus;
import com.raishxn.mmr_industrial_capacities.common.tile.DeepStorageBusTile;
import es.degrassi.mmreborn.client.container.ContainerBase;
import es.degrassi.mmreborn.client.container.SlotItemComponent;
import es.degrassi.mmreborn.common.data.MMRConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DeepStorageContainer extends ContainerBase<DeepStorageBusTile> {

    // Construtor do Servidor
    public DeepStorageContainer(int id, Inventory playerInv, DeepStorageBusTile entity) {
        super(entity, playerInv.player, MMRIndustrialCapacitiesMenus.DEEP_STORAGE_MENU.get(), id);
    }

    // Construtor do Cliente (Packet)
    public DeepStorageContainer(int id, Inventory playerInv, FriendlyByteBuf buffer) {
        this(id, playerInv, (DeepStorageBusTile) playerInv.player.level().getBlockEntity(buffer.readBlockPos()));
    }

    @Override
    public void init() {
        // Não chamamos super.init() porque ele usa a lógica padrão que queremos evitar.
        // Definimos manualmente os slots.
        AtomicInteger slotIdx = new AtomicInteger(this.getFirstComponentSlotIndex());
        addDeepStorageSlots(slotIdx);
        addPlayerSlots(slotIdx);
    }

    private void addDeepStorageSlots(AtomicInteger atomicInteger) {
        int xOffset = 8; // Posição X padrão
        int yOffset = 18; // Posição Y padrão (topo)
        int cols = 9;
        int rows = 9; // 81 slots / 9 colunas = 9 linhas

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Pega o slot real do inventário da Tile Entity
                int index = row * cols + col;
                // Verifica se o índice existe para evitar crash
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
        // Precisamos empurrar o inventário do jogador para baixo, pois o grid 9x9 é alto
        // 9 linhas * 18px = 162px de altura para o container
        int inventoryYOffset = 18 + (9 * 18) + 14; // Margem extra

        // Inventário principal do jogador
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