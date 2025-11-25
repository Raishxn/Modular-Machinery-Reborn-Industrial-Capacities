package com.raishxn.mmr_industrial_capacities.common.block;

import com.raishxn.mmr_industrial_capacities.common.tile.DeepStorageBusTile;
import com.raishxn.mmr_industrial_capacities.client.container.DeepStorageContainer;
import es.degrassi.mmreborn.common.block.BlockOutputBus;
import es.degrassi.mmreborn.common.block.prop.ItemBusSize;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DeepStorageBlock extends BlockOutputBus {

    private final int capacity;
    private final Supplier<BlockEntityType<DeepStorageBusTile>> typeSupplier;

    public DeepStorageBlock(ItemBusSize size, int capacity, Supplier<BlockEntityType<DeepStorageBusTile>> typeSupplier) {
        super(size);
        this.capacity = capacity;
        this.typeSupplier = typeSupplier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        // Aqui chamamos o construtor da NOSSA Tile Entity
        return new DeepStorageBusTile(typeSupplier.get(), blockPos, blockState, capacity);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity te = level.getBlockEntity(pos);
        if(te instanceof DeepStorageBusTile entity) {
            if (player instanceof ServerPlayer serverPlayer) {
                // CORREÇÃO: Abre o Menu customizado em vez do ItemBusContainer
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        // Use uma chave de tradução ou nome fixo
                        return Component.translatable("block.mmr_industrial_capacities.deep_storage_bus");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                        return new DeepStorageContainer(id, inv, entity);
                    }
                }, buf -> buf.writeBlockPos(pos));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}