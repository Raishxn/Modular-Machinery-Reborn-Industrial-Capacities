package com.raishxn.mmr_industrial_capacities.common.block;

import com.raishxn.mmr_industrial_capacities.common.tile.DeepFluidHatchTile;
import es.degrassi.mmreborn.client.container.FluidHatchContainer;
import es.degrassi.mmreborn.common.block.BlockFluidOutputHatch;
import es.degrassi.mmreborn.common.block.prop.FluidHatchSize;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DeepFluidHatchBlock extends BlockFluidOutputHatch {

    private final long capacity;
    private final Supplier<BlockEntityType<DeepFluidHatchTile>> typeSupplier;

    public DeepFluidHatchBlock(long capacity, Supplier<BlockEntityType<DeepFluidHatchTile>> typeSupplier) {
        super(FluidHatchSize.TINY); // Visual base
        this.capacity = capacity;
        this.typeSupplier = typeSupplier;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DeepFluidHatchTile(typeSupplier.get(), pos, state, capacity);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity te = level.getBlockEntity(pos);
        if(te instanceof DeepFluidHatchTile entity) {
            if (player instanceof ServerPlayer serverPlayer)
                FluidHatchContainer.open(serverPlayer, entity);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}