package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.sql.Array;

public class ArrayCenterBlock extends Block implements EntityBlock {


    public ArrayCenterBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArrayCenterBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    // Adiciona o Ticker para que a lógica de colisão (se adicionares) funcione
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        // Só rodamos a lógica no Servidor (isClientSide == false)
        return pLevel.isClientSide ? null : (level, pos, state, blockEntity) -> {
            if (blockEntity instanceof ArrayCenterBlockEntity be) {
                be.tick(level, pos, state, be);
            }
        };
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ArrayCenterBlockEntity centerBE) {
                centerBE.clearGlows();
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ArrayCenterBlockEntity centerBE) {

                centerBE.choseRandomGlowBlockPos();
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) { // Só executa no servidor
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ArrayCenterBlockEntity arrayCenter) {
                arrayCenter.increaseRadius();
                arrayCenter.clearGlows();
                arrayCenter.choseRandomGlowBlockPos();

                return InteractionResult.SUCCESS;
            }
        }
                return InteractionResult.sidedSuccess(level.isClientSide);
    }

}
