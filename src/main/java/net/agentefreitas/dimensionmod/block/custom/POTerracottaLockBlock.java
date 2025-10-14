package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class POTerracottaLockBlock extends HorizontalDirectionalBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public POTerracottaLockBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Faz o bloco apontar para o jogador (como uma fornalha)
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {


        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.is(ModItems.SPACE_STAFF.get())) {
            if (!world.isClientSide) {

                Direction facing = state.getValue(FACING);

                // Determina o "vetor lateral" com base na direção do bloco
                Direction left;
                Direction right;

                switch (facing) {
                    case NORTH -> {
                        left = Direction.WEST;
                        right = Direction.EAST;
                    }
                    case SOUTH -> {
                        left = Direction.EAST;
                        right = Direction.WEST;
                    }
                    case WEST -> {
                        left = Direction.SOUTH;
                        right = Direction.NORTH;
                    }
                    case EAST -> {
                        left = Direction.NORTH;
                        right = Direction.SOUTH;
                    }
                    default -> {
                        left = Direction.WEST;
                        right = Direction.EAST;
                    }
                }

                Block newBlock = ModBlocks.CUSTOM_PORTAL_BLOCK.get();

                BlockState newState = newBlock.defaultBlockState();
                if (newState.hasProperty(HorizontalDirectionalBlock.FACING)) {

                    Direction playerFacing = player.getDirection().getOpposite();
                    newState = newState.setValue(HorizontalDirectionalBlock.FACING, playerFacing);
                }

                // Para cada uma das 4 camadas acima
                for (int i = 1; i <= 4; i++) {
                    BlockPos abovePos = pos.above(i);

                    // centro
                    world.setBlock(abovePos, newState, 3);

                    // esquerda
                    world.setBlock(abovePos.relative(left), newState, 3);

                    // direita
                    world.setBlock(abovePos.relative(right), newState, 3);
                }

                world.playSound(
                        null,
                        pos,
                        SoundEvents.TOTEM_USE,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

}