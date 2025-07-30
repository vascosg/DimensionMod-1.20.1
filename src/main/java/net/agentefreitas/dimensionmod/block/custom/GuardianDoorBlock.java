package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Thread.sleep;

public class GuardianDoorBlock extends Block implements EntityBlock {

    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public GuardianDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GuardianDoorBlockEntity(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (level0, pos0, state0, blockEntity) -> {
            if (blockEntity instanceof GuardianDoorBlockEntity guardianEntity) {
                guardianEntity.tick(level0, pos0, state0, (GuardianDoorBlockEntity) blockEntity);
            }
        };
    }


    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && state.hasProperty(OPEN) && state.getValue(OPEN)) {
            if (player instanceof ServerPlayer serverPlayer) {
                ResourceKey<Level> destination = ResourceKey.create(Registries.DIMENSION,
                        new ResourceLocation("dimensionmod", "aqua_dimension"));
                ServerLevel targetWorld = serverPlayer.server.getLevel(destination);
                if (targetWorld != null) {
                    serverPlayer.teleportTo(targetWorld, 5.5, 32.0, 13.5, serverPlayer.getYRot(), serverPlayer.getXRot());


                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }




}
